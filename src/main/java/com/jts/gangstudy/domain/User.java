package com.jts.gangstudy.domain;

import java.sql.Date;

public class User {
	private Integer user_no;
	private String name;
	private String phone;
	private String id;
	private String pw;
	private String email;
	private Date bod;
	private String gender;
	private Integer rate;
	private String points;
	private String note;
	private String retired;
	
	private Booking booking;
	
	
	
	
	
	
	public User(String name, Integer rate, Booking booking) {
		super();
		this.name = name;
		this.rate = rate;
		this.booking = booking;
	}




	public boolean isMatchPassword(String pw){
		boolean isMatch=false;
		if(this.pw.equals(pw)) {
			isMatch=true;
		}
		return isMatch;
	}

	

	
	@Override
	public String toString() {
		return "User [user_no=" + user_no + ", name=" + name + ", phone=" + phone + ", id=" + id + ", pw=" + pw
				+ ", email=" + email + ", bod=" + bod + ", gender=" + gender + ", rate=" + rate + ", points=" + points
				+ ", note=" + note + "]";
	}



	public User(Integer user_no, String name, String phone, String id, String pw, String email, Date bod,
			String gender, Integer rate, String points, String note) {
		super();
		this.user_no = user_no;
		this.name = name;
		this.phone = phone;
		this.id = id;
		this.pw = pw;
		this.email = email;
		this.bod = bod;
		this.gender = gender;
		this.rate = rate;
		this.points = points;
		this.note = note;
	}

		
	

	public User(String name, String phone, String id, String pw, String email, Date bod, String gender) {
		super();
		this.name = name;
		this.phone = phone;
		this.id = id;
		this.pw = pw;
		this.email = email;
		this.bod = bod;
		this.gender = gender;
	}
	public User(String name, String phone, String id,  String email, Date bod, String gender) {
		super();
		this.name = name;
		this.phone = phone;
		this.id = id;
		this.email = email;
		this.bod = bod;
		this.gender = gender;
	}
	
	



	public User(String name, String phone, String id, String pw, String email, Date bod, String gender, Integer rate,
			String points, String note) {
		super();
		this.name = name;
		this.phone = phone;
		this.id = id;
		this.pw = pw;
		this.email = email;
		this.bod = bod;
		this.gender = gender;
		this.rate = rate;
		this.points = points;
		this.note = note;
	}



	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



	public User(String user_id, String pw, String nickname) {
		this.id = user_id;
		this.pw = pw;
		this.name = nickname;
	}




	public User(String name2, String phone_number, String id2, String string, String string2, String birthdate,
			String gender2) {
		// TODO Auto-generated constructor stub
	}




	public Integer getUser_no() {
		return user_no;
	}



	public void setUser_no(Integer user_no) {
		this.user_no = user_no;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getPw() {
		return pw;
	}



	public void setPw(String pw) {
		this.pw = pw;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Date getBod() {
		return bod;
	}



	public void setBod(Date bod) {
		this.bod = bod;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public Integer getRate() {
		return rate;
	}



	public void setRate(Integer rate) {
		this.rate = rate;
	}



	public String getPoints() {
		if(points==null) {
			return "0";
		} else {
			return points;
		}
	}



	public void setPoints(String points) {
		this.points = points;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}




	public String getRetired() {
		return retired;
	}




	public void setRetired(String retired) {
		this.retired = retired;
	}


	
	
	
	
	
	
	
	
	
	
}
