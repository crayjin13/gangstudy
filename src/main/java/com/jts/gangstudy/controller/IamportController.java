package com.jts.gangstudy.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

import org.json.simple.parser.JSONParser;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jts.gangstudy.service.UserService;
import com.jts.gangstudy.service.AdminService;
import com.jts.gangstudy.service.BookingService;
import com.jts.gangstudy.service.PaymentService;
import com.jts.gangstudy.service.UserService;

@RestController
public class IamportController {
	
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private BookingService bookingService;
	  
	@Autowired
	private PaymentService paymentService;

	Logger logger;

	
	
	

	@ExceptionHandler
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public String getToken(String imp_key, String imp_secret) throws Exception {

		// requestURL 아임포트 고유키, 시크릿 키 정보를 포함하는 url 정보

		 imp_key = URLEncoder.encode("9522889134837493", "UTF-8");

		 imp_secret = URLEncoder
				.encode("lL7KMseL1s1SiV1BoVVbnTxCWEXEfNwAyEll6z68271k33VsYRDmLbZHPWm5JTFix1DEBKMESOYVRUnQ", "UTF-8");
		 JSONObject json = new JSONObject();
		
		
		json.put("imp_key", imp_key);

		json.put("imp_secret", imp_secret);

		String _token = "";
		
		//String _token = getToken(request, response, json, requestURL);

		try {

			String requestString = "";
			String requestURL = "";
			requestURL = "https://api.iamport.kr/users/getToken";
			
			URL url = new URL(requestURL);
  
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoOutput(true);

			connection.setInstanceFollowRedirects(false);

			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type", "application/json");

			OutputStream os = connection.getOutputStream();

			os.write(json.toString().getBytes());

			connection.connect();

			StringBuilder sb = new StringBuilder();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

				String line = null;

				while ((line = br.readLine()) != null) {

					sb.append(line + "\n");

				}

				br.close();

				requestString = sb.toString();

			} 

			os.flush();

			connection.disconnect(); 

			JSONParser jsonParser = new JSONParser();
			
			
			JSONObject jsonObj = (JSONObject) jsonParser.parse(requestString);

			if ((Long) jsonObj.get("code") == 0) {

				JSONObject getToken = (JSONObject) jsonObj.get("response");

				System.out.println("getToken==>>" + getToken.get("access_token"));

				_token = (String) getToken.get("access_token");

			}

		} catch (Exception e) {

			e.printStackTrace();

			_token = "";

		}
		
		System.out.println(_token);

		return _token;

	}
	
	
	
	
	
	
	
	
	
	
	
	

	/**
     * REST API 호출
     *  
     * @param paramUrl
     * @param jsonObject void
     */
	
	@RequestMapping(value = "/restCall" ,method = RequestMethod.POST)
    private void RestCall(String paramUrl,JSONObject jsonObject){
    	try {
            URL url = new URL(paramUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-Auth-Token", "9522889134837493");            
            conn.setRequestProperty("X-Data-Type", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            osw.write(jsonObject.toString());
            osw.flush();
            osw.close();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            if (conn.getResponseCode() != 200) {
                System.out.println("Failed: HTTP error code : " + conn.getResponseCode());
            	throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
            } else {
                System.out.println("발송 성공");
            }
            
            String line = null;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
            br.close();           
            conn.disconnect();
        } catch (IOException e) {
            System.out.println("RestCall Fail : " + e.getMessage());
        }
    }
	




}