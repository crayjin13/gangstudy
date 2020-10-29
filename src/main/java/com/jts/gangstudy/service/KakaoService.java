package com.jts.gangstudy.service;

import com.jts.gangstudy.domain.KakaoProfile;

public interface KakaoService {
	
	public String getLoginUrl();
	
	public String getAccessToken(String code);

	public KakaoProfile getProfile(String access_token);

}
