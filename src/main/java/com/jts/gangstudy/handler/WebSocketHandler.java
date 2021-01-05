package com.jts.gangstudy.handler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

	// 클라이언트가 연결을 끊었을 때 실행
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionSet.remove(session);
        this.logger.info("remove session!");
	}
	
	// 클라이언트가 서버로 연결된 이후에 실행
	@Override
    public void afterConnectionEstablished(WebSocketSession session)throws Exception {
		super.afterConnectionEstablished(session);
		sessionSet.add(session);
		this.logger.info("add session!");
	}

	// 클라이언트가 서버로 메세지를 전송했을 때 실행
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		super.handleMessage(session, message);
  		logger.info("{}로 부터 {} 받음", session.getId(), message.getPayload());
  		for (WebSocketSession sess : sessionSet) {
  			sess.sendMessage(new TextMessage("Client "+session.getId() + " : " + message.getPayload()));
  		}
    }
    
    // 연결된 클라이언트에서 예외 발생 시 실행
    @Override
    public void handleTransportError(WebSocketSession session,Throwable exception) throws Exception {
          this.logger.error("web socket error!", exception);
    }    

    // 연결된 Client에 메시지 Broadcast 목적
    public void broadcast(String message){
        for (WebSocketSession session: this.sessionSet){
			if (session.isOpen()){
				try{
				  	session.sendMessage(new TextMessage(message));
				}catch (Exception ignored){
				  	this.logger.error("fail to send message!", ignored);
            		sessionSet.remove(session);
                    try 
                    {
                    	session.close();
                    } 
                    catch (IOException e1) {}				  	
				}
			}
        }
    }   
    
    /**
     * Bean 생성후 서버에서 Client로 주기적으로 메시지를 Broadcast 
     * Test를 위하여 생성
     * Thre로 3초마다 증가된 값을 연결된 Client에 Broadcast 한다.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Thread thread = new Thread(){
            int i=0;
            @Override
            public void run() {
               while (true){
				  try {
					  	broadcast("send message index "+i++);
				        Thread.sleep(3000);
				  } catch (InterruptedException e) {
				         e.printStackTrace();
				         break;
				  }
               }
            }
         };
         thread.start();
    }    
}
    