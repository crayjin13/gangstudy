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
	
	// 모든 유저 검색
	public List<User> selectAll();
	
	//占쏙옙占쏙옙 占쏙옙占� 
	User getUser(int user_no);

	// 회占쏙옙 占쏙옙占쏙옙 Create
	boolean insertUser(User user);
	
	boolean insertKakaoUser(User user);

	// 회占쏙옙 占쏙옙占싱듸옙占� 占싯삼옙
	User selectById(String id);

	/* 占쏙옙占쏙옙 占쏙옙활占쏙옙화 (mRetire ON --> OFF) */
	boolean deleteMember(String pw, String email, String retire);

	/* 占쏙옙占쏙옙 활占쏙옙화 (mRetire OFF --> ON) */
	boolean accountOn(String mRetire, String mEmail);

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
	
	boolean updatePoints(int user_no, Float points);

	boolean deleteUser(Integer user_no);

	boolean updateNote(Integer user_no, String note);

	boolean updateRate(Integer user_no, Float rate);
}
