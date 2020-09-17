package com.jts.gangstudy.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.exception.PasswordMismatchException;
import com.jts.gangstudy.exception.UserNotFoundException;
import com.jts.gangstudy.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/")
	public String index() {
		return "";
	}

	

	Logger logger;

	/* 로그인 */
	@ResponseBody
	@RequestMapping(value = "/sign_in_action", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String sign_in_action_post(@RequestParam("id") String id, @RequestParam("pw") String pw,
			HttpSession session, Model model, HttpServletRequest request) {
		System.out.println("################로그인 컨트롤러 테스트" + "id:" + id + " pw:" + pw);
		String forwardPath = "";
		// String a= request.getSession().getServletContext().getRealPath("/");
		User user = userService.selectById(id);
		// logger.info("프로젝트 경로 찾기" + a);

		try {
			User signInuser = userService.signIn(id, pw);
			System.out.println();
			if (signInuser != null) {
				
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
		/*
		 * if(user.getmRetire()=="off"){
		 * System.out.println("## 비활성화된 계정으로 로그인 할 수 없음"); //forwardPath = 계정 활성화 창으로
		 * 포워딩 }
		 */
		return forwardPath;
	}
	
	
	/*로그아웃*/
	@ResponseBody
	@RequestMapping(value="/sign_out_action")
	public String sign_out_action(HttpSession session) {
		System.out.println("sign_out_action 컨트롤러 테스트");
		session.invalidate();
		return "sign_in";
	}
	
	

	// 유저 목록 (
	@RequestMapping(value = "/login")
	public ModelAndView main() {
		ModelAndView m = new ModelAndView();
		List<User> userList = userService.UserList();

		// m.addObject("data", userService.UserList());
		m.addObject("list", userList);
		m.setViewName("login");
		System.out.println(userList);
		return m;

	}

	// 회원가입
	@ResponseBody
	@RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String signUp(User user) {

		System.out.println(user);
		boolean newUser = userService.insertUser(user);

		if (newUser) {
			newUser = true;
		} else {
			newUser = false;
		}
		System.out.println(newUser);

		return newUser + "";
	}

	/*
	 * //회원가입
	 * 
	 * @RequestMapping(value = "/signUp", method = RequestMethod.POST) public
	 * ModelAndView signUp(HttpServletRequest request) { ModelAndView m = new
	 * ModelAndView(); String name = request.getParameter("name"); String phone =
	 * request.getParameter("phone"); String id = request.getParameter("id"); String
	 * pw = request.getParameter("pw"); String email =
	 * request.getParameter("email"); String bod = request.getParameter("bod");
	 * String gender = request.getParameter("gender");
	 * 
	 * 
	 * User user= new User(name, phone, id, pw, email, bod, gender); boolean signUp
	 * = userService.insertUser(user); m.addObject("signUp", signUp);
	 * m.setViewName("login"); return m ; }
	 */

}
