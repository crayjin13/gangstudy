package com.jts.gangstudy.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
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
import com.jts.gangstudy.thread.ListenerRunnable;

@Service
public class AdminServiceImpl implements AdminService, ApplicationListener<ContextClosedEvent> {
	private final String ip = "211.201.46.200";			// studyroom ip
	private final int port = 1200;						// studyroom port
	
	private Socket socket = null;						// aws socket(client)
	private PrintWriter printWriter = null;				// 출력 담당 객체
	private BufferedReader bufferedReader = null;		// 입력 담당 객체
	ListenerRunnable listenerRunnable = null;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CommandMapper commandMapper;
	@Autowired
	private RemoteLogMapper remoteLogMapper;
	
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
		
		
	
	// 서버 시작시 실행 (1번)
	@PostConstruct
	public void init() {
		System.out.println("AdminService PostConstruct init start");
		createSocket();
	}
	
	// 서버 종료시 실행 (1번)
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		printWriter.close();
		try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	@Scheduled(cron="0 */1 * * * *" )
	public void cronSocketConnect() {
		printWriter.println("socket connect");
	}
	@Scheduled(fixedDelay = 2000)
	public void socketListener() {
		System.out.println("ListenerRunnable start");
		String msg = null;
		try {
			while(true) {
				msg = bufferedReader.readLine();
				if(!msg.equals("socket connect")) {
					System.out.println("From StudyRoom : " + msg);
					RemoteLog log = new RemoteLog(msg, LocalDateTime.now(), RemoteLog.LogType.remote);
					remoteLogMapper.insertLog(log);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException ListenerRunnable. ");
		}
	}
	
	@Override
	public void sendMessage(String message) {
		if(!socket.isConnected()) {
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

}
