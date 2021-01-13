package com.jts.gangstudy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/remote")
public class RemoteGangController {
	
	@ResponseBody
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public String sign(HttpServletRequest request, @RequestParam("ipAdress") String ipAdress,
			@RequestParam("portNumber") String portNumber, @RequestParam("message") String message) {
		int port = Integer.parseInt(portNumber);
        try (Socket socket = new Socket()) {
                InetSocketAddress ipep = new InetSocketAddress(ipAdress, port);
                socket.connect(ipep);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        		printWriter.println(message);
        		printWriter.close();
                socket.close();
                return message;
        } catch (UnknownHostException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return "fail";
	}
}
