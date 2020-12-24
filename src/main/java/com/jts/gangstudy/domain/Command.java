package com.jts.gangstudy.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;

public class Command {
	private int command_no;
	private String message;
	private LocalTime reserveTime;
	
	public Command(BigDecimal command_no, String message, Timestamp reserveTime) {
		super();
		this.command_no = command_no.intValue();
		this.message = message;
		this.reserveTime = reserveTime.toLocalDateTime().toLocalTime();
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
