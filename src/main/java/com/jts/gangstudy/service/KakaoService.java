package com.jts.gangstudy.service;

import com.jts.gangstudy.domain.KakaoProfile;
import com.jts.gangstudy.domain.KakaoUser;

public interface KakaoService {
	
	public String getLoginUrl();
	
	public String getAccessToken(String code);

	public KakaoProfile getProfile(String access_token);

	public void insertKakaoUser(KakaoUser kakaoUser);
}
