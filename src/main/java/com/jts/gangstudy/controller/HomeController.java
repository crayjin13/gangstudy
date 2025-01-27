package com.jts.gangstudy.controller;

import com.jts.gangstudy.domain.Booking;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpSession session, Model model) {
		ModelAndView mav = new ModelAndView("index");
		Booking book = (Booking)session.getAttribute("book");
		
		if(book != null) {
			mav.addObject("date", book.getCheck_in().toLocalDate())
				.addObject("startTime", book.getCheck_in().toLocalTime())
				.addObject("endTime", book.getCheck_out().toLocalTime())
				.addObject("people", book.getPeople());
			session.removeAttribute("book");
		}
		return mav;
	}      


	// extra work
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public String map() {
		return "/extra/map";     
	}
	
	  

	   
	
	@RequestMapping(value = "/pages/signup", method = RequestMethod.GET)
	public String signup() {
		return "pages/signup";     
	}
	
	
//	이용안내 메인메뉴 바로가기 
	@RequestMapping(value = "/information", method = RequestMethod.GET)
	public String information() {
		return "informations/main"; 
	}   
//	 이용안내 페이지 바로가기 
	@RequestMapping(value = "/info_of_use", method = RequestMethod.GET)      
	public String infoOfUse() {         
		return "informations/info_of_use";          
	}
	
//	 이용안내 환불규정 페이지 바로가기 
	@RequestMapping(value = "/refund_policy", method = RequestMethod.GET)      
	public String refund_policy() {          
		return "informations/refund_policy";          
	}
//	 이용안내 리모콘 이용방법 페이지 바로가기  
	@RequestMapping(value = "/how_to_use", method = RequestMethod.GET)      
	public String how_to_use() {           
		return "informations/how_to_use";          
	}    
//	 이용안내 리모콘 이용방법 페이지 바로가기 
	@RequestMapping(value = "/precautions", method = RequestMethod.GET)      
	public String precautions() {            
		return "informations/precautions";          
	}     
	
//	 결제페이지 
	@RequestMapping(value = "/payment", method = RequestMethod.GET)      
	public String payment() {            
		return "booking/payment";          
	}     
	
//	 결제(old)페이지 
	@RequestMapping(value = "/makecart", method = RequestMethod.GET)      
	public String makecart() {            
		return "pages/makecart";            
	}       
	
	  
	@RequestMapping(value = "/send", method = RequestMethod.POST)    
	public String send() {       
		return "pages/send";           
	}    
	       
	
	
	@RequestMapping(value = "/user_bookingList", method = RequestMethod.GET)      
	public String user_bookingList() {         
		return "_User/user_bookingList";         
	}
	
	@RequestMapping(value = "/change_booking", method = RequestMethod.GET)      
	public String change_booking() {         
		return "booking/change_booking";         
	}
	   
	
	
                 
	 
	 
	
	

	         
	                
	     

   
	
	

	
	
	

	
}
