package com.jts.gangstudy.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


import org.json.simple.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import oracle.jdbc.proxy.annotation.Post;



@Service
public class IamportServiceImpl implements IamportService {

	
	/*
	 * @SerializedName("imp_uid") private String imp_uid;
	 * 
	 * @SerializedName("merchant_uid") private String merchant_uid;
	 * 
	 * @SerializedName("amount") private BigDecimal amount;
	 * 
	 */
	@Autowired
	IamportService iamportService;
	     
	@Post
	@Override
	public HashMap<String, String> cancel(String tid, String amount) throws Exception {
		
		HashMap<String, String> map = new HashMap<String, String>();

		String imp_key = URLEncoder.encode("9522889134837493", "UTF-8");

		String imp_secret = URLEncoder
				.encode("lL7KMseL1s1SiV1BoVVbnTxCWEXEfNwAyEll6z68271k33VsYRDmLbZHPWm5JTFix1DEBKMESOYVRUnQ", "UTF-8");
		String access_token = iamportService.getToken(imp_key, imp_secret);

		System.out.println(access_token + "토큰값 잘 오나 확인 From IamportServiceimpl.cancel 매소드");

// 토큰값 받음
		
			JSONObject json = new JSONObject();
			
			
			json.put("merchant_uid", tid);  // PaymentController.beready 안에 결제할떄 imp_uid 보내는지 merchant_uid 보내는지 확인할것. 

			json.put("amount", amount);
			json.put("checksum", amount);
			//checksum은 우리 서버가 기록하고 있는 환불가능금액과 아임포트 서버가 기록하고 있는 환불가능금액의 일치여부를 체크

			String _token = "";
			
			//String _token = getToken(request, response, json, requestURL);

			try {

				String requestString = "";
				String requestURL = "";
				requestURL = "https://api.iamport.kr/payments/cancel";
				
				
				URL url = new URL(requestURL);
	  
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setDoOutput(true);

				connection.setInstanceFollowRedirects(false);

				connection.setRequestMethod("POST");

				connection.setRequestProperty("Content-Type", "application/json");
				
				connection.addRequestProperty("Authorization", access_token);
			
			
			
			
			//환불요청 
			
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
			
			
			
		
			
				System.out.println(json +" json으로 돌려 받은 값");  
				System.out.println(jsonObj + " jsonobj로 돌려 받은값 ");
				
				
				
				String canceled_uid = (String)jsonObj.get("merchant_uid");
				
				Integer code = ((Long) jsonObj.get("code")).intValue();
				//(Integer)Object <-- 오브젝트를 인티저로 바로 형변환하다가
				// java.lang.Long cannot be cast to java.lang.Integer error 가 나서 Long 으로 변경후 넣어줌.
				
				String message =  (String)jsonObj.get("message");
				
				
				map.put("tid", canceled_uid);
				map.put("code",Integer.toString(code));
				map.put("message", message);
				
				if ((Long) jsonObj.get("code") == 0) {
						System.out.println("환불가능합니다. ");

				}

			} catch (Exception e) {

				e.printStackTrace();

			}
				
				return map;

	}
	
	
	 
	@ExceptionHandler
	@Post
	@Override
	public String getToken(String imp_key, String imp_secret) throws Exception{

		// requestURL 아임포트 고유키, 시크릿 키 정보를 포함하는 url 정보
/*
		 imp_key = URLEncoder.encode("9522889134837493", "UTF-8");

		 imp_secret = URLEncoder
				.encode("lL7KMseL1s1SiV1BoVVbnTxCWEXEfNwAyEll6z68271k33VsYRDmLbZHPWm5JTFix1DEBKMESOYVRUnQ", "UTF-8");
	*/
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
	
	
}