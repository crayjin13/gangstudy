package com.jts.gangstudy.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.mapper.BookingMapper;

@Service
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	private BookingMapper mapper;
	
	private int avaliablePeriod = 7;
	private SimpleDateFormat yearFormat = new SimpleDateFormat ( "yyyy");
	private SimpleDateFormat monthFormat = new SimpleDateFormat ( "MM");
	private SimpleDateFormat dateFormat = new SimpleDateFormat ( "dd");
	private Calendar cal = Calendar.getInstance();

	private String[][] avaliableDates = new String[avaliablePeriod][3];
	
	public void checkAvaliableDates() {
		for(int i = 0; i < avaliablePeriod; i++) {
			avaliableDates[i][0] = yearFormat.format(cal.getTime());
			avaliableDates[i][1] = monthFormat.format(cal.getTime());
			avaliableDates[i][2] = dateFormat.format(cal.getTime());
	        cal.add(Calendar.DATE, 1);
		}
		cal = Calendar.getInstance();
	}
	
	public List<Booking> checkAvaliableBooking() {
		String start_dt, end_dt;
		start_dt = avaliableDates[0][0] + avaliableDates[0][1] + avaliableDates[0][2];
		end_dt = avaliableDates[avaliablePeriod-1][0] + avaliableDates[avaliablePeriod-1][1] + avaliableDates[avaliablePeriod-1][2];
		System.out.println(start_dt + ", " + end_dt);

		HashMap<String, String> val = new HashMap<String, String>(); 
		val.put("start_dt", start_dt);
		val.put("end_dt", end_dt);
		
		return mapper.existsBooking(val);
	}


	@Override
	public List<Booking> viewAll() {
		return mapper.viewAll();
	}
	
	@Override
	public List<Booking> getAvaliableBooking() {
		checkAvaliableDates();
		return checkAvaliableBooking();
	}

	@Override
	public Booking bookingInfo(String book_dt, String ci, String co, int people) {
		return new Booking(22, 1, book_dt, ci, co, people, "wait");
	}

	@Override
	public void addBooking(Booking book) {
		mapper.addBooking(book);
	}
}