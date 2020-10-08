package com.jts.gangstudy.domain;

public class Admin {

	
	private Integer user_no;
	private Integer levels;
	
	private User user;
	private Booking booking;
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Admin(Integer user_no, Integer levels) {
		super();
		this.user_no = user_no;
		this.levels = levels;
	}


	public Integer getUser_no() {
		return user_no;
	}


	public void setUser_no(Integer user_no) {
		this.user_no = user_no;
	}


	public Integer getLevels() {
		return levels;
	}


	public void setLevels(Integer levels) {
		this.levels = levels;
	}



	
	
	
}
