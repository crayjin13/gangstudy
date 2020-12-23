package com.jts.gangstudy.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ListenerRunnable implements Runnable {
	
	private Socket socket;
	private String msg;
	
	private BufferedReader bufferedReader = null;		// 입력 담당 객체
	
	public ListenerRunnable(Socket socket) {
		this.socket = socket;
		
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		System.out.println("ListenerRunnable start");
		try {
			while(true) {
				msg = bufferedReader.readLine();
				System.out.println("From StudyRoom : " + msg);
				if (msg.equals("listener close")) {
					System.out.println("now disconnted");
					break;
				}
			}
			bufferedReader.close();
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch(IOException e) {
				e.printStackTrace();
			} 
			
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	}

}
