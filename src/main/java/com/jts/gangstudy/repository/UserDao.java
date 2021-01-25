package com.jts.gangstudy.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.jts.gangstudy.domain.Admin;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.domain.Booking;

public interface UserDao {

	// *********** 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 ***************************//

	// 회占쏙옙 占쏙옙占�
	List<User> UserList();

	// 회占쏙옙 占쏙옙占쏙옙 占쏙옙占�
	List<User> userBookingList();

	// 회占쏙옙占쏙옙 占싯삼옙
	List<User> findUserList(String search);

	/*****************************************************/
	
	//인코딩된 패스워드 불러오기 
	String getPw(String id);
	
	
	// 비번변경 
	boolean changePw(String id , String pw);
	
	
	// 블랙고객 
	
	public List<User>blackList();
	
	// 모든 유저 검색
	public List<User> selectAll();
	
	// 회원번호로 유저찾기 
	User getUser(int user_no);

	// 회원가입
	boolean insertUser(User user);
	
	//카카오유저가입
	boolean insertKakaoUser(User user);

	// 아이디로찾기
	User selectById(String id);

	// 관리자 찾기
	User selectAdmin(String id);

	/*  */
	boolean deleteMember(String pw, String email, String retire);

	/*  */
	boolean accountOn(String mRetire, String mEmail);

	// 유저수정
	boolean updateUser(User user);

	// 아이디중복체크
	boolean idDuplicateCheck(String id);

	// 비번체크
	boolean pwMatch(String pw);

	// 유저탈퇴
	boolean deleteUser(String id, String pw);

	// 아이디찾기 
	User findId(String email, String name);

	// 비번찾기
	User findPw(String id, String email);

	// 임시비번
	User temporaryPw(String pw, String id);
	
	boolean updatePoints(int user_no, Float points);

	boolean deleteUser(Integer user_no);

	boolean updateNote(Integer user_no, String note);

	boolean updateRate(Integer user_no, Float rate);

	User getUserByNo(int user_no);
}
