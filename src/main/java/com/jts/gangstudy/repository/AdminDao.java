package com.jts.gangstudy.repository;

import com.jts.gangstudy.domain.Admin;
import com.jts.gangstudy.domain.User;

public interface AdminDao {
	
	boolean loginCheck(Admin admin) throws Exception;    //로그인을 체크하는 메소드
	 
	 
    void admin_member_forced_evictionCheck(User user) throws Exception;    //회원 강제탈퇴 관련 메소드
	
}
