package com.jts.gangstudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Payment;
import com.jts.gangstudy.mapper.PaymentMapper;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentMapper mapper;

	@Override
	public void insertPayment(Payment payment) {
		mapper.insertPayment(payment);
		
	}
	
	@Override
	public Payment selectPayment(int book_no) {
		return mapper.selectPayment(book_no);
	}
}
