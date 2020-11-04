package com.jts.gangstudy.domain;

public class KakaoUser {
	private String id;
	private String refresh_token;

	public KakaoUser(String id, String refresh_token) {
		super();
		this.id = id;
		this.refresh_token = refresh_token;
	}

	public String getId() {
		return id;
	}
	public String getToken() {
		return refresh_token;
	}
}
