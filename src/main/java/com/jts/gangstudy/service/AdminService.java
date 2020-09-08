package com.jts.gangstudy.service;

import javax.servlet.http.HttpSession;

import com.jts.gangstudy.domain.Admin;
import com.jts.gangstudy.domain.User;

public interface AdminService {
	
	 boolean loginCheck(Admin admin, HttpSession session) throws Exception;    //관리자 로그인을 체크하는 메소드
	 
	    
	    void admin_member_forced_evictionCheck(User user) throws Exception; //강제탈퇴 시킬때 해당 회원이 있는지 체크하는 메소드
}
