package com.jts.gangstudy.service;

import java.time.LocalDate;
import java.util.List;

import com.jts.gangstudy.domain.Command;
import com.jts.gangstudy.domain.RemoteLog;
import com.jts.gangstudy.domain.User;

public interface AdminService {
	public void sendMessage(String message);

	public void insertCommand(Command command);
	public void deleteCommand(Integer command_no);
	public List<Command> selectCommands();
	

	public void insertRemoteLogs(RemoteLog remoteLog);
	public List<RemoteLog> selectRemoteLogs();
	
	// 관리자 로그인
	User adminsignIn(String id, String pw) throws Exception;
	
	public List<User> blackList();

	public List<RemoteLog> selectRemoteLogsByDate(LocalDate now);
	
	public void MMSCall(String msg);
}
