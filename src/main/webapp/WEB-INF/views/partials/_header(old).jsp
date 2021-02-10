<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--begin::Header-->
<script>
	$(document).ready(function() {

		document.getElementById("logout").onclick = function() {
			document.user_logout.submit();

		}
	});
</script>
					<div id="kt_header" class="header header-fixed">

						<!--begin::Container-->
						<div class="container-fluid d-flex align-items-stretch justify-content-between">

							<!--begin::Header Menu Wrapper-->
							<div class="header-menu-wrapper header-menu-wrapper-left" id="kt_header_menu_wrapper">

								

								<!--end::Header Menu-->
							</div>

							<!--end::Header Menu Wrapper-->

							<!--begin::Topbar-->
							<div class="topbar">

								<!--begin::Search-->
								<div class="dropdown" id="kt_quick_search_toggle">

									<!--begin::Toggle-->
									<div class="topbar-item" data-toggle="dropdown" data-offset="10px,0px">
										
									</div>

									<!--end::Toggle-->

									<!--begin::Dropdown-->
									<div class="dropdown-menu p-0 m-0 dropdown-menu-right dropdown-menu-anim-up dropdown-menu-lg">

										<!--[html-partial:include:{"file":"partials/_extras/dropdown/search-dropdown.html"}]/-->
									</div>

									<!--end::Dropdown-->
								</div>

								<!--end::Search-->

								<!--begin::Notifications-->
								<div class="dropdown">

									<!--begin::Toggle-->
									<div class="topbar-item" data-toggle="dropdown" data-offset="10px,0px">
										
									</div>

									<!--end::Toggle-->

									<!--begin::Dropdown-->
									<div class="dropdown-menu p-0 m-0 dropdown-menu-right dropdown-menu-anim-up dropdown-menu-lg">
										<form>

											<!--[html-partial:include:{"file":"partials/_extras/dropdown/notifications.html"}]/-->
										</form>
									</div>

									<!--end::Dropdown-->
								</div>

								<!--end::Notifications-->

								<!--begin::Quick Actions-->
								<div class="dropdown">

									<!--begin::Toggle-->
									<div class="topbar-item" data-toggle="dropdown" data-offset="10px,0px">
										
									</div>

									<!--end::Toggle-->

									<!--begin::Dropdown-->
									<div class="dropdown-menu p-0 m-0 dropdown-menu-right dropdown-menu-anim-up dropdown-menu-lg">

										<!--[html-partial:include:{"file":"partials/_extras/dropdown/quick-actions.html"}]/-->
									</div>

									<!--end::Dropdown-->
								</div>

								<!--end::Quick Actions-->

								<!--begin::Cart-->
								<div class="topbar-item">
									
								</div>

								<!--end::Cart-->

								<!--begin::Quick panel-->
								<div class="topbar-item">
									
								</div>

								<!--end::Quick panel-->

								<!--begin::Chat-->
								<div class="topbar-item">
									
								</div>

								<!--end::Chat-->

								<!--begin::Languages-->
								<div class="dropdown">

									<!--begin::Toggle-->
									<div class="topbar-item" data-toggle="dropdown" data-offset="10px,0px">
										
									</div>

									<!--end::Toggle-->

									<!--begin::Dropdown-->
									<div class="dropdown-menu p-0 m-0 dropdown-menu-anim-up dropdown-menu-sm dropdown-menu-right">

										<!--[html-partial:include:{"file":"partials/_extras/dropdown/languages.html"}]/-->
									</div>

									<!--end::Dropdown-->
								</div>

								<!--end::Languages-->  

								<!--begin::User-->               
								<%
            // 로그인 안되었을 경우 - 로그인, 회원가입 버튼을 보여준다.    
            if(session.getAttribute("sUserId")==null){ 
        %>
	 	<button onclick="location.href='signin'" class="btn btn-light-primary font-weight-bold">로그인</button>
		
		<%
            // 로그인 되었을 경우 - 로그아웃, 내정보 버튼을 보여준다.
            }else{ 
        %>
        
		<input type="button"  id="logout"  class="btn btn-light-primary font-weight-bold" onclick="location.href='/logout.do'" value="로그아웃" />
	
		<% } %>
								
								<div class="topbar-item">
									<div class="btn btn-icon btn-icon-mobile w-auto btn-clean d-flex align-items-center btn-lg px-2" id="kt_quick_user_toggle">
										<span class="text-muted font-weight-bold font-size-base d-none d-md-inline mr-1"></span>
										<span class="text-dark-50 font-weight-bolder font-size-base d-none d-md-inline mr-3"></span>
										<span class="symbol symbol-lg-35 symbol-25 symbol-light-success">
										</span>
									</div>  
								</div>

								<!--end::User-->
							</div>

							<!--end::Topbar-->
						</div>

						<!--end::Container-->
					</div>

					<!--end::Header-->