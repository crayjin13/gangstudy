package com.jts.gangstudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.controller.UserLoginCheck;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.exception.PasswordMismatchException;
import com.jts.gangstudy.exception.UserNotFoundException;
import com.jts.gangstudy.repository.UserDaoImpl;
import com.jts.gangstudy.service.UserService;
import com.jts.gangstudy.service.BookingService;

@Controller
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookingService bookingService;
	
	@RequestMapping(value ="ubList")
	public ModelAndView UBList() {
		ModelAndView mv = new ModelAndView();
		List<User> userList = userService.userBookingList();
				
		mv.addObject("UBList", userList);
		mv.setViewName("admin_cm");
		System.out.println(userList);
		return mv;
		
	}
	
	
	
	
	
	
}