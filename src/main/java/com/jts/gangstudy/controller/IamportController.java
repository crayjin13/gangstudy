package com.jts.gangstudy.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;




import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import com.jts.gangstudy.service.UserService;
import com.siot.IamportRestClient.request.AgainPaymentData;
import com.siot.IamportRestClient.request.AuthData;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.OnetimePaymentData;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.request.ScheduleData;
import com.siot.IamportRestClient.request.UnscheduleData;
import com.siot.IamportRestClient.request.escrow.EscrowLogisData;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.BillingCustomer;
import com.siot.IamportRestClient.response.Certification;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.PagedDataList;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.PaymentBalance;
import com.siot.IamportRestClient.response.Prepare;
import com.siot.IamportRestClient.response.Schedule;
import com.siot.IamportRestClient.response.escrow.EscrowLogisInvoice;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.jts.gangstudy.service.AdminService;
import com.jts.gangstudy.service.BookingService;
import com.jts.gangstudy.service.PaymentService;

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



	
	
	
	@POST("/users/getToken")
	Call<IamportResponse<AccessToken>> token(
		@Body AuthData auth) {
		return null;
	}
	
	@GET("/payments/{imp_uid}/balance")
    Call<IamportResponse<PaymentBalance>> balance_by_imp_uid(
    	@Header("Authorization") String token,
        @Path("imp_uid") String imp_uid
    ) {
		return null;
	}
	
	@GET("/payments/{imp_uid}")
    Call<IamportResponse<Payment>> payment_by_imp_uid(
    	@Header("Authorization") String token,
        @Path("imp_uid") String imp_uid
    ) {
		return null;
	}
	
	@GET("/payments/status/{payment_status}")
    Call<IamportResponse<PagedDataList<Payment>>> payments_by_status(
    	@Header("Authorization") String token,
        @Path("payment_status") String payment_status
    ) {
		return null;
	}
	
	@POST("/payments/cancel")
	Call<IamportResponse<Payment>> cancel_payment(
		@Header("Authorization") String token,
		@Body CancelData cancel_data
	) {
		return null;
	}

	@POST("/payments/prepare")
	Call<IamportResponse<Prepare>> post_prepare(
		@Header("Authorization") String token,
		@Body PrepareData prepare_data
	) {
		return null;
	}

	@GET("/payments/prepare/{merchant_uid}")
	Call<IamportResponse<Prepare>> get_prepare(
		@Header("Authorization") String token,
		@Path("merchant_uid") String merchant_uid
	) {
		return null;
	}
	
	@POST("/subscribe/payments/onetime")
	Call<IamportResponse<Payment>> onetime_payment(
		@Header("Authorization") String token,
		@Body OnetimePaymentData onetime_data
	) {
		return null;
	}
	
	@POST("/subscribe/payments/again")
	Call<IamportResponse<Payment>> again_payment(
		@Header("Authorization") String token,
		@Body AgainPaymentData again_data
	) {
		return null;
	}
	
	@POST("/subscribe/payments/schedule")
	Call<IamportResponse<List<Schedule>>> schedule_subscription(
		@Header("Authorization") String token,
		@Body ScheduleData schedule_data
	) {
		return null;
	}
	
	@POST("/subscribe/payments/unschedule")
	Call<IamportResponse<List<Schedule>>> unschedule_subscription(
		@Header("Authorization") String token,
		@Body UnscheduleData unschedule_data
	) {
		return null;
	}

	@GET("/subscribe/customers/{customer_uid}")
	Call<IamportResponse<BillingCustomer>> get_billing_customer(
			@Header("Authorization") String token,
			@Path("customer_uid") String customer_uid
	) {
		return null;
	}
	
	/* 본인인증 결과 (certification result) */
	@GET("/certifications/{imp_uid}")
    Call<IamportResponse<Certification>> certification_by_imp_uid(
    	@Header("Authorization") String token,
        @Path("imp_uid") String imp_uid
    ) {
		return null;
	}
	
	@POST("/escrows/logis/{imp_uid}")
	Call<IamportResponse<EscrowLogisInvoice>> post_escrow_logis(
		@Header("Authorization") String token,
		@Path("imp_uid") String imp_uid,
		@Body EscrowLogisData logis_data
	) {
		return null;
	}




/**
     * REST API 호출
     *  
     * @param paramUrl
     * @param jsonObject void
     */
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