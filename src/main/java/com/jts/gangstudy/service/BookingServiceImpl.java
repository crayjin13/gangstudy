package com.jts.gangstudy.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.mapper.BookingMapper;

@Service
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	private BookingMapper mapper;
	
	private int avaliablePeriod = 7; // 현재 날짜로 부터 예약 가능한 기간
	private String[][] avaliableDates = new String[avaliablePeriod][3]; // 예약 가능 기간의 년, 월, 일 배열
	
	private SimpleDateFormat yearFormat = new SimpleDateFormat ( "yyyy");
	private SimpleDateFormat monthFormat = new SimpleDateFormat ( "MM");
	private SimpleDateFormat dayFormat = new SimpleDateFormat ( "dd");
	
	private Calendar cal = Calendar.getInstance();
	
	@Override
	public String[][] getAvaliableDates() {
		for(int i = 0; i < avaliablePeriod; i++) {
			avaliableDates[i][0] = yearFormat.format(cal.getTime());
			avaliableDates[i][1] = monthFormat.format(cal.getTime());
			avaliableDates[i][2] = dayFormat.format(cal.getTime());
	        cal.add(Calendar.DATE, 1);
		}
		return avaliableDates;
	}

	@Override
	public List<Booking> viewAll() {
		getAvaliableDates();
		return mapper.viewAll();
	}
}