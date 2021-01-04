<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
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

<html lang="en">
<!--begin::Head-->
<head>
<base href="../../../">
<meta charset="utf-8" />
<title>갱스터디 관리자 페이지 | Gangstudy</title>
<meta name="description" content="Bootstrap touchspin examples" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<link rel="canonical" href="https://keenthemes.com/metronic" />
<!--begin::Fonts-->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />
<!--end::Fonts-->
<!--begin::Global Theme Styles(used by all pages)-->
<link
	href="${pageContext.request.contextPath}/resources/assets/plugins/global/plugins.bundle.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/resources/assets/plugins/custom/prismjs/prismjs.bundle.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/resources/assets/css/style.bundle.css"
	rel="stylesheet" type="text/css" />
<!--end::Global Theme Styles-->
<!--begin::Layout Themes(used by all pages)-->
<!--end::Layout Themes-->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/assets/media/logos/logo.png" />
</head> 
<!--end::Head-->
<!--begin::Body-->
<body id="kt_body"
	class="header-mobile-fixed subheader-enabled aside-enabled aside-fixed aside-secondary-enabled page-loading">
	<!--begin::Main-->
	<!--begin::Header Mobile-->
	<%-- <div id="kt_header_mobile" class="header-mobile">
		<!--begin::Logo-->
		<a href="/gangstudy"> <img alt="Logo"
			src="${pageContext.request.contextPath}/resources/assets/media/logos/logo-g1.png"
			class="logo-default max-h-30px" />
		</a>
		<!--end::Logo-->
		<!--begin::Toolbar-->
		<div class="d-flex align-items-center">
			<button class="btn p-0 burger-icon burger-icon-left"
				id="kt_aside_mobile_toggle">
				<span></span>
			</button>
		</div>
		<!--end::Toolbar-->
	</div> --%>
	<!--end::Header Mobile-->
	<div class="d-flex flex-column flex-root">
		<!--begin::Page-->
		<div class="d-flex flex-row flex-column-fluid page">
			<!--begin::Aside-->
			<div class="aside aside-left d-flex aside-fixed" id="kt_aside">
			<%@ include file="/WEB-INF/views/partials/_aside2.jsp"%>
				<!--begin::Primary-->
				<div
					class="aside-primary d-flex flex-column align-items-center flex-row-auto">
					<!--begin::Brand-->
					<div
						class="aside-brand d-flex flex-column align-items-center flex-column-auto py-5 py-lg-12">
						<!--begin::Logo-->
						<a href="/"> <img alt="Logo"
							src="${pageContext.request.contextPath}/resources/assets/media/logos/logo-g1.png"
							class="max-h-30px" />
						</a>
						<!--end::Logo-->
					</div>
					<!--end::Brand-->
					<!--begin::Nav Wrapper-->

					<!--end::Nav Wrapper-->
					<!--begin::Footer-->

					<!--end::Footer-->
				</div>
				<!--end::Primary-->
				<!--begin::Secondary-->
				<div class="aside-secondary d-flex flex-row-fluid">
					<!--begin::Workspace-->
					<div class="aside-workspace scroll scroll-push my-2">
						<!--begin::Tab Content-->
						<%-- 	<img alt="Logo" src="${pageContext.request.contextPath}/resources/assets/media/logos/logo-g1.png"
							class="max-h-30px"> --%>
						<div class="tab-content">
							<!--begin::Tab Pane-->

							<!--end::Tab Pane-->
							<!--begin::Tab Pane-->
			
							<!--end::Aside Menu-->     
						</div>
						<!--end::Tab Pane-->
					</div>  
					<!--end::Tab Content-->
				</div>
				<!--end::Workspace-->
			</div>
			<!--end::Secondary-->
		</div>
		<!--end::Aside-->
		<!--begin::Wrapper-->
		<div class="d-flex flex-column flex-row-fluid wrapper" id="kt_wrapper">
			<!--begin::Content-->
			<div class="content d-flex flex-column flex-column-fluid"
				id="kt_content">
				<!--begin::Subheader-->
				<div class="subheader py-3 py-lg-8 subheader-transparent"
					id="kt_subheader">
					<div
						class="container d-flex align-items-center justify-content-between flex-wrap flex-sm-nowrap">
						<!--begin::Info-->
						<div class="d-flex align-items-center flex-wrap mr-1">
							<!--begin::Page Heading-->
							<div class="d-flex align-items-baseline flex-wrap mr-5">
								<!--begin::Page Title-->
								<h2 class="subheader-title text-dark font-weight-bold my-1 mr-3">Gang
									Study</h2>
								<!--end::Page Title-->
								<!--begin::Breadcrumb-->
								<ul
									class="breadcrumb breadcrumb-transparent breadcrumb-dot font-weight-bold my-2 p-0">
									<li class="breadcrumb-item"><a href="/"
										class="text-muted">메인으로</a></li>

								</ul>
								<!--end::Breadcrumb-->
							</div>
							<!--end::Page Heading-->
						</div>
						<!--end::Info-->
						<!--begin::Toolbar-->

						<!--end::Dropdown-->
						<!--begin::Button-->

						<!--end::Button-->
					</div>
					<!--end::Toolbar-->
				</div>
			</div>
			<!--end::Subheader-->
			<!--begin::Entry-->
			<div class="d-flex flex-column-fluid">
				<!--begin::Container-->
				<div class="container">
					<!--begin::Notice-->

					<!--end::Notice-->
					<!--begin::Card-->
					<div class="card card-custom gutter-b example example-compact">
						<div class="card-header">
							<h3 class="card-title cafe24">갱스터디 매장 리모컨트롤러 </h3>
							<div class="card-toolbar">
								<div class="example-tools justify-content-center"></div>
							</div>
						</div>
						<!--begin::Form-->
						<form class="form">
							<div class="card-body">
								<!--begin: Code-->
								<div class="example-code mb-10">

									<span class="example-copy" data-toggle="tooltip"
										title="Copy code"></span>
									<div class="tab-content">
										<div class="tab-pane active" id="example_code_html"
											role="tabpanel">
											<div class="example-highlight"></div>
										</div>
										<div class="tab-pane" id="example_code_js">
											<div class="example-highlight"></div>
										</div>
									</div>
								</div>
								<!--end: Code-->
								<div class="form-group row">
									<label class="col-form-label text-right col-lg-3 col-sm-12">문</label>
									<div class="col-lg-9 col-md-9 col-sm-12">

										<button type="button" class="btn btn-outline-dark">
											열기<i class="fas fa-lock-open text-dark"></i>
										</button>
										<button type="button" class="btn btn-outline-dark">
											닫기<i class="fas fa-lock text-dark"></i>
										</button>

									</div>

								</div>

								<div class="form-group row">
									<label class="col-form-label text-right col-lg-3 col-sm-12">에어컨</label>

									<div class="col-lg-4 col-md-9 col-sm-12">
										<button type="button" class="btn btn-outline-dark">
											켜기 <i class="flaticon2-arrow"></i>
										</button>
										<button type="button" class="btn btn-outline-dark">
											끄기 <i class="flaticon2-cancel "></i>
										</button>
									</div>
								</div>


								<div class="form-group row">
									<label class="col-form-label text-right col-lg-3 col-sm-12">실내온도</label>

									<div class="col-lg-4 col-md-9 col-sm-12">
										<input id="kt_touchspin" type="text" class="form-control"
											value="27" name="demo0" readonly />

									</div>
								</div>

								<div class="form-group row">
									<label class="col-form-label text-right col-lg-3 col-sm-12">
										설정온도 </label>
									<!-- 최소값 15, 최대값 40 으로 설정 -->
									<div class="col-lg-4 col-md-9 col-sm-12">
										<input id="kt_touchspin_3" type="text" class="form-control"
											value="25" name="demo0" placeholder="Select time" />
									</div>
								</div>

								<div class="form-group row">
									<label class="col-form-label text-right col-lg-3 col-sm-12">시간
										난방 </label>
									<!-- 최소값 15, 최대값 40 으로 설정 -->
									<div class="col-lg-4 col-md-9 col-sm-12">
										<input id="kt_touchspin_2" type="text" class="form-control"
											value="0" name="demo0" placeholder="Select time" />
									</div>
								</div>


									</div>
							<div class="card-footer">
								<div class="row">
									<div class="col-lg-9 ml-lg-auto">
										<button type="reset" class="btn btn-primary mr-2">설정변경</button>
										<button type="reset" class="btn btn-secondary"
											onclick="location.href =  '/';">메인으로</button>
									</div>
								</div>
							</div>
						</form>
						<!--end::Form-->
						
						    
         <!--begin::Card-->
					<div class="card card-custom gutter-b example example-compact">
						<div class="card-header">
							<h3 class="card-title cafe24">갱스터디 간판 </h3>
							<div class="card-toolbar">
								<div class="example-tools justify-content-center"></div>
							</div>
						</div>  
							<div class="card-body">
								<div class="boardcontrol">

								<!--begin::Form--> 
									<form class="form2">    
									<!--      간판 시작              -->
									<div class="form-group row">
										<label class="col-form-label text-right col-lg-3 col-sm-12">간판
											키는 명령어 옵션 </label>

										<div class="col-lg-4 col-md-9 col-sm-12">
											<select name="group_select" id="messageSelect">
												<option value="">명령어 직접 입력</option>
												
												<option value="<C0111><C0131>">간판켜기(빨강+파랑)</option>
												<option value="<C0111><C0121>">간판켜기(빨강+초록)</option>							
												<option value="<C0121><C0131>">간판켜기(초록+파랑)</option>
												<option value="<C0141><C0151><C0161>">간판켜기(나머지)</option>
<option>---------------------------------------------</option>
												<option value="<C0112><C0132>">간판끄기(빨강+파랑)</option>
												<option value="<C0112><C0122>">간판끄기(빨강+초록)</option>
												<option value="<C0122><C0132>">간판끄기(초록+파랑)</option>
												<option value="<C0142><C0152><C0162>">간판끄기(나머지)</option>
											</select>

										</div> 
									</div>

									<div class="form-group row">
										<label class="col-form-label text-right col-lg-3 col-sm-12">
											명령어 직접입력 </label>

										<div class="col-lg-4 col-md-9 col-sm-12">
											<input id="messageInput" type="text" class="form-control"
												name="demo0" />
										</div>
									</div>


									<div class="form-group row">
										<label class="col-form-label text-right col-lg-3 col-sm-12">간판
											시간 예약 <i class="flaticon2-dashboard"></i>
										</label>

										<div class="col-lg-4 col-md-9 col-sm-12">
											<input id="kt_timepicker_1_validate" type="text" class="form-control"
												value="0" name="demo0" placeholder="Select time" />
											<button id="saveCommand" type="button" class="btn btn-dark mr-2">저장</button>
										</div>
									</div>
									</form>
								</div>

								<div class="form-group row">

									<label class="col-form-label text-right col-lg-3 col-sm-12">소켓
										예약 목록 <i class="flaticon-list-3"></i>
									</label>
										<ul id="command_list">
										</ul>
								</div>


								<div class="admin">
									<div class="title blueon">
										간판<br> Control
									</div>
									<button class="signbtn blueon btn btn-danger " data-value="11">
										갱<br>(RED ON!)
									</button>
									<button class="signbtn blueon btn btn-success " data-value="21">
										갱<br>(GRE ON!)
									</button>
									<button class="signbtn blueon btn btn-primary" data-value="31">
										갱<br>(BLUE ON!)
									</button>
									<br>
									<button class="signbtn blueoff btn btn-light-danger"
										data-value="12">
										갱<br>(RED OFF)
									</button>
									<button class="signbtn blueoff btn btn-light-success"
										data-value="22">
										갱<br>(GRE OFF)
									</button>
									<button class="signbtn blueoff  btn btn-light-primary"
										data-value="32">
										갱<br>(BLUE OFF)
									</button>
									<br>
									<button class="signbtn blueon btn btn-warning " data-value="41">
										캐릭터<br>(IMA ON!)
									</button>
									<button class="signbtn blueon btn btn-warning " data-value="51">
										스터디<br>(STU ON!)
									</button>
									<button class="signbtn blueon btn btn-warning " data-value="61">
										URL주소<br>(URL ON!)
									</button>
									<br>
									<button class="signbtn blueoff  btn btn-light-warning "
										data-value="42">
										캐릭터<br>(IMA OFF)
									</button>
									<button class="signbtn blueoff btn btn-light-warning "
										data-value="52">
										스터디<br>(STU OFF)
									</button>
									<button class="signbtn blueoff btn btn-light-warning "
										data-value="62">
										URL주소<br>(URL OFF)
									</button>
									<!-- <br> <input id="time" value="">분</input>
									<button class="timeset blueon" data-value="">SET</button>
									<br> -->


									<h3 id="ct" value=""></h3>
									<h3 id="cd" value=""></h3>
									<br>
									<div class="title redon">
										실내 장비<br> Control
									</div>
									<button class="insidebtn redon btn btn-dark" data-value="11">
										DOOR<br>(LOCK ON!)
									</button>
									<button class="insidebtn redon btn btn-warning" data-value="21">
										실내등1<br>(ON)
									</button>
									<button class="insidebtn redon btn  btn-warning"
										data-value="31">
										실내등2<br>(ON)
									</button>
									<br>
									<button class="insidebtn redoff btn btn-light-dark"
										data-value="12">
										DOOR<br>(LOCK OFF)
									</button>
									<button class="insidebtn redoff btn btn-light-dark"
										data-value="22">
										실내등1<br>(OFF)
									</button>
									<button class="insidebtn redoff btn btn-light-dark"
										data-value="32">
										실내등2<br>(OFF)
									</button>
									<br>
									<button class="insidebtn redon btn btn-light-dark"
										data-value="1">
										BUZZER ON!<br>(ON)
									</button>
									<button class="insidebtn redon btn btn-light-dark"
										data-value="2">
										LED ON<br>(ON)
									</button>
									<button class="insidebtn redon btn btn-light-dark"
										data-value="3">
										LED OFF<br>(OFF)
									</button>
									<br>
									<button class="text redon btn btn-light-dark"
										data-value="문자테스트">
										문 자 테스트<br>(send)
									</button>
									<button class="cancel redoff btn btn-light-dark"
										data-value="문자예약취소">
										문자 취소<br>(cancel)
									</button>
									<button class="insidebtn redoff btn btn-light-dark"
										data-value="62">
										<br>(OFF)
									</button>
									<br>
									<div id="outlog" class="log">송신:</div>
									<div id="inlog" class="log">수신:<br>
									<c:forEach items="${dateTimes}" varStatus="status">
										시간 : <c:out value="${dateTimes[status.index]}" />
										  내용 : <c:out value="${messages[status.index]}" />
										  종류 : <c:out value="${logTypes[status.index]}" /><br>
									</c:forEach>
									</div>

								</div>








  



								<!-- <div class="form-group row">
									<label class="col-form-label text-right col-lg-3 col-sm-12">Wifi
										비밀번호 <i class="fas fa-wifi"></i>
									</label>
									<div class="col-lg-4 col-md-9 col-sm-12">
										<button type="button" class="btn btn-outline-success">gangstudents123
										</button>
									</div>
								</div> -->







							</div>
							<div class="card-footer">
								<div class="row">
									<div class="col-lg-9 ml-lg-auto">
										<!-- <button type="reset" class="btn btn-primary mr-2">설정변경</button>
										<button type="reset" class="btn btn-secondary"
											onclick="location.href =  '/gangstudy';">메인으로</button> -->
									</div>
								</div>
							</div>
						<!--end::Form-->
					</div>
					<!--end::Card-->
					<!--begin::Card-->

					<!--end::Card-->
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
			<div
				class="container d-flex flex-column flex-md-row align-items-center justify-content-between">
				<!--begin::Copyright-->
				<div class="text-dark order-2 order-md-1">
					<span class="text-muted font-weight-bold mr-2">리모컨 페이지 </span> <a
						href="http://gangstudy.com" target="_blank"
						class="text-dark-75 text-hover-primary">갱스터디 </a>
				</div>
				<!--end::Copyright-->
				<!--begin::Nav-->
				<div class="nav nav-dark">
					<a href="http://pf.kakao.com/_xbgCJxb" target="_blank"
						class="nav-link pl-0 pr-5">카톡으로 문의하기 </a> <a
						href="tel:+821021367733" target="_blank"
						class="nav-link pl-0 pr-5">전화하기 </a> <a
						onclick="window.open('${pageContext.request.contextPath}/resources/images/regit.png','_blank','scrollbars=no,width=564,height=860,top=10,left=20');"
						target="_blank" class="nav-link pl-0 pr-0">사업자 등록 번호 :
						783-17-01344</a>
				</div>
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

	</div>
	</div>
	<!--end::Section-->
	</form>
	</div>
	<!--end::Tabpane-->
	</div>
	</div>
	<!--end::Content-->
	</div>
	<!--end::Quick Panel-->
	<!--begin::Chat Panel-->
	
	<!--end::Chat Panel-->
	<!--begin::Scrolltop-->
	<div id="kt_scrolltop" class="scrolltop">
		<span class="svg-icon"> <!--begin::Svg Icon | path:${pageContext.request.contextPath}/resources/assets/media/svg/icons/Navigation/Up-2.svg-->
			<svg xmlns="http://www.w3.org/2000/svg"
				xmlns:xlink="http://www.w3.org/1999/xlink" width="24px"
				height="24px" viewBox="0 0 24 24" version="1.1">

					<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
						<polygon points="0 0 24 0 24 24 0 24" />
						<rect fill="#000000" opacity="0.3" x="11" y="10" width="2"
					height="10" rx="1" />
						<path
					d="M6.70710678,12.7071068 C6.31658249,13.0976311 5.68341751,13.0976311 5.29289322,12.7071068 C4.90236893,12.3165825 4.90236893,11.6834175 5.29289322,11.2928932 L11.2928932,5.29289322 C11.6714722,4.91431428 12.2810586,4.90106866 12.6757246,5.26284586 L18.6757246,10.7628459 C19.0828436,11.1360383 19.1103465,11.7686056 18.7371541,12.1757246 C18.3639617,12.5828436 17.7313944,12.6103465 17.3242754,12.2371541 L12.0300757,7.38413782 L6.70710678,12.7071068 Z"
					fill="#000000" fill-rule="nonzero" />
					</g>
				</svg> <!--end::Svg Icon-->    
		</span>
	</div>
	<!--end::Scrolltop-->
	<!--begin::Sticky Toolbar-->

	<!--end::Sticky Toolbar-->
	<!--begin::Demo Panel-->
	<div id="kt_demo_panel" class="offcanvas offcanvas-right p-10">
		<!--begin::Header-->
		<div
			class="offcanvas-header d-flex align-items-center justify-content-between pb-7">
			<h4 class="font-weight-bold m-0">Select A Demo</h4>
			<a href="#" class="btn btn-xs btn-icon btn-light btn-hover-primary"
				id="kt_demo_panel_close"> <i
				class="ki ki-close icon-xs text-muted"></i>
			</a>
		</div>
		<!--end::Header-->
		<!--begin::Content-->
		<div class="offcanvas-content">
			<!--begin::Wrapper-->

		</div>
		<!--end::Wrapper-->
		<!--begin::Purchase-->
		<div class="offcanvas-footer">
			<a href="https://1.envato.market/EA4JP" target="_blank"
				class="btn btn-block btn-danger btn-shadow font-weight-bolder text-uppercase">Buy
				Metronic Now!</a>
		</div>
		<!--end::Purchase-->
	</div>
	<!--end::Content-->
	</div>
	<!--end::Demo Panel-->
	<script>
		var HOST_URL = "https://preview.keenthemes.com/metronic/theme/html/tools/preview";
	</script>
	<!--begin::Global Config(global config for global JS scripts)-->
	<script>
		var KTAppSettings = {
			"breakpoints" : {
				"sm" : 576,
				"md" : 768,
				"lg" : 992,
				"xl" : 1200,
				"xxl" : 1200
			},
			"colors" : {
				"theme" : {
					"base" : {
						"white" : "#ffffff",
						"primary" : "#1BC5BD",
						"secondary" : "#E5EAEE",
						"success" : "#1BC5BD",
						"info" : "#6993FF",
						"warning" : "#FFA800",
						"danger" : "#F64E60",
						"light" : "#F3F6F9",
						"dark" : "#212121"
					},
					"light" : {
						"white" : "#ffffff",
						"primary" : "#1BC5BD",
						"secondary" : "#ECF0F3",
						"success" : "#C9F7F5",
						"info" : "#E1E9FF",
						"warning" : "#FFF4DE",
						"danger" : "#FFE2E5",
						"light" : "#F3F6F9",
						"dark" : "#D6D6E0"
					},
					"inverse" : {
						"white" : "#ffffff",
						"primary" : "#ffffff",
						"secondary" : "#212121",
						"success" : "#ffffff",
						"info" : "#ffffff",
						"warning" : "#ffffff",
						"danger" : "#ffffff",
						"light" : "#464E5F",
						"dark" : "#ffffff"
					}
				},
				"gray" : {
					"gray-100" : "#F3F6F9",
					"gray-200" : "#ECF0F3",
					"gray-300" : "#E5EAEE",
					"gray-400" : "#D6D6E0",
					"gray-500" : "#B5B5C3",
					"gray-600" : "#80808F",
					"gray-700" : "#464E5F",
					"gray-800" : "#1B283F",
					"gray-900" : "#212121"
				}
			},
			"font-family" : "Poppins"
		};
	</script>
	<!--end::Global Config-->
	<!--begin::Global Theme Bundle(used by all pages)-->
	<script
		src="${pageContext.request.contextPath}/resources/assets/plugins/global/plugins.bundle.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/plugins/custom/prismjs/prismjs.bundle.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/scripts.bundle.js"></script>
	<!--end::Global Theme Bundle-->
	<!--begin::Page Scripts(used by this page)-->
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/pages/crud/forms/widgets/bootstrap-touchspin.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/pages/crud/forms/widgets/bootstrap-timepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/pages/crud/forms/widgets/bootstrap-switch.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
	<!--end::Page Scripts-->
</body>
<!--end::Body-->
</html>