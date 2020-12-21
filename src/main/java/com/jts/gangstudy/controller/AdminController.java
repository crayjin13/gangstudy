package com.jts.gangstudy.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.service.UserService;
import com.jts.gangstudy.service.BookingService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BookingService bookingService;
	
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ModelAndView showBooks() {
		ModelAndView mav = new ModelAndView("pages/showAllBook");
		List<Booking> books = bookingService.searchAll();
		
		JSONArray array = new JSONArray();
		for(Booking book : books) {
			String name = userService.getUser(book.getUser_no()).getName();
			array.put(
					new JSONArray()
					.put(book.getBook_no())
					.put(name)
					.put(book.getCheck_in().toLocalDate().toString() + " " +
						book.getCheck_in().toLocalTime().toString())
					.put(book.getCheck_out().toLocalDate().toString() + " " +
						book.getCheck_out().toLocalTime().toString())
					.put(book.getPeople() + "명")
					.put(book.getState())
					.put(book.getRequest_dt())
					);
		}

		mav.addObject("books", array);
		return mav;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView showUsers() {
		ModelAndView mav = new ModelAndView("pages/showAllUser");
		List<User> users = userService.selectAll();
		
		JSONArray array = new JSONArray();
		for(User user : users) {
			array.put(
					new JSONArray()
					.put(user.getUser_no())
					.put(user.getId())
					.put(user.getName())
					.put(user.getPhone())
					.put(user.getEmail())
					.put(user.getBod())
					.put(user.getGender())
					.put(user.getPoints())
					.put(user.getRate())
					.put(user.getNote())
					.put(new JSONObject().put("user_no", user.getUser_no()) )
					);
		}

		mav.addObject("users", array);
		return mav;
	}
	
	// 회원 탈퇴
	@ResponseBody
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public boolean deleteUser(@RequestParam("user_no") Integer user_no)
			throws Exception {
		boolean delete = userService.deleteUser(user_no);
		if (delete) {    
			System.out.println("유저 탈퇴 성공");
		} else {
			System.out.println("탈퇴 실패");
		}
		return delete;
	}
}