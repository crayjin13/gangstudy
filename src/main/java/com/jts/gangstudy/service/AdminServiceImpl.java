package com.jts.gangstudy.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Command;
import com.jts.gangstudy.mapper.CommandMapper;
import com.jts.gangstudy.thread.ListenerRunnable;

@Service
public class AdminServiceImpl implements AdminService, ApplicationListener<ContextClosedEvent> {
	private final String ip = "211.201.46.200";			// studyroom ip
	private final int port = 1200;						// studyroom port
	private Runnable runnable = null;					// runnable
	private Socket socket = null;						// aws socket(client)
	private PrintWriter printWriter = null;				// 출력 담당 객체

	@Autowired
	private CommandMapper mapper;
	
	// 서버 시작시 실행 (1번)
	@PostConstruct
	public void init() {
		InetSocketAddress isa = null;					// studyroom address
		
		System.out.println("AdminService PostConstruct init start");
		try {
			isa = new InetSocketAddress(ip, port);
			socket = new Socket();						// socket 생성
			socket.connect(isa);						// studyroom 연결
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			
			runnable = new ListenerRunnable(socket);	// runnable 생성
			Thread thread = new Thread(runnable);		// thread 생성
			thread.start();								// thread 시작
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	// 서버 종료시 실행 (1번)
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		printWriter.close();
	}
	
	@Scheduled(cron="*/1 * * * * *" )
	public void cornTrigger() {
		LocalTime now = LocalTime.now();
		List<Command> list = mapper.selectAll();
		for(Command command : list) {
			LocalTime time = command.getReserveTime();
			Duration duration = Duration.between(now, time);
			
			if(Math.abs(duration.getSeconds()) <= 1) {
				sendMessage(command.getMessage());
			}
		}
	}

	@Override
	public void sendMessage(String message) {
		printWriter.println(message);
	}

	@Override
	public void insertCommand(Command command) {
		mapper.insertCommand(command);
	}

	@Override
	public void deleteCommand(Integer command_no) {
		mapper.deleteCommand(command_no);
		
	}

	@Override
	public List<Command> selectAll() {
		return mapper.selectAll();
	}
}
