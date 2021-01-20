package com.jts.gangstudy.service;

import javax.servlet.http.HttpServletRequest;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Payment;
import com.jts.gangstudy.domain.Payment.State;

public interface PaymentService {
	public void insertPayment(Payment payment);
	public Payment selectPayment(Booking book);
	void changeState(Payment payment, State state);
	public String getDeviceType(HttpServletRequest request);
	
	public int pgDanalCheck(int book_no);
	public void payByPoint(Booking book, int usePoint);
	public void cancelByPoint(Booking book, Payment payment);
}
