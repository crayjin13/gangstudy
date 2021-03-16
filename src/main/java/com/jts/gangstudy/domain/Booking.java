package com.jts.gangstudy.domain;

import java.time.*;

public class Booking {
	private int book_no;
	private int user_no;
	private int room_no;
	private LocalDateTime check_in;
	private LocalDateTime check_out;
	private int people;
	private State state;
	private LocalDateTime request_dt;
	
	public enum State {
		uncharge("uncharge", "미결제"),
		wait("wait", "확정"),
		use("use", "사용중"),
		clear("clear", "완료"),
		cancel("cancel", "취소"),
		error("error", "오류");
		
		private String DBValue;
		private String UIValue;
		
		State(String DBValue, String UIValue) {
			this.DBValue = DBValue;
			this.UIValue = UIValue;
		}
		
		public String getUIValue() {
			return UIValue;
		}
	}

	public Booking() {
		super();
	}
	
	// MyBatis용 생성자
	public Booking(Integer book_no, Integer user_no, Integer room_no, LocalDateTime check_in, LocalDateTime check_out, Integer people,
			String state, LocalDateTime request_dt) {
		super();
		this.book_no = book_no;
		this.user_no = user_no;
		this.room_no = room_no;
		this.check_in = check_in;
		this.check_out = check_out;
		this.people = people;
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

	public Booking(String date, String startTime, String endTime, Integer people, int room_no, State state) {
		LocalDateTime start_dt = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(startTime));
		LocalDateTime end_dt = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(endTime));
		if(start_dt.toLocalTime().isAfter(end_dt.toLocalTime())) {
			end_dt = end_dt.plusDays(1);
		}
		
		this.check_in = start_dt;
		this.check_out = end_dt;
		this.people = people;
		this.room_no = room_no;
		this.state = state;
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

	public void setRequest_dt(LocalDateTime request_dt) {
		this.request_dt = request_dt;
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

	public State getState() {
		return state;
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
