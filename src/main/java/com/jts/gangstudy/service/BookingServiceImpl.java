package com.jts.gangstudy.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

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
	private final int minuteSize = 30;		// 에약 신청 시간 단위
	private final int amount = 500;			// 예약 신청 시간 당 필요 요금

	@Override
	public int getHourPrice() {
		return (60 / minuteSize) * amount;
	}
	
	// 전체 예약 목록 (디버깅용)
	@Override
	public List<Booking> viewAll() {
		return mapper.selectAll();
	}
	// 예약 추가
	public void addBook(Booking book) {
		mapper.insertBook(book);
	}
	// 예약 제거
	@Override
	public void removeBook(Booking book) {
		mapper.deleteBook(book.getBook_no());
	}
	// 중복 검색
	public int viewDuplicate(Booking book) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("check_in", book.getCheck_in());
		map.put("check_out", book.getCheck_out());
		return mapper.checkDuplicate(map);
	}
	// 날짜 검색
	@Override
	public List<Booking> viewDate(String date) {
		return mapper.selectDate(date);
	}

	// 선택 가능한 날짜 목록
	@Override
	public ArrayList<String> makeDates() {
		SimpleDateFormat calFormat = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<String> dates = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		
		for(int i = 0; i < dateSize; i++) {
			dates.add(calFormat.format(cal.getTime()));
	        cal.add(Calendar.DATE, 1);
		}
		return dates;
	}
	// 예약 요금
	@Override
	public int getCharge(Booking book) {
		Duration duration = book.getDuration();
		int slot = (int)duration.toMinutes() / minuteSize;
		// 단위 수 * 단위 금액 * 인원
		return slot * amount * book.getPeople();
	}

	// 동기화 처리 된 예약 추가
	public synchronized String insertDB(Booking book) {
		int result = viewDuplicate(book);
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
	
	
	
	
	
	
	
	
	// 대기중인 예약
	@Override
	public Booking getWaitBooking(User user) {
		List<Booking> books = getUserBooking(user, "wait");
		Booking book = null;
		if(books.size() > 1) {
			System.err.println("BookingServiceImpl:getUserWaitBooking: booking 'wait' state is least one more.");
		} else if(books.size() == 1) {
			book = books.get(0);
		}
		return book;
	}
	
	@Override
	public List<Booking> getUserBooking(User user, String state) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_no", user.getUser_no().toString());
		map.put("state", state);
		List<Booking> books = mapper.viewUserState(map);
		return books;
	}

	// 시작일 기준의 시작시간 목록
	@Override
	public List<String> getUnbookedTimeList(String date, List<Booking> books) {
	    List<String> timeList = null;
		
	    if(LocalDate.now().toString().equals(date)) {			// 오늘 예약시 현재시간 기준 생성
	    	LocalTime localTime = LocalTime.now().plusHours(1);
		    if(localTime.getHour() == 0) { // 23시 지나갔음.
		    	return null;
		    }
		    localTime = localTime.getMinute() < 30 ? localTime.withMinute(0) : localTime.withMinute(30);
	    	timeList = createTimeList(localTime.getHour(), localTime.getMinute());
	    } else {												// 예약 시간 단위로 모든 예약 시간 생성
	    	timeList = createTimeList(0, 0);
	    }
		
		// 기존 예약을 예약 시간에서 제거
		for(Booking book : books) {
			removeTimes(timeList, date, book);
		}
		return timeList;
	}

	// 시작일시 ~ 종료일시 간의 시간차이 계산
	@Override
	public String getTimeInterval(Booking book) {
		Duration duration = book.getDuration();
		int hour = (int) duration.toHours();
		int minute = (int) duration.toMinutes();

		
		String timeInterval;
		if(hour > 0) {
			timeInterval = hour + "시간 " + minute + "분";
		} else {
			timeInterval = minute + "분";
		}
		return timeInterval;
	}
	
	// 1인당 이용요금 (= 총액 / 인원 )
	@Override
	public String getTimeAmount(Booking book) {
		Duration duration = book.getDuration();
		Long minute = duration.toMinutes();
		int charge = minute.intValue() / minuteSize;
		return Integer.toString(charge);
	}
	
	@Override
	public void changeState(Booking book, String state) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("book_no", Integer.toString(book.getBook_no()));
		map.put("state", state);
		
		mapper.updateState(map);
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
	
	// 시작시간 기준의 종료시간 목록
	@Override
	public List<String> getEndTimes(List<Booking> books, String startDate, String startTime, String endDate) {
		// 당일 예약 기준 설정
		LocalDateTime start_dt = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));
		LocalDateTime end_dt = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MIN).plusDays(1);

		List<Booking> overnight = mapper.viewOvernight(startDate);
		if(startDate.equals(endDate)) {			// 당일 예약
			if(startTime.equals("23:30")) {		// 당일 마지막 시간에 예약을 신청하는 경우(==23:30)
				return null;
			}
			start_dt = start_dt.plusMinutes(minuteSize);
		} else { 
			if(overnight.isEmpty()) { 			// 밤을 새는 예약
				start_dt = end_dt.minusDays(1);
			} else { 							// 밤을 새는 예약이 이미 있는 경우
				return null;
			}
		}
		
		System.out.println(
				"startDate : " + startDate +
				", startTime : " + startTime +
				", endDate : " + endDate +
				", start_dt : " + start_dt.toString() +
				", end_dt : " + end_dt.toString()
				);
		
		List<String> unbookedTimeList = getUnbookedTimeList(endDate, books);	// 예약되지 않은 전체 시간목록
		List<String> endTimeList = new ArrayList<>();							// 선택 가능한 예약종료 시간목록

		// 예약가능시간 존재 OR 마지막 시간까지
		String time = start_dt.toLocalTime().toString();
		endTimeList.add(time);
		start_dt = start_dt.plusMinutes(minuteSize);
		
		while(unbookedTimeList.contains(time) && start_dt.isBefore(end_dt)) {
			time = start_dt.toLocalTime().toString();		// 시간 설정
			endTimeList.add(time);							// 시간 추가
			start_dt = start_dt.plusMinutes(minuteSize);	// 시간 증가
		}
		
		return endTimeList;
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
		
//		System.out.println(
//				"BOOKNO : " + book.getBook_no() +
//				", book_CIDT : " + book.getciDateTime().toString() +
//				", book_CODT : " + book.getcoDateTime().toString() +
//				", startDateTime : " + startDateTime.toString() +
//				", endDateTime : " + endDateTime.toString()
//				);
		for(;
				!startDateTime.isEqual(endDateTime);
				startDateTime = startDateTime.plusMinutes(minuteSize)) {
			String time = startDateTime.toLocalTime().toString();
			times.remove(time);
		}
	}
	
	
	
	
	
	
	
	// time 목록 반환
	public List<String> createTimeList(int startHour, int startMin) {
		List<String> times = new ArrayList<>();
		LocalDateTime start_dt = LocalDateTime.of(LocalDate.MIN, LocalTime.of(startHour, startMin));
		LocalDateTime end_dt = LocalDateTime.of(LocalDate.MIN, LocalTime.MIN).plusDays(1);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		for(; start_dt.isBefore(end_dt); start_dt = start_dt.plusMinutes(minuteSize)) {
			times.add(dtf.format(start_dt));
		}
		
		return times;
	}
	
	public LocalDateTime getDateTime(String date, String time) {
		LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
		return dateTime;
	}

}