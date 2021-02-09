package com.jts.gangstudy.domain;

import java.math.BigDecimal;

public class KakaoUser {
	private Integer user_no;
	private String kakao_id;
	
	public KakaoUser(Integer user_no, String kakao_id) {
		super();
		this.user_no = user_no;
		this.kakao_id = kakao_id;
	}
	
	public Integer getUser_no() {
		return user_no;
	}

	public void setUser_no(Integer user_no) {
		this.user_no = user_no;
	}

	public String getKakao_id() {
		return kakao_id;
	}

	public void setKakao_id(String kakao_id) {
		this.kakao_id = kakao_id;
	}
}
