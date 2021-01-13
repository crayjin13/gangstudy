package com.jts.gangstudy.service;



import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface IamportService {

	public String getToken(String imp_key, String imp_secret)
			throws Exception;




	HashMap<String, String> cancel(String tid, String amount) throws Exception;
	
	
	
	

	
}
