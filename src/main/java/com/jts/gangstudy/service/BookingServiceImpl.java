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
		HashMap<String, LocalDateTime> map = new HashMap<String, LocalDateTime>();
		map.put("check_in", book.getCheck_in());
		map.put("check_out", book.getCheck_out());
		return mapper.selectDuplicate(map);
	}

	@Override
	public List<Booking> searchByUser(User user) {
		return mapper.selectByUser(user.getUser_no());
	}

	// 요청한 유저의 예약을 제외하고 존재하는 예약의 목록
	@Override
	public List<Booking> searchAlreadyBooked(Integer book_no, String startDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(book_no == null) { book_no = 0; }
		map.put("book_no", book_no);
		map.put("date", startDate);
		List<Booking> books = mapper.selectAlreadyBooked(map);
		if(books == null) {
			return new ArrayList<Booking>();
		} else {
			return books;
		}
	}
	
	// 시작시간 목록
	@Override
	public List<String> getStartTimes(List<Booking> books, String startDate) {
	    List<String> timeList = null;
	    
	    // 선택가능한 시간 생성
	    if(LocalDate.now().isEqual(LocalDate.parse(startDate))) {
	    	LocalTime localTime = LocalTime.now().plusHours(1);
	    	
		    if(localTime.getHour() == 0) { // 23시 지나갔음.
		    	return null;
		    }
		    localTime = localTime.getMinute() < 30 ? localTime.withMinute(0) : localTime.withMinute(30);
		    
	    	timeList = makeTimes(localTime);
	    } else {
	    	timeList = makeTimes(LocalTime.MIN);
	    }
		
		// 가능한 시간에서 예약된 시간 제거
		for(Booking book : books) {
			removeTimes(timeList, startDate, book);
		}
		return timeList;
	}
	
	// time 목록 반환
	public List<String> makeTimes(LocalTime localTime) {
		List<String> times = new ArrayList<>();
		LocalDateTime start_dt = LocalDateTime.of(LocalDate.MIN, localTime);
		LocalDateTime end_dt = LocalDateTime.of(LocalDate.MIN, LocalTime.MIN).plusDays(1);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		for(; start_dt.isBefore(end_dt); start_dt = start_dt.plusMinutes(minuteSize)) {
			times.add(dtf.format(start_dt));
		}
		
		return times;
	}
	
	// 요청한 유저의 예약을 제외하고 종료가능한 시간 이후 존재하는 첫번째 예약
	@Override
	public Booking searchNextBook(User user, String startDate, String startTime, String endDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer user_no = 0;
		if(user != null) { user_no = user.getUser_no(); }
		map.put("user_no", user_no);
		map.put("startDateTime", LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime).plusMinutes(minimumSize)));
		map.put("endDate", endDate);
		return  mapper.selectNextBook(map);
	}
	
	// 종료시간 목록
	@Override
	public List<String> getEndTimes(Booking book, String startDate, String startTime, String endDate) {
	    List<String> timeList = new ArrayList<String>();
	    
		LocalDateTime start_dt = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime)).plusMinutes(minimumSize);
		LocalDateTime end_dt = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MIN).plusDays(1);
		
		if(!start_dt.toLocalDate().isEqual(LocalDate.parse(startDate)) && startDate.equals(endDate)) {
			return null;
		} else if(!start_dt.toLocalDate().isEqual(LocalDate.parse(endDate))) {
			start_dt = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MIN);
		}
		
		// book == null 인 경우 -> book.check_in = end_dt
		LocalDateTime check_in = end_dt;
		if(book != null) {
			check_in = book.getCheck_in();
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		for(; !start_dt.isAfter(check_in) && start_dt.isBefore(end_dt); start_dt = start_dt.plusMinutes(minuteSize)) {
			timeList.add(dtf.format(start_dt));
		}
		
		return timeList;
	}
	
	// 시간 유효성 체크
	@Override
	public boolean allowsBooking(Booking book) {
		LocalDateTime CIDT = book.getCheck_in();
		LocalDateTime CODT = book.getCheck_out();
		LocalDateTime now = LocalDateTime.now();
		if(now.isBefore(CIDT) && now.plusDays(8).with(LocalTime.MIN).isBefore(CODT)) return false;
		if(CODT.getDayOfYear() - CIDT.getDayOfYear() > 1) return false;
		if(CIDT.isAfter(CODT)) return false;
		return true;
	}
	
	// 날짜+시간으로 검색
	@Override
	public List<Booking> searchByDateTime(LocalDateTime dateTime) {
		return mapper.selectByDateTime(dateTime);
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
	public Booking searchByBookNo(int book_no) {
		return mapper.selectByBookNo(book_no);
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


	// 예약 요금
	@Override
	public int getAmount(Booking book) {
		Duration duration = Duration.between(book.getCheck_in(), book.getCheck_out());
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
		Duration duration = Duration.between(book.getCheck_in(), book.getCheck_out());
		int hour = (int) duration.toHours();
		int minute = (int) duration.toMinutes() % 60;
		
		return hour + "시간 " + minute + "분";
	}

	// 일정구간 예약을 지우는 함수
	public void removeTimes(List<String> times, String date, Booking book) {
		// 예약의 시작일시 -2시간
		LocalDateTime startDateTime = book.getCheck_in().minusMinutes(minimumSize).plusMinutes(minuteSize);
		String startDate = startDateTime.toLocalDate().toString();
		// 예약의 종료일시
		LocalDateTime endDateTime = book.getCheck_out();
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
	
}