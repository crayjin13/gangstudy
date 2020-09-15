package com.jts.gangstudy.service;

import java.util.List;

import com.jts.gangstudy.domain.Booking;


public interface BookingService {
	// 전체 예약 목록 (디버깅용)
	public List<Booking> viewAll();

	// 선택 가능한 날짜 목록
	public String[] getDateSelectOptions();
	
	// 해당 날짜의 예약 목록
	public List<Booking> getDateBooking(String date);

	// 가능한 예약 시작 시간
	public List<String> getCITimes(List<Booking> books);
	
	// 가능한 예약 종료 시간
	public List<String> getCOTimes(String ci, String[] options);
	
	// 예약 정보 생성
	public Booking createBook(String book_dt, String ci, String co, int people);

	// 예약 추가
	public void insertBook(Booking book);

}
