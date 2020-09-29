package com.jts.gangstudy.mapper;

import java.util.HashMap;
import java.util.List;

import com.jts.gangstudy.domain.Booking;

public interface BookingMapper {
	public List<Booking> viewAll();
	public List<Booking> viewDate(String date);
	public List<Booking> viewUser(int user_no);
	public List<Booking> viewUserState(HashMap<String, String> map);
	public void insertBook(Booking book);
	public int checkDuplicate(HashMap<String, String> map);
}
