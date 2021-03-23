package com.jts.gangstudy.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Booking.State;
import com.jts.gangstudy.domain.User;


public interface BookingService {
	
	/*		데이터베이스 연결	*/
	
	// datetime으로 검색
	public List<Booking> selectByDateTime(LocalDateTime now);
	// 유저의 uncharge를 제외한 해당일에 존재하는 다음 예약
	public Booking searchNextBook(LocalDateTime dateTime, Integer book_no);

	
	
	/*		컨트롤러 연결	*/
	
	// 시작시간 목록
	public List<String> getStartTimes(LocalDate date, List<Booking> userBooks);
	// 종료시간 목록
	public List<String> getEndTimes(LocalDateTime dateTime, Booking book);

	// 사용시간(00시간00분)
	public String getTimeInterval(Booking book);
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 예약 추가(sync)
	public String insertBook(Booking book);
	// 예약 제거
	public int removeBook(Booking book);
	// 상태 변경
	public int changeState(Booking book, State cancel);

	// 모든 예약 검색
	public List<Booking> searchAll();
	
	public List<Booking> searchAlreadyBooked(Integer book_no, String startDate);
	public List<Booking> searchByUser(User user); 
	// 예약 번호로 검색
	public Booking searchByBookNo(int book_no);
	
	
	
	
	
	
	
	
	
	
	// 상태로 검색
	public List<Booking> searchByState(State state);
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

	
	// 시간 유효성 체크
	public boolean isTimeLegal(Booking book);
	
	
}
