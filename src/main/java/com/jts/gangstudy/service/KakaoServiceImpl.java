package com.jts.gangstudy.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.KakaoProfile;
import com.jts.gangstudy.domain.KakaoUser;
import com.jts.gangstudy.mapper.KakaoMapper;

@Service
public class KakaoServiceImpl implements KakaoService {
	
	@Autowired
	private KakaoMapper mapper;
	
	private final static String login = "https://kauth.kakao.com/oauth/authorize";		// Kakao 인가코드 요청 URI
	private final static String token = "https://kauth.kakao.com/oauth/token";			// Kakao 토큰 요청 URI
	private final static String profile = "https://kapi.kakao.com/v2/user/me";			// Kakao 사용자 정보 요청 URI
	
	private final static String redirect = "http://localhost:8080/kakao/oauth";			// 인가 코드가 리다이렉트될 URI
	private final static String client_id = "f740ab2294a7a6569c89717bdf837231";			// 앱 생성 시 발급 받은 REST API 키


	@Override
	public String getLoginUrl() {
		URI uri;
		try {
			uri = new URI(login);
			uri = new URIBuilder(uri)
					.addParameter("client_id", client_id)
					.addParameter("redirect_uri", redirect)
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
			        .addParameter("redirect_uri", redirect)								// 인가 코드가 리다이렉트된 URI
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
	public KakaoProfile getProfile(String access_token) {
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
	        URI uri = new URI(profile);
	        uri = new URIBuilder(uri).build();
	        
	        HttpPost httpPost = new HttpPost(uri);
	        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	        httpPost.addHeader("Authorization", "Bearer " + access_token);
        
			try (CloseableHttpResponse response = httpClient.execute(httpPost)){
		        String json = EntityUtils.toString(response.getEntity());
		        JSONObject jsonObject = new JSONObject(json);
		        
		        Long id = jsonObject.getLong("id");										// 회원번호
		        JSONObject properties = jsonObject.getJSONObject("properties");			// 추가 정보
		        
		        /* 불필요한 정보
		         * 
		        String connected_at = jsonObject.getString("connected_at");				// 서비스에 연결 완료된 시각, UTC
		        JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");	// 카카오계정 정보
		        *
		        */
		        
		        return new KakaoProfile(id.toString(), properties);
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
}
