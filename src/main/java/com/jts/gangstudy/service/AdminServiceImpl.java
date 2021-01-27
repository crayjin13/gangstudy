package com.jts.gangstudy.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
	InetSocketAddress isa = new InetSocketAddress(ip, port);
	
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
	
	@Scheduled(cron="0 */10 * * * *" )
	public void cronSocketConnect() {
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		Socket socket = new Socket();
		
		String message = "keep alive";
		String index = Integer.toString(LocalDateTime.now().getMinute());
		message = message.concat(index);
		
		String buffer;
		
		try {
			socket.connect(isa);
			socket.setSoTimeout(6000);
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
			// send message
			System.out.println("["+ LocalDateTime.now() +"]cronSocketConnect : " + message);
			printWriter.println(message);
			// read message
			while((buffer = bufferedReader.readLine())!=null && !buffer.equals(message));
			socket.close();
			bufferedReader.close();
			printWriter.close();
			System.out.println("["+ LocalDateTime.now() +"]cornTrigger::close socket msg : " + message);
		} catch (SocketTimeoutException t) {
			System.out.println("["+ LocalDateTime.now() +"]cornTrigger::socket timeout exception msg : " + message);
			System.err.println(t);
			// 서버 무반응 문자 전송
				
		} catch (IOException e) {
			System.out.println("["+ LocalDateTime.now() +"]cornTrigger::socket IO exception msg : " + message);
			e.printStackTrace();
			// 서버 에러 문자 전송
			
		}
	}

	@Override
	public void sendMessage(String message) {
		try {
			Socket socket = new Socket();
			socket.connect(isa);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			
			// send message
			System.out.println("["+ LocalDateTime.now() +"]AdminServiceImpl::sendMessage: " + message);
			printWriter.println(message);
			printWriter.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
