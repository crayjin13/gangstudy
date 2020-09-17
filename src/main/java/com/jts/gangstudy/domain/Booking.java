package com.jts.gangstudy.domain;

import java.math.BigDecimal;
import java.util.Calendar;

public class Booking {
	private int book_no;
	private int user_no;
	private int room_no;
	private String book_dt;
	private String ci;
	private String co;
	private int people;
	private String state;
	private String request_dt;
	
	public Booking(BigDecimal book_no, BigDecimal user_no, BigDecimal room_no, String book_dt, String ci, String co, BigDecimal people,
			String state, String request_dt) {
		super();
		this.book_no = book_no.intValue();
		this.user_no = user_no.intValue();
		this.room_no = room_no.intValue();
		this.book_dt = book_dt;
		this.ci = ci;
		this.co = co;
		this.people = people.intValue();
		this.state = state;
		this.request_dt = request_dt;
	}
	
	public Booking(int book_no, int user_no, int room_no, String book_dt, String ci, String co, int people,
			String state, String request_dt) {
		super();
		this.book_no = book_no;
		this.user_no = user_no;
		this.room_no = room_no;
		this.book_dt = book_dt;
		this.ci = ci;
		this.co = co;
		this.people = people;
		this.state = state;
		this.request_dt = request_dt;
	}
	public Booking(int user_no, int room_no, String book_dt, String ci, String co, int people,
			String state) {
		super();
		this.user_no = user_no;
		this.room_no = room_no;
		this.book_dt = book_dt;
		this.ci = ci;
		this.co = co;
		this.people = people;
		this.state = state;
	}

	public int getBook_no() {
		return book_no;
	}

	public int getUser_no() {
		return user_no;
	}

	public int getRoom_no() {
		return room_no;
	}

	public String getBook_dt() {
		return book_dt;
	}

	public String getCi() {
		return ci;
	}

	public String getCo() {
		return co;
	}

	public int getPeople() {
		return people;
	}

	public String getState() {
		return state;
	}

	public String getRequest_dt() {
		return request_dt;
	}
	
	// format(%02d:%02d)의 시 반환
	public static int getHour(String time) {
		return Integer.parseInt(time.substring(0, 2));
	}
	// format(%02d:%02d)의 분 반환
	public static int getMinute(String time) {
		return Integer.parseInt(time.substring(3, 5));
	}
	// calendar format(%02d:%02d)의 반환
	public static String getTime(Calendar cal) {
		String time;
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		time = String.format("%02d:%02d", hour, min);
		return time;
	}
	// calendar의 시간을 설정하는 함수
	public static void setTime(Calendar cal, int hour, int minute) {
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
	}


}
