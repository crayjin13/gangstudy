package com.jts.gangstudy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.service.BookingService;


@Controller
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView booking() {
		ModelAndView mav = new ModelAndView();
		List<Booking> debug = bookingService.viewAll();
		List<String> dates = bookingService.getDateSelectOptions();
		
		mav.setViewName("booking");
		mav.addObject("debug", debug);
		mav.addObject("dates", dates);
		return mav;
	}

	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ModelAndView bookCheck(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String book_dt = request.getParameter("book_dt");
		String ci = request.getParameter("ci");
		String co = request.getParameter("co");
		int people = Integer.parseInt(request.getParameter("people"));
		Booking book = new Booking(1, 1, book_dt, ci, co, people, "wait");
		int charge = bookingService.getCharge(ci, co, people);
		
		mav.setViewName("bookCheck");
		mav.addObject("book", book);
		mav.addObject("userID", "userid");
		mav.addObject("charge", charge);
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public synchronized String insertBook(HttpServletRequest request) {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		int room_no = Integer.parseInt(request.getParameter("room_no"));
		String book_dt = request.getParameter("book_dt");
		String ci = request.getParameter("ci");
		String co = request.getParameter("co");
		int people = Integer.parseInt(request.getParameter("people"));
		Booking book = new Booking(user_id, room_no, book_dt, ci, co, people, "uncharge");
		
		// 중복체크
		int result = bookingService.duplicateCheck(book);
		if(result == 0) { // 성공 시 데이터 추가, 결재 페이지로 넘기기
			bookingService.insertBook(book);
			return "true";
		} else { // 실패시 실패 이유 보내기
			return "duplicate";
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
