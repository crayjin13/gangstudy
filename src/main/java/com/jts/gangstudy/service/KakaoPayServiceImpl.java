package com.jts.gangstudy.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

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
public class KakaoPayServiceImpl implements KakaoPayService {
	
	@Autowired
	private KakaoMapper mapper;
	
	private final static String ready = "https://kapi.kakao.com/v1/payment/ready";		// Kakao 결제 준비
	
	private final static String admin_key = "e3997b8a69a9cb42bbbb32be03bd67df";			// Admin 키
	
	// 필수 요구사항
	private final static String cid = "TC0ONETIME";										// 가맹점 코드, 10자
	private final static String partner_order_id = "partner_order_id";					// 가맹점 주문번호, 최대 100자
	private final static String partner_user_id = "partner_user_id";					// 가맹점 회원 id, 최대 100자
	private final static String item_name = "room";										// 상품명, 최대 100자
	private final static String quantity	 = "1";										// 상품 수량
	private final static String total_amount = "2500";									// 상품 총액
	private final static String tax_free_amount = "100";								// 상품 비과세 금액
	private final static String approval_url = "http://localhost:8080";		// 결제 성공 시 redirect url, 최대 255자
	private final static String cancel_url = "http://localhost:8080";		// 결제 취소 시 redirect url, 최대 255자
	private final static String fail_url = "http://localhost:8080";			// 상품 비과세 금액
	
	// 선택 요구사항
	private final static String cid_secret = "f740ab2294a7a6569c89717bdf837231";		// 가맹점 코드 인증키, 24자, 숫자와 영문 소문자 조합
	private final static String vat_amount = "f740ab2294a7a6569c89717bdf837231";		// 상품 부가세 금액

	public String getResponse() {
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
			URI uri = new URI(ready);
			uri = new URI(ready);
			uri = new URIBuilder(uri)
					.addParameter("cid", cid)
					.addParameter("partner_order_id", partner_order_id)
					.addParameter("partner_user_id", partner_user_id)
					.addParameter("item_name", item_name)
					.addParameter("quantity", quantity)
					.addParameter("total_amount", total_amount)
					.addParameter("vat_amount", vat_amount)
					.addParameter("tax_free_amount", tax_free_amount)
					.addParameter("approval_url", approval_url)
					.addParameter("cancel_url", cancel_url)
					.addParameter("fail_url", fail_url)
					.build();
			
			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			httpPost.addHeader("Authorization", "KakaoAK " + admin_key);
			
			
			try(CloseableHttpResponse response = httpClient.execute(httpPost)) {
		        String json = EntityUtils.toString(response.getEntity());
		        JSONObject jsonObject = new JSONObject(json);

		        String tid = jsonObject.getString("tid");								// 결제 고유 번호, 20자
		        String app_url = jsonObject.getString("next_redirect_app_url");			// 요청한 클라이언트(Client)가 모바일 앱일 경우
		        String mobile_url = jsonObject.getString("next_redirect_mobile_url");	// 요청한 클라이언트가 모바일 웹일 경우
		        String pc_url = jsonObject.getString("next_redirect_pc_url");			// 요청한 클라이언트가 PC 웹일 경우
		        String android_app_scheme = jsonObject.getString("android_app_scheme");	// 카카오페이 결제 화면으로 이동하는 Android 앱 스킴(Scheme)
		        String ios_app_scheme = jsonObject.getString("ios_app_scheme");			// 카카오페이 결제 화면으로 이동하는 iOS 앱 스킴(Scheme)
		        String created_at = jsonObject.getString("created_at");					// 결제 준비 요청 시간
		        
				return json;
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
}
