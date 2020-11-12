package com.jts.gangstudy.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "pages/index";
		   
	
	}      
	  
	
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin() {
		return "pages/signin";
	}
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		return "pages/signup";
	}
	   
	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public String forgot() {
		return "pages/forgot";
	}
	@RequestMapping(value = "/remo-control", method = RequestMethod.GET)
	public String remo() {
		return "pages/remo-control";
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admincontorl() {
		return "pages/admin";
	}
	@RequestMapping(value = "/shoppingcart", method = RequestMethod.GET)
	public String shoppingcart() {
		return "pages/shoppingcart";
	}
	@RequestMapping(value = "/order-details", method = RequestMethod.GET)
	public String orderDetails() {
		return "pages/order-details";
	}
	
	@RequestMapping(value = "/modifycart", method = RequestMethod.GET)
	public String modifycart() {
		return "pages/modifycart";
	}
	@RequestMapping(value = "/edit-user", method = RequestMethod.GET)
	public String edituser() {
		return "pages/edit-user";
	}
	
	
	
}
