package com.jts.gangstudy.controller;

import java.time.format.DateTimeFormatter;
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
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.service.BookingService;
import com.jts.gangstudy.service.KakaoPayService;
import com.jts.gangstudy.service.UserService;


@Controller
@RequestMapping("/booking")
public class BookingController {
	private static final int room_no = 1;
	
	@Autowired
	private BookingService bookingService;
	@Autowired
	private UserService userService;
	@Autowired
	private KakaoPayService kakaoPayService;
	
	// booking main page
	@UserLoginCheck
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView bookMain() {
		ModelAndView mav = new ModelAndView("booking/bookMain");
		List<Booking> books = bookingService.viewAll();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> costs = new ArrayList<String>();
		for(Booking book : books) {
			int user_no = book.getUser_no();
			User user = userService.getUser(user_no);
			String user_name = user.getName();
			names.add(user_name);
		}
		for(Booking book : books) {
			String charge = Integer.toString(bookingService.getCharge(book));
			costs.add(charge);
		}
				
		mav.addObject("books", books);
		mav.addObject("names", names);
		mav.addObject("costs", costs);
		return mav;
	}
	
	// booking shop page
	@UserLoginCheck
	@RequestMapping(value = "/make", method = RequestMethod.GET)
	
	public ModelAndView makeBook(HttpServletRequest request, HttpSession session,
			@RequestParam("startDateInput") String startDate, 	@RequestParam("startTimeInput") String startTime,
			@RequestParam("endDateInput") String endDate, 		@RequestParam("endTimeInput") String endTime,
			@RequestParam("userCountInput") String people) {
		ModelAndView mav = new ModelAndView("pages/shoppingcart");
		User sUserId = (User)session.getAttribute("sUserId");
		
		Booking book = new Booking();
		book.setCheck_in(startDate, startTime);
		book.setCheck_out(endDate, endTime);
		book.setPeople(1);
		session.setAttribute("book", book);
		
		String startDateTime = book.getFormattedCI("M월 d일 H시 mm분");
		String endDateTime = book.getFormattedCO("M월 d일 H시 mm분");
		String timeInterval = bookingService.getTimeInterval(book);
		int chargePerPeople = bookingService.getCharge(book);
		String point = sUserId.getPoints();
		
		mav.addObject("startDateTime", startDateTime);
		mav.addObject("endDateTime", endDateTime);
		mav.addObject("timeInterval", timeInterval);
		mav.addObject("chargePerPeople", chargePerPeople);
		mav.addObject("userCount", people);
		mav.addObject("point", point);
		mav.addObject("charge", chargePerPeople*Integer.parseInt(people));
		return mav;
	}

	// 예약 신청 전에 수정을 한번 더 거치므로 거기서 처리해야 될 것들.
	// 유효성 검사 필요
	// 세션에 넘기기 전에 요금을 계산해둔다.
	// 세션에 넘기고 결제 페이지로 넘어간다.
	
	
	// booking order page
	@UserLoginCheck
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ModelAndView bookCheck(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("pages/order-details");
		
		User sUserId = (User)session.getAttribute("sUserId");
		System.out.println(sUserId.getUser_no());
		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
		mav.addObject("name", sUserId.getName());
		mav.addObject("start", book.getFormattedCI("M월 d일 h시 mm분"));
		mav.addObject("end", book.getFormattedCO("M월 d일 h시 mm분"));
		mav.addObject("people", book.getPeople());
		mav.addObject("timeInterval", bookingService.getTimeInterval(book));
		mav.addObject("hourPrice", bookingService.getHourPrice());
		mav.addObject("totalCharge", bookingService.getCharge(book));
		return mav;
	}
		
	// booking modify page
	@UserLoginCheck
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView editBook(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("pages/modifycart");
		
//		User sUserId = (User)session.getAttribute("sUserId");
//		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
//		
//		if(book == null) {	// 수정 불가능한 경우
//			// do nothing.
//		} else {			// 수정 가능한 경우
//			int charge = bookingService.getCharge(book);
//			ArrayList<String> dates = bookingService.makeDates();
//			
//			mav.addObject("dates", dates)
//			.addObject("book", book)
//			.addObject("userID", sUserId.getId())
//			.addObject("charge", charge);
//		}
		
		return mav;
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	//booking 신청시 처리
	// 결제를 진행한 후 wait 상태로 변경해야 함.
	@ResponseBody
	@RequestMapping(value = "/make", method = RequestMethod.POST)
	public String insertBook(HttpServletRequest request, HttpSession session) {
		Booking book = (Booking)session.getAttribute("book");
		session.removeAttribute("book");
		return bookingService.insertDB(book);
	}
	
	
	// booking 수정시 처리
	// 수정 완료 후 차액에 대해 재결제 처리해야함
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String updateBook(HttpServletRequest request, HttpSession session) {
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = (Booking)session.getAttribute("book");
		session.removeAttribute("book");
		Booking preBook = bookingService.getUserWaitBooking(sUserId.getUser_no());
		bookingService.removeBook(preBook);
		return bookingService.insertDB(book);
	}
	
	
	
	

	
	@ResponseBody
	@RequestMapping(value = "/reqStartTime", method = RequestMethod.GET)
	public List<String> reqStartTime(HttpServletRequest request, HttpSession session) {
		User sUserId = (User)session.getAttribute("sUserId");
		String date = request.getParameter("startDate");
		List<Booking> books = bookingService.viewDate(date);
//		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
//		books.remove(book);
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
//		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
//		books.remove(book);
		List<String> times = bookingService.getEndTimes(books, startDate, startTime, endDate);
		return times;
	}
}
