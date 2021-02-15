package com.jts.gangstudy.controller;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	
	
	 // 유저정보 메인 페이지 이동
	@RequestMapping(value = "/myinfo_main", method = RequestMethod.GET)
	public String myinfo_main() {       
			return "_User/myinfo_main";           
	}
	// 유저정보 비밀번호 변경 페이지 이동
	@RequestMapping(value = "/change_pw", method = RequestMethod.GET)
	public String change_pw() {       
		return "_User/change_pw";            
	}
	// 유저 탈퇴  페이지 이동
	@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
	public String withdraw() {       
		return "_User/withdraw";                
	}
	
	
	// 유저 정보 수정 페이지 (세션값으로 값 불러옴) 
	 
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify() {
		return "_User/modify_info";
	} 
	
	@UserLoginCheck
	@RequestMapping(value = "/remo-control", method = RequestMethod.GET)
	public String remo() {
		return "pages/remo-control";
	}
	
	
	
	// Spring security  비밀번호 암호화
	
	 @Inject
	    PasswordEncoder passwordEncoder;
	  
	// 회원가입
	@ResponseBody
	@RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String signUp(User user) throws Exception {

		
		
		String encodePw = passwordEncoder.encode(user.getPw());
		
		user.setPw(encodePw);
		
		boolean newUser = userService.insertUser(user);
		
		
		
		if (newUser) {
			newUser = true;       
		} else {
			newUser = false;
		}
		  
		System.out.println(" # 비밀번호 암호화 후 회원 가입 성공여부  [ "+newUser+" ]" );

		return newUser + "";
	}
	
	
	
	/* 로그인 */
	@ResponseBody
	@RequestMapping(value = "/sign_in_action", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String sign_in_action_post(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session,
			Model model, HttpServletRequest request) throws Exception {
		String forwardPath = "";
		// String a= request.getSession().getServletContext().getRealPath("/");
		User user = userService.selectById(id);
		// logger.info("프로젝트 경로 찾기" + a);
		try {
			if (passwordEncoder.matches(pw, user.getPw())) {
				System.out.println(" # 암호화된 비밀번호와 회원이 입력한 자신의 비밀번호가 일치함을 확인  ");
				
				// 암호화시킨 비밀번호와 클라이언트가 적은 raw 비밀번호가 같을 시 세션에 값 저장해줌.
				User signInuser = userService.signIn(id, user.getPw());
			     
				session.setAttribute("id", id);
				session.setAttribute("name", user.getName());
				session.setAttribute("sUserId", signInuser);
				forwardPath = "true";

			} else {

				forwardPath = "false2";
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
	
	

	// 비번찾기 페이지 이동 
	@RequestMapping(value = "/findPw", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain; charset=UTF-8")
	public String findPw() throws Exception {
		return "findPw";
	}
	

	/* 비밀번호 찾기  */
	@ResponseBody
	@RequestMapping(value = "findPw_action", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String findPw(@RequestParam("id") String id, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("password") String password ) throws Exception {
	
		if(password == null) {
			return "pwnull";
		}else {
		
		
		User findPw = userService.findPw(id, email, phone);  
		if (findPw != null) {
			String newPw = passwordEncoder.encode(password);
			boolean updatePw = userService.changePw(id, newPw);
			if(updatePw) {
				return "good";
				
			}
			return "fail";
		
			     
		}
		return "false";
		}
	}

	// 회원 탈퇴
	@ResponseBody
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public boolean deleteUser(@RequestParam("id") String id, @RequestParam("Dpw") String Dpw,
			@RequestParam("email") String email, HttpSession session) throws Exception {

		User user = userService.selectById(id);

		if (user == null) {
			System.out.println("아이디 Null ");
			return false;
		}

		if (passwordEncoder.matches(Dpw, user.getPw())) {
			System.out.println("탈퇴전 비번 매치 확인됨 ");

			boolean delete = userService.deleteUser(id, email);
			System.out.println(delete);
			if (delete) {
				System.out.println("유저 탈퇴 성공");
				session.invalidate();
				return true;
			} else {
				System.out.println("탈퇴 실패");
				return false;
			}
		} else {
			return false;
		}

	}


	// 유저 정보 수정
	@UserLoginCheck
	@ResponseBody
	@RequestMapping(value = "/modifyInfo", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String modifyInfo(@RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam(value = "id") String id, @RequestParam("pw") String pw, @RequestParam("email") String email,
			@RequestParam("bod") Date bod, @RequestParam("gender") String gender, HttpSession session,
			HttpServletRequest request) throws Exception {

		User user = userService.selectById(id);  
		// logger.info("프로젝트 경로 찾기" + a);
		
		if(pw == null) {   
			System.out.println("NULL 반환 "); 
			return "null";
		}

		if (passwordEncoder.matches(pw, user.getPw())) {
			System.out.println(" # 회원이 입력한 비밀번호와 디코딩시킨 비밀번호 매치여부 = true ");

			String encodedPw = passwordEncoder.encode(pw);
			boolean updateUser = userService.updateUser(new User(name, phone, id, encodedPw, email, bod, gender));
     
			System.out.println(updateUser);       

			if (updateUser) {
				System.out.println("유저 정보 수정 성공.");
				
				session.invalidate();
				return "signin";
				   
			} else {
				System.out.println("유저 정보 수정 안됨.");
				
				return "false";
			}
			
		} else {

			return"pwfalse";
		}

	}
	
	
	/* 비번 수정  */
	@UserLoginCheck
	@ResponseBody
	@RequestMapping(value = "/updatePw" , method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String updatePw(@RequestParam(value = "oldPw") String oldPw,@RequestParam(value = "newPw") String newPw, HttpSession session,
			HttpServletRequest request) throws Exception {
		
		String id = (String)session.getAttribute("id");
		
		User user = userService.selectById(id); 
		
		if (passwordEncoder.matches(oldPw, user.getPw())) {
			System.out.println("기존 비번이랑 같은지 체크 = TRUE --> 새로운 pw 받아서 인코딩후 업데이트 ");
			
			String pw = passwordEncoder.encode(newPw);
			
			boolean updatePw = userService.changePw(id, pw);
			
			if(updatePw) {
				System.out.println("업뎃성공 ");
				return "done";
			}else {
				System.out.println("업뎃실패 ");
				return "fail";
				
			}  
			
		}else {
			System.out.println("비번오류");
			
			return "pwUnmatch";
		}
		
	
		
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
 
	

}
