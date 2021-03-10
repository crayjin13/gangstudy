package com.jts.gangstudy.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.jts.gangstudy.domain.RemoteLog;
import com.jts.gangstudy.service.AdminService;

public class AdminWebSocketHandler extends TextWebSocketHandler implements InitializingBean, DisposableBean {
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	protected Log log = LogFactory.getLog(AsyncHandlerInterceptor.class);
	
	private final String ip = "211.201.46.200";			// studyroom ip
	private final int port = 1200;						// studyroom port
	private InetSocketAddress isa = new InetSocketAddress(ip, port);

	private Socket socket = null;						// 서버 소켓(스터디룸에 접속할 클라이언트)
	private PrintWriter printWriter = null;				// 출력 담당 객체
	private BufferedReader bufferedReader = null;		// 입력 담당 객체
	
	@Autowired
	private AdminService adminService;

	
	// 소켓 생성
	public void connectSocket() {
		
		try {
			socket = new Socket();								// socket 생성
			socket.setKeepAlive(true);
			socket.connect(isa);								// studyroom 연결
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			String text = "[* 갱스터디 *] 소켓 연결에 실패했습니다. 서버 OR 스터디룸 소켓 장치 확인 바랍니다.";   // [필수] 문자내용 - 이름(names)값이 있다면 [*이름*]가 치환되서 발송됨
			adminService.MMSCall(text);
		} finally {
			
		}
	}
	
	class ListenerThread extends Thread {
		@Override
		public void run() {
			System.out.println("ListenerThread start");
			String msg = null;
		    String pattern = "keep alive[0-9][0-9]?";
			connectSocket();
			try {
				while(true) {
					msg = bufferedReader.readLine();
					if(msg == null || msg.equals(null)) {
						socket.close();
						printWriter.close();
						bufferedReader.close();
						connectSocket();
					} else if(msg.matches(pattern)) {
						continue;
					} else if(msg.equals("<M1>")) {
						// 문자 요청
						String text = "[* 갱스터디 *] M1 !  즉시 지원 바랍니다. ";   // [필수] 문자내용 - 이름(names)값이 있다면 [*이름*]가 치환되서 발송됨
						adminService.MMSCall(text);
					} else if(msg.equals("<M2>")) {
						// 문자 요청
						String text = "[* 갱스터디 *] M2 !  즉시 지원 바랍니다. ";   // [필수] 문자내용 - 이름(names)값이 있다면 [*이름*]가 치환되서 발송됨
						adminService.MMSCall(text);
					} else if(msg.equals("<M3>")) {
						// 문자 요청
						String text = "[* 갱스터디 *] M3 !  즉시 지원 바랍니다. ";   // [필수] 문자내용 - 이름(names)값이 있다면 [*이름*]가 치환되서 발송됨
						adminService.MMSCall(text);
					} else {
						System.out.println("["+LocalDateTime.now()+"]" + "From StudyRoom : " + msg);
						if(msg.length() >= 100) {
							System.err.println("msg length > 100 ERROR...");
						} else {
							RemoteLog log = new RemoteLog(msg, LocalDateTime.now(), RemoteLog.LogType.remote);
							adminService.insertRemoteLogs(log);
							
							// 모든 유저에게 방금 들어온 로그를 보내기.
							for(WebSocketSession session : sessionList) {
								session.sendMessage(new TextMessage(parseJSONLog(log).toString()));
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("["+LocalDateTime.now()+"]"+"socket IO Exception.");
			} catch (NullPointerException e2) {
				System.err.println("["+LocalDateTime.now()+"]"+e2);
			}
		}
	}
	
	
	
	// 웹소켓이 연결되면 호출되는 함수
	@Override
	public void afterConnectionEstablished(WebSocketSession session) 
            throws Exception {
		sessionList.add(session);
		log.info(session.getId() + " 연결됨");
    }
    // 웹소켓 클라이언트가 텍스트 메세지 전송시 호출되는 메소드
    // WebSocketSession session : 전송 주체 정보가 담긴 세션
    // TextMessage message : 전송 받은 메세지 정보
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("["+LocalDateTime.now()+"]"+"["+session.getId()+"]: " + message.getPayload());
//        session.sendMessage(new TextMessage("[echo]: " + message.getPayload()));
		
		// 오늘 날짜의 로그 요청시.
		List<RemoteLog> logs = adminService.selectRemoteLogsByDate(LocalDate.now());
		if(message.getPayload().equals("request today info")) {
			for(RemoteLog log : logs) {
				session.sendMessage(new TextMessage(parseJSONLog(log).toString()));
			}
		}
    }
    // 웹소켓이 연결이 종료되면 호출되는 함수
    @Override
    public void afterConnectionClosed(
            WebSocketSession session, CloseStatus status) throws Exception {
		log.info("["+LocalDateTime.now()+"]"+session.getId() + " 연결 끊김");
		sessionList.remove(session);
    }


    // 객체 생성 시
    @Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("["+LocalDateTime.now()+"]"+"AdminWebSocketHandler::afterPropertiesSet");
		ListenerThread thread = new ListenerThread();
		thread.start();
	}
    // 객체 소멸 시
	@Override
	public void destroy() throws Exception {
		System.out.println("["+LocalDateTime.now()+"]"+"AdminWebSocketHandler::destroy");
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
	
	public JSONObject parseJSONLog(RemoteLog log) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

		JSONObject obj = new JSONObject()
				.append("time", log.getTime().format(dtf))
				.append("message", log.getMessage().replace("<", "&#60;").replace(">", "&#62;"))
				.append("type", log.getType());
		return obj;
	}
}
