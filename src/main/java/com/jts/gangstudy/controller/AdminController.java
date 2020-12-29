package com.jts.gangstudy.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.controller.UserLoginCheck;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.exception.PasswordMismatchException;
import com.jts.gangstudy.exception.UserNotFoundException;
import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Command;
import com.jts.gangstudy.service.UserService;
import com.jts.gangstudy.service.AdminService;
import com.jts.gangstudy.service.BookingService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private BookingService bookingService;
	    

	Logger logger;
	
	
	
	
	
	/* 관리자 로그인 */
	@ResponseBody
	@RequestMapping(value = "/sign_in_admin", method =  RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String sign_in_admin(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session,
			Model model, HttpServletRequest request) throws Exception {
		System.out.println(" 관계자 아이디비번값받기  " + "id:" + id + " pw:" + pw);
		String forwardPath = "";
		// String a= request.getSession().getServletContext().getRealPath("/");
		User user = userService.selectAdmin(id);
		// logger.info("�봽濡쒖젥�듃 寃쎈줈 李얘린" + a);

		try {
			User signInuser = userService.signIn(id, pw);
			System.out.println();
			if (signInuser != null) {
				System.out.println(" 성공");
				session.setAttribute("id", id);
				session.setAttribute("name", user.getName());

				session.setAttribute("sUserId", signInuser);
				forwardPath = "true";

			} else {

				forwardPath = "false3";
			}
		} catch (UserNotFoundException e) {
			forwardPath = "false1";
			e.printStackTrace();
		} catch (PasswordMismatchException e) {
			forwardPath = "false2";
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			forwardPath = "false";
		}
		return forwardPath;
	}
	
	@RequestMapping(value = "/jts",  method = {RequestMethod.GET, RequestMethod.POST})
	public String admin() {
		return "pages/admin";
	}
	
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
					.put(book.getPeople() + "紐�")
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
	
	// �쉶�썝 �깉�눜
	@ResponseBody
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public boolean deleteUser(@RequestParam("user_no") Integer user_no) {
		boolean delete = userService.deleteUser(user_no);
		return delete;
	}
	
	// 硫붾え 蹂�寃�
	@ResponseBody
	@RequestMapping(value = "/noteUser", method = RequestMethod.GET)
	public boolean noteUser(@RequestParam("user_no") Integer user_no, @RequestParam("note") String note) {
		boolean update = userService.updateNote(user_no, note);
		return update;
	}
	
	// �룊�젏 蹂�寃�
	@ResponseBody
	@RequestMapping(value = "/rateUser", method = RequestMethod.GET)
	public boolean rateUser(@RequestParam("user_no") Integer user_no, @RequestParam("rate") String rate) {
		boolean update = userService.updateRate(user_no, Float.parseFloat(rate));
		return update;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addCommand", method = RequestMethod.GET)
	public void addCommand(HttpServletRequest request, @RequestParam("message") String msg, @RequestParam("time") String time) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm:ss");
		LocalTime localTime = LocalTime.parse(time, dtf);
		Command command = new Command(msg, localTime);
		adminService.insertCommand(command);
	}

	@ResponseBody
	@RequestMapping(value = "/removeCommand", method = RequestMethod.GET)
	public void removeCommand(HttpServletRequest request, @RequestParam("command_no") String command_no) {
		adminService.deleteCommand(Integer.parseInt(command_no));
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCommands", method = RequestMethod.GET)
	public List<Command> getCommands(HttpServletRequest request) {
		return adminService.selectAll();
	}
	
	// 리모컨 기능
	@ResponseBody
	@RequestMapping(value = "/remote", method = RequestMethod.GET)
	public void remote(HttpServletRequest request, @RequestParam("message") String message) {
		adminService.sendMessage(message);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test() {
		ModelAndView mav = new ModelAndView("notyet/testList");
		return mav;
	}
}