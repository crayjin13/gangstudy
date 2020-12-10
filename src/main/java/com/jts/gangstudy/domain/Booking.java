package com.jts.gangstudy.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Booking {
	private int book_no;
	private int user_no;
	private int room_no;
	private LocalDateTime check_in;
	private LocalDateTime check_out;
	private int people;
	private String state;			// 'uncharge', 'wait', 'use', 'clear', 'cancel', 'error'
	private String request_dt;

	public Booking() {
		super();
	}
	
	// MyBatis용 생성자
	public Booking(BigDecimal book_no, BigDecimal user_no, BigDecimal room_no, Date check_in, Date check_out, BigDecimal people,
			String state, String request_dt) {
		super();
		this.book_no = book_no.intValue();
		this.user_no = user_no.intValue();
		this.room_no = room_no.intValue();
		this.check_in = check_in.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		this.check_out = check_out.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		this.people = people.intValue();
		this.state = state;
		this.request_dt = request_dt;
	}
	
	public Booking(int book_no, int user_no, int room_no, LocalDateTime check_in, LocalDateTime check_out, int people,
			String state, String request_dt) {
		super();
		this.book_no = book_no;
		this.user_no = user_no;
		this.room_no = room_no;
		this.check_in = check_in;
		this.check_out = check_out;
		this.people = people;
		this.state = state;
		this.request_dt = request_dt;
	}
	public Booking(int user_no, int room_no, LocalDateTime check_in, LocalDateTime check_out, int people,
			String state) {
		super();
		this.user_no = user_no;
		this.room_no = room_no;
		this.check_in = check_in;
		this.check_out = check_out;
		this.people = people;
		this.state = state;
	}

	public String toString() {
		return "book_no:"+this.book_no+" , " + 
				"user_no:"+this.user_no+" , " + 
				"room_no:"+this.room_no+" , " + 
				"check_in:"+this.check_in+" , " + 
				"check_out:"+this.check_out+" , " + 
				"people:"+this.people+" , " + 
				"state:"+this.state+" , " + 
				"request_dt:"+this.request_dt;
	}
	
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public void setCheck_in(LocalDateTime check_in) {
		this.check_in = check_in;
	}

	public void setCheck_out(LocalDateTime check_out) {
		this.check_out = check_out;
	}
	
	public void setCheck_in(String startDate, String startTime) {
		this.check_in = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));
	}

	public void setCheck_out(String endDate, String endTime) {
		this.check_out = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.parse(endTime));
	}

	public void setPeople(int people) {
		this.people = people;
	}
	public void setPeople(String people) {
		this.people = Integer.parseInt(people);
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setRequest_dt(String request_dt) {
		this.request_dt = request_dt;
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

	public LocalDateTime getCheck_in() {
		return check_in;
	}

	public LocalDateTime getCheck_out() {
		return check_out;
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Booking other = (Booking)obj;
		if (book_no != other.getBook_no()) return false;
		return true;
	}

}
