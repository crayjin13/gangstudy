package com.jts.gangstudy.service;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jts.gangstudy.domain.User;
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

	// 占싸깍옙占쏙옙
	User signIn(String id, String pw) throws Exception;

	// 회占쏙옙 占쏙옙占쏙옙 Create
	boolean insertUser(User user);

	// 회占쏙옙 占쏙옙占싱듸옙占� 占싯삼옙
	User selectById(String id) throws Exception;

	// 회占쏙옙 占쏙옙占쏙옙
	boolean updateUser(User user);

	// 占쏙옙占싱듸옙 占쌩븝옙체크
	boolean idDuplicateCheck(String id);

	// 占쏙옙橘占싫� 占쏙옙치占쏙옙占쏙옙 체크
	boolean pwMatch(String pw);

	// 회占쏙옙 탈占쏙옙
	boolean deleteUser(String id, String pw);

	// 占쏙옙占싱듸옙 찾占쏙옙
	User findId(String email, String name);

	// 占쏙옙橘占싫� 찾占쏙옙
	User findPw(String id, String email);

	// 占쌈시븝옙橘占싫� 占쌩깍옙
	User temporaryPw(String pw, String id);

	User User(String name, String phone, String id, String pw, String email, String bod, String gender, int rate,
			String points, String note);

	boolean insertKakaoUser(User kakaoUser);

	void logout(HttpServletResponse response) throws Exception;

	boolean deductPoints(User user, int point);
}
