package com.jts.gangstudy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.domain.KakaoProfile;
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
			return "/";
		} else {
			String access_token = kakaoService.getAccessToken(code);
	        KakaoProfile profile = kakaoService.getProfile(access_token);
	        String user_id = profile.getId();
			boolean isdup = userService.idDuplicateCheck(user_id);
	       
			if(isdup == false) {
				User kakaoUser = new  User(user_id, "#", profile.getNickname());
				userService.insertKakaoUser(kakaoUser);
		        // 회원가입 페이지는 카카오 계정 정보를 이용하여 데이터를 넣고, 나머지 정보만 입력하도록 해야 함. -> 어떤 정보가 필요한지?
				// 가입 절차는 어떻게 처리할지? 가입 없이? 가입페이지 추가해서 정보 얻기?
			}
			// login
			User user = userService.selectById(user_id);
			session.setAttribute("id", user.getId());
			session.setAttribute("name", user.getName());
			session.setAttribute("sUserId", user);

			return "redirect:/";
		}
	}
}