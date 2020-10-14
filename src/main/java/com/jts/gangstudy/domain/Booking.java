package com.jts.gangstudy.domain;

import java.math.BigDecimal;
import java.time.*;

public class Booking {
	private int book_no;
	private int user_no;
	private int room_no;
	private String check_in;
	private String check_out;
	private int people;
	private String state;
	private String request_dt;
	
	public Booking(BigDecimal book_no, BigDecimal user_no, BigDecimal room_no, String check_in, String check_out, BigDecimal people,
			String state, String request_dt) {
		super();
		this.book_no = book_no.intValue();
		this.user_no = user_no.intValue();
		this.room_no = room_no.intValue();
		this.check_in = check_in;
		this.check_out = check_out;
		this.people = people.intValue();
		this.state = state;
		this.request_dt = request_dt;
	}
	
	public Booking(int book_no, int user_no, int room_no, String check_in, String check_out, int people,
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
	public Booking(int user_no, int room_no, String check_in, String check_out, int people,
			String state) {
		super();
		this.user_no = user_no;
		this.room_no = room_no;
		this.check_in = check_in;
		this.check_out = check_out;
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

	public String getCheck_in() {
		return check_in;
	}

	public String getCheck_out() {
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

	public LocalTime getciTime() {
		return LocalTime.parse(check_in.substring(11, 16));
	}
	public LocalTime getcoTime() {
		return LocalTime.parse(check_out.substring(11, 16));
	}
	
	public LocalDate getciDate() {
		return LocalDate.parse(check_in.substring(0, 10));
	}
	public LocalDate getcoDate() {
		return LocalDate.parse(check_out.substring(0, 10));
	}
	
	public LocalDateTime getciDateTime() {
		return LocalDateTime.of(getciDate(), getciTime());
	}
	public LocalDateTime getcoDateTime() {
		return LocalDateTime.of(getcoDate(), getcoTime());
	}
}
