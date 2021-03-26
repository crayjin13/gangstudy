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
import com.jts.gangstudy.domain.SendMmsMessage;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.exception.PasswordMismatchException;
import com.jts.gangstudy.exception.UserNotFoundException;
import com.jts.gangstudy.mapper.CommandMapper;
import com.jts.gangstudy.mapper.RemoteLogMapper;
import com.jts.gangstudy.repository.UserDao;

@Service
public class AdminServiceImpl implements AdminService {
	
	static String senders = "01021367733";
	 static String recipients = "01093705565||01058093031";
	
	
	private final String ip = "211.201.46.200";			// studyroom ip
	private final int port = 1200;						// studyroom port
	InetSocketAddress isa = new InetSocketAddress(ip, port);
	private static boolean roomSocketError = false;
	
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
			if(!roomSocketError) {
				// 서버 무반응 문자 전송
				roomSocketError = true;
				String text = "[* 갱스터디 *] 서버 소켓 에러 !";
				MMSCall(text);
			}
		} catch (IOException e) {
			System.out.println("["+ LocalDateTime.now() +"]cornTrigger::socket IO exception msg : " + message);
			e.printStackTrace();
			// 서버 에러 문자 전송
			
		}
	}
	
	
	

	
	// 뿌리오 문자 연동 API 
	@Override
	public void MMSCall(String msg) {
		String userid = "neojts";           // [필수] 뿌리오 아이디             
		String callback = AdminServiceImpl.senders;    // [필수] 발신번호 - 숫자만
		String phone = AdminServiceImpl.recipients;       // [필수] 수신번호 - 여러명일 경우 |로 구분 "010********|010********|010********"
		String names = "";            // [선택] 이름 - 여러명일 경우 |로 구분 "홍길동|이순신|김철수"
		String appdate = "";  // [선택] 예약발송 (현재시간 기준 10분이후 예약가능)
		String subject = "뿌리오 문자 api 테스트";          // [선택] 제목 (30byte)
		String file1Path = "";    // [선택]  포토발송 (jpg, jpeg만 지원  300 K  이하) 
		try {
			     
		    SendMmsMessage sendMmsMessage = new SendMmsMessage();
		    // filePath가 null 혹은 blank("")인 경우 일반 단/장문 발송. 
			String response_str = sendMmsMessage.send( userid,  callback,  phone,  msg, names, appdate, subject, file1Path );

			//response 
			System.out.println("=============================");
			System.out.println(response_str);
			System.out.println("=============================");
		} catch (IOException localIOException) {
		    System.out.println(localIOException.toString());
		} catch (Exception ex ){
			ex.printStackTrace();
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
	public List<RemoteLog> selectRemoteLogsByDate(LocalDateTime date) {
		return remoteLogMapper.selectByDateTime(date);
	}

	@Override
	public int mmsnumberchange(String sender, String recipient) {
		AdminServiceImpl.senders = sender;
		AdminServiceImpl.recipients = recipient;
		return 1;
	}

	


}
