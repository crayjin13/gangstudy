package com.jts.gangstudy.mapper;

import java.util.List;

import com.jts.gangstudy.domain.Booking;

public interface BookingMapper {
	public List<Booking> viewAll();
	public List<Booking> viewDate(String date);
	public void insertBook(Booking book);
}
