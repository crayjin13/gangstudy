package com.jts.gangstudy.domain;

import java.math.BigDecimal;

public class Payment {
	private int pay_no;
	private int book_no;
	private int amount;
	private int point;
	private String tid;
	private String pay_type;
	
	private PGName pg_nm;
	private State state;
	
	public enum PGName {
		KakaoPay,
		Danal,
		PointOnly
	}
	
	public enum State {
		ready,
		paid,
		cancelled,
		failed
	}
	
	public Payment(Integer amount, Integer point, String pg_name, String tid, String pay_type, String state, Integer book_no) {
		super();
		this.amount = amount;
		this.point = point;
		this.pg_nm = PGName.valueOf(pg_name);
		this.tid = tid;
		this.pay_type = pay_type;
		this.state = State.valueOf(state);
		this.book_no = book_no;
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
		return pg_nm.toString();
	}
	public void setPg_name(String pg_name) {
		this.pg_nm = PGName.valueOf(pg_name);
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
	public State getState() {
		return state;
	}
	public void setState(String state) {
		this.state = State.valueOf(state);
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
	
	@Override
	public String toString() {
		return "Payment [pay_no=" + pay_no + ", amount=" + amount + ", point=" + point + ", pg_name=" + pg_nm
				+ ", tid=" + tid + ", pay_type=" + pay_type + ", state=" + state + ", book_no=" + book_no + "]";
	}
	public void setState(State state) {
		this.state = state;
	}
}
