package com.jts.gangstudy.domain;

import java.math.BigDecimal;

public class Payment {
	private int pay_no;
	private int amount;
	private int point;
	private String pg_name;
	private String tid;
	private String pay_type;
	private String state;
	private int book_no;
	public Payment(BigDecimal amount, BigDecimal point, String pg_name, String tid, String pay_type, String state, BigDecimal book_no) {
		super();
		this.amount = amount.intValue();
		this.setPoint(point.intValue());
		this.pg_name = pg_name;
		this.tid = tid;
		this.pay_type = pay_type;
		this.state = state;
		this.book_no = book_no.intValue();
	}
	public Payment() {
		// TODO Auto-generated constructor stub
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPg_name() {
		return pg_name;
	}
	public void setPg_name(String pg_name) {
		this.pg_name = pg_name;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getBook_no() {
		return book_no;
	}
	public void setBook_no(int book_no) {
		this.book_no = book_no;
	}
	public int getPay_no() {
		return pay_no;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
}
