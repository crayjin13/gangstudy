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
	<title>리모컨 | Gangstudy</title>
	<meta name="description" content="Remote studyroom system" />
	<%@ include file="/WEB-INF/views/layout/header.jsp"%>
	
	<!--begin::Page Vendors Styles(used by this page)-->
	<!--end::Page Vendors Styles-->
</head>

<body class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed page-loading" id="kt_body">
	<!--begin::Main-->
	<div class="d-flex flex-column flex-root">
		<!--begin::Page-->
		<div class="d-flex flex-row flex-column-fluid page">
			<!--begin::Wrapper-->
			<div class="d-flex flex-column flex-row-fluid wrapper" id="kt_wrapper">
				<!--begin::Content-->
				<div class="content d-flex flex-column flex-column-fluid pt-0" id="kt_content">
					<div class="container">
						<div class="row justify-content-center pb-5">
							<p class="myinfo_text d-none d-xl-block">리모컨</p>
							<p class="mobile_myinfo_text d-xl-none">리모컨</p>
						</div>
						<div class="row justify-content-center pt-5 pb-10">
							<!--begin::Form-->
							<form class="col-md-5 form">
								<div class="row form-group-white my-3">
									<div class="col-md-6 col-3 my-auto remo_txt">문
									</div>
									<div class="col-md-6 col-9 my-auto text-right">
										<button class="btn-xs listbtn-s">닫기</button>
										<button class="listbtn-s">열기</button>
									</div>
								</div>
								<div class="row form-group-white my-3">
									<div class="col-md-6 col-3 my-auto remo_txt">에어컨
									</div>
									<div class="col-md-6 col-9 my-auto text-right">
										<button class=" btn-xs listbtn-s">끄기</button>
										<button class=" btn-xs listbtn-s">켜기</button>
									</div>
								</div>
								<div class="row form-group-white my-3">
									<div class="col-md-6 col-6 my-auto remo_txt">
										실내온도 설정
									</div>
									<div class="col-md-6 col-6 my-auto text-right">
										현재온도 <span style="color: blue; font-size: 2rem;">27</span> ℃
									</div>
									<div class="col-md-12">
										<input class="form-control input-lg ml-2 mr-2" id="kt_touchspin_3"
											type="text" value="25" placeholder="Select time"
											style="text-align: center;"/>
									</div>
								</div>
								<div class="row form-group-white my-3">
									<div class="col-md-6 col-6 my-auto remo_txt">Wifi 비밀번호
									</div>
									<div class="col-md-6 col-6 my-auto text-right">
										<div style="color: blue;">gangstudent123</div>
									</div>
								</div>
								
								<div class="row form-group mt-10">
									<button class="btn btn-dark btn-lg col mr-md-2" type="button" onclick="location.href='/'">취소</button>
									<button class="btn btn-blue btn-lg col-md-8 ml-md-2" type="button" id="bookingButton">설정변경</button>
								</div>
							</form>
							<!--end::Form-->
						</div>
					</div>
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
	<script src="${pageContext.request.contextPath}/resources/assets/js/pages/crud/forms/widgets/bootstrap-touchspin.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
	<!--end::Page Vendors-->
</body>