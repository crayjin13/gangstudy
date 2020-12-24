package com.jts.gangstudy.service;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.exception.PasswordMismatchException;
import com.jts.gangstudy.exception.UserNotFoundException;
import com.jts.gangstudy.domain.Booking;

public interface UserService {
	


	/*************** 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 *********************/

	List<User> userBookingList();

	// 회占쏙옙 占쏙옙占�
	List<User> UserList();

	// 회占쏙옙占쏙옙 占싯삼옙
	List<User> findUserList(String search);
	
	
	User getUser(int user_no);

	/*********************************************/
	
	// 모든 유저 검색
	public List<User> selectAll();
	
	// 로그인
	User signIn(String id, String pw) throws Exception;
	
	// 관리자 로그인
	User adminsignIn(String id, String pw) throws Exception;
	
	

	// 회원가입
	boolean insertUser(User user);

	// 아이디로 선택 
	User selectById(String id) throws Exception;
	
	
	// 관리자 찾기  
	User selectAdmin(String id) throws Exception;

	// 회원 수정
	boolean updateUser(User user);

	// 아이디중복체크
	boolean idDuplicateCheck(String id);

	// 비번체크
	boolean pwMatch(String pw);

	// 회원탈퇴
	boolean deleteUser(String id, String pw);

	// 아이디 찾기
	User findId(String email, String name);

	// 비번찾기
	User findPw(String id, String email);

	// 임시비번
	User temporaryPw(String pw, String id);

	User User(String name, String phone, String id, String pw, String email, String bod, String gender, int rate,
			String points, String note);

	boolean insertKakaoUser(User kakaoUser);

	void logout(HttpServletResponse response) throws Exception;

	boolean plusPoints(User user, float point);
	
	boolean minusPoints(User user, float point);

	boolean deleteUser(Integer user_no);

	boolean updateNote(Integer user_no, String note);

	boolean updateRate(Integer user_no, float rate);


}
