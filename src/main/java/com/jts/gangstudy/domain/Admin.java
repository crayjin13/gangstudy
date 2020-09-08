package com.jts.gangstudy.domain;

public class Admin {

	private Integer user_no;
	private Integer levels;
	private String admin_id;
	private String admin_pw;
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(Integer user_no, Integer levels, String admin_id, String admin_pw) {
		super();
		this.user_no = user_no;
		this.levels = levels;
		this.admin_id = admin_id;
		this.admin_pw = admin_pw;
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

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getAdmin_pw() {
		return admin_pw;
	}

	public void setAdmin_pw(String admin_pw) {
		this.admin_pw = admin_pw;
	}
	
	
	
	
	
	
	
	
}
