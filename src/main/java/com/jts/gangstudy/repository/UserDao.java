package com.jts.gangstudy.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.jts.gangstudy.domain.Admin;
import com.jts.gangstudy.domain.User;

 
public interface UserDao {

	
	
	// 회원 가입 Create
	boolean insertUser(User user);
	
	// 회원 정보
	User userInfo(Integer user_no);
	
	// 회원 아이디로 검색
	User selectById(String id);
	
	
	/* 계정 비활성화 (mRetire ON --> OFF)*/
	boolean deleteMember(String pw, String email, String retire);
	
	/* 계정 활성화 (mRetire OFF --> ON)*/
	boolean accountOn(String mRetire, String mEmail);
	
	
	//회원 수정
	boolean updateUser(User user);
	
	//아이디 중복체크 
	boolean idDuplicateCheck(String id);
	
	//비밀번호 일치여부 체크
	boolean pwMatch(String pw);
	
	//회원 탈퇴
	boolean deleteUser(String id, String pw);
	
	//아이디 찾기
	User findId(String email, String name);
	
	//비밀번호 찾기
	User findPw(String id, String email);
	
	// 임시비밀번호 발급
	User temporaryPw(String pw, String id);
	
	// 회원 목록
	List<User> UserList();
	
	//회원들 검색
	List<User> findUserList(String search);

	
	
	
	
	
	
}
