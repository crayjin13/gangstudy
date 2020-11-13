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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Item;
import com.jts.gangstudy.mapper.KakaoMapper;

@Service
@PropertySource("classpath:config.properties")
public class KakaoPayServiceImpl implements KakaoPayService {
	
	@Autowired
	private KakaoMapper mapper;

    @Value("${kakao.ready}")
	private String ready;														// Kakao 결제 준비
    @Value("${kakao.complete}")
    private String complete;													// Kakao 결제 준비

    @Value("${kakao.admin_key}")
	private String admin_key;													// Admin 키
	
	// 필수 요구사항
    @Value("${kakao.cid}")
	private String cid;															// 가맹점 코드, 10자
    @Value("${kakao.partner_order_id}")
	private String partner_order_id;											// 가맹점 주문번호, 최대 100자
    @Value("${kakao.partner_user_id}")
	private String partner_user_id;												// 가맹점 회원 id, 최대 100자
    
	private final static String tax_free_amount = "0";						// 상품 비과세 금액
	private final static String approval_url = "/payment/kakaopay/complete";			// 결제 성공 시 redirect url, 최대 255자
	private final static String cancel_url = "/can";		// 결제 취소 시 redirect url, 최대 255자
	private final static String fail_url = "/fail";		// 결제 실패 시 redirect url, 최대 255자
	
	// 선택 요구사항
	private final static String cid_secret = "fqwrqrqwf";						// 가맹점 코드 인증키, 24자, 숫자와 영문 소문자 조합
	private final static String vat_amount = "100";								// 상품 부가세 금액

	@Override
	public HashMap<String, String> ready(String domain, String deviceType, Item item) {
		HashMap<String, String> map = new HashMap<String, String>();
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
			URI uri = new URI(ready);
			uri = new URIBuilder(uri)
					.addParameter("cid", cid)
					.addParameter("partner_order_id", partner_order_id)
					.addParameter("partner_user_id", partner_user_id)
					.addParameter("item_name", item.getItem_name())
					.addParameter("quantity", Integer.toString(item.getQuantity()))
					.addParameter("total_amount", Integer.toString(item.getTotal_amount()))
					.addParameter("tax_free_amount", tax_free_amount)
					.addParameter("approval_url", domain + approval_url)
					.addParameter("cancel_url", domain + cancel_url)
					.addParameter("fail_url", domain + fail_url)
					.build();
			
			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			httpPost.addHeader("Authorization", "KakaoAK " + admin_key);
			
			try(CloseableHttpResponse response = httpClient.execute(httpPost)) {
		        String json = EntityUtils.toString(response.getEntity());
		        JSONObject jsonObject = new JSONObject(json);
		        /*
		        String app_url = jsonObject.getString("next_redirect_app_url");			// 요청한 클라이언트(Client)가 모바일 앱일 경우
		        String android_app_scheme = jsonObject.getString("android_app_scheme");	// 카카오페이 결제 화면으로 이동하는 Android 앱 스킴(Scheme)
		        String ios_app_scheme = jsonObject.getString("ios_app_scheme");			// 카카오페이 결제 화면으로 이동하는 iOS 앱 스킴(Scheme)
		        String created_at = jsonObject.getString("created_at");					// 결제 준비 요청 시간
		        */

		        String tid = jsonObject.getString("tid");								// 결제 고유 번호, 20자
		        String pc_url = jsonObject.getString("next_redirect_pc_url");			// 요청한 클라이언트가 PC 웹일 경우
		        String mobile_url = jsonObject.getString("next_redirect_mobile_url");	// 요청한 클라이언트가 모바일 웹일 경우
		        map.put("tid", tid);
		        if(deviceType.equals("mobile")) {
			        map.put("url", mobile_url);
		        } else if(deviceType.equals("desktop")) {
			        map.put("url", pc_url);
		        } else { // tablet
			        map.put("url", pc_url);
		        }
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

	@Override
	public void getPayInfo(String tid, String pg_token) {
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
			URI uri = new URI(complete);
			uri = new URIBuilder(uri)
					.addParameter("cid", cid)
					.addParameter("tid", tid)
					.addParameter("partner_order_id", partner_order_id)
					.addParameter("partner_user_id", partner_user_id)
					.addParameter("pg_token", pg_token)
					.build();
			
			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			httpPost.addHeader("Authorization", "KakaoAK " + admin_key);
			
			try(CloseableHttpResponse response = httpClient.execute(httpPost)) {
		        String json = EntityUtils.toString(response.getEntity());
		        JSONObject jsonObject = new JSONObject(json);
		        
		        System.out.println(json);
		        /*
		        String sid = jsonObject.getString("sid");							// 정기결제용 ID, 정기결제 CID로 단건결제 요청 시 발급
		        String tid = jsonObject.getString("tid");								// 결제 고유 번호
		        */

		        String aid = jsonObject.getString("aid");								// 요청 고유 번호
		        String cid = jsonObject.getString("cid");								// 가맹점 코드
		        String partner_order_id = jsonObject.getString("partner_order_id");		// 가맹점 주문번호, 최대 100자
		        String partner_user_id = jsonObject.getString("partner_user_id");		// 가맹점 회원 id, 최대 100자
		        String payment_method_type = jsonObject.getString("payment_method_type");		// 결제 수단, CARD 또는 MONEY 중 하나
		        JSONObject amount = jsonObject.getJSONObject("amount");				// 결제 금액 정보
		        JSONObject card_info = jsonObject.getJSONObject("card_info");		// 결제 상세 정보, 결제수단이 카드일 경우만 포함
		        String item_name = jsonObject.getString("item_name");				// 상품 이름, 최대 100자
		        String item_code = jsonObject.getString("item_code");				// 상품 코드, 최대 100자
		        int quantity = jsonObject.getInt("quantity");						// 상품 수량
		        String created_at = jsonObject.getString("created_at");				// 결제 준비 요청 시각
		        String approved_at = jsonObject.getString("approved_at");			// 결제 승인 시각
		        String payload = jsonObject.getString("payload");					// 결제 승인 요청에 대해 저장한 값, 요청 시 전달된 내용

			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
