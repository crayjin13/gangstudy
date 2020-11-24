package com.jts.gangstudy.service;

import java.util.HashMap;

import com.jts.gangstudy.domain.Payment;

public interface KakaoPayService {
	public HashMap<String, String> ready(String domain, String deviceType, Payment payment);

	public HashMap<String, String> getPayInfo(String tid, String pg_token);
	
	public HashMap<String, String> cancel(String tid, String amount);
}
