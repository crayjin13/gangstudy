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
public class UserController {
	@Autowired
	private UserService userService;
	
	
	
	// 유저 목록 (
	@RequestMapping(value ="/login")
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
	//회원가입 
		@ResponseBody
		@RequestMapping(value = "/signUp", method = RequestMethod.POST, produces="text/plain; charset=UTF-8")
		public String signUp(User user) {
			
			System.out.println(user);
			boolean newUser = userService.insertUser(user);
			if(newUser) {
				newUser= true;
			}else{
				newUser= false;
				}
			System.out.println(newUser);
			
		return newUser+"";
	}
	
	*/

	//회원가입 
		@RequestMapping(value = "/signUp", method = RequestMethod.POST)
		public ModelAndView signUp(HttpServletRequest request) {
			ModelAndView m = new ModelAndView();
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String email = request.getParameter("email");
			String bod = request.getParameter("bod");
			String gender = request.getParameter("gender");
			

			User user= new User(name, phone, id, pw, email, bod, gender);
			boolean signUp = userService.insertUser(user);
			m.addObject("signUp", signUp);
			m.setViewName("login");
			return m ;
		}
		

	
	
	
	}

	
	
	
	

