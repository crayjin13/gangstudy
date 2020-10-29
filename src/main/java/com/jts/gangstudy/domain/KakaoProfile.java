package com.jts.gangstudy.domain;

import org.json.JSONObject;

public class KakaoProfile {
	private String id;
	private String nickname;

	public KakaoProfile(String id, JSONObject properties) {
		super();
		this.id = id;
		this.nickname = properties.getString("nickname");
	}

	public String getId() {
		return id;
	}
	public String getNickname() {
		return nickname;
	}
}
