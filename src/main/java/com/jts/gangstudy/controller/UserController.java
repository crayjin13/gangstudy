package com.jts.gangstudy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/login")
public class UserController {
	@Autowired
	private UserService userService;
	
	
	
	@RequestMapping("")
	public ModelAndView main() {
		ModelAndView m = new ModelAndView();
		List<User> userList = userService.UserList();
		
		//m.addObject("data", userService.UserList());
		m.addObject("list", userList);
		m.setViewName("login");
		System.out.println(userList);
		return m;
		
	}
	
	
	/*
	 * @RequestMapping(value="/sign_up", method = RequestMethod.POST,
	 * produces="text/plain; charset=UTF-8") public String sign_up(User user) {
	 * 
	 * boolean newUser = userService.insertUser(user); if(newUser) { newUser = true;
	 * }else { newUser = false; }
	 * 
	 * return newUser+""; }
	 */
	/*
	@RequestMapping(value="/selectById", method=RequestMethod.POST)
	public ModelAndView selectById(@Param("id")String id) {
		ModelAndView mv = new ModelAndView();
		userService.selectById("id");
		mv.addObject("id",id);
		mv.setViewName("home");
		return mv;
		
	}
	*/
	
	
	
	@RequestMapping(value = "/request", method = RequestMethod.POST)
	public String request(HttpServletRequest request) {
		
		
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String bod = request.getParameter("bod");
		String gender = request.getParameter("gender");
		int rate = Integer.parseInt(request.getParameter("rate"));
		String points = request.getParameter("points");
		String note = request.getParameter("note");
		
		
		
    	User user = userService.User(name, phone, id, pw, email, bod, gender, rate, points, note);
		
		boolean newUser = userService.insertUser(user);
		if(newUser) {
			newUser= true;
		}else{
			newUser= false;
			}
	return newUser+"redirect:/login";
	}
	
	
}
