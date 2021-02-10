<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--
Template Name: Metronic - Bootstrap 4 HTML, React, Angular 11 & VueJS Admin Dashboard Theme
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
<head>
<base href="">
<meta charset="utf-8" />
<title>Main | Gangstudy</title>
<meta name="description" content="Updates and statistics" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<link rel="canonical" href="https://gangstudy.com" />

<!--begin::Fonts-->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />

<!--end::Fonts-->

<!--begin::Page Vendors Styles(used by this page)-->
<link
	href="${pageContext.request.contextPath}/resources/assets/plugins/custom/fullcalendar/fullcalendar.bundle.css"
	rel="stylesheet" type="text/css" />

<!--end::Page Vendors Styles-->
     
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

<link href="${pageContext.request.contextPath}/resources/assets/css/themes/layout/header/base/light.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/assets/css/themes/layout/header/menu/light.css"
	rel="stylesheet" type="text/css" />

     
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/assets/css/pikaday/pikaday.css">




<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/assets/media/logos/ganglogo.svg" />
</head>












<!--end::Head-->
<script src="/js/cbpHorizontalMenu.min.js"></script>

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


<!--begin::Body-->
  



<!--end::Head-->     
   
<!--begin::Body-->
<body id="kt_body"   
	 <%-- style="background-image: url(${pageContext.request.contextPath}/resources/assets/media/bg/bg-10.jpg)"  --%>
	class="page-loading-enabled page-loading quick-panel-right demo-panel-right offcanvas-right header-fixed header-mobile-fixed subheader-enabled subheader-fixed page-loading">
	       
<!-- 	<body id="kt_body"      
        class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-minimize-hoverable page-loading"> -->
	          
	
	<jsp:include page="/WEB-INF/views/layout.jsp" flush="true" />

   
	<jsp:include
		page="/WEB-INF/views/partials/_extras/offcanvas/quick-user.jsp"
		flush="true" />

   

	<jsp:include
		page="/WEB-INF/views/partials/_extras/offcanvas/quick-panel.jsp"
		flush="true" />

	<jsp:include page="/WEB-INF/views/partials/_extras/chat.jsp"
		flush="true" />

	<jsp:include page="/WEB-INF/views/partials/_extras/scrolltop.jsp"
		flush="true" />

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
						"primary" : "#6993FF",
						"secondary" : "#E5EAEE",
						"success" : "#1BC5BD",
						"info" : "#8950FC",
						"warning" : "#FFA800",
						"danger" : "#F64E60",
						"light" : "#F3F6F9",
						"dark" : "#212121"
					},
					"light" : {
						"white" : "#ffffff",
						"primary" : "#E1E9FF",
						"secondary" : "#ECF0F3",
						"success" : "#C9F7F5",
						"info" : "#EEE5FF",
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
		src="${pageContext.request.contextPath}/resources/assets/assets/plugins/global/plugins.bundle.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/assets/plugins/custom/prismjs/prismjs.bundle.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/assets/js/scripts.bundle.js"></script>
 
	<!--end::Global Theme Bundle-->      

	<!--begin::Page Vendors(used by this page)-->
	<script
		src="${pageContext.request.contextPath}/resources/assets/assets/plugins/custom/fullcalendar/fullcalendar.bundle.js"></script>

	<!--end::Page Vendors-->

	<!--begin::Page Scripts(used by this page)-->      
	<script>
		function getContextPath() {
			return "${pageContext.request.contextPath}";
		};
	</script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/js/pikaday.js"></script>   
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/booking.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/mainBook.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/inappBridge.js"></script>

	<!--end::Page Scripts-->
</body>

<!--end::Body-->
</html>