package com.jts.gangstudy.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
	private static Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();

	Socket testSocket = null;
	InetSocketAddress isa = null;
	BufferedReader testBufferedReader = null;
	
	class TestListenerThread extends Thread {
		@Override
		public void run() {
			try {
				testSocket = new Socket();
				testSocket.connect(isa);
				testBufferedReader = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("###TestListenerThread connection fail");
				return;
			} finally {
				
			}

			System.out.println("###TestListenerThread start");
			String msg = null;
			try {
				while(true) {
					msg = testBufferedReader.readLine();
					if(msg == null) {
						testSocket.close();
						testBufferedReader.close();
						testSocket = new Socket();
						testSocket.connect(isa);
						testBufferedReader = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
					} else {
						// 모든 유저에게 방금 들어온 로그를 보내기.
						JSONObject obj = new JSONObject()
								.append("time", LocalDateTime.now())
								.append("message", msg)
								.append("type", "test");
						for(WebSocketSession session : sessionSet) {
							session.sendMessage(new TextMessage(obj.toString()));
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("["+LocalDateTime.now()+"]"+"testSocket IO Exception.");
			}
		}
	}
	
	// 클라이언트가 연결을 끊었을 때 실행
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionSet.remove(session);
	}
	
	// 클라이언트가 서버로 연결된 이후에 실행
	@Override
    public void afterConnectionEstablished(WebSocketSession session)throws Exception {
		super.afterConnectionEstablished(session);
		sessionSet.add(session);
	}
    
    // 연결된 클라이언트에서 예외 발생 시 실행
    @Override
    public void handleTransportError(WebSocketSession session,Throwable exception) throws Exception {
          this.logger.error("web socket error!", exception);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
		isa = new InetSocketAddress("222.117.228.95", 80);
    	TestListenerThread thread = new TestListenerThread();
        thread.start();
    }    
}
    