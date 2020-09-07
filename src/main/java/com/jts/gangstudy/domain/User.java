package com.jts.gangstudy.domain;



public class User {
	private Integer user_no;
	private String user_nm;
	private String phone;
	private String user_id;
	private String user_pw;
	private String email;
	private String birth_dt;
	private char gender;
	private Integer rate;
	private double points;
	private String note;
	

	
	public User() {
				// TODO Auto-generated constructor stub
	}


	public User(Integer user_no, String user_nm, String phone, String user_id, String user_pw, String email,
			String birth_dt, char gender, Integer rate, double points, String note) {
		super();
		this.user_no = user_no;
		this.user_nm = user_nm;
		this.phone = phone;
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.email = email;
		this.birth_dt = birth_dt;
		this.gender = gender;
		this.rate = rate;
		this.points = points;
		this.note = note;
	}
	
	
	public Integer getUser_no() {
		return user_no;
	}
	public void setUser_no(Integer user_no) {
		this.user_no = user_no;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirth_dt() {
		return birth_dt;
	}
	public void setBirth_dt(String birth_dt) {
		this.birth_dt = birth_dt;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
	
	
}
