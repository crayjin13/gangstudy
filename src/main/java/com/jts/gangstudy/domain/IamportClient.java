package com.jts.gangstudy.domain;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.siot.IamportRestClient.Iamport;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.*;
import com.siot.IamportRestClient.request.escrow.EscrowLogisData;
import com.siot.IamportRestClient.request.escrow.EscrowLogisInvoiceData;
import com.siot.IamportRestClient.request.naver.*;
import com.siot.IamportRestClient.response.*;
import com.siot.IamportRestClient.response.escrow.EscrowLogisInvoice;
import com.siot.IamportRestClient.response.naver.NaverProductOrder;
import com.siot.IamportRestClient.response.naver.NaverReview;
import com.siot.IamportRestClient.serializer.BalanceEntrySerializer;
import com.siot.IamportRestClient.serializer.EscrowInvoiceEntrySerializer;
import com.siot.IamportRestClient.serializer.ScheduleEntrySerializer;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IamportClient {

	public static final String API_URL = "https://api.iamport.kr";
	public static final String STATIC_API_URL = "https://static-api.iamport.kr";
	protected String apiKey = null;
	protected String apiSecret = null;
	protected String tierCode = null;
	protected Iamport iamport = null;

	public IamportClient(String apiKey, String apiSecret) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.iamport = this.create(false);
	}

	public IamportClient(String apiKey, String apiSecret, boolean useStaticIP) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.iamport = this.create(useStaticIP);
	}

	public IamportClient(String apiKey, String apiSecret, String tierCode) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.tierCode = tierCode;
		this.iamport = this.create(false);
	}

	public IamportClient(String apiKey, String apiSecret, String tierCode, boolean useStaticIP) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.tierCode = tierCode;
		this.iamport = this.create(useStaticIP);
	}

	public void setTierCode(String tier_code) {
		this.tierCode = tier_code;
	}

	public IamportResponse<AccessToken> getAuth() throws IamportResponseException, IOException {
		Call<IamportResponse<AccessToken>> call = this.iamport.token( new AuthData(this.apiKey, this.apiSecret) );
		Response<IamportResponse<AccessToken>> response = call.execute();

		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<PaymentBalance> paymentBalanceByImpUid(String impUid) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<PaymentBalance>> call = this.iamport.balance_by_imp_uid(auth.getToken(), impUid);

		Response<IamportResponse<PaymentBalance>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<com.siot.IamportRestClient.response.Payment> paymentByImpUid(String impUid) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<com.siot.IamportRestClient.response.Payment>> call = this.iamport.payment_by_imp_uid(auth.getToken(), impUid);

		Response<IamportResponse<com.siot.IamportRestClient.response.Payment>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<PagedDataList<com.siot.IamportRestClient.response.Payment>> paymentsByStatus(String status) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<PagedDataList<com.siot.IamportRestClient.response.Payment>>> call = this.iamport.payments_by_status(auth.getToken(), status);

		Response<IamportResponse<PagedDataList<com.siot.IamportRestClient.response.Payment>>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<com.siot.IamportRestClient.response.Payment> cancelPaymentByImpUid(CancelData cancelData) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<com.siot.IamportRestClient.response.Payment>> call = this.iamport.cancel_payment(auth.getToken(), cancelData);

		Response<IamportResponse<com.siot.IamportRestClient.response.Payment>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<Prepare> postPrepare(PrepareData prepareData) throws IOException, IamportResponseException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<Prepare>> call = this.iamport.post_prepare(auth.getToken(), prepareData);

		Response<IamportResponse<Prepare>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<Prepare> getPrepare(String merchantUid) throws IOException, IamportResponseException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<Prepare>> call = this.iamport.get_prepare(auth.getToken(), merchantUid);

		Response<IamportResponse<Prepare>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<com.siot.IamportRestClient.response.Payment> onetimePayment(OnetimePaymentData onetimeData) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<com.siot.IamportRestClient.response.Payment>> call = this.iamport.onetime_payment(auth.getToken(), onetimeData);

		Response<IamportResponse<com.siot.IamportRestClient.response.Payment>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<com.siot.IamportRestClient.response.Payment> againPayment(AgainPaymentData againData) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<com.siot.IamportRestClient.response.Payment>> call = this.iamport.again_payment(auth.getToken(), againData);

		Response<IamportResponse<com.siot.IamportRestClient.response.Payment>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<List<Schedule>> subscribeSchedule(ScheduleData scheduleData) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<List<Schedule>>> call = this.iamport.schedule_subscription(auth.getToken(), scheduleData);

		Response<IamportResponse<List<Schedule>>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<List<Schedule>> unsubscribeSchedule(UnscheduleData unscheduleData) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<List<Schedule>>> call = this.iamport.unschedule_subscription(auth.getToken(), unscheduleData);

		Response<IamportResponse<List<Schedule>>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	/* 본인인증 */
	public IamportResponse<Certification> certificationByImpUid(String impUid) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<Certification>> call = this.iamport.certification_by_imp_uid(auth.getToken(), impUid);

		Response<IamportResponse<Certification>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	/* 에스크로 배송처리 */
	public IamportResponse<EscrowLogisInvoice> postEscrowLogis(String impUid, EscrowLogisData logisData) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<EscrowLogisInvoice>> call = this.iamport.post_escrow_logis(auth.getToken(), impUid, logisData);

		Response<IamportResponse<EscrowLogisInvoice>> response = call.execute();

		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	public IamportResponse<BillingCustomer> getBillingCustomer(String customerUid) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<BillingCustomer>> call = this.iamport.get_billing_customer(auth.getToken(), customerUid);

		Response<IamportResponse<BillingCustomer>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}

	protected Iamport create(boolean useStaticIP) {
		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(30, TimeUnit.SECONDS)
				.connectTimeout(10, TimeUnit.SECONDS)
				.addInterceptor(new Interceptor() { //Tier Header
					public okhttp3.Response intercept(Chain chain) throws IOException {
						Request request = chain.request();

						if (IamportClient.this.tierCode != null) {
							request = request.newBuilder().addHeader("Tier", IamportClient.this.tierCode).build();
						}

						return chain.proceed(request);
					}
				})
				.build();

		Retrofit retrofit = new Retrofit.Builder()
								.baseUrl(useStaticIP ? STATIC_API_URL:API_URL)
								.addConverterFactory(buildGsonConverter())
								.client(client)
								.build();

		return retrofit.create(Iamport.class);
	}

	protected GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Adding custom deserializers
        Object escrowInvoiceStrategy = new EscrowInvoiceEntrySerializer();

        gsonBuilder.registerTypeAdapter(ScheduleEntry.class, new ScheduleEntrySerializer());
        gsonBuilder.registerTypeAdapter(Schedule.class, new ScheduleEntrySerializer());
        gsonBuilder.registerTypeAdapter(PaymentBalanceEntry.class, new BalanceEntrySerializer());
        gsonBuilder.registerTypeAdapter(EscrowLogisInvoiceData.class, escrowInvoiceStrategy);
        gsonBuilder.registerTypeAdapter(EscrowLogisInvoice.class, escrowInvoiceStrategy);

        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);
    }

	protected String getExceptionMessage(Response<?> response) {
		String error = null;
		try {
			JsonElement element = new JsonParser().parse(response.errorBody().string());
			error = element.getAsJsonObject().get("message").getAsString();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if ( error == null )	error = response.message();

		return error;
	}

}
