package com.jts.gangstudy.domain;

public class Notice {

	private Integer notice_no;
	private Integer user_no;
	private String title;
	private Integer views;
	private String content;
	private String notice_dt;
	
	
	
	public Notice() {
				// TODO Auto-generated constructor stub
	}



	public Notice(Integer notice_no, Integer user_no, String title, Integer views, String content, String notice_dt) {
		super();
		this.notice_no = notice_no;
		this.user_no = user_no;
		this.title = title;
		this.views = views;
		this.content = content;
		this.notice_dt = notice_dt;
	}



	public Integer getNotice_no() {
		return notice_no;
	}



	public void setNotice_no(Integer notice_no) {
		this.notice_no = notice_no;
	}



	public Integer getUser_no() {
		return user_no;
	}



	public void setUser_no(Integer user_no) {
		this.user_no = user_no;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public Integer getViews() {
		return views;
	}



	public void setViews(Integer views) {
		this.views = views;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getNotice_dt() {
		return notice_dt;
	}



	public void setNotice_dt(String notice_dt) {
		this.notice_dt = notice_dt;
	}
	
	
	
	
	
}
