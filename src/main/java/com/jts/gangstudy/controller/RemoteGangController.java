package com.jts.gangstudy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
public class RemoteGangController {
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
