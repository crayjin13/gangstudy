package com.jts.gangstudy.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import javax.xml.crypto.Data;

import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jts.gangstudy.controller.UserLoginCheck;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.exception.PasswordMismatchException;
import com.jts.gangstudy.exception.UserNotFoundException;
import com.jts.gangstudy.service.UserService;

@Controller
public class UserController {

//	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	/*
	 * @RequestMapping(value = "/") public String index() { return ""; }
	 */

	Logger logger;

	
	
	@UserLoginCheck
	@RequestMapping(value = "/edit-user", method = RequestMethod.GET)
	public String edituser() {
		return "pages/edit-user";
	}
	
	@UserLoginCheck
	@RequestMapping(value = "/remo-control", method = RequestMethod.GET)
	public String remo() {
		return "pages/remo-control";
	}
	

	

	// 비번찾기 페이지 이동 
	@RequestMapping(value = "/findPw", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain; charset=UTF-8")
	public String findPw() throws Exception {
		return "findPw";
	}
	

	/* 비밀번호 찾기  */
	@ResponseBody
	@RequestMapping(value = "findPw_action", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String findPw(@RequestParam("id") String id, @RequestParam("email") String email) throws Exception {
		User findPw = userService.findPw(id, email);

		if (findPw != null) {
			System.out.println("## 회원의 비밀번호는:" + findPw.getPw() + "입니다.");
			String pw = findPw.getPw();
			return pw;
		}
		return "";
	}

	// 회원 탈퇴
	@ResponseBody
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public  boolean deleteUser(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session)
			throws Exception {

	   

		boolean delete = userService.deleteUser(id, pw);
		if (delete) {    
			System.out.println("유저 탈퇴 성공");
			delete = true;
			session.invalidate();    
		} else {
			System.out.println("탈퇴 실패");
			delete = false;
		}
		System.out.println("컨트롤러 타는지 "); 
	   

		return delete;

	}

	/* 비밀번호 일치 여부 체크 유저 정보 수정할때 ㄴㄴ 지금안쓰고있음 (js로 비번같은지 체크하는방식으로 하고있음) */
	@UserLoginCheck
	@ResponseBody
	@RequestMapping(value = "/pw_Check", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String retirePwCheck(@RequestParam(value = "pw") String pw) {
		boolean truePw = userService.pwMatch(pw);
		if (truePw) {
			System.out.println("## 비밀번호 일치 여부:" + truePw);
			truePw = true;
		} else {
			System.out.println("## 비밀번호 불일치:" + truePw);

		}
		return truePw + "";
	}

	// 유저 정보 수정
	@UserLoginCheck
	@ResponseBody
	@RequestMapping(value = "/modifyInfo", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String modifyInfo(@RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam(value = "id") String id, @RequestParam("pw") String pw, @RequestParam("email") String email,
			@RequestParam("bod") Date bod, @RequestParam("gender") String gender, HttpSession session,
			HttpServletRequest request) {
		boolean updateUser = userService.updateUser(new User(name, phone, id, pw, email, bod, gender));

		System.out.println(updateUser);

		if (updateUser) {
			System.out.println("유저 정보 수정 성공.");
			updateUser = true;
		} else {
			System.out.println("유저 정보 수정 안됨.");
			updateUser = false;
		}
		session.invalidate();

		return updateUser + "signin";
	}

	/* 아이디 중복 체크 */
	@ResponseBody
	@RequestMapping(value = "/duplicate_check", method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public String existeduser(@RequestParam(value = "id") String id) {
		boolean newId = userService.idDuplicateCheck(id);
		if (newId) {
			System.out.println("중복된 아이디 입니다.");
			newId = false;
		} else {
			newId = true;
		}
		return newId + "";
	}

	/* 로그인 */
	@ResponseBody
	@RequestMapping(value = "/sign_in_action", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String sign_in_action_post(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session,
			Model model, HttpServletRequest request) throws Exception {
		System.out.println(" 로그인 아이디 비번 값 받기  " + "id:" + id + " pw:" + pw);
		String forwardPath = "";
		// String a= request.getSession().getServletContext().getRealPath("/");
		User user = userService.selectById(id);
		// logger.info("프로젝트 경로 찾기" + a);

		try {
			User signInuser = userService.signIn(id, pw);
			System.out.println();
			if (signInuser != null) {
				System.out.println(" 로 그 인 성 공");
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
	/*
	 * if(user.getmRetire()=="off"){ System.out.println("## 비활성화된 계정으로 로그인 할 수 없음");
	 * //forwardPath = 계정 활성화 창으로 포워딩 }
	 */

	// 로그아웃
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public void logout(HttpSession session, HttpServletResponse response) throws Exception {
		session.invalidate();
		System.out.println("로그아웃 성공 ");
//		session.removeAttribute("sUserId");
		userService.logout(response);
	}

	/* 관리자 입장 검색,유저 목록 */

	@RequestMapping(value = "/admin_cm")
	public ModelAndView userList(@Param(value = "search") String search) {
		ModelAndView mv = new ModelAndView();
		System.out.println("##검색 기능 ->" + search);
		if (search == null) {
			List<User> userList = userService.UserList();
			mv.addObject("userList", userList);
		} else {
			List<User> findUserList = userService.findUserList(search);
			mv.addObject("userList", findUserList);
		}
		mv.setViewName("admin_cm");
		return mv;

	}

	@RequestMapping(value = "/click_user", method = RequestMethod.GET)
	public String userDetail(Model model, @RequestParam(value = "id") String id) throws Exception {
		logger.info("회원 상세 정보 조회 진입.");
		// 멤버 객체 생성
		User user = new User();
//		 getMember() 값을 memberVO 객체에 저장

		user = userService.selectById(id);
		// 모델 객체에 값 저장
		model.addAttribute("user", user);

		return "click_user";
	}

	// 회원상세정보조희 목록에서 클릭했을
	@RequestMapping("user/view.do")
	public String clickUser(String id, Model model) throws Exception {
		model.addAttribute("user", userService.selectById(id));

		logger.info("클릭한 아이디:" + id);

		return "/click_user";
	}

	/*
	 * 
	 * //* 관리자 입장 회원 예약 목록 *
	 * 
	 * @RequestMapping(value = "/login") public ModelAndView main() { ModelAndView m
	 * = new ModelAndView(); List<User> userList = userService.userBookingList();
	 * 
	 * // m.addObject("data", userService.UserList()); m.addObject("list",
	 * userList); m.setViewName("login"); System.out.println(userList); return m; }
	 * 
	 */
 
	// 회원가입
	@ResponseBody
	@RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String signUp(User user) throws Exception {

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

}
