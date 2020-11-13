package com.jts.gangstudy.service;

import java.util.HashMap;

import com.jts.gangstudy.domain.Item;

public interface KakaoPayService {
	public HashMap<String, String> ready(String domain, String deviceType, Item item);

	public void getPayInfo(String tid, String pg_token);
}
