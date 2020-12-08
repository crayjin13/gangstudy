package com.jts.gangstudy.service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.mapper.BookingMapper;

@Service
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	private BookingMapper mapper;
	
	private final int dateSize = 7;			// 예약 신청이 가능한 기간
	private final int minimumSize = 120;	// 에약 신청 최소 단위
	private final int minuteSize = 30;		// 에약 신청 시간 단위
	private final int amount = 500;			// 예약 신청 시간 당 필요 요금

	/*						*/
	/*		데이터베이스 기능	*/
	/*						*/
	// 예약 추가(sync)
	public synchronized String insertBook(Booking book) {
		int result = searchDuplicate(book);
		if(result == 0) {
			mapper.insertBook(book);
			return "done";
		} else if(result > 1){
			System.err.println("BookingController:insertDB: duplicate booking is exist.");
			return "critical";
		} else {
			return "duplicate";
		}
	}
	
	// 예약 제거
	@Override
	public void removeBook(Booking book) {
		mapper.deleteBook(book.getBook_no());
	}

	// 상태 변경
	@Override
	public void changeState(Booking book, String state) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("book_no", Integer.toString(book.getBook_no()));
		map.put("state", state);
		
		mapper.updateState(map);
	}
	
	// 중복 검색
	public int searchDuplicate(Booking book) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("check_in", book.getCheck_in());
		map.put("check_out", book.getCheck_out());
		return mapper.selectDuplicate(map);
	}

	// 날짜로 검색
	@Override
	public List<Booking> searchByDate(String date) {
		List<Booking> books = mapper.selectByDate(date);
		if(books == null) {
			return new ArrayList<Booking>();
		} else {
			return books;
		}
	}

	// 날짜+시간으로 검색
	@Override
	public List<Booking> searchByDateTime(LocalDateTime dateTime) {
		String formatDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		return mapper.selectByDateTime(formatDateTime);
	}

	// 날짜로 첫 예약 검색
	public Booking searchByDateTimeFist(LocalDateTime dateTime) {
		String formatDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		List<Booking> books = mapper.selectByDateTimeFirst(formatDateTime);
		if(books.size() == 0) {
			return null;
		} else {
			return books.get(0);
		}
	}
	
	// 상태로 검색
	@Override
	public List<Booking> searchByState(String state) {
		return mapper.selectByState(state);
	}

	// 유저의 특정 상태로 검색
	@Override
	public List<Booking> searchByUserState(User user, String state) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_no", user.getUser_no().toString());
		map.put("state", state);
		List<Booking> books = mapper.selectByUserState(map);
		if(books == null) {
			return new ArrayList<Booking>();
		} else {
			return books;
		}
	}

	// 유저의 대기중인 예약
	@Override
	public Booking searchByUserWait(User user) {
		List<Booking> books = searchByUserState(user, "wait");
		Booking book = null;
		if(books.size() > 1) {
			System.err.println("BookingServiceImpl:getUserWaitBooking: booking 'wait' state is least one more.");
		} else if(books.size() == 1) {
			book = books.get(0);
		}
		return book;
	}

	// 예약 번호로 검색
	@Override
	public Booking searchByBookNo(Booking book) {
		return mapper.selectByBookNo(book.getBook_no());
	}

	/*						*/
	/*		페이지 정보 기능	*/
	/*						*/
	// 날짜 목록
	@Override
	public ArrayList<String> makeDates() {
		ArrayList<String> dates = new ArrayList<String>();
		LocalDate localDate = LocalDate.now();
		
		for(int i = 0; i < dateSize; i++) {
			dates.add(localDate.toString());
			localDate = localDate.plusDays(1);
		}
		
		return dates;
	}

	// time 목록 반환
	public List<String> makeTimes(int startHour, int startMin) {
		List<String> times = new ArrayList<>();
		LocalDateTime start_dt = LocalDateTime.of(LocalDate.MIN, LocalTime.of(startHour, startMin));
		LocalDateTime end_dt = LocalDateTime.of(LocalDate.MIN, LocalTime.MIN).plusDays(1);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		for(; start_dt.isBefore(end_dt); start_dt = start_dt.plusMinutes(minuteSize)) {
			times.add(dtf.format(start_dt));
		}
		
		return times;
	}

	// 예약 요금
	@Override
	public int getAmount(Booking book) {
		Duration duration = book.getDuration();
		int slot = (int)duration.toMinutes() / minuteSize;
		// 단위 수 * 단위 금액 * 인원
		return slot * amount * book.getPeople();
	}
	
	// 시간당 요금
	@Override
	public int getAmountPerHour() {
		return (60 / minuteSize) * amount;
	}

	// 사용시간(00시간00분)
	@Override
	public String getTimeInterval(Booking book) {
		Duration duration = book.getDuration();
		int hour = (int) duration.toHours();
		int minute = (int) duration.toMinutes() % 60;
		
		return hour + "시간 " + minute + "분";
	}

	// 시작시간 목록
	@Override
	public List<String> getStartTimes(List<Booking> userBooks, String startDate) {
		List<Booking> datebooks = searchByDate(startDate);
	    List<String> timeList = null;
	    
	    // 요청한 사용자의 예약 제거
	    datebooks.removeAll(userBooks);
	    
	    // 선택가능한 시간 생성
	    if(LocalDate.now().isEqual(LocalDate.parse(startDate))) {
	    	LocalTime localTime = LocalTime.now().plusHours(1);
	    	
		    if(localTime.getHour() == 0) { // 23시 지나갔음.
		    	return null;
		    }
		    localTime = localTime.getMinute() < 30 ? localTime.withMinute(0) : localTime.withMinute(30);
		    
	    	timeList = makeTimes(localTime.getHour(), localTime.getMinute());
	    } else {
	    	timeList = makeTimes(0, 0);
	    }
		
		// 가능한 시간에서 예약된 시간 제거
		for(Booking book : datebooks) {
			removeTimes(timeList, startDate, book);
		}
		return timeList;
	}

	// 일정구간 예약을 지우는 함수
	public void removeTimes(List<String> times, String date, Booking book) {
		// 예약의 시작일시 -2시간
		LocalDateTime startDateTime = book.getciDateTime().minusMinutes(minimumSize).plusMinutes(minuteSize);
		String startDate = startDateTime.toLocalDate().toString();
		// 예약의 종료일시
		LocalDateTime endDateTime = book.getcoDateTime();
		String endDate = endDateTime.toLocalDate().toString();
		
		if(!date.equals(startDate)) {		// date != book.startdate 인 경우 -> startDateTime을 date+1일 0시 0분 으로 설정
			startDateTime = startDateTime.plusDays(1);
			startDateTime = startDateTime.with(LocalTime.MIN);
		} else if(!date.equals(endDate)) {	// date != book.enddate 인 경우   -> endDateTime  을 date+1일 0시 0분 으로 설정
			endDateTime = endDateTime.with(LocalTime.MIN);
		}

		for(;
				!startDateTime.isEqual(endDateTime);
				startDateTime = startDateTime.plusMinutes(minuteSize)) {
			String time = startDateTime.toLocalTime().toString();
			times.remove(time);
		}
	}
	
	// 종료시간 목록
	@Override
	public List<String> getEndTimes(Booking userBook, String startDate, String startTime, String endDate) {
		// 최소 선택가능한 일시
		LocalDateTime min_dt = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));
		// 최대 선택가능한 일시
		LocalDateTime max_dt = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MIN);
		// 최소일시 다음 예약의 시작일시
		LocalDateTime nextBook_dt = getNextBookingCIDT(min_dt, max_dt.plusDays(1), userBook);
		// 밤샘예약
		Boolean allowsOvernight = allowsOvernight(min_dt.toLocalDate().toString(), userBook);
	    min_dt = getMinDateTime(min_dt, max_dt, allowsOvernight);
		max_dt = max_dt.plusDays(1);
		
		if(min_dt == null) {	// 선택가능한 시간이 없음.
			return null;
		}
		
	    List<String> times = new ArrayList<String>();
	    // 다음예약 시작일시 or 최대 선택가능일시 까지 time을 추가
	    for(;
	    		!min_dt.isAfter(nextBook_dt) && min_dt.isBefore(max_dt);
	    		min_dt = min_dt.plusMinutes(minuteSize)) {
	    	times.add(min_dt.toLocalTime().toString());
	    }
		
		return times;
	}
 
	// 최소일시 다음 예약의 시작일시
	public LocalDateTime getNextBookingCIDT(LocalDateTime min_dt, LocalDateTime max_dt, Booking userBook) {
		// min 3일, max 3일	-> 최대선택가능일시 = 4일00시
		// min 3일, max 4일	-> 최대선택가능일시 = 5일00시
		// 고른시간 3일 23:30 원하는 목록 4일 00:00 ~
		
		LocalDateTime nextBook_dt = null;
		Booking firstBook = searchByDateTimeFist(min_dt);
		
		Booking secondBook = null;
		
		if(firstBook != null) {					// 첫 예약이 있는 경우
	    	if(firstBook.equals(userBook)) {	// 첫 예약 = 사용자 예약인 경우
	    		secondBook = searchByDateTimeFist(userBook.getcoDateTime());
	    		if(secondBook != null) {		// 두번째 예약이 있는 경우
	    			// 다음예약 시작일시 = 두번째예약 시작일시
	    			nextBook_dt = secondBook.getciDateTime();
	    		} else {						// 두번째 예약이 없는 경우
	    			// 다음예약 시작일시 = 최대 선택가능한 일시
			    	nextBook_dt = max_dt;
	    		}
	    	} else {							// 첫 예약 != 사용자 예약
		    	// 다음예약 시작일시 = 첫예약 시작일시
	    		nextBook_dt = firstBook.getciDateTime();
	    	}
	    } else {								// 첫 예약이 없는 경우
	    	// 다음예약 시작일시 = 최대 선택가능한 일시
	    	nextBook_dt = max_dt;
	    }
		return nextBook_dt;
	}
	
	// 최소일시 얻기
	public LocalDateTime getMinDateTime(LocalDateTime min_dt, LocalDateTime max_dt, Boolean allowsOvernight) {
		// 시작일과 종료일을 통해 밤샘예약에 따른 최소일시 얻기.
		if(min_dt.toLocalDate().isEqual(max_dt.toLocalDate())) {
			if(min_dt.isBefore( max_dt.plusDays(1).minusMinutes(minimumSize) )) {
				// 최소일시+2시간 ~
				min_dt = min_dt.plusMinutes(minimumSize);
			} else {
				return null;
			}
		} else if(allowsOvernight) {
			if(min_dt.isBefore( max_dt.minusMinutes(minimumSize) )) {
				// 내일 00시 ~
				min_dt = max_dt;
			} else {
				// 내일 00시 + 초과시간 ~
				min_dt = min_dt.plusMinutes(minimumSize);
			}
		} else {
			return null;
		}
		return min_dt;
	}
	
	// 해당 예약을 제외한, 밤샘예약이 있는지 확인
	public Boolean allowsOvernight(String date, Booking book) {
		HashMap<String, String> map = new HashMap<String, String>();
		String book_no;
		map.put("date", date);
		
		if(book != null) {
			book_no = Integer.toString(book.getBook_no());
		} else {
			book_no = "";
		}
		map.put("book_no", book_no);
		List<Booking> books = mapper.selectOvernightWithoutBookno(map);

		if(books.isEmpty()) {
			return true;
		}
		return false;
	}
	
	// 시간 유효성 체크
	@Override
	public boolean allowsBooking(Booking book) {
		LocalDateTime CIDT = book.getciDateTime();
		LocalDateTime CODT = book.getcoDateTime();
		LocalDateTime now = LocalDateTime.now();
		if(now.isBefore(CIDT) && now.plusDays(8).with(LocalTime.MIN).isBefore(CODT)) return false;
		if(CODT.getDayOfYear() - CIDT.getDayOfYear() > 1) return false;
		if(CIDT.isAfter(CODT)) return false;
		return true;
	}


}