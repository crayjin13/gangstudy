package com.jts.gangstudy.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Item;
import com.jts.gangstudy.service.BookingService;
import com.jts.gangstudy.service.KakaoPayService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private KakaoPayService kakaoPayService;
	@Autowired
	private BookingService bookingService;

	// 결제 수단 선택
	@RequestMapping(value = "/choose", method = RequestMethod.GET)
	public ModelAndView choose(HttpServletRequest request) {
		// 상품명
		// 상품 수량
		// 상품 총액
		// 상품 비과세 금액?
		// redirect url
		// 취소 url
		//
		ModelAndView mav = new ModelAndView("/payment/choose");
		return mav;
	}

	@RequestMapping(value = "/kakaopay", method = RequestMethod.GET)
	public String kakaopay(HttpServletRequest request, HttpSession session) {
		Item item = (Item)session.getAttribute("item");
		Device device = DeviceUtils.getCurrentDevice(request);
		String deviceType;
		if (device.isMobile()) {
			deviceType = "mobile";
		} else if (device.isTablet()) {
			deviceType = "tablet";
		} else {
			deviceType = "desktop";
		}
		String domain = request.getScheme()+"://"+request.getServerName() + ":" +request.getServerPort();
		HashMap<String, String> map = kakaoPayService.ready(domain, deviceType, item);
		
		session.setAttribute("tid", map.get("tid"));
		return "redirect:" + map.get("url");
	}

	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	public String complete(HttpServletRequest request, HttpSession session) {
		// 결제 정보를 저장한다.
		String tid = (String)session.getAttribute("tid");
		String pg_token = request.getParameter("pg_token");
		kakaoPayService.getPayInfo(tid, pg_token);

		// 예약에 대해서 uncharge -> wait으로 변경한다.
		Booking book = (Booking)session.getAttribute("book");
		bookingService.changeState(book, "wait");
		
		session.removeAttribute("item");
		session.removeAttribute("book");
		session.removeAttribute("tid");
		
		// 완료 페이지로 이동한다.
		return "redirect:" + "/booking/check";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request, HttpSession session) {
		return "home";
	}
}