package com.jts.gangstudy.domain;

import java.math.BigDecimal;

public class Command {
	private int command_no;
	private String ipAdress;
	private String portNumber;
	private String message;
	private String reserveTime;
	public Command(BigDecimal command_no, String ipAdress, String portNumber, String message, String reserveTime) {
		super();
		this.command_no = command_no.intValue();
		this.ipAdress = ipAdress;
		this.portNumber = portNumber;
		this.message = message;
		this.reserveTime = reserveTime;
	}
	public Command(String ipAdress, String portNumber, String message, String reserveTime) {
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
	public String getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	public int getCommand_no() {
		return command_no;
	}

	public String getHour() {
		return reserveTime.substring(0, 2);
	}
	public String getMinute() {
		return reserveTime.substring(3, 5);
	}
	public String getSecond() {
		return reserveTime.substring(6, 8);
	}
	
}
