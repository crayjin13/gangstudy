package com.jts.gangstudy.service;

import java.time.LocalDateTime;
import java.util.*;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Booking.State;
import com.jts.gangstudy.domain.User;


public interface BookingService {
	// 예약 추가(sync)
	public String insertBook(Booking book);
	// 예약 제거
	public void removeBook(Booking book);
	// 상태 변경
	public void changeState(Booking book, Booking.State cancel);

	// 모든 예약 검색
	public List<Booking> searchAll();
	
	public List<Booking> searchAlreadyBooked(Integer book_no, String startDate);
	public Booking searchNextBook(User user, String startDate, String startTime, String endDate);
	public List<Booking> searchByUser(User user); 
	// 예약 번호로 검색
	public Booking searchByBookNo(int book_no);
	
	
	
	
	
	
	
	
	
	
	// 날짜+시간으로 검색
	public List<Booking> searchByDateTime(LocalDateTime now);
	// 상태로 검색
	public List<Booking> searchByState(String state);
	// 유저의 특정 상태로 검색
	public List<Booking> searchByUserState(User user, String state);
	// 유저의 대기중인 예약
	public Booking searchByUserWait(User user);
	

	
	// 날짜 목록
	public ArrayList<String> makeDates();
	// 예약 요금
	public int getAmount(Booking book);
	// 시간당 요금
	public int getAmountPerHour();

	// 시작시간 목록
	public List<String> getStartTimes(List<Booking> userBooks, String startDate);
	// 종료시간 목록
	public List<String> getEndTimes(Booking book, String startDate, String startTime, String endDate);

	// 사용시간(00시간00분)
	public String getTimeInterval(Booking book);
	
	// 시간 유효성 체크
	public boolean allowsBooking(Booking book);
	
	
}
