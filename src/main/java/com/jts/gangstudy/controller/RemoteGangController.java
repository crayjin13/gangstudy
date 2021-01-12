package com.jts.gangstudy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jts.gangstudy.domain.Command;
import com.jts.gangstudy.mapper.CommandMapper;

@Controller
@RequestMapping("/remote")
public class RemoteGangController {
	
	@ResponseBody
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public void sign(HttpServletRequest request, @RequestParam("ipAdress") String ipAdress,
			@RequestParam("portNumber") String portNumber, @RequestParam("message") String message) {
		sendMessage(ipAdress, portNumber, message);
	}
	
	public void sendMessage(String ip, String port, String message) {
		int portNumber = Integer.parseInt(port);
        try (Socket socket = new Socket()) {
                InetSocketAddress ipep = new InetSocketAddress(ip, portNumber);
                socket.connect(ipep);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        		printWriter.println(message);
        		printWriter.close();
                socket.close();
        } catch (UnknownHostException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
	}
}
