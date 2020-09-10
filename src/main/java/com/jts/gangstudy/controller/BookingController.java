package com.jts.gangstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jts.gangstudy.service.BookingService;


@Controller
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@RequestMapping("")
	public String main(Model model) {
		model.addAttribute("data", bookingService.viewAll());
		return "booking";
	}
	
}
