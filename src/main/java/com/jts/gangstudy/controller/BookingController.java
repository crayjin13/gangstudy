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
import com.jts.gangstudy.domain.Item;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.service.BookingService;
import com.jts.gangstudy.service.UserService;


@Controller
@RequestMapping("/booking")
public class BookingController {
	private final int room_no = 1;
	private final String item_name = "스터디룸 예약";
	
	@Autowired
	private BookingService bookingService;
	@Autowired
	private UserService userService;
	
	// booking shop page - 결제 직전 페이지
	@UserLoginCheck
	@RequestMapping(value = "/make", method = RequestMethod.GET)
	
	public ModelAndView makeBook(HttpServletRequest request, HttpSession session,
			@RequestParam("startDateInput") String startDate, 	@RequestParam("startTimeInput") String startTime,
			@RequestParam("endDateInput") String endDate, 		@RequestParam("endTimeInput") String endTime,
			@RequestParam("userCountInput") String people) {
		ModelAndView mav = new ModelAndView("pages/shoppingcart");
		User sUserId = (User)session.getAttribute("sUserId");
		// check a existing booking
		sUserId.getUser_no();
		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
		if(book!=null) {
			mav.setViewName("redirect:/booking/check");
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
		String point = sUserId.getPoints();
		
		mav.addObject("startDateTime", startDateTime)
		.addObject("endDateTime", endDateTime)
		.addObject("timeInterval", timeInterval)
		.addObject("chargePerPeople", chargePerPeople)
		.addObject("userCount", people)
		.addObject("point", point)
		.addObject("charge", chargePerPeople*Integer.parseInt(people));
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
		// 시간 유효성 체크
		// 시간 유효성 체크
		// 시간 유효성 체크
		// 시간 유효성 체크
		// 시간 유효성 체크
		// 시간 유효성 체크
		// 시간 유효성 체크
		
		
		
		
		
		
		
	
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
		Item item = new Item(item_name, 1, charge);
		session.setAttribute("item", item);
		
		// 결제 페이지(선택페이지)로 이동
		return "/payment/kakaopay";
	}
	
	// booking order page - 결제 확인 페이지
	@UserLoginCheck
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ModelAndView bookCheck(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("pages/order-details");
		
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
		if(book==null) {
			mav.addObject("hasBooking", "false");
		} else {
			mav.addObject("hasBooking", "true")
			.addObject("start", book.getFormattedCI("M월 d일 h시 mm분"))
			.addObject("end", book.getFormattedCO("M월 d일 h시 mm분"))
			.addObject("people", book.getPeople())
			.addObject("timeInterval", bookingService.getTimeInterval(book))
			.addObject("hourPrice", bookingService.getHourPrice())
			.addObject("totalCharge", bookingService.getCharge(book));
		}
		mav.addObject("name", sUserId.getName());
		return mav;
	}
		
	// booking modify page - 결제 수정 페이지
	@UserLoginCheck
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView editBook(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("pages/modifycart");
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
		
		if(book == null) {	// 수정 불가능한 경우
			mav.setViewName("/");
		} else {			// 수정 가능한 경우
			int charge = bookingService.getCharge(book); // 결제 정보를 통해서 알아내야함
			int usedPoint = 0; // 결제 정보를 통해서 알아내야함

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
//		User sUserId = (User)session.getAttribute("sUserId");
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
//		User sUserId = (User)session.getAttribute("sUserId");
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
