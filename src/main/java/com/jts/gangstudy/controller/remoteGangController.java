package com.jts.gangstudy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jts.gangstudy.domain.Command;
import com.jts.gangstudy.mapper.CommandMapper;

@Controller
@RequestMapping("/remote")
public class remoteGangController {
	private final String ipAdress = "211.201.46.200";
	private final String portNumber = "1200";

	@Autowired
	private CommandMapper mapper;
	
	@ResponseBody
	@RequestMapping(value = "/sign", method = RequestMethod.GET)
	public String sign(HttpServletRequest request, @RequestParam("message") String message) {
		return sendMessage(ipAdress, portNumber, message);
	}
	
	@ResponseBody
	@RequestMapping(value = "/insertCommand", method = RequestMethod.GET)
	public void reserve(HttpServletRequest request,
			@RequestParam("ipAdress") String ipAdress, @RequestParam("portNumber") String portNumber, 
			@RequestParam("message") String message, @RequestParam("reserveTime") String reserveTime) {
		Command command = new Command(ipAdress, portNumber, message, reserveTime);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
		LocalTime time = LocalTime.parse(reserveTime, formatter);
		
		if(time.getSecond() == 0) {
			reserveTime = time.toString() + ":00";
		}
		mapper.insertCommand(command);
	}

	@ResponseBody
	@RequestMapping(value = "/removeCommand", method = RequestMethod.GET)
	public void delete(HttpServletRequest request, @RequestParam("command_no") String command_no) {
		mapper.deleteCommand(Integer.parseInt(command_no));
	}
	
	@ResponseBody
	@RequestMapping(value = "/reqCommandList", method = RequestMethod.GET)
	public List<Command> reqCommandList(HttpServletRequest request) {
		List<Command> commands = mapper.selectAll();
		return commands;
	}
	
	public String sendMessage(String ip, String port, String message) {
		int portNumber = Integer.parseInt(port);
		byte[] buf = null;
        byte[] temp = null;
        try (Socket socket = new Socket()) {
                InetSocketAddress ipep = new InetSocketAddress(ip, portNumber);
                socket.connect(ipep);
                System.out.println("socket connected IP address =" + socket.getRemoteSocketAddress().toString());

                OutputStream send = socket.getOutputStream();
                InputStream recv = socket.getInputStream();

                buf = new byte[1024];
                buf = message.getBytes();
                send.write(buf);
                System.out.println("send: " + new String(buf));

                buf = new byte[1024];
                int bytes = recv.read(buf);
                temp = new byte[bytes];
                temp = Arrays.copyOf(buf, bytes);
                System.out.println("recv: " + new String(temp));

                socket.close();
        } catch (UnknownHostException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return new String(temp);

	}
}
