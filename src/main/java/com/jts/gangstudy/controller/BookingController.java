package com.jts.gangstudy.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Payment;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.service.BookingService;
import com.jts.gangstudy.service.PaymentService;


@Controller
@RequestMapping("/booking")
public class BookingController {
	private final int room_no = 1;
	
	@Autowired
	private BookingService bookingService;
	@Autowired
	private PaymentService paymentService;
	
	// booking shop page - 결제 직전 페이지
	@UserLoginCheck
	@RequestMapping(value = "/make", method = RequestMethod.GET)
	
	public ModelAndView makeBook(HttpServletRequest request, HttpSession session,
			@RequestParam("startDateInput") String startDate, 	@RequestParam("startTimeInput") String startTime,
			@RequestParam("endDateInput") String endDate, 		@RequestParam("endTimeInput") String endTime,
			@RequestParam("userCountInput") String people) {
		ModelAndView mav = new ModelAndView("pages/makecart");
		User sUserId = (User)session.getAttribute("sUserId");
		// check a existing booking
		sUserId.getUser_no();
		Booking book = bookingService.getWaitBooking(sUserId);
		if(book!=null) {
			mav.setViewName("redirect:/booking/check");
		}
		// check a uncharge booking
		List<Booking> books = bookingService.getUserBooking(sUserId, "uncharge");
		for(Booking item : books) {
			bookingService.removeBook(item);
		}
		
		// registry booking in session
		book = new Booking();
		book.setCheck_in(startDate, startTime);
		book.setCheck_out(endDate, endTime);
		book.setPeople(people);
		session.setAttribute("book", book);
		
		// mav add
		String startDateTime = book.getFormattedCI("M월 d일 H시 mm분");
		String endDateTime = book.getFormattedCO("M월 d일 H시 mm분");
		String timeInterval = bookingService.getTimeInterval(book);
		int chargePerPeople = bookingService.getCharge(book) / book.getPeople();
		int point = Integer.parseInt(sUserId.getPoints());
		
		mav.addObject("startDateTime", startDateTime)
		.addObject("endDateTime", endDateTime)
		.addObject("timeInterval", timeInterval)
		.addObject("chargePerPeople", chargePerPeople)
		.addObject("userCount", people)
		.addObject("point", point)
		.addObject("charge", chargePerPeople*Integer.parseInt(people) - point);
		return mav;
	}

	// booking shop page - 결제 요청 시
	@UserLoginCheck
	@ResponseBody
	@RequestMapping(value = "/make", method = RequestMethod.POST)
	public String bookSubmit(HttpServletRequest request, HttpSession session,
			@RequestParam("people") String peoples, @RequestParam("point") String point) {
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = (Booking)session.getAttribute("book");
		// validation check
		int people = Integer.parseInt(peoples);
		if(people < 1 || people > 6) {
			return "?people=error";
		}

		int usePoint = Integer.parseInt(point);
		int maxPoint = Integer.parseInt(sUserId.getPoints());
		if(usePoint > maxPoint || usePoint < 0) {
			return "?point=error";
		}
		if(bookingService.allowsBooking(book) == false) {
			return "?date=error";
		}
		
		// insert DB
		book.setPeople(people);
		book.setUser_no(sUserId.getUser_no());
		book.setRoom_no(room_no);
		book.setState("uncharge");
		String result = bookingService.insertDB(book);
		if(result.equals("duplicate")) {
			return "?booking=duplicate";
		}
		
		// session registry
		int charge = bookingService.getCharge(book) - usePoint;
		Payment payment = new Payment();
		payment.setAmount(charge);
		payment.setPoint(usePoint);
		payment.setBook_no(book.getBook_no());
		payment.setState("ready");
		session.setAttribute("payment", payment);
		
		// 결제 페이지(선택페이지)로 이동
		return "/payment/kakaopay";
	}
	
	// booking order page - 결제 확인 페이지
	@UserLoginCheck
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ModelAndView bookCheck(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("pages/order-details");
		
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = bookingService.getWaitBooking(sUserId);
		
		// payment.getPoint();
		
		if(book==null) {
			mav.addObject("hasBooking", "false");
		} else {
			// book_no와 연결되는 payment를 얻는다.
			Payment payment = paymentService.selectPayment(book.getBook_no());
			
			mav.addObject("hasBooking", "true")
			.addObject("start", book.getFormattedCI("M월 d일 H시 mm분"))
			.addObject("end", book.getFormattedCO("M월 d일 H시 mm분"))
			.addObject("people", book.getPeople())
			.addObject("timeInterval", bookingService.getTimeInterval(book))
			.addObject("hourPrice", bookingService.getHourPrice())
			.addObject("totalCharge", bookingService.getCharge(book))
			.addObject("usedpoint", payment.getPoint());
		}
		mav.addObject("name", sUserId.getName());
		return mav;
	}
		
	// booking modify page - 결제 수정 페이지
	@UserLoginCheck
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView editBook(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("pages/modify");
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = bookingService.getWaitBooking(sUserId);
		
		if(book == null) {	// 수정 불가능한 경우
			mav.setViewName("?booking=null");
		} else {			// 수정 가능한 경우
			Payment payment = paymentService.selectPayment(book.getBook_no());
			
			int charge = payment.getAmount();
			int usedPoint = payment.getPoint();

			// mav add
			String timeInterval = bookingService.getTimeInterval(book);
			
			mav.addObject("startDateInput", book.getciDate())
			.addObject("startTimeInput", book.getciTime())
			.addObject("endDateInput", book.getcoDate())
			.addObject("endTimeInput", book.getcoTime())
			.addObject("people", book.getPeople())
			.addObject("timeInterval", timeInterval)
			.addObject("charge", charge)
			.addObject("usedPoint", usedPoint);
		}
		
		return mav;
	}
	// booking modify page - 결제 직전 페이지
	@UserLoginCheck
	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public ModelAndView editSubmit(HttpServletRequest request, HttpSession session,
			@RequestParam("startDateInput") String startDate, @RequestParam("endDateInput") String endDate,
			@RequestParam("startTimeInput") String startTime, @RequestParam("endTimeInput") String endTime,
			@RequestParam("people") String people) {
		ModelAndView mav = new ModelAndView("pages/modifycart");
		User sUserId = (User)session.getAttribute("sUserId");
		Booking currentBook = bookingService.getWaitBooking(sUserId);
		Booking book = new Booking();
		book.setCheck_in(startDate, startTime);
		book.setCheck_out(endDate, endTime);
		book.setPeople(people);
		
		if(book.getciDateTime().isEqual(currentBook.getciDateTime())&&book.getcoDateTime().isEqual(currentBook.getcoDateTime())) {
			// current == new
			mav.setViewName("redirect:/booking/check");
			return mav;
		}
		Payment payment = paymentService.selectPayment(currentBook.getBook_no());
		// registry booking in session
		session.setAttribute("book", book);
		
		// mav add
		String startDateTime = book.getFormattedCI("M월 d일 H시 mm분");
		String endDateTime = book.getFormattedCO("M월 d일 H시 mm분");
		String timeInterval = bookingService.getTimeInterval(book);
		int charge = bookingService.getCharge(book) - payment.getAmount(); // 추가요금
		String point = sUserId.getPoints();
		
		mav.addObject("startDateTime", startDateTime)
		.addObject("endDateTime", endDateTime)
		.addObject("timeInterval", timeInterval)
		.addObject("userCount", people)
		.addObject("point", point)
		.addObject("charge", charge);
		return mav;
	}
		
		// 유효성 체크 + payment로 요청

		//if(book.getciDateTime().equals(CIDT) && book.getcoDateTime().equals(CODT)) {
		//	return "?date=equal";
		//}
		// 추가로 기존 book과 시간을 비교해서 동일한지 확인

		
		// 기존 예약 cancel로 상태 변경
		// insert DB
//		book.setPeople(people);
//		book.setUser_no(sUserId.getUser_no());
//		book.setRoom_no(room_no);
//		book.setState("uncharge");
//		String result = bookingService.insertDB(book);
//		if(result.equals("duplicate")) {
//			return "?booking=duplicate";
//		}
		
		// 금액은 기존 예약과의 차이만큼 받는다.
		// session registry
//		int charge = bookingService.getCharge(book) - usePoint;
//		Item item = new Item(item_name, 1, charge);
//		session.setAttribute("item", item);
		
		// 결제 페이지(선택페이지)로 이동
//		return "/payment/kakaopay";
		
	
	@ResponseBody
	@RequestMapping(value = "/reqStartTime", method = RequestMethod.GET)
	public List<String> reqStartTime(HttpServletRequest request, HttpSession session) {
		User sUserId = (User)session.getAttribute("sUserId");
		String date = request.getParameter("startDate");
		List<Booking> books = bookingService.viewDate(date);
		Booking book = bookingService.getWaitBooking(sUserId);
		books.remove(book);
		List<String> times = bookingService.getUnbookedTimeList(date, books);
		return times;
	}
	
	@ResponseBody
	@RequestMapping(value = "/reqEndTime", method = RequestMethod.GET)
	public List<String> reqEndTime(HttpServletRequest request, HttpSession session) {
		User sUserId = (User)session.getAttribute("sUserId");
		String startTime = request.getParameter("startTime");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<Booking> books = bookingService.viewDate(endDate);
		Booking book = bookingService.getWaitBooking(sUserId);
		books.remove(book);
		List<String> times = bookingService.getEndTimes(books, startDate, startTime, endDate);
		return times;
	}
}
