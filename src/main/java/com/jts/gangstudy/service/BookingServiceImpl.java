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
		return mapper.selectByDate(date);
	}

	// 날짜+시간으로 검색
	@Override
	public List<Booking> searchByDateTime(LocalDateTime dateTime) {
		String formatDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		return mapper.selectByDateTime(formatDateTime);
	}

	// 날짜로 첫 예약 검색
	@Override
	public Booking searchByDateFist(String date) {
		List<Booking> books = mapper.selectByDateFirst(date);
		if(books.isEmpty()) {
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
		return books;
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
	public List<String> getStartTimes(String date, List<Booking> books) {
	    List<String> timeList = null;
	    
	    // 선택가능한 시간 생성
	    if(LocalDate.now().isEqual(LocalDate.parse(date))) {
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
		for(Booking book : books) {
			removeTimes(timeList, date, book);
		}
		return timeList;
	}

	// 일정구간 예약을 지우는 함수
	public void removeTimes(List<String> times, String date, Booking book) {
		String startDate = book.getciDate().toString();
		String endDate = book.getcoDate().toString();
		// 예약의 시작일-시작시 로 된 localdatetime 생성
		LocalDateTime startDateTime = book.getciDateTime();
		LocalDateTime endDateTime = book.getcoDateTime();
		
		if(!date.equals(startDate)) {		// date != book.startdate 인 경우 -> startdatetime을 date+1일 0시 0분 으로 설정
			startDateTime = startDateTime.plusDays(1);
			startDateTime = startDateTime.with(LocalTime.MIN);
		} else if(!date.equals(endDate)) {	// date != book.enddate 인 경우   -> enddatetime을   date+1일 0시 0분 으로 설정
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
	public List<String> getEndTimes(List<Booking> books, String startDate, String startTime, String endDate) {
		// 시작일시
		LocalDateTime from_dt = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));
		// 종료일 + 00:00
		LocalDateTime to_dt = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MIN);
		// 밤샘예약
		List<Booking> overNight = mapper.viewOvernight(startDate);
		
		if(from_dt.toLocalDate().isEqual(to_dt.toLocalDate())) {
			if(from_dt.isBefore( to_dt.plusDays(1).minusMinutes(minimumSize) )) {
				// 시작시간+2시간 에서 내일 00시 까지 확인
				System.out.println("시작시간+2시간 에서 내일 00시 까지 확인");
				from_dt = from_dt.plusMinutes(minimumSize);
				to_dt = to_dt.plusDays(1);
			} else {
				System.out.println("22시 이후 선택 = 선택할 수 있는 시간 없음");
				return null;
			}
		} else if(!overNight.isEmpty()) {
			return null;
		} else {
			if(from_dt.isBefore( to_dt.minusMinutes(minimumSize) )) {
				// 내일 00시 에서 모레 00시 까지 확인
				System.out.println("내일 00시 에서 모레 00시 까지 확인");
				from_dt = to_dt;
				to_dt = to_dt.plusDays(1);
			} else {
				// 내일 00시 + 초과시간 에서 모레 00시 까지 확인
				System.out.println("내일 00시 + 초과시간 에서 모레 00시 까지 확인");
				from_dt = from_dt.plusMinutes(minimumSize);
				to_dt = to_dt.plusDays(1);
			}
		}
		
		
		System.out.println(
				"from_dt : " + from_dt +
				", to_dt : " + to_dt
				);

//	    List<String> times = makeTimes(from_dt.getHour(), from_dt.getMinute());
	    List<String> times = new ArrayList<String>();
	    Booking firstBook = searchByDateFist(to_dt.toLocalDate().toString());
	    LocalDateTime firstBook_dt;
	    if(firstBook != null) {
	    	firstBook_dt = firstBook.getcoDateTime();
	    } else {
	    	firstBook_dt = to_dt;
	    }
	    
	    // from_dt의 time이 종료시간 or firstBook의 시작시간 이전인가?
	    // 맞다면 추가
	    // from_dt의 시간을 minuteSize 만큼 증가.
	    for(;	from_dt.isBefore(firstBook_dt) && from_dt.isBefore(to_dt);
	    		from_dt = from_dt.plusMinutes(minuteSize)) {
	    	times.add(from_dt.toLocalTime().toString());
	    }
		
		return times;
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