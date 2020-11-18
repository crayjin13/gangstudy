package com.jts.gangstudy.service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.mapper.BookingMapper;

@Service
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	private BookingMapper mapper;
	
	private final int dateSize = 7;			// 예약 신청이 가능한 기간
	private final int minuteSize = 30;		// 에약 신청 시간 단위
	private final int amount = 500;			// 예약 신청 시간 당 필요 요금
	private final int startHour = 0;		// 예약 가능한 시작 시간
	private final int startMin = 0;			// 예약 가능한 시작 분
	private final int endHour = 24;			// 예약 가능한 종료 시간
	private final int endMin = 0;			// 예약 가능한 종료 분
	
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
	public int calcCharge(Booking book) {
		LocalDateTime ciDateTime = book.getciDateTime();
		LocalDateTime coDateTime = book.getcoDateTime();
		Duration duration = Duration.between(ciDateTime, coDateTime);
		int slot = (int)duration.toMinutes() / minuteSize;
		
		// 단위 수 * 단위 금액 * 인원
		return slot * amount * book.getPeople();
	}

	// 동기화 처리 된 예약 추가
	public synchronized String insertDB(Booking book) {
		int result = viewDuplicate(book);
		if(result == 0) {
			mapper.insertBook(book);
			return "true";
		} else if(result > 1){
			System.err.println("BookingController:insertDB: duplicate booking is exist.");
			return "critical";
		} else {
			return "duplicate";
		}
	}
	
	
	
	
	// 대기중인 예약
	@Override
	public Booking getUserWaitBooking(Integer user_no) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_no", user_no.toString());
		map.put("state", "wait");
		List<Booking> books = mapper.viewUserState(map);
		Booking book = null;
		if(books.size() > 1) {
			System.err.println("BookingServiceImpl:getUserWaitBooking: booking 'wait' state is least one more.");
		} else if(books.size() == 1) {
			book = books.get(0);
		}
		return book;
	}

	// 시작시간 기준의 종료시간 목록
	@Override
	public List<String> getEndTimes(List<Booking> books, String startDate, String startTime, String endDate) {
		LocalDateTime start_dt;
		LocalDateTime end_dt = getDateTime(endDate, "23:29");		// 24시 - 1 - minutesize
		List<Booking> overnight = mapper.viewOvernight(startDate);

		if(startDate.equals(endDate)) { // 당일 예약
			start_dt = getDateTime(startDate, startTime);
			start_dt = start_dt.plusMinutes(minuteSize);
		} else { 
			if(!overnight.isEmpty()) { // 밤을 새는 예약이 이미 있는 경우
				System.out.println("getEndTimes : overnighit is not empty");
				return null;
			} else { // 밤을 새는 예약
				System.out.println("getEndTimes : overnighit is empty");
				start_dt = getDateTime(endDate, "00:00");
			}
		}
		List<String> unbookedTimeList = getUnbookedTimeList(endDate, books);	// 예약되지 않은 전체 시간목록
		List<String> endTimeList = new ArrayList<>();							// 선택 가능한 예약종료 시간목록

		// 예약가능시간 존재 OR 마지막 시간까지
		String time = start_dt.toLocalTime().toString();
		endTimeList.add(time);
		while(unbookedTimeList.contains(time) && start_dt.isBefore(end_dt)) {
			start_dt = start_dt.plusMinutes(minuteSize);	// 시간 증가
			time = start_dt.toLocalTime().toString();		// 시간 설정
			endTimeList.add(time);							// 시간 추가
		}
		
		return endTimeList;
	}

	// 시작일 기준의 시작시간 목록
	@Override
	public List<String> getUnbookedTimeList(String date, List<Booking> books) {
		// 예약 시간 단위로 모든 예약 시간 생성
		List<String> timeList = createTimeList();
		
		// 기존 예약을 예약 시간에서 제거
		for(Booking book : books) {
			removeTimes(timeList, date, book);
		}
		return timeList;
	}
	
	// 년 월 일 시 분 포맷 변환
	@Override
	public String getViewFormat(String date, String time) {
	    LocalDateTime localDateTime = getDateTime(date, time);
	    String stringDateTime = localDateTime.format(DateTimeFormatter.ofPattern("M월 d일 h시 mm분"));
		return stringDateTime;
	}

	// 시작일시 ~ 종료일시 간의 시간차이 계산
	@Override
	public String getTimeInterval(String startDate, String startTime, String endDate, String endTime) {
		LocalDateTime startDateTime = getDateTime(startDate, startTime);
		LocalDateTime endDateTime = getDateTime(endDate, endTime);

		int hour = Duration.between(startDateTime, endDateTime).toHoursPart();
		int minute = Duration.between(startDateTime, endDateTime).toMinutesPart();
		String timeInterval;
		if(hour > 0) {
			timeInterval = hour + "시간 " + minute + "분";
		} else {
			timeInterval = minute + "분";
		}
		return timeInterval;
	}
	
	
	
	
	
	
	
	
	
	// time 목록 반환
	public List<String> createTimeList() {
		List<String> times = new ArrayList<>();
		LocalDateTime start_dt = LocalDateTime.of(1, 1, 1, startHour, startMin);
		LocalDateTime end_dt = start_dt.plusHours(endHour-startHour);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		for(; start_dt.isBefore(end_dt); start_dt = start_dt.plusMinutes(minuteSize)) {
			times.add(dtf.format(start_dt));
		}
		
		return times;
	}
	// 일정구간 예약을 지우는 함수
		public void removeTimes(List<String> times, String date, Booking book) {
			LocalTime startTime = book.getciTime();
			String end = book.getcoTime().toString();
			
			// overnight
			if(!date.equals(book.getciDate())) {		// 예약일 != 시작일
				startTime = LocalTime.parse("00:00");
			} else if(!date.equals(book.getcoDate())) {	// 예약일 != 종료일
				end = "23:30";
			}
			
			for(String time = startTime.toString(); !time.equals(end); startTime = startTime.plusMinutes(minuteSize)) {
				time = startTime.toString();
				times.remove(time);
			}
		}
		
		public LocalDateTime getDateTime(String date, String time) {
			LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
			return dateTime;
		}
}