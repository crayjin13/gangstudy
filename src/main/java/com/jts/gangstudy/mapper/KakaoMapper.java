package com.jts.gangstudy.mapper;

import java.util.List;

import com.jts.gangstudy.domain.KakaoUser;

public interface KakaoMapper {
	public void insertKakaoUser(KakaoUser kakaoUser);

	public List<KakaoUser> selectKakaoUser(String kakao_id);
}
