package com.jts.gangstudy.controller;

import java.util.List;

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


@Controller
@RequestMapping("/booking")
public class BookingController {
	private static final int room_no = 1;
	
	@Autowired
	private BookingService bookingService;
	
	@UserLoginCheck
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView booking() {
		ModelAndView mav = new ModelAndView();
		List<Booking> debug = bookingService.viewAll();
		List<String> dates = bookingService.getDateSelectOptions();
		
		mav.addObject("debug", debug);
		mav.addObject("dates", dates);
		mav.setViewName("booking");
		return mav;
	}

	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ModelAndView bookCheck(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sUserId = (User)session.getAttribute("sUserId");
		
		String book_dt = request.getParameter("book_dt");
		String ci = request.getParameter("ci");
		String co = request.getParameter("co");
		int people = Integer.parseInt(request.getParameter("people"));
		int charge = bookingService.getCharge(ci, co, people);
		Booking book = new Booking(sUserId.getUser_no(), room_no, book_dt, ci, co, people, "wait");

		session.setAttribute("book", book);

		ModelAndView mav = new ModelAndView();
		mav.addObject("book", book);
		mav.addObject("userID", sUserId.getId());
		mav.addObject("charge", charge);
		mav.setViewName("bookCheck");
		return mav;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView bookEdit(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = bookingService.getUserWaitBooking(sUserId.getUser_no());
		int charge = bookingService.getCharge(book.getCi(), book.getCo(), book.getPeople());
		
		ModelAndView mav = new ModelAndView();
		List<String> dates = bookingService.getDateSelectOptions();
		mav.addObject("book", book);
		mav.addObject("userID", sUserId.getId());
		mav.addObject("charge", charge);
		mav.addObject("dates", dates);
		mav.setViewName("bookEdit");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String insertBook(HttpServletRequest request) {
		HttpSession session = request.getSession(false); // session이 null 인 경우 login으로 핸들링 필요
		Booking book = (Booking)session.getAttribute("book");
		session.removeAttribute("book");
		// 중복체크
		synchronized(this) {
			int result = bookingService.duplicateCheck(book);
			if(result == 0) { // 성공 시 데이터 추가, 결재 페이지로 넘기기
				bookingService.insertBook(book);
				return "true";
			} else { // 실패시 실패 이유 보내기
				return "duplicate";
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCI", method = RequestMethod.GET)
	public List<String> getCI(HttpServletRequest request) {
		String date = request.getParameter("date");
		List<Booking> books = bookingService.getDateBooking(date);
		List<String> times = bookingService.getCITimes(books);
		return times;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCO", method = RequestMethod.GET)
	public List<String> getCO(HttpServletRequest request, String[] options) {
		String ci = request.getParameter("ci");
		List<String> times = bookingService.getCOTimes(ci, options);
		return times;
	}
}
