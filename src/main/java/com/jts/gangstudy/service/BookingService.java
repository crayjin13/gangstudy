package com.jts.gangstudy.service;

import java.util.List;

import com.jts.gangstudy.domain.Booking;


public interface BookingService {
	
	// 예약 가능한 날짜 얻기
	public void getAvaliableDates();
	public List<Booking> viewAll();

}
