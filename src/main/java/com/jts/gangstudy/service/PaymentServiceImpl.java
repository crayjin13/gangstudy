package com.jts.gangstudy.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Payment;
import com.jts.gangstudy.domain.Payment.State;
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
	public List<Payment> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public Payment selectPayment(Booking book) {
		return mapper.selectPayment(book.getBook_no());
	}

	@Override
	public void changeState(Payment payment, State state) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pay_no", Integer.toString(payment.getPay_no()));
		map.put("state", state.toString());
		mapper.updateState(map);
	}

	@Override
	public String getDeviceType(HttpServletRequest request) {
		Device device = DeviceUtils.getCurrentDevice(request);
		String deviceType;
		if (device.isMobile()) {
			deviceType = "mobile";
		} else if (device.isTablet()) {
			deviceType = "tablet";
		} else {
			deviceType = "desktop";
		}
		return deviceType;
	}

	@Override
	public int pgDanalCheck(int book_no) {
		
		return mapper.pgDanalCheck(book_no);
	}


	@Override
	public void payByPoint(Booking book, int usePoint) {
		Payment payment = new Payment();
		payment.setAmount(usePoint);
		payment.setPoint(usePoint);
		payment.setPg_name("PointOnly");
		payment.setTid("PointOnly");
		payment.setPay_type("point");
		payment.setState("paid");
		payment.setBook_no(book.getBook_no());
		insertPayment(payment);
	}

	@Override
	public void cancelByPoint(Booking book, Payment payment) {
		changeState(payment, Payment.State.cancelled);
		
	}


	
	
	@Override
	public Payment findByBookNo(List<Payment> payments, Integer book_no) {
		for(Payment payment : payments) {
			if(payment.getBook_no() == book_no) {
				return payment;
			}
		}
		return null;
	}
}
