package com.jts.gangstudy.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
			String charge = Integer.toString(bookingService.calcCharge(book));
			costs.add(charge);
		}
				
		mav.addObject("books", books);
		mav.addObject("names", names);
		mav.addObject("costs", costs);
		return mav;
	}
	
	// booking 신청 페이지
	@UserLoginCheck
	@RequestMapping(value = "/make", method = RequestMethod.GET)
	public ModelAndView makeBook(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("booking/makeBook");
		List<String> dates = bookingService.makeDates();
		
		mav.addObject("dates", dates);
		return mav;
	}

	//booking 신청시 처리
	// 결제를 진행한 후 wait 상태로 변경해야 함.
	@ResponseBody
	@RequestMapping(value = "/make", method = RequestMethod.POST)
	public String insertBook(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Booking book = (Booking)session.getAttribute("book");
		session.removeAttribute("book");
		return bookingService.insertDB(book);
	}
	
	// booking 수정 페이지
	@UserLoginCheck
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editBook(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("booking/editBook");
		HttpSession session = request.getSession();
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
		
		if(book == null) {	// 수정 불가능한 경우
			// do nothing.
		} else {			// 수정 가능한 경우
			int charge = bookingService.calcCharge(book);
			ArrayList<String> dates = bookingService.makeDates();
			
			mav.addObject("dates", dates)
			.addObject("book", book)
			.addObject("userID", sUserId.getId())
			.addObject("charge", charge);
		}
		return mav;
	}
	
	// booking 수정시 처리
	// 수정 완료 후 차액에 대해 재결제 처리해야함
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String updateBook(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = (Booking)session.getAttribute("book");
		session.removeAttribute("book");
		Booking preBook = bookingService.getUserWaitBooking(sUserId.getUser_no());
		bookingService.removeBook(preBook);
		return bookingService.insertDB(book);
	}
	
	
	
	// 예약 신청, 변경 등에 대한 최종 확인
	@UserLoginCheck
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ModelAndView bookCheck(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("booking/checkBook");
		HttpSession session = request.getSession();
		User sUserId = (User)session.getAttribute("sUserId");
		int people = Integer.parseInt(request.getParameter("people"));
		String href = request.getParameter("href");
		String startDateTime = request.getParameter("startDate") + " " + request.getParameter("startTime");
		String endDateTime = request.getParameter("endDate") + " " + request.getParameter("endTime");
		Booking book = new Booking(sUserId.getUser_no(), room_no, startDateTime, endDateTime, people, "wait");
		int charge = bookingService.calcCharge(book);
		

		
		// payment에 필요한 클래스를 생성해서 담아야 한다.
		// 여기서 charge, item, url 등을 넘겨줘야 한다. -> 세션으로 보내준다.
		session.setAttribute("book", book);
		
		
		mav.addObject("book", book)
			.addObject("userID", sUserId.getId())
			.addObject("charge", charge)
			.addObject("href", href);
		return mav;
	}

	
	@ResponseBody
	@RequestMapping(value = "/reqStartTime", method = RequestMethod.GET)
	public List<String> reqStartTime(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sUserId = (User)session.getAttribute("sUserId");
		String date = request.getParameter("startDate");
		List<Booking> books = bookingService.viewDate(date);
		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
		books.remove(book);
		List<String> times = bookingService.getUnbookedTimeList(date, books);
		return times;
	}
	
	@ResponseBody
	@RequestMapping(value = "/reqEndTime", method = RequestMethod.GET)
	public List<String> reqEndTime(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sUserId = (User)session.getAttribute("sUserId");
		String startTime = request.getParameter("startTime");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<Booking> books = bookingService.viewDate(endDate);
		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
		books.remove(book);
		List<String> times = bookingService.getEndTimes(books, startDate, startTime, endDate);
		return times;
	}
}
