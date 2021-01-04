package com.jts.gangstudy.thread;

import java.io.BufferedReader;
import java.io.IOException;

// not used
public class ListenerRunnable implements Runnable {
	
	private String msg;
	
	private BufferedReader bufferedReader = null;		// 입력 담당 객체
	
	public ListenerRunnable(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}
	
	@Override
	public void run() {
		System.out.println("ListenerRunnable start");
		try {
			while(true) {
				msg = bufferedReader.readLine();
				if(!msg.equals("socket connect")) {
					System.out.println("From StudyRoom : " + msg);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException ListenerRunnable. ");
		}
	}
}
