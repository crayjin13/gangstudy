package com.jts.gangstudy.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Booking;
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
	public Payment selectPayment(Booking book) {
		return mapper.selectPayment(book.getBook_no());
	}

	@Override
	public void changeState(Payment payment, String state) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pay_no", Integer.toString(payment.getPay_no()));
		map.put("state", state);
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
}
