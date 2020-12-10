package com.jts.gangstudy.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;

public class Command {
	private int command_no;
	private String ipAdress;
	private String portNumber;
	private String message;
	private LocalTime reserveTime;
	
	public Command(BigDecimal command_no, String ipAdress, String portNumber, String message, Timestamp reserveTime) {
		super();
		this.command_no = command_no.intValue();
		this.ipAdress = ipAdress;
		this.portNumber = portNumber;
		this.message = message;
		this.reserveTime = reserveTime.toLocalDateTime().toLocalTime();
	}
	
	public Command(String ipAdress, String portNumber, String message, LocalTime reserveTime) {
		super();
		this.ipAdress = ipAdress;
		this.portNumber = portNumber;
		this.message = message;
		this.reserveTime = reserveTime;
	}
	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	public String getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
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
