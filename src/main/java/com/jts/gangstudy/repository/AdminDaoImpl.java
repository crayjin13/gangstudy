package com.jts.gangstudy.repository;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import com.jts.gangstudy.domain.Admin;
import com.jts.gangstudy.domain.User;

public class AdminDaoImpl implements AdminDao {
	
	@Inject
	SqlSession sqlSession;

	 //로그인 체크
	@Override
	public boolean loginCheck(Admin admin) throws Exception {
		 String name = sqlSession.selectOne("admin.login_check", admin);
	        
	        //조건식 ? true일때의 값 : false일때의 값
	        return (name==null) ? false : true;
	}

	//회원 강제탈퇴 관련 메소드 구현
	@Override
	public void admin_member_forced_evictionCheck(User user) throws Exception {
		sqlSession.delete("admin.admin_member_forced_evictionCheck", user);
		
	}

}
