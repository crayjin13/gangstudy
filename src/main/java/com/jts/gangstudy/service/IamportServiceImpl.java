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
		
		

		String imp_key = URLEncoder.encode("9522889134837493", "UTF-8");

		String imp_secret = URLEncoder
				.encode("lL7KMseL1s1SiV1BoVVbnTxCWEXEfNwAyEll6z68271k33VsYRDmLbZHPWm5JTFix1DEBKMESOYVRUnQ", "UTF-8");
		String access_token = iamportService.getToken(imp_key, imp_secret);

		System.out.println(access_token + "토큰값 잘 오나 확인 From IamportServiceimpl.cancel 매소드");

		HashMap<String, String> map = new HashMap<String, String>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			URI uri = new URI("https://api.iamport.kr/payments/cancel");
			
			uri = new URIBuilder(uri)
					.addParameter("merchant_uid", tid)
					.addParameter("amount", amount)
					.addParameter("checksum", amount) 
					//우리 서버가 기록하고 있는 환불가능금액과 아임포트 서버가 기록하고 있는 환불가능금액의 일치여부를 체크
					.addParameter("tax_free", "0")
					.build();

			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader("Authorization", access_token);

			String json = null;
			
			try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
				json = EntityUtils.toString(response.getEntity());
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonobj = (JSONObject) jsonParser.parse(json);
			
				System.out.println(json);  
				
			 String checksum =	(String)jsonobj.get("checksum");
			 String cancelTid =	(String)jsonobj.get("tid"); // imp_uid 아임포트에서 주는 고유번호 
			
			
				
				map.put("checksum", checksum);
				map.put("tid", cancelTid);
				
				return map;
			} catch (org.json.JSONException e) {
				System.err.println("at IamportServiceimpl.cancel JSONException : " + e);
				
				return null;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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