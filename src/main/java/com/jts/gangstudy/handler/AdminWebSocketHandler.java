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

	private Socket socket = null;						// 서버 소켓(스터디룸에 접속할 클라이언트)
	private PrintWriter printWriter = null;				// 출력 담당 객체
	private BufferedReader bufferedReader = null;		// 입력 담당 객체
	
	@Autowired
	private AdminService adminService;
	
	class ListenerThread extends Thread {
		@Override
		public void run() {
			System.out.println("ListenerThread start");
			String msg = null;
			try {
				while(true) {
					msg = bufferedReader.readLine();
					System.out.println("From StudyRoom : " + msg);
					RemoteLog log = new RemoteLog(msg, LocalDateTime.now(), RemoteLog.LogType.remote);
					adminService.insertRemoteLogs(log);
					
					// 모든 유저에게 방금 들어온 로그를 보내기.
					for(WebSocketSession session : sessionList) {
						session.sendMessage(new TextMessage(parseJSONLog(log).toString()));
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("socket IO Exception.");
			}
		}
	}
	// 웹소켓이 연결되면 호출되는 함수
    @Override
    public void afterConnectionEstablished(WebSocketSession session) 
            throws Exception {
		sessionList.add(session);
		log.info(session.getId() + " 연결됨");
		
		// 해당 날의 이전 로그 불러와서 올려놓기.
		List<RemoteLog> logs = adminService.selectRemoteLogsByDate(LocalDate.now());
		for(RemoteLog log : logs) {
			session.sendMessage(new TextMessage(parseJSONLog(log).toString()));
		}
    }
    // 웹소켓 클라이언트가 텍스트 메세지 전송시 호출되는 메소드
    // WebSocketSession session : 전송 주체 정보가 담긴 세션
    // TextMessage message : 전송 받은 메세지 정보
    @Override
    protected void handleTextMessage(
            WebSocketSession session, TextMessage message) throws Exception {
		log.info("["+session.getId()+"]: " + message.getPayload());
        session.sendMessage(new TextMessage("[echo]: " + message.getPayload()));
    }
    // 웹소켓이 연결이 종료되면 호출되는 함수
    @Override
    public void afterConnectionClosed(
            WebSocketSession session, CloseStatus status) throws Exception {
		log.info(session.getId() + " 연결 끊김");
    }


    // 객체 생성 시
    @Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("AdminWebSocketHandler::afterPropertiesSet");
		createSocket();
		ListenerThread thread = new ListenerThread();
		thread.start();
	}
    // 객체 소멸 시
	@Override
	public void destroy() throws Exception {
		System.out.println("AdminWebSocketHandler::destroy");
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
	
	public JSONObject parseJSONLog(RemoteLog log) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

		JSONObject obj = new JSONObject()
				.append("time", log.getTime().format(dtf))
				.append("message", log.getMessage().replace("<", "&#60;").replace(">", "&#62;"))
				.append("type", log.getType());
		return obj;
	}
}
