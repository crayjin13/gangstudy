package com.jts.gangstudy.domain;

public class KakaoUser {
	private Integer user_no;
	private String id;

	public KakaoUser(Integer user_no, String id) {
		super();
		this.user_no = user_no;
		this.id = id;
	}
	
	public Integer getUser_no() {
		return user_no;
	}

	public String getId() {
		return id;
	}

}
