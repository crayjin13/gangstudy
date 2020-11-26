package com.jts.gangstudy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.domain.KakaoUser;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.service.KakaoService;
import com.jts.gangstudy.service.UserService;

@Controller
@RequestMapping("/kakao")
public class KakaoController {
	@Autowired
	private KakaoService kakaoService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("redirect:"+kakaoService.getLoginUrl());
		return mav;
	}

	@RequestMapping(value = "/oauth", method = RequestMethod.GET)
	public String oauth(HttpServletRequest request, HttpSession session) throws Exception {
		String code = request.getParameter("code");
		String error = request.getParameter("error");
		
		if(error == "access_denied") {	// 사용자 로그인 취소 or 만 14세 미만 사용자의 보호자 동의 실패
			return "?kakaologin=access_denied";
		} else {
			String access_token = kakaoService.getAccessToken(code);
	        User profile = kakaoService.getProfile(access_token);
	        String user_id = profile.getId();
	        
	        System.out.println("[debug] user_id : " + user_id);
	        
			boolean isdup = kakaoService.isDuplicate(user_id);
			// signup
			if(isdup == false) {
				userService.insertUser(profile);
				User user = userService.selectById(user_id);
				kakaoService.insertKakaoUser(new KakaoUser(user.getUser_no(), user_id));
			}
			// login
			Integer user_no = kakaoService.selectUserNo(profile.getId());
			if(user_no == null) {
				return "redirect:/?kakaologin=fail";
			}
			User user = userService.getUser(user_no);
			session.setAttribute("id", user.getId());
			session.setAttribute("name", user.getName());
			session.setAttribute("sUserId", user);

			return "redirect:/";
		}
	}
}