package com.jts.gangstudy.controller;

import com.jts.gangstudy.controller.UserLoginCheck;
import com.jts.gangstudy.domain.Booking;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.service.KakaoService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	KakaoService kakaoService;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpSession session, Model model) {
		ModelAndView mav = new ModelAndView("pages/index");
		Booking book = (Booking)session.getAttribute("book");
		
		if(book != null) {
			mav.addObject("startDate", book.getCheck_in().toLocalDate())
				.addObject("startTime", book.getCheck_in().toLocalTime())
				.addObject("endDate", book.getCheck_out().toLocalDate())
				.addObject("endTime", book.getCheck_out().toLocalTime())
				.addObject("people", book.getPeople());
			session.removeAttribute("book");
		}
		return mav;
	}      
	  
	
	

	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public String forgot() {
		return "pages/forgot";
	}
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin() {
		return "pages/signin";
	}
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		return "pages/signup";
	}

	
	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public String notice() {
		return "pages/notice";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "views/login";
	}
	
	
	
	

	@RequestMapping(value = "/admin-signin", method = RequestMethod.GET)
	public String adminSingin() {
		return "pages/admin-signin";
	}
	@RequestMapping(value = "/admin-timeline", method = RequestMethod.GET)
	public String admintimeline() {
		return "pages/admin-timeline";
	}
	@RequestMapping(value = "/admin-charts", method = RequestMethod.GET)
	public String adminChart() {
		return "pages/admin-charts";
	}
	@RequestMapping(value = "/admin-blackList", method = RequestMethod.GET)
	public String adminBlackList() {
		return "pages/admin-blackList";
	}
	@RequestMapping(value = "/admin-userList", method = RequestMethod.GET)
	public String adminUserList() {
		return "pages/admin-userList";
	}
	@RequestMapping(value = "/admin-bookinglist", method = RequestMethod.GET)
	public String adminbooking() {
		return "pages/admin-bookinglist";
	}
	    
	  
	     

	@RequestMapping(value = "/html", method = RequestMethod.GET)
	public String html() {
		return "notyet/html";
	}
	  
	

	
	
	@RequestMapping(value = "/privacyPolicy", method = RequestMethod.GET)
	public String privacyPolicy() {
		return "partials/privacyPolicy";
	}
	@RequestMapping(value = "/TermsOfService", method = RequestMethod.GET)
	public String TermsOfService() {
		return "partials/TermsOfService";
	}

	
	
	

	
}
