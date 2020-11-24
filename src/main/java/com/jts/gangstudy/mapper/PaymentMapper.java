package com.jts.gangstudy.mapper;

import com.jts.gangstudy.domain.Payment;

public interface PaymentMapper {
	public void insertPayment(Payment payment);
	public Payment selectPayment(int book_no);
}
