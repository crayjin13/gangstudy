package com.jts.gangstudy.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.*;
import java.util.Date;

public class RemoteLog {
	private Integer log_no;
	private String message;
	private LocalDateTime log_dt;
	private LogType log_type;
	
	public RemoteLog(Integer log_no, String message, Timestamp log_dt, String log_type) {
		super();
		this.log_no = log_no;
		this.message = message;
		this.log_dt = log_dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		this.log_type = LogType.valueOf(log_type);
	}
	public RemoteLog(String message, LocalDateTime time, LogType type) {
		super();
		this.message = message;
		this.log_dt = time;
		this.log_type = type;
	}
	
	public enum LogType {
		command,
		remote,
		request;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTime() {
		return log_dt;
	}

	public void setTime(LocalDateTime time) {
		this.log_dt = time;
	}

	public LogType getType() {
		return log_type;
	}

	public void setType(LogType type) {
		this.log_type = type;
	}

	public Integer getLog_no() {
		return log_no;
	}
}
