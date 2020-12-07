package com.jts.gangstudy.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.KakaoUser;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.mapper.KakaoMapper;

@Service
@PropertySource("classpath:config.properties")
public class KakaoServiceImpl implements KakaoService {
	
	@Autowired
	private KakaoMapper mapper;

    @Value("${gang.domain}")
    private String domain;													// 도메인 이름
    
    @Value("${kakao.login}")
	private String login;			// Kakao 인가코드 요청 URI
    @Value("${kakao.token}")
	private String token;			// Kakao 토큰 요청 URI
    @Value("${kakao.profile}")
	private String profile;			// Kakao 사용자 정보 요청 URI
	
    @Value("${kakao.restapi_key}")
	private String client_id;			// 앱 생성 시 발급 받은 REST API 키

	@Override
	public String getLoginUrl() {
		URI uri;
		try {
			uri = new URI(login);
			uri = new URIBuilder(uri)
					.addParameter("client_id", client_id)
					.addParameter("redirect_uri", domain+"/kakao/oauth")
					.addParameter("response_type", "code")
					.build();
			return uri.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String getAccessToken(String code) {
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
			URI uri = new URI(token);
			uri = new URIBuilder(uri)
			        .addParameter("grant_type", "authorization_code")					// authorization_code로 고정
			        .addParameter("client_id", client_id)								// 앱 생성 시 발급 받은 REST API
			        .addParameter("redirect_uri", domain+"/kakao/oauth")								// 인가 코드가 리다이렉트된 URI
			        .addParameter("code", code)											// 인가 코드 받기 요청으로 얻은 인가 코드	
			        .build();
			
			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			
			try(CloseableHttpResponse response = httpClient.execute(httpPost)) {
		        String json = EntityUtils.toString(response.getEntity());
		        JSONObject jsonObject = new JSONObject(json);

		        String access_token = jsonObject.getString("access_token");				// 사용자 액세스 토큰
		        String refresh = jsonObject.getString("refresh_token");					// 사용자 리프레시 토큰 값
		        System.out.println(refresh);
		        /* 불필요한 정보
		         * 
		        String token_type = jsonObject.getString("token_type");					// 토큰 타입, bearer로 고정
		        int expires = jsonObject.getInt("expires_in");							// 액세스 토큰 만료 시간(초)
		        int refresh_expires = jsonObject.getInt("refresh_token_expires_in");	// 리프레시 토큰 만료 시간(초)
		        String scope = jsonObject.getString("scope");							// 인증된 사용자의 정보 조회 권한 범위. 범위가 여러 개일 경우, 공백으로 구분
				*
		        */
		        
				return access_token;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

    // access_token을 통해 카카오 유저 정보를 얻는다.
	@Override
	public User getProfile(String access_token) {
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
	        URI uri = new URI(profile);
	        uri = new URIBuilder(uri).build();
	        
	        HttpPost httpPost = new HttpPost(uri);
	        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	        httpPost.addHeader("Authorization", "Bearer " + access_token);
        
			try (CloseableHttpResponse response = httpClient.execute(httpPost)){
		        String json = EntityUtils.toString(response.getEntity());
		        System.out.println(json);
		        User user = parseProfile(new JSONObject(json));
		        return user;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public void insertKakaoUser(KakaoUser kakaoUser) {
		mapper.insertKakaoUser(kakaoUser);
	}
	
	public User parseProfile(JSONObject object) {
		// id
		Long long_id = object.getLong("id");
		String id = long_id.toString();
		
		// properties
		JSONObject kakao_account = object.getJSONObject("kakao_account");
		
		// gender
		String gender = kakao_account.getString("gender").equals("male") ? "M" : "F";
		
		// bod
		String birthdate = kakao_account.getString("birthyear") + kakao_account.getString("birthday");
		SimpleDateFormat kakaoformat = new SimpleDateFormat("yyyyMMdd");
		java.util.Date date = null;
		try {
			date = kakaoformat.parse(birthdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// phone_number
		String phone_number = kakao_account.getString("phone_number");
		if(phone_number.startsWith("+82")){
			phone_number = phone_number.replace("+82 ", "0");
		}
		phone_number = phone_number.replaceAll("[^0-9]", "");
		
		// name(nickname)
		String name = null;
		try {
			JSONObject profile = kakao_account.getJSONObject("profile");
			if(profile.has("nickname")) {
				name = profile.getString("nickname");
			}
		} catch(org.json.JSONException e) {
			name="사용자";
		}
		System.out.println("[Debug] name : " + name + 
				", phone_number : " + phone_number + 
				", id : " + id + 
				", birthdate : " + birthdate + 
				", gender : " + gender);
		
		
		return new User(name, phone_number, id, "#", "", new Date(date.getTime()), gender);
	}

	@Override
	public boolean isDuplicate(String kakao_id) {
		List<KakaoUser> users = mapper.selectKakaoUser(kakao_id);
		if(users.size() == 1) {
			return true;
		}
		return false;
	}

	@Override
	public Integer selectUserNo(String id) {
		List<KakaoUser> users = mapper.selectKakaoUser(id);
		if(users.size() == 1) {
			return users.get(0).getUser_no();
		}
		return null;
	}
}
