package com.jts.gangstudy.service;

import java.util.List;

import com.jts.gangstudy.domain.Booking;


public interface BookingService {
	
	// 예약 가능한 시간 확인
	public List<Booking> getAvaliableBooking();
	
	// 예약 정보 생성
	public Booking bookingInfo();
	

}
