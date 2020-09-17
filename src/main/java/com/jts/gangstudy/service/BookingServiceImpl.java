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
	private Calendar cal;
	
	private int dateSize = 7;			// 예약 신청이 가능한 기간
	private int minuteSize = 30;		// 에약 신청 시간 단위
	private int start_hour = 9;			// 예약 가능한 시작 시간
	private int end_hour = 23;			// 예약 가능한 종료 시간
	
	// 예약 가능한 날짜 배열 구하는 함수
	public String[] getAvaliableDates() {
		cal = Calendar.getInstance();
		SimpleDateFormat calFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] dates = new String[dateSize];
		
		for(int i = 0; i < dateSize; i++) {
			dates[i] = calFormat.format(cal.getTime());
	        cal.add(Calendar.DATE, 1);
		}
		return dates;
	}
	
//	public HashMap<String, String> getBookingRange() {
//		String start_dt, end_dt;
//		String[] avaliableDates = getAvaliableDates();
//		start_dt = avaliableDates[0];
//		end_dt = avaliableDates[dateSize-1];
//
//		HashMap<String, String> range = new HashMap<String, String>(); 
//		range.put("start_dt", start_dt);
//		range.put("end_dt", end_dt);
//		
//		return range;
//	}
	
	// 예약 가능한 날짜의 기존 예약 목록을 가져오는 함수
	@Override
	public List<Booking> getDateBooking(String date) {
		return mapper.viewDate(date);
	}
	
	// 해당 날짜의 예약 목록을 통해 그 날짜의 예약 가능한 시간을 구하는 함수
	@Override
	public List<String> getCITimes(List<Booking> books) {
		int hour, min;
		String time;
		List<String> times = new ArrayList<>();
		
		cal = Calendar.getInstance();

		// 예약 시간 단위로 모든 예약 시간 생성
		for(cal.set(Calendar.HOUR_OF_DAY, start_hour), cal.set(Calendar.MINUTE, 0);
			cal.get(Calendar.HOUR_OF_DAY) != end_hour;
			cal.add(Calendar.MINUTE, minuteSize))
		{
			hour = cal.get(Calendar.HOUR_OF_DAY);
			min = cal.get(Calendar.MINUTE);
			times.add(String.format("%02d:%02d", hour, min));
		}
		
		// 이미 예약된 시간 제거
		for(int i = 0; i < books.size(); i++) {
			hour = Integer.parseInt(books.get(i).getCi().substring(0, 2));
			min = Integer.parseInt(books.get(i).getCi().substring(3, 5));
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, min);
			do {
				hour = cal.get(Calendar.HOUR_OF_DAY);
				min = cal.get(Calendar.MINUTE);
				time = String.format("%02d:%02d", hour, min);
				cal.add(Calendar.MINUTE, minuteSize);
				if(time.equals(books.get(i).getCo())) {
					break;
				}
			} while(times.remove(time) != false);
		}
		return times;
	}

	@Override
	public List<String> getCOTimes(String ci, String[] options) {
		int hour, min;
		String time;
		List<String> times = new ArrayList<>();
		hour = Integer.parseInt(ci.substring(0, 2));
		min = Integer.parseInt(ci.substring(3, 5));
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		List<String> ciList = Arrays.asList(options);
		
		// ci + minuteSize 부터 시작해서
		// options에 해당 값이 있는동안 List<String>에 추가
		do {
			cal.add(Calendar.MINUTE, minuteSize);
			hour = cal.get(Calendar.HOUR_OF_DAY);
			min = cal.get(Calendar.MINUTE);
			time = String.format("%02d:%02d", hour, min);
			times.add(time);
		} while(ciList.contains(time));
		return times;
	}
	
	// 전체 예약 목록 (디버깅용)
	@Override
	public List<Booking> viewAll() {
		return mapper.viewAll();
	}
	// 예약 정보 생성
	@Override
	public Booking createBook(String book_dt, String ci, String co, int people) {
		return new Booking(22, 1, book_dt, ci, co, people, "wait");
	}

	@Override
	// 예약 추가
	public void insertBook(Booking book) {
		mapper.insertBook(book);
	}

	@Override
	// 선택 가능한 날짜 목록
	public String[] getDateSelectOptions() {
		return getAvaliableDates();
	}
}