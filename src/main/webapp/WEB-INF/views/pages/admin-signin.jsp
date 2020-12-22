<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
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
<style>  @font-face {     font-family: 'Cafe24Oneprettynight';    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.1/Cafe24Oneprettynight.woff') format('woff');    font-weight: normal;    font-style: normal;}</style>
	<!--begin::Head-->
	<head><base href="../../../../">
		<meta charset="utf-8" />				
		<title>로그인 | Gangstudy</title>
		<meta name="description" content="Singin page example" />
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
		<link rel="canonical" href="https://gagnstudy.com" />
		<!--begin::Fonts-->
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />
		<!--end::Fonts-->
		<!--begin::Page Custom Styles(used by this page)-->
		<link href="${pageContext.request.contextPath}/resources/assets/css/pages/login/login-4.css" rel="stylesheet" type="text/css" />
		<!--end::Page Custom Styles-->
		<!--begin::Global Theme Styles(used by all pages)-->
		<link href="${pageContext.request.contextPath}/resources/assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/resources/assets/plugins/custom/prismjs/prismjs.bundle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/resources/assets/css/style.bundle.css" rel="stylesheet" type="text/css" />
		<!--end::Global Theme Styles-->
		<!--begin::Layout Themes(used by all pages)-->
		<!--end::Layout Themes-->
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/assets/media/logos/logo.png" />
	</head>
	<!--end::Head-->
	<!--begin::Body-->
	<body id="kt_body" class="header-mobile-fixed subheader-enabled aside-enabled aside-secondary-enabled page-loading">
		<!--begin::Main-->
												<!--begin::Header Mobile-->		<div id="kt_header_mobile" class="header-mobile align-items-center header-mobile-fixed">			<!--begin::Logo-->			<a href="index.html">				<img alt="Logo" src="${pageContext.request.contextPath}/resources/assets/media/logos/logo-g1.png" />			</a>			<!--end::Logo-->			<!--begin::Toolbar-->			<div class="d-flex align-items-center">				<!--begin::Aside Mobile Toggle-->				<button class="btn p-0 burger-icon burger-icon-left" id="kt_aside_mobile_toggle">					<span></span>				</button>				<!--end::Aside Mobile Toggle-->	<!--begin::Topbar Mobile Toggle-->				<button class="btn btn-hover-text-primary p-0 ml-2" id="kt_header_mobile_topbar_toggle">					<span class="svg-icon svg-icon-xl">						<!--begin::Svg Icon | path:${pageContext.request.contextPath}/resources/assets/media/svg/icons/General/User.svg-->												<!--end::Svg Icon-->					</span>				</button>				<!--end::Topbar Mobile Toggle-->			</div>			<!--end::Toolbar-->		</div>		<!--end::Header Mobile-->		<div class="d-flex flex-column flex-root">			<!--begin::Page-->			<div class="d-flex flex-row flex-column-fluid page">				<!--begin::Aside-->	<%@ include file="/WEB-INF/views/partials/_aside.jsp"%>				<!--end::Aside-->											
			<!--begin::Login-->
			<div class="login login-4 wizard d-flex flex-column flex-lg-row flex-column-fluid">
				<!--begin::Content-->
				<div class="login-container order-2 order-lg-1 d-flex flex-center flex-row-fluid px-7 pt-lg-0 pb-lg-0 pt-4 pb-6 bg-white">
					<!--begin::Wrapper-->
					<div class="login-content d-flex flex-column pt-lg-0 pt-12">
						<!--begin::Logo--> 
						<a href="/" class="login-logo pb-xl-20 pb-15">
						<img src="${pageContext.request.contextPath}/resources/assets/media/logos/logo-g1.png" width="100"  alt="" />
						<!-- 갱스터디 그림로고 	<img src="${pageContext.request.contextPath}/resources/images/logo.png" width="200"  alt="" /> -->
						</a>
						<!--end::Logo-->
						<!--begin::Signin-->                
						<div class="login-form">
							<!--begin::Form-->
							<form   id="admin_signin" action="/" method="post">
								<!--begin::Title-->
								<div class="pb-5 pb-lg-15">								
									<h3 class="font-weight-bolder text-dark cafe24 font-size-h2 font-size-h1-lg">관리자 로그인</h3>
								
								</div>
								<!--begin::Title-->
								<!--begin::Form group-->
								<div class="form-group">
									<label class="font-size-h6 font-weight-bolder cafe24 text-dark">관리자 아이디</label>
									<input class="form-control form-control-solid h-auto py-7 px-6 rounded-lg border-0" id="aid" type="text" name="aid" autocomplete="off" />
								</div>
								<!--end::Form group-->
								<!--begin::Form group-->
								<div class="form-group">
									<div class="d-flex justify-content-between mt-n5">
										<label class="font-size-h6 font-weight-bolder cafe24 text-dark pt-5">관리자 비밀번호</label>
										<a href="/forgot" class="text-primary cafe24 font-size-h6 font-weight-bolder text-hover-primary pt-5">비밀번호 찾기</a>
									</div>
									<input class="form-control form-control-solid h-auto py-7 px-6 rounded-lg border-0" id="apw" type="password" name="apw" autocomplete="off" />
								</div>
								<!--end::Form group-->
								<!--begin::Action-->  
								<div class="pb-lg-0 pb-5">
									<button type="submit" id="kt_login_singin_form_submit_button" class="btn btn-primary font-weight-bolder cafe24 font-size-h6 px-8 py-4 my-3 mr-3" name="loginbtn">로그인 </button>
									
									<!--  <span class="svg-icon svg-icon-md">
										begin::Svg Icon | path:${pageContext.request.contextPath}/resources/assets/media/svg/social-icons/google.svg
										 <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
											<path d="M19.9895 10.1871C19.9895 9.36767 19.9214 8.76973 19.7742 8.14966H10.1992V11.848H15.8195C15.7062 12.7671 15.0943 14.1512 13.7346 15.0813L13.7155 15.2051L16.7429 17.4969L16.9527 17.5174C18.879 15.7789 19.9895 13.221 19.9895 10.1871Z" fill="#4285F4" />
											<path d="M10.1993 19.9313C12.9527 19.9313 15.2643 19.0454 16.9527 17.5174L13.7346 15.0813C12.8734 15.6682 11.7176 16.0779 10.1993 16.0779C7.50243 16.0779 5.21352 14.3395 4.39759 11.9366L4.27799 11.9466L1.13003 14.3273L1.08887 14.4391C2.76588 17.6945 6.21061 19.9313 10.1993 19.9313Z" fill="#34A853" />
											<path d="M4.39748 11.9366C4.18219 11.3166 4.05759 10.6521 4.05759 9.96565C4.05759 9.27909 4.18219 8.61473 4.38615 7.99466L4.38045 7.8626L1.19304 5.44366L1.08875 5.49214C0.397576 6.84305 0.000976562 8.36008 0.000976562 9.96565C0.000976562 11.5712 0.397576 13.0882 1.08875 14.4391L4.39748 11.9366Z" fill="#FBBC05" />
											<path d="M10.1993 3.85336C12.1142 3.85336 13.406 4.66168 14.1425 5.33717L17.0207 2.59107C15.253 0.985496 12.9527 0 10.1993 0C6.2106 0 2.76588 2.23672 1.08887 5.49214L4.38626 7.99466C5.21352 5.59183 7.50242 3.85336 10.1993 3.85336Z" fill="#EB4335" />
										</svg> 
										end::Svg Icon
									</span>  --> 
								</div>  
								<!--end::Action--> 
							</form>
							<!--end::Form-->  
						</div>   
						<!--end::Signin-->						
					</div>
					<!--end::Wrapper-->					
				</div>  
				<!--begin::Content-->
				<!--begin::Aside-->				<!--begin::Login-->																																	 					
				</div>
				<!--end::Aside-->
			</div>
			<!--end::Login-->			<div>			<%@ include file="/WEB-INF/views/partials/_footer.jsp"%>			</div>
		</div>
		<!--end::Main-->
		<script>var HOST_URL = "https://preview.keenthemes.com/metronic/theme/html/tools/preview";</script>
		<!--begin::Global Config(global config for global JS scripts)-->
		<script>var KTAppSettings = { "breakpoints": { "sm": 576, "md": 768, "lg": 992, "xl": 1200, "xxl": 1200 }, "colors": { "theme": { "base": { "white": "#ffffff", "primary": "#1BC5BD", "secondary": "#E5EAEE", "success": "#1BC5BD", "info": "#6993FF", "warning": "#FFA800", "danger": "#F64E60", "light": "#F3F6F9", "dark": "#212121" }, "light": { "white": "#ffffff", "primary": "#1BC5BD", "secondary": "#ECF0F3", "success": "#C9F7F5", "info": "#E1E9FF", "warning": "#FFF4DE", "danger": "#FFE2E5", "light": "#F3F6F9", "dark": "#D6D6E0" }, "inverse": { "white": "#ffffff", "primary": "#ffffff", "secondary": "#212121", "success": "#ffffff", "info": "#ffffff", "warning": "#ffffff", "danger": "#ffffff", "light": "#464E5F", "dark": "#ffffff" } }, "gray": { "gray-100": "#F3F6F9", "gray-200": "#ECF0F3", "gray-300": "#E5EAEE", "gray-400": "#D6D6E0", "gray-500": "#B5B5C3", "gray-600": "#80808F", "gray-700": "#464E5F", "gray-800": "#1B283F", "gray-900": "#212121" } }, "font-family": "Poppins" };</script>
		<!--end::Global Config-->
		<!--begin::Global Theme Bundle(used by all pages)-->
		<script src="${pageContext.request.contextPath}/resources/assets/plugins/global/plugins.bundle.js"></script>
		<script src="${pageContext.request.contextPath}/resources/assets/plugins/custom/prismjs/prismjs.bundle.js"></script>
		<script src="${pageContext.request.contextPath}/resources/assets/js/scripts.bundle.js"></script>		<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>		
		<!--end::Global Theme Bundle-->		<!--begin::Page Scripts(used by this page)-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wUser.js"></script>
		<script src="${pageContext.request.contextPath}/resources/assets/js/pages/custom/login/login-4.js"></script>
		<!--end::Page Scripts-->
	</body>
	<!--end::Body-->
</html>