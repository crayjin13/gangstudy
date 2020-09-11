package com.jts.gangstudy.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
 
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value="/sign_up", method = RequestMethod.POST, produces="text/plain; charset=UTF-8")
	public String sign_up(User user) {
		
		boolean newUser = userService.insertUser(user);
			if(newUser) {
				newUser = true;
			}else {
				newUser = false;
			}
		
			return newUser+"";	
	}
	
	
	@RequestMapping(value="/selectById", method=RequestMethod.POST)
	public ModelAndView selectById(@Param("id")String id) {
		ModelAndView mv = new ModelAndView();
		userService.selectById("id");
		mv.addObject("id",id);
		mv.setViewName("home");
		return mv;
		
	}
	
	
	
}
