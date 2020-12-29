package com.jts.gangstudy.service;

import java.util.List;

import com.jts.gangstudy.domain.Command;
import com.jts.gangstudy.domain.User;

public interface AdminService {
	public void sendMessage(String message);

	public void insertCommand(Command command);

	public void deleteCommand(Integer command_no);

	public List<Command> selectAll();
	
	
	
	// 관리자 로그인
	User adminsignIn(String id, String pw) throws Exception;
	
	public List<User> blackList();
}
