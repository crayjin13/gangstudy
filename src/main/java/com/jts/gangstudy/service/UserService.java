package com.jts.gangstudy.service;

import java.util.List;

import com.jts.gangstudy.domain.User;

public interface UserService {

	// 로그인 
	User signIn(String id, String pw) throws Exception;

	// 회원 가입 Create
	boolean insertUser(User user);
	
	// 회원 정보
	User userInfo(Integer user_no);
	
	// 회원 아이디로 검색
	User selectById(String id);
	
	
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
	
	//회원들  검색
	List<User> findUserList(String search);

	
}
