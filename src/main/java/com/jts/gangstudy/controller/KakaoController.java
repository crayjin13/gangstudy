package com.jts.gangstudy.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String oauth(HttpServletRequest request) {
		String code = request.getParameter("code");
		String error = request.getParameter("error");
		
		if(error == "access_denied") {	// 사용자 로그인 취소 or 만 14세 미만 사용자의 보호자 동의 실패
			return "/";
		} else {
			String access_token = kakaoService.getAccessToken(code);
	        KakaoProfile profile = kakaoService.getProfile(access_token);
	        
	        // 유저 정보를 가지고 DB에 일치하는 회원이 있는지 찾는다.
			boolean isdup = userService.idDuplicateCheck(profile.getId());
	        
	       
			if(isdup == true) { // 가입된 회원일 경우 로그인 처리함.
//				userService.getUser(user_no)
				// login
		       
			} else { // 가입되지 않은 회원일 경우 회원가입 페이지로 연결함.
				// new account
				// user 게정도 만들고 kakaoUser에도 id와 연동시켜 놓는다.
		        // 회원가입 페이지는 카카오 계정 정보를 이용하여 데이터를 넣고, 나머지 정보만 입력하도록 해야 함. -> 어떤 정보가 필요한지?
				// 가입 절차는 어떻게 처리할지?
			}

			return "home";
		}
	}
}