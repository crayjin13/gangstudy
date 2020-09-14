package com.jts.gangstudy.service;

import java.util.List;

import com.jts.gangstudy.domain.Booking;


public interface BookingService {
	// 전체 예약 목록 (디버깅용)
	public List<Booking> viewAll();
	
	// 예약 가능한 시간 확인 (디버깅용)
	public List<Booking> getExistBooking();
	
	// 선택 가능한 날짜 목록
	public String[] getDateSelectOptions();
	
	// 예약 정보 생성
	public Booking createBook(String book_dt, String ci, String co, int people);

	// 예약 추가
	public void insertBook(Booking book);

}
