package com.jts.gangstudy.service;

import java.text.SimpleDateFormat;
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
	private final int startHour = 9;		// 예약 가능한 시작 시간
	private final int endHour = 23;			// 예약 가능한 종료 시간
	
	// 전체 예약 목록 (디버깅용)
	@Override
	public List<Booking> viewAll() {
		return mapper.viewAll();
	}

	// 선택 가능한 날짜 목록
	@Override
	public List<String> getDateSelectOptions() {
		return getDates(dateSize);
	}

	// 해당 날짜의 예약 목록
	@Override
	public List<Booking> getDateBooking(String date) {
		return mapper.viewDate(date);
	}
	
	// 시작일 기준의 시작시간 목록
	@Override
	public List<String> getCITimes(List<Booking> books) {
		// 예약 시간 단위로 모든 예약 시간 생성
		List<String> ciList = getTimes(startHour, endHour, minuteSize);
		
		// 기존 예약을 예약 시간에서 제거
		for(Booking book : books) {
			removeTimes(ciList, book.getCi(), book.getCo(), minuteSize);
		}
		return ciList;
	}

	// 시작시간 기준의 종료시간 목록
	@Override
	public List<String> getCOTimes(String ci, String[] ciArray) {
		String time;
		List<String> coList = new ArrayList<>();
		List<String> ciList = Arrays.asList(ciArray);

		int hour = Booking.getHour(ci);
		int min = Booking.getMinute(ci);
		Calendar cal = Calendar.getInstance();
		Booking.setTime(cal, hour, min);
		
		// 종료 시간 추가
		do {
			cal.add(Calendar.MINUTE, minuteSize);
			time = Booking.getTime(cal);
			coList.add(time);
		} while(ciList.contains(time));
		return coList;
	}

	// 동기화 예약 추가
	public String insertBookSync(Booking book, String option) {
		return "";
	}
	// 예약 추가
	@Override
	public void insertBook(Booking book) {
		mapper.insertBook(book);
	}
	
	// 시간 중복 체크
	@Override
	public int duplicateCheck(Booking book) {
		String bood_dt = book.getBook_dt();
		String ci = book.getCi();
		String co = book.getCo();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("book_dt", bood_dt);
		map.put("ci", ci);
		map.put("co", co);
		return mapper.checkDuplicate(map);
	}

	// 예약 요금
	@Override
	public int getCharge(String ci, String co, int people) {
		int charge = 0;
		int hour, min;
		Calendar cal = Calendar.getInstance();
		hour = Booking.getHour(ci);
		min = Booking.getMinute(ci);
		
		for(Booking.setTime(cal, hour, min); !co.equals(Booking.getTime(cal)); cal.add(Calendar.MINUTE, minuteSize)) {
			charge += people * amount;
		}
		return charge;
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
			System.err.println("BookingController: bookEdit: booking 'wait' state is least one more.");
		} else if(books.size() == 1) {
			book = books.get(0);
		}
		return book;
	}
	// date 목록 반환
	public List<String> getDates(int dateSize) {
		SimpleDateFormat calFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<String> dates = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		
		for(int i = 0; i < dateSize; i++) {
			dates.add(calFormat.format(cal.getTime()));
	        cal.add(Calendar.DATE, 1);
		}
		return dates;
	}

	// time 목록 반환
	public List<String> getTimes(int start, int end, int minuteSize) {
		List<String> times = new ArrayList<>();
		String time;
		Calendar cal = Calendar.getInstance();
		
		for(Booking.setTime(cal, start, 0); cal.get(Calendar.HOUR_OF_DAY) != end; cal.add(Calendar.MINUTE, minuteSize)) {
			time = Booking.getTime(cal);
			times.add(time);
		}
		return times;
	}
	
	// 일정구간 예약을 지우는 함수
	public void removeTimes(List<String> times, String start, String end, int minuteSize){
		int hour, min;
		Calendar cal = Calendar.getInstance();
		
		hour = Booking.getHour(start);
		min = Booking.getMinute(start);
		Booking.setTime(cal, hour, min);
			
		for(String time = Booking.getTime(cal); !time.equals(end); cal.add(Calendar.MINUTE, minuteSize), time = Booking.getTime(cal)) {
			times.remove(time);
		}
	}
}