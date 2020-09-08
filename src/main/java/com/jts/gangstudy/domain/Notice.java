package com.jts.gangstudy.domain;

public class Notice {

	private Integer notice_no;
	private String admin_id;
	private String title;
	private Integer views;
	private String content;
	private String nDate;
	
	
	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Notice(Integer notice_no, String admin_id, String title, Integer views, String content, String nDate) {
		super();
		this.notice_no = notice_no;
		this.admin_id = admin_id;
		this.title = title;
		this.views = views;
		this.content = content;
		this.nDate = nDate;
	}


	public Integer getNotice_no() {
		return notice_no;
	}


	public void setNotice_no(Integer notice_no) {
		this.notice_no = notice_no;
	}


	public String getAdmin_id() {
		return admin_id;
	}


	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
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


	public String getnDate() {
		return nDate;
	}


	public void setnDate(String nDate) {
		this.nDate = nDate;
	}


	
	
	
	
	
	
}
