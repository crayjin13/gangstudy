package com.jts.gangstudy.mapper;

import java.util.HashMap;
import java.util.List;

import com.jts.gangstudy.domain.Payment;

public interface PaymentMapper {
	public void insertPayment(Payment payment);
	public List<Payment> selectAll();
	public Payment selectPayment(int book_no);
	public void updateState(HashMap<String, String> map);
	public int pgDanalCheck(int book_no);
}
