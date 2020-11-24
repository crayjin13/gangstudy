package com.jts.gangstudy.service;

import java.time.LocalDateTime;
import java.util.*;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.User;


public interface BookingService {
	// 전체 예약 목록 (디버깅용)
	public List<Booking> viewAll();
	// 예약 제거
	public void removeBook(Booking book);
	// 예약 추가 요청
	public String insertBook(Booking book);
	// 해당 날짜의 예약 목록
	public List<Booking> viewDate(String date);
	
	// 선택 가능한 날짜 목록
	public ArrayList<String> makeDates();
	// 예약 요금
	public int getCharge(Booking book);
	
	
	
	// 예약되지 않은 시간 목록
	public List<String> getUnbookedTimeList(String date, List<Booking> books);
	
	// 시작시간 기준의 종료시간 목록
	public List<String> getEndTimes(List<Booking> books, String startDate, String startTime, String endDate);

	// 대기중인 예약
	public Booking getWaitBooking(User user);
	
	// 특정 상태의 예약
	public List<Booking> getUserBooking(User user, String state);

	public List<Booking> getTimeBooking(LocalDateTime now);
	public List<Booking> getStateBooking(String string);
	
	public void changeState(Booking book, String state);
	public String getTimeInterval(Booking book);
	public String getTimeAmount(Booking book);
	public int getHourPrice();
	public boolean allowsBooking(Booking book);
}
