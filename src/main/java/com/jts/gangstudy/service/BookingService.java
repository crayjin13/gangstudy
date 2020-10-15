package com.jts.gangstudy.service;

import java.util.*;

import com.jts.gangstudy.domain.Booking;


public interface BookingService {
	// 전체 예약 목록 (디버깅용)
	public List<Booking> viewAll();
	// 예약 제거
	public void removeBook(Booking book);
	// 예약 추가 요청
	public String insertDB(Booking book);
	// 해당 날짜의 예약 목록
	public List<Booking> viewDate(String date);
	
	// 선택 가능한 날짜 목록
	public ArrayList<String> makeDates();
	// 예약 요금
	public int calcCharge(Booking book);
	
	
	
	// 예약되지 않은 시간 목록
	public List<String> getUnbookedTimeList(String date, List<Booking> books);
	
	// 시작시간 기준의 종료시간 목록
	public List<String> getEndTimes(List<Booking> books, String startDate, String startTime, String endDate);

	// 대기중인 예약
	public Booking getUserWaitBooking(Integer user_no);
}
