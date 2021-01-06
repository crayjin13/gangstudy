package com.jts.gangstudy.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Command;
import com.jts.gangstudy.domain.RemoteLog;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.exception.PasswordMismatchException;
import com.jts.gangstudy.exception.UserNotFoundException;
import com.jts.gangstudy.mapper.CommandMapper;
import com.jts.gangstudy.mapper.RemoteLogMapper;
import com.jts.gangstudy.repository.UserDao;

@Service
public class AdminServiceImpl implements AdminService {
	private final String ip = "211.201.46.200";			// studyroom ip
	private final int port = 1200;						// studyroom port

	private Socket socket = null;						// 서버 소켓(스터디룸에 접속할 클라이언트)
	private PrintWriter printWriter = null;				// 출력 담당 객체
	private BufferedReader bufferedReader = null;		// 입력 담당 객체
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CommandMapper commandMapper;
	@Autowired
	private RemoteLogMapper remoteLogMapper;
	
	// 관리자 로그인
		@Override
		public User adminsignIn(String id, String pw) throws Exception, PasswordMismatchException, UserNotFoundException {
			User user = userDao.selectAdmin(id);
			if (user == null) {
				throw new UserNotFoundException(id + "없는유저.");
			}
			if (!user.isMatchPassword(pw)) {
				throw new PasswordMismatchException("정보가 일치하지않습니다.");
			}
			return user;
		}
	
		@Override
		public List<User> blackList() {
			// 블랙리스트
			return userDao.blackList();
		}
		
	// scheduled threads
	@Scheduled(cron="*/1 * * * * *" )
	public void cornTrigger() {
		LocalTime now = LocalTime.now();
		List<Command> list = commandMapper.selectAll();
		for(Command command : list) {
			LocalTime time = command.getReserveTime();
			Duration duration = Duration.between(now, time);
			
			if(Math.abs(duration.getSeconds()) <= 1) {
				sendMessage(command.getMessage());
			}
		}
	}
	// 소켓 생성
	public void createSocket() {
		InetSocketAddress isa = null;
		
		try {
			isa = new InetSocketAddress(ip, port);
			socket = new Socket();								// socket 생성
			socket.setKeepAlive(true);
			socket.connect(isa);								// studyroom 연결
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	@Override
	public void sendMessage(String message) {
		if(socket == null || !socket.isConnected()) {
			createSocket();
		}
		printWriter.println(message);
	}

	@Override
	public void insertCommand(Command command) {
		commandMapper.insertCommand(command);
	}
	@Override
	public void deleteCommand(Integer command_no) {
		commandMapper.deleteCommand(command_no);
		
	}
	@Override
	public List<Command> selectCommands() {
		return commandMapper.selectAll();
	}

	@Override
	public void insertRemoteLogs(RemoteLog remoteLog) {
		remoteLogMapper.insertLog(remoteLog);
	}
	public List<RemoteLog> selectRemoteLogs() {	
		return remoteLogMapper.selectAll();
	}

	@Override
	public List<RemoteLog> selectRemoteLogsByDate(LocalDate date) {
		return remoteLogMapper.selectByDate(date);
	}
}
