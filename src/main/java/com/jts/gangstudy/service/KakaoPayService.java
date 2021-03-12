package com.jts.gangstudy.service;

import java.util.HashMap;

import com.jts.gangstudy.domain.Payment;

public interface KakaoPayService {
	public HashMap<String, String> ready(String deviceType, int amount, int book_no);

	public HashMap<String, String> getPayInfo(String tid, String pg_token);
	
	public String cancel(String tid, String amount);
}
