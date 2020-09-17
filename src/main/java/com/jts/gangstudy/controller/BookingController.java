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
	
	@RequestMapping("")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView();
		List<Booking> debug = bookingService.viewAll();
		List<String> dates = bookingService.getDateSelectOptions();
		
		mav.setViewName("booking");
		mav.addObject("debug", debug);
		mav.addObject("dates", dates);
		return mav;
	}

	@RequestMapping(value = "/request", method = RequestMethod.POST)
	public String request(HttpServletRequest request) {
		String book_dt = request.getParameter("book_dt");
		String ci = request.getParameter("ci");
		String co = request.getParameter("co");
		int people = Integer.parseInt(request.getParameter("people"));
		Booking book = bookingService.createBook(book_dt, ci, co, people);
		bookingService.insertBook(book);
		return "redirect:/booking";
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
