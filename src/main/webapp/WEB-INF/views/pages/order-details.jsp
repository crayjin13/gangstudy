<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><!DOCTYPE html>
<!--
Template Name: Metronic - Bootstrap 4 HTML, React, Angular 9 & VueJS Admin Dashboard Theme
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
<html lang="en">
	<!--begin::Head-->
	<head><base href="../../../">
		<meta charset="utf-8" />
		<title>결제 청구서 | Gangstudy</title>
		<meta name="description" content="Invoice example" />
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
		<link rel="canonical" href="https://keenthemes.com/metronic" />
		<!--begin::Fonts-->
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />
		<!--end::Fonts-->
		<!--begin::Global Theme Styles(used by all pages)-->
		<link href="${pageContext.request.contextPath}/resources/assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/resources/assets/plugins/custom/prismjs/prismjs.bundle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/resources/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
		<!--end::Global Theme Styles-->
		<!--begin::Layout Themes(used by all pages)-->
		<!--end::Layout Themes-->
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/assets/media/logos/logo-g1.png" />
	</head>
	<!--end::Head-->   
	<!--begin::Body-->
	<body id="kt_body" class="print-content-only header-mobile-fixed subheader-enabled aside-enabled aside-fixed aside-secondary-enabled page-loading">
		<!--begin::Main-->
		<!--begin::Header Mobile-->
		<div id="kt_header_mobile" class="header-mobile">
			<!--begin::Logo-->      
			<a href="/">
				<img alt="Logo" src="${pageContext.request.contextPath}/resources/assets/media/logos/logo-g1.png" class="logo-default max-h-30px" />
			</a>   
			<!--end::Logo-->
			<!--begin::Toolbar-->
		<!-- 	<div class="d-flex align-items-center">
				<button class="btn p-0 burger-icon burger-icon-left" id="kt_aside_mobile_toggle">
					<span></span>
				</button>
			</div> -->
			<!--end::Toolbar-->
		</div>
		<!--end::Header Mobile-->
		<div class="d-flex flex-column flex-root">
			<!--begin::Page-->
			<div class="d-flex flex-row flex-column-fluid page">
				<!--begin::Aside-->
			
				<!--end::Aside-->
				<!--begin::Wrapper-->`
				<div class="d-flex flex-column flex-row-fluid wrapper" id="kt_wrapper">
					<!--begin::Content-->
					<div class="content d-flex flex-column flex-column-fluid" id="kt_content">
						<!--begin::Subheader-->
					
						<!--end::Subheader-->
						<!--begin::Entry-->
						<div class="d-flex flex-column-fluid">
							<!--begin::Container-->
							<div class="container">
								<!-- begin::Card-->
								<div class="card card-custom overflow-hidden">
									<div class="card-body p-0">
										<!-- begin: Invoice-->
										<!-- begin: Invoice header-->
										<div class="row justify-content-center py-8 px-8 py-md-27 px-md-0">
											<div class="col-md-9">
												<div class="d-flex justify-content-between pb-10 pb-md-20 flex-column flex-md-row">
													<h1 class="display-4 font-weight-boldest mb-10"> ${name}님의 결제 내역 </h1>
													<div class="d-flex flex-column align-items-md-end px-0">
														<!--begin::Logo-->
														<a href="/gangstudy" class="mb-5">
															<img src="${pageContext.request.contextPath}/resources/assets/media/logos/logo-g1.png" alt="" />
														</a>
														<!--end::Logo-->
														<span class="d-flex flex-column align-items-md-end opacity-70">
															<span>  </span>   
															<span>  </span>   
														</span>
													</div>
												</div>												<%												String hasBooking = (String)request.getAttribute("hasBooking");												if(hasBooking.equals("false")){												%>													스터디룸 예약이 없습니다.													</div>												</div>												<%												} else {												%>
												<div class="border-bottom w-100"></div>
												<div class="d-flex justify-content-between pt-6">
													<div class="d-flex flex-column flex-root">
														<span class="font-weight-bolder mb-2">날짜&시간 </span>
														<span class="opacity-70"> 시작시간:<br/>${start}</span>
														<span class="opacity-70"> 종료시간:<br/>${end}</span>
													</div>
													<div class="d-flex flex-column flex-root">
														<span class="font-weight-bolder mb-2">인원수 </span>
														<span class="opacity-70">${people}명</span>
													</div>
													<div class="d-flex flex-column flex-root">
														<span class="font-weight-bolder mb-2">1인 1시간 이용금액</span>
														<span class="opacity-70">${hourPrice}  
														<br /> </span>    
													</div>
												</div>	
											</div>
										</div>
										<!-- end: Invoice header-->
										<!-- begin: Invoice body-->
										<div class="row justify-content-center py-8 px-8 py-md-10 px-md-0">
											<div class="col-md-9">
												<div class="table-responsive">
													<table class="table">
														<thead>
															<tr>
																<th class="pl-0 font-weight-bold text-muted text-uppercase">인원 수</th>
																<th class="text-right font-weight-bold text-muted text-uppercase">총 이용시간</th>
																<th class="text-right font-weight-bold text-muted text-uppercase">포인트 차감 </th>
																<th class="text-right pr-0 font-weight-bold text-muted text-uppercase">가 격 </th>
															</tr>
														</thead>
														<tbody>
															<tr class="font-weight-boldest">
																<td class="pl-0 pt-7">${people}명</td>
																<td class="text-right pt-7">${timeInterval}</td>
																<td class="text-right pt-7">0원</td>
																<td class="text-danger pr-0 pt-7 text-right">${totalCharge}원</td>
															</tr>														<!-- 	한사람이 예약 두번하면 목록 
															<tr class="font-weight-boldest border-bottom-0">
																<td class="border-top-0 pl-0 py-4">Front-End Development</td>
																<td class="border-top-0 text-right py-4">120</td>
																<td class="border-top-0 text-right py-4">$40.00</td>
																<td class="text-danger border-top-0 pr-0 py-4 text-right">$4800.00</td>
															</tr>
															<tr class="font-weight-boldest border-bottom-0">
																<td class="border-top-0 pl-0 py-4">Back-End Development</td>
																<td class="border-top-0 text-right py-4">210</td>
																<td class="border-top-0 text-right py-4">$60.00</td>
																<td class="text-danger border-top-0 pr-0 py-4 text-right">$12600.00</td>
															</tr> -->
														</tbody>
													</table>
												</div>
											</div>  
										</div>										<%										}										%>										<!-- end: Invoice body-->
										<!-- begin: Invoice footer-->
									<!--	<p>위아래 배경색 있고 없고, 가격 폰트 사이즈 차이, 둘중에 맘에 드는 디자인으로 </p>
										 <div class="row justify-content-center bg-gray-100 py-8 px-8 py-md-10 px-md-0">
											<div class="col-md-9">
												<div class="table-responsive">
													<table class="table">
														<thead>
															<tr>
																<th class="font-weight-bold text-muted text-uppercase">인원 수</th>
																<th class="font-weight-bold text-muted text-uppercase">총 이용 시간</th>
																<th class="font-weight-bold text-muted text-uppercase">포인트 차감</th>
																<th class="font-weight-bold text-muted text-uppercase">가 격</th>
															</tr>
														</thead>   
														<tbody>
															<tr class="font-weight-bolder">
																<td>5명</td>
																<td>6시간</td>
																<td>-500원</td>
																<td class="text-danger font-size-h3 font-weight-boldest">29,500원</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div> -->
										<!-- end: Invoice footer-->
										<!-- begin: Invoice action-->
										<div class="row justify-content-center py-8 px-8 py-md-10 px-md-0">
											<div class="col-md-9">
												<div class="d-flex justify-content-between">												<!-- onclick="window.print(); 프린트 바로 할수있는 버튼  -->
													<button type="button" class="btn btn-secondary font-weight-bold" onclick="location.href='/'">메인으로 </button>
													<button type="button" class="btn btn-light-primary font-weight-bold" id="modify" onclick="location.href='/booking/modify'">수정하기 </button>																								</div>
													
											</div>     
										</div>
										<!-- end: Invoice action-->
										<!-- end: Invoice-->
									</div>
								</div>
								<!-- end::Card-->
							</div>
							<!--end::Container-->
						</div>
						<!--end::Entry-->
					</div>
					<!--end::Content-->
					<!--begin::Footer-->
					<!--doc: add "bg-white" class to have footer with solod background color-->
					<div class="footer py-4 d-flex flex-lg-column" id="kt_footer">
						<!--begin::Container-->
						<div class="container d-flex flex-column flex-md-row align-items-center justify-content-between">
							<!--begin::Copyright-->
							<div class="text-dark order-2 order-md-1">								<span class="text-muted font-weight-bold mr-2">대표이사 김정훈 </span>								<a href="/gangstudy" target="_blank" class="text-dark-75 text-hover-primary">갱스터디</a>							</div>							<!--end::Copyright-->							<!--begin::Nav-->							<div class="nav nav-dark">								<a href="http://pf.kakao.com/_xbgCJxb" target="_blank" class="nav-link pl-0 pr-5">카톡으로 문의하기 </a>								<a href="tel:+821021367733" target="_blank" class="nav-link pl-0 pr-5">전화하기 </a>								<a onclick="window.open('${pageContext.request.contextPath}/resources/images/regit.png','_blank','scrollbars=no,width=564,height=860,top=10,left=20');" target="_blank" class="nav-link pl-0 pr-0">사업자 등록 번호 : 783-17-01344</a>							</div>
							<!--end::Nav-->
						</div>
						<!--end::Container-->
					</div>
					<!--end::Footer-->
				</div>
				<!--end::Wrapper-->
			</div>
			<!--end::Page-->
		</div>
		<!--end::Main-->
		<!--begin::Quick Actions Panel-->
	
		<!--end::Quick Actions Panel-->
		<!-- begin::User Panel-->
	
		<!-- end::User Panel-->
		<!--begin::Quick Panel-->
	
		<!--end::Quick Panel-->
		<!--begin::Chat Panel-->
		
		<!--end::Chat Panel-->
		<!--begin::Scrolltop-->
		<div id="kt_scrolltop" class="scrolltop">
			<span class="svg-icon">
				<!--begin::Svg Icon | path:${pageContext.request.contextPath}/resources/assets/media/svg/icons/Navigation/Up-2.svg-->
				<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
					<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
						<polygon points="0 0 24 0 24 24 0 24" />
						<rect fill="#000000" opacity="0.3" x="11" y="10" width="2" height="10" rx="1" />
						<path d="M6.70710678,12.7071068 C6.31658249,13.0976311 5.68341751,13.0976311 5.29289322,12.7071068 C4.90236893,12.3165825 4.90236893,11.6834175 5.29289322,11.2928932 L11.2928932,5.29289322 C11.6714722,4.91431428 12.2810586,4.90106866 12.6757246,5.26284586 L18.6757246,10.7628459 C19.0828436,11.1360383 19.1103465,11.7686056 18.7371541,12.1757246 C18.3639617,12.5828436 17.7313944,12.6103465 17.3242754,12.2371541 L12.0300757,7.38413782 L6.70710678,12.7071068 Z" fill="#000000" fill-rule="nonzero" />
					</g>
				</svg>
				<!--end::Svg Icon-->
			</span>
		</div>
		<!--end::Scrolltop-->
		<!--begin::Sticky Toolbar-->
	
		<!--end::Sticky Toolbar-->
		<!--begin::Demo Panel-->
		<div id="kt_demo_panel" class="offcanvas offcanvas-right p-10">
			<!--begin::Header--> 
			<div class="offcanvas-header d-flex align-items-center justify-content-between pb-7">
				<h4 class="font-weight-bold m-0">Select A Demo</h4>
				<a href="#" class="btn btn-xs btn-icon btn-light btn-hover-primary" id="kt_demo_panel_close">
					<i class="ki ki-close icon-xs text-muted"></i>
				</a>
			</div>
			<!--end::Header-->
			<!--begin::Content-->
	
			<!--end::Content-->
		</div>
		<!--end::Demo Panel-->
		<script>var HOST_URL = "https://preview.keenthemes.com/metronic/theme/html/tools/preview";</script>
		<!--begin::Global Config(global config for global JS scripts)-->
		<script>var KTAppSettings = { "breakpoints": { "sm": 576, "md": 768, "lg": 992, "xl": 1200, "xxl": 1200 }, "colors": { "theme": { "base": { "white": "#ffffff", "primary": "#1BC5BD", "secondary": "#E5EAEE", "success": "#1BC5BD", "info": "#6993FF", "warning": "#FFA800", "danger": "#F64E60", "light": "#F3F6F9", "dark": "#212121" }, "light": { "white": "#ffffff", "primary": "#1BC5BD", "secondary": "#ECF0F3", "success": "#C9F7F5", "info": "#E1E9FF", "warning": "#FFF4DE", "danger": "#FFE2E5", "light": "#F3F6F9", "dark": "#D6D6E0" }, "inverse": { "white": "#ffffff", "primary": "#ffffff", "secondary": "#212121", "success": "#ffffff", "info": "#ffffff", "warning": "#ffffff", "danger": "#ffffff", "light": "#464E5F", "dark": "#ffffff" } }, "gray": { "gray-100": "#F3F6F9", "gray-200": "#ECF0F3", "gray-300": "#E5EAEE", "gray-400": "#D6D6E0", "gray-500": "#B5B5C3", "gray-600": "#80808F", "gray-700": "#464E5F", "gray-800": "#1B283F", "gray-900": "#212121" } }, "font-family": "Poppins" };</script>
		<!--end::Global Config-->
		<!--begin::Global Theme Bundle(used by all pages)-->
		<script src="${pageContext.request.contextPath}/resources/assets/plugins/global/plugins.bundle.js"></script>
		<script src="${pageContext.request.contextPath}/resources/assets/plugins/custom/prismjs/prismjs.bundle.js"></script>
		<script src="${pageContext.request.contextPath}/resources/assets/js/scripts.bundle.js"></script>
		
		<!--end::Global Theme Bundle-->
	</body>
	<!--end::Body-->
</html>