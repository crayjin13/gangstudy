package com.jts.gangstudy.mapper;

import java.util.HashMap;
import java.util.List;

import com.jts.gangstudy.domain.Booking;

public interface BookingMapper {
	public void insertBook(Booking book);
	public void deleteBook(int book_no);
	public List<Booking> selectAll();
	public List<Booking> selectDate(String date);
	
	public List<Booking> viewUser(int user_no);
	public List<Booking> viewUserState(HashMap<String, String> map);
	public int checkDuplicate(HashMap<String, String> map);
	public List<Booking> viewOvernight(String date);
	public void updateState(HashMap<String, String> map);
}
