package com.jts.gangstudy.service;

import com.jts.gangstudy.domain.KakaoUser;
import com.jts.gangstudy.domain.User;

public interface KakaoService {
	
	public String getLoginUrl();
	
	public String getAccessToken(String code);

	public User getProfile(String access_token);

	public void insertKakaoUser(KakaoUser kakaoUser);
}
