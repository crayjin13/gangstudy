package com.jts.gangstudy.domain;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;

public class Command {
	private int command_no;
	private String message;
	private LocalTime reserveTime;
	
	public Command(Integer command_no, String message, Time reserveTime) {
		super();
		this.command_no = command_no;
		this.message = message;
		this.reserveTime = reserveTime.toLocalTime();
	}
	
	public Command(String message, LocalTime reserveTime) {
		super();
		this.message = message;
		this.reserveTime = reserveTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalTime getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(LocalTime reserveTime) {
		this.reserveTime = reserveTime;
	}
	public int getCommand_no() {
		return command_no;
	}
	
}
