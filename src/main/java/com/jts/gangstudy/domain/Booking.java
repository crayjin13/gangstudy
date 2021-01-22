package com.jts.gangstudy.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.time.*;

public class Booking {
	private int book_no;
	private int user_no;
	private int room_no;
	private LocalDateTime check_in;
	private LocalDateTime check_out;
	private int people;
	private State state;
	
	public enum State {
		uncharge,
		wait,
		use,
		clear,
		cancel,
		error;
	}
	
	private LocalDateTime request_dt;

	public Booking() {
		super();
	}
	
	// MyBatis용 생성자
	public Booking(BigDecimal book_no, BigDecimal user_no, BigDecimal room_no, Date check_in, Date check_out, BigDecimal people,
			String state, LocalDateTime request_dt) {
		super();
		this.book_no = book_no.intValue();
		this.user_no = user_no.intValue();
		this.room_no = room_no.intValue();
		this.check_in = check_in.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		this.check_out = check_out.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		this.people = people.intValue();
		this.state = State.valueOf(state);
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
		this.state = State.valueOf(state);
	}

	public String toString() {
		return "{" + '\n' +
				"\tbook_no\t\t:\t"		+	this.book_no	+ "," + '\n' +
				"\tuser_no\t\t:\t"		+	this.user_no	+ "," + '\n' +
				"\troom_no\t\t:\t"		+	this.room_no	+ "," + '\n' +
				"\tcheck_in\t:\t"	+	this.check_in	+ "," + '\n' +
				"\tcheck_out\t:\t"	+	this.check_out	+ "," + '\n' +
				"\tpeople\t\t:\t"		+	this.people		+ "," + '\n' +
				"\tstate\t\t:\t"		+	this.state		+ "," + '\n' +
				"\trequest_dt\t:\t" 	+	this.request_dt	+ "," + '\n' +
				"}";
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
		this.state = State.valueOf(state);
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
		return state.toString();
	}

	public LocalDateTime getRequest_dt() {
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

	public void setState(State state) {
		this.state = state;
	}

}
