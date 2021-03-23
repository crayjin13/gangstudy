<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
Template Name: Metronic - Bootstrap 4 HTML, React, Angular 10 & VueJS Admin Dashboard Theme
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Dribbble: www.dribbble.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: https://1.envato.market/EA4JP
Renew Support: https://1.envato.market/EA4JP
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<html lang="ko">
<head>
	<title>Gangstudy</title>
	<meta name="description" content="Metronic admin dashboard live demo. Check out all the features of the admin panel. A large number of settings, additional services and widgets." />
	<%@ include file="/WEB-INF/views/layout/header.jsp"%>
	
	<!--begin::Page Vendors Styles(used by this page)-->
	<link href="${pageContext.request.contextPath}/resources/assets/css/svg.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/assets/css/pikaday/pikaday.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/assets/plugins/custom/fullcalendar/fullcalendar.bundle.css" rel="stylesheet" type="text/css" />
	<!--end::Page Vendors Styles-->
	<!--begin::Custom Scripts(used by this page)-->
	<!-- Global site tag (gtag.js) - Google Analytics 구글 -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-166780031-1"></script>
	<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());
	  gtag('config', 'UA-166780031-1');
	</script>
	<!-- NAVER Analytics 네이버서비스 -->
	<script type="text/javascript" src="http://wcs.naver.net/wcslog.js"></script>
	<script type="text/javascript">
		if(!wcs_add) var wcs_add = {};
		wcs_add["wa"] = "2c6acdbed3f912";
		if(window.wcs) {
		  wcs_do();
		}
	</script>
	<!--end::Custom Scripts-->
</head>
<style>
img {
	display: block;
	margin: auto;
}
</style>
<body class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed  page-loading" id="kt_body">
	<!--begin::Main-->
	<div class="d-flex flex-column flex-root">
		<!--begin::Page-->
		<div class="d-flex flex-row flex-column-fluid page">
			<!--begin::Wrapper-->
			<div class="d-flex flex-column flex-row-fluid wrapper" id="kt_wrapper">
				<!--begin::Content-->
				<div class="content d-flex flex-column flex-column-fluid" id="kt_content">
					<!--begin::Container-->
					<div class="container">
						<div class="row">
							<div class="col-xxl-6">   
								<!--begin::Form-->
								<form class="form" id="bookingForm" action="/booking/make">   
								       <!-- 데스크탑에서만 보임 -->
										<p class = "main_text d-none d-xl-block">갱스터디에서 공부하고<br/>   공부깡패 되자!</p>
										<!-- 데스크탑에서만 숨김 -->
										<p class = "mobile_main_text d-xl-none">갱스터디에서  공부하고<br/>   공부깡패 되자!</p>
										   
									                       
									<div class="card-footer">                 
										<div class="form-group form-group-last">    
											<div class="alert-text" style ="text-align: center ">
											</div>   
										</div>
										<div class="form-group ">
											 <input class="form-control col-md-10 select booking-input" type="text" readOnly
												id="dateInput" name="dateInput"  
												value="${date}">
											<span class="icon">
											<object data="${pageContext.request.contextPath}/resources/images/ic-calendar.svg" type="image/svg+xml"></object>
											</span>
										</div>
										<div class="form-group ">
											 <select class="form-control col-md-10 select booking-input"
												id="start-time-input" name="startTimeInput" form="bookingForm" 
												time="${startTime}">
												<option value="" > 시작시간</option>
											</select>
											<span class="icon">
												<object data="${pageContext.request.contextPath}/resources/images/ic-time.svg" type="image/svg+xml"></object>
											</span>
										</div>
										<div class="form-group ">
											<span class="icon">
											<object data="${pageContext.request.contextPath}/resources/images/ic-time.svg" type="image/svg+xml"></object>
											</span>
											 <select class="form-control col-md-10 select booking-input"
												id="end-time-input" name="endTimeInput" form="bookingForm"
												time="${endTime}">
												<option value=""> 종료시간</option> 
											</select>
										</div> 
										<div class="form-group ">
											<span class="icon">
											<object data="${pageContext.request.contextPath}/resources/images/ic-member.svg" type="image/svg+xml"></object>
											</span>
											 <select class="form-control col-md-10 select booking-input"
											 	id="people-input" name="people" 
											 	people="${people}">
												<option value="">인원수</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
											</select>
										</div>
										<div class="form-group ">
											<button type="button" class="btn btn-booking btn-lg col-md-5" id="bookingButton">
												예약하기
											</button>  
										
											<button type="button" class="btn btn-kakao btn-lg text-dark col-md-5" onclick="location.href='http://pf.kakao.com/_xbgCJxb'">                 
												<img src="${pageContext.request.contextPath}/resources/images/ic-kakao.svg" class="ic_kakao" >카카오톡 문의하기
											</button>    
											
										</div>
										
							
										<!--begin: Code-->  
										<div class="example-code mt-10">
											<div class="example-highlight"></div>
										</div>
										<!--end: Code-->
									</div>
								</form>
								<!--end::Form-->
							</div>
						    
							<div class="col-md-4 d-none d-xl-block " >
								<img src="${pageContext.request.contextPath}/resources/images/img-main.svg" class="img_main ">
							</div>
						</div>
					</div>
					<!--end::Container-->
				</div>
				<!--end::Content-->
				<%@ include file="/WEB-INF/views/layout/footer-include.jsp"%>
			</div>
			<!--end::Wrapper-->
		</div>
		<!--end::Page-->
	</div>
	<!--end::Main-->
	
	<%@ include file="/WEB-INF/views/layout/footer.jsp"%>
	<!--begin::Page Vendors(used by this page)-->
	<script src="${pageContext.request.contextPath}/resources/assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>
	<script>function getContextPath(){ return "${pageContext.request.contextPath}"; };</script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/js/pikaday.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/view/book-util.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/view/index.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/inappBridge.js"></script>
	<!--end::Page Vendors-->
</body>