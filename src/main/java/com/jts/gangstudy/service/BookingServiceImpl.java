package com.jts.gangstudy.service;

import java.text.SimpleDateFormat;
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
	private SimpleDateFormat calFormat;
	
	private int dateSize = 7;			// 예약 신청이 가능한 기간
	private int minuteSize = 30;		// 에약 신청 시간 단위
	
	// 예약 가능한 날짜 배열 구하는 함수
	public String[] getAvaliableDates() {
		cal = Calendar.getInstance();
		calFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] dates = new String[dateSize];
		
		for(int i = 0; i < dateSize; i++) {
			dates[i] = calFormat.format(cal.getTime());
	        cal.add(Calendar.DATE, 1);
		}
		return dates;
	}
	
	// 예약 가능한 날짜의 시작일, 종료일을 구하는 함수
	public HashMap<String, String> getBookingRange() {
		String start_dt, end_dt;
		String[] avaliableDates = getAvaliableDates();
		start_dt = avaliableDates[0];
		end_dt = avaliableDates[dateSize-1];

		HashMap<String, String> range = new HashMap<String, String>(); 
		range.put("start_dt", start_dt);
		range.put("end_dt", end_dt);
		
		return range;
	}
	
	// 예약 가능한 날짜의 기존 예약 목록을 가져오는 함수
	@Override
	public List<Booking> getExistBooking() {
		return mapper.viewBeteenDate(getBookingRange());
	}
	
	// 해당 날짜의 예약 목록을 통해 그 날짜의 예약 가능한 시간을 구하는 함수
	public void checkAvaliableTimes(String[][] times) {
		for(int i = 0; i < times.length; i++) {
		}
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