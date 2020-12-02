package com.jts.gangstudy.mapper;

import java.util.HashMap;
import java.util.List;

import com.jts.gangstudy.domain.Booking;

public interface BookingMapper {
	public void insertBook(Booking book);
	public void deleteBook(int book_no);
	public List<Booking> selectAll();
	public List<Booking> selectByDate(String date);
	public List<Booking> selectWithDateTime(String dateTime);
	
	public List<Booking> selectWithUser(int user_no);
	public List<Booking> selectByState(String state);
	public List<Booking> selectWithUserState(HashMap<String, String> map);
	public int selectDuplicate(HashMap<String, String> map);
	public List<Booking> viewOvernight(String date);
	public void updateState(HashMap<String, String> map);
}
