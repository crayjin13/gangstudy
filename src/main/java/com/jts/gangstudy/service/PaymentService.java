package com.jts.gangstudy.service;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Payment;

public interface PaymentService {
	public void insertPayment(Payment payment);
	public Payment selectPayment(Booking book);
	public void changeState(Payment payment, String state);
}