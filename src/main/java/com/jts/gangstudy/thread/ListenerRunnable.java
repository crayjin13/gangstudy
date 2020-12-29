package com.jts.gangstudy.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

public class ListenerRunnable implements Runnable {
	
	private String msg;
	private BufferedReader bufferedReader = null;		// 입력 담당 객체
	
	private Logger logger = null;
	
	
	public ListenerRunnable(BufferedReader bufferedReader, Logger logger) throws IOException  {
		this.bufferedReader = bufferedReader;
		this.logger = logger;
	}
	
	@Override
	public void run() {
		System.out.println("ListenerRunnable start");
		try {
			while(true) {
				msg = bufferedReader.readLine();
				if(!msg.equals("socket connect")) {
					System.out.println("From StudyRoom : " + msg);
					logger.info(msg);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException ListenerRunnable. ");
		}
	}

}
