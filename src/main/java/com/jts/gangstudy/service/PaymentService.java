package com.jts.gangstudy.service;

import com.jts.gangstudy.domain.Payment;

public interface PaymentService {
	public void insertPayment(Payment payment);
	public Payment selectPayment(int book_no);
}
