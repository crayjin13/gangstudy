package com.jts.gangstudy.mapper;

import java.util.HashMap;

import com.jts.gangstudy.domain.Payment;

public interface PaymentMapper {
	public void insertPayment(Payment payment);
	public Payment selectPayment(int book_no);
	public void updateState(HashMap<String, String> map);
	public int pgDanalCheck(int book_no);
}
