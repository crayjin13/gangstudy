package com.jts.gangstudy.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
	private Calendar cal;
	
	private int dateSize = 7;			// �삁�빟 �떊泥��씠 媛��뒫�븳 湲곌컙
	private int minuteSize = 30;		// �뿉�빟 �떊泥� �떆媛� �떒�쐞
	private int start_hour = 9;		// �삁�빟 媛��뒫�븳 �떆�옉 �떆媛�
	private int end_hour = 23;			// �삁�빟 媛��뒫�븳 醫낅즺 �떆媛�
	
	// �삁�빟 媛��뒫�븳 �궇吏� 諛곗뿴 援ы븯�뒗 �븿�닔
	public String[] getAvaliableDates() {
		cal = Calendar.getInstance();
		SimpleDateFormat calFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] dates = new String[dateSize];
		
		for(int i = 0; i < dateSize; i++) {
			dates[i] = calFormat.format(cal.getTime());
	        cal.add(Calendar.DATE, 1);
		}
		return dates;
	}
	
//	// �삁�빟 媛��뒫�븳 �궇吏쒖쓽 �떆�옉�씪, 醫낅즺�씪�쓣 援ы븯�뒗 �븿�닔
//	public HashMap<String, String> getBookingRange() {
//		String start_dt, end_dt;
//		String[] avaliableDates = getAvaliableDates();
//		start_dt = avaliableDates[0];
//		end_dt = avaliableDates[dateSize-1];
//
//		HashMap<String, String> range = new HashMap<String, String>(); 
//		range.put("start_dt", start_dt);
//		range.put("end_dt", end_dt);
//		
//		return range;
//	}
	
	// �삁�빟 媛��뒫�븳 �궇吏쒖쓽 湲곗〈 �삁�빟 紐⑸줉�쓣 媛��졇�삤�뒗 �븿�닔
	@Override
	public List<Booking> getDateBooking(String date) {
		return mapper.viewDate(date);
	}
	
	// �빐�떦 �궇吏쒖쓽 �삁�빟 紐⑸줉�쓣 �넻�빐 洹� �궇吏쒖쓽 �삁�빟 媛��뒫�븳 �떆媛꾩쓣 援ы븯�뒗 �븿�닔
	@Override
	public List<String> getCITimes(List<Booking> books) {
		int hour, min;
		String time;
		List<String> times = new ArrayList<>();
		
		cal = Calendar.getInstance();

		// �삁�빟 �떆媛� �떒�쐞濡� 紐⑤뱺 �삁�빟 �떆媛� �깮�꽦
		for(cal.set(Calendar.HOUR_OF_DAY, start_hour), cal.set(Calendar.MINUTE, 0);
			cal.get(Calendar.HOUR_OF_DAY) != end_hour;
			cal.add(Calendar.MINUTE, minuteSize))
		{
			hour = cal.get(Calendar.HOUR_OF_DAY);
			min = cal.get(Calendar.MINUTE);
			times.add(String.format("%02d:%02d", hour, min));
		}
		
		// �씠誘� �삁�빟�맂 �떆媛� �젣嫄�
		for(int i = 0; i < books.size(); i++) {
			hour = Integer.parseInt(books.get(i).getCi().substring(0, 2));
			min = Integer.parseInt(books.get(i).getCi().substring(3, 5));
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, min);
			do {
				hour = cal.get(Calendar.HOUR_OF_DAY);
				min = cal.get(Calendar.MINUTE);
				time = String.format("%02d:%02d", hour, min);
				cal.add(Calendar.MINUTE, minuteSize);
				if(time.equals(books.get(i).getCo())) {
					break;
				}
			} while(times.remove(time) != false);
		}
		return times;
	}

	@Override
	public List<String> getCOTimes(String ci, String[] options) {
		int hour, min;
		String time;
		List<String> times = new ArrayList<>();
		hour = Integer.parseInt(ci.substring(0, 2));
		min = Integer.parseInt(ci.substring(3, 5));
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		List<String> ciList = Arrays.asList(options);
		
		// ci + minuteSize 遺��꽣 �떆�옉�빐�꽌
		// options�뿉 �빐�떦 媛믪씠 �엳�뒗�룞�븞 List<String>�뿉 異붽�
		do {
			cal.add(Calendar.MINUTE, minuteSize);
			hour = cal.get(Calendar.HOUR_OF_DAY);
			min = cal.get(Calendar.MINUTE);
			time = String.format("%02d:%02d", hour, min);
			times.add(time);
		} while(ciList.contains(time));
		return times;
	}
	
	// �쟾泥� �삁�빟 紐⑸줉 (�뵒踰꾧퉭�슜)
	@Override
	public List<Booking> viewAll() {
		return mapper.viewAll();
	}
	// �삁�빟 �젙蹂� �깮�꽦
	@Override
	public Booking createBook(String book_dt, String ci, String co, int people) {
		return new Booking(1, 1, book_dt, ci, co, people, "wait");
	}

	@Override
	// �삁�빟 異붽�
	public void insertBook(Booking book) {
		mapper.insertBook(book);
	}

	@Override
	// �꽑�깮 媛��뒫�븳 �궇吏� 紐⑸줉
	public String[] getDateSelectOptions() {
		return getAvaliableDates();
	}
}