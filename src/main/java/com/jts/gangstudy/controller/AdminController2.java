/*
package com.jts.gangstudy.controller;
 * package com.jts.gangstudy.controller;
 * 
 * import javax.inject.Inject; import javax.servlet.http.HttpSession;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.servlet.ModelAndView;
 * 
 * import com.jts.gangstudy.domain.Admin; import com.jts.gangstudy.domain.User;
 * import com.jts.gangstudy.service.AdminService;
 * 
 * //@Controller public class AdminController {
 * 
 * @Inject //서비스를 호출하기 위해서 의존성을 주입 AdminService adminservice;
 * 
 * 
 * private static final Logger logger=
 * LoggerFactory.getLogger(UserController.class); //로깅을 위한 변수
 * 
 * 
 * //메뉴 페이지에서 관리자 로그인 버튼을 클릭하면 맵핑되는 메소드 //관리자 로그인 페이지로 이동시킨다.
 * 
 * @RequestMapping("/admin/admin_login_view.do") public String
 * admin_login_view() {
 * 
 * return "admin/admin_login"; }
 * 
 * 
 * //관리자 로그인 페이지에서 관리자 아이디와 패스워드를 입력후 로그인 버튼을 누를시에 맵핑되는 메소드 //관리자 로그인을 할 수 있도록
 * 한다.
 * 
 * @RequestMapping("/admin/admin_login.do") public ModelAndView
 * admin_login(String admin_id, String admin_pw,HttpSession session) throws
 * Exception {
 * 
 * //로그인 체크도 같이 함 //dto에 값들을 넣기 위해 객체를 생성한다. Admin admin = new Admin();
 * 
 * 
 * admin.setAdmin_id(admin_id); admin.setAdmin_pw(admin_pw);
 * 
 * //로그인 체크를 하기위한 메소드, 로그인 체크후 결과를 result 변수에 넣는다. boolean result =
 * adminservice.loginCheck(admin, session); ModelAndView mav = new
 * ModelAndView();
 * 
 * 
 * if(result) {//로그인이 성공했을시 출력되는 구문 mav.setViewName("home"); //로그인이 성공했을시 이동하게되는
 * 뷰의 이름 mav.addObject("admin_id", session.getAttribute(admin_id));
 * 
 * }else if(session.getAttribute(admin_id) == null) { //로그인 실패 했을시 출력
 * 
 * //로그인이 실패했을 시에 다시 관리자 로그인 페이지로 이동함
 * 
 * mav.setViewName("admin/admin_login");
 * 
 * //뷰에 전달할 값
 * 
 * mav.addObject("message", "관리자의 아이디 혹은 비밀번호가 일치하지 않습니다.");
 * 
 * }
 * 
 * return mav; }
 * 
 * 
 * //관리자로 로그인 후에 회원을 강제 탈퇴시키는 페이지로 연결시키는 메소드
 * 
 * @RequestMapping("/admin/admin_member_forced_eviction_view.do") public String
 * admin_member_forced_evction_view() {
 * 
 * 
 * return "admin/admin_member_forced_eviction_view"; }
 * 
 * 
 * //관리자로 로그인 후에 강제 탈퇴시킬 회원의 아이디를 입력후 강제탈퇴 버튼을 누르면 연결되는 메소드
 * 
 * @RequestMapping("/admin/admin_member_forced_eviction.do") public ModelAndView
 * admin_member_forced_eviction(String id) throws Exception {
 * 
 * //유저의 아이디를 삭제 (강제탈퇴) 시키기위해서 dto에 담는다. User user = new User(); user.setId(id);
 * 
 * //회원탈퇴 체크를 하기위한 메소드, 탈퇴 시키려는 회원의 아이디가 있는지 검사한후에 result 변수에 저장한다.
 * adminservice.admin_member_forced_evictionCheck(user);
 * 
 * 
 * ModelAndView mav = new ModelAndView();
 * 
 * if(user.getId() != null) { //회원 강제탈퇴가 성공했을시 출력되는 뷰
 * 
 * mav.setViewName("home");
 * 
 * mav.addObject("message", "회원이 정상적으로 강제탈퇴 처리 되었습니다.");
 * 
 * }else {
 * 
 * mav.setViewName("admin/admin_member_forced_eviction_view");
 * 
 * mav.addObject("message", "회원 목록에 없는 회원입니다. 다시 확인해주세요."); }
 * 
 * 
 * return mav;
 * 
 * }
 * 
 * 
 * }
 */