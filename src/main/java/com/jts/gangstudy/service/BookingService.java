package com.jts.gangstudy.service;

import java.util.List;

import com.jts.gangstudy.domain.Booking;


public interface BookingService {
	// 전체 예약 목록 (디버깅용)
	public List<Booking> viewAll();
	
	// 선택 가능한 날짜 목록
	public List<String> getDateSelectOptions();
	
	// 해당 날짜의 예약 목록
	public List<Booking> getDateBooking(String date);

	// 시작일 기준의 시작시간 목록
	public List<String> getCITimes(List<Booking> books);
	
	// 시작시간 기준의 종료시간 목록
	public List<String> getCOTimes(String ci, String[] options);

	// 예약 추가
	public void insertBook(Booking book);
	
	// 시간 중복 체크
	public int duplicateCheck(Booking book);

	// 예약 요금
	public int getCharge(String ci, String co, int people);

	// 대기중인 예약
	public Booking getUserWaitBooking(Integer user_no);
}
