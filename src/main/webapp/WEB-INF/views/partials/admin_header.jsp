<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
						<div class="container d-flex align-center-items-stretch justify-content-center">  

							<!--begin::Left-->     
							<div class="d-flex align-items-stretch mr-3">
           
								<!--begin::Header Logo-->          
								<div class="header-logo">	<!-- 모바일에서는 안보이게 적용 d-none d-lg-block d-xl-none  -->      
									<a href="/">   
										<img alt="Logo" src="${pageContext.request.contextPath}/resources/assets/media/logos/gang-logo-txt.svg" class="img_logo_txt max-h-200px d-none d-xxl-block mt-6" /> 
									<%-- max-h-200px d-none d-xl-block  d-none d-lg-block d-xl-none mt-6 px-12	 <img alt="Logo" src="${pageContext.request.contextPath}/resources/assets/media/logos/gang-logo-txt.svg" class="logo-sticky max-h-40px" />  --%>
									</a>                        
								</div>                 
        
								<!--end::Header Logo-->
            
								<!--begin::Header Menu Wrapper-->
								<div class="header-menu-wrapper header-menu-wrapper-middle" id="kt_header_menu_wrapper">
  
									<!--begin::Header Menu--> 
									<div id="kt_header_menu" class="header-menu header-menu-middle header-menu-mobile header-menu-layout-default">

										<!--begin::Header Nav-->
										<ul class="menu-nav">
											<li class="menu-item menu-item-submenu" aria-haspopup="true"
									data-menu-toggle="hover"><a href="/admin/jts"
									class="menu-link "> 
													<span class="menu-text ">관리툴</span>    
													<i class="menu-arrow"></i> 
												</a>
												
											</li>
												<li class="menu-item menu-item-submenu" aria-haspopup="true"
									data-menu-toggle="hover"><a
									href="/admin/users" class="menu-link ">
													<span class="menu-text">회원목록</span> 
													<span class="menu-desc"></span>
													<i class="menu-arrow"></i>
												</a>
												
											</li>
											
											<li class="menu-item menu-item-submenu" aria-haspopup="true"
									data-menu-toggle="hover"><a
									href="/admin/books" class="menu-link "> 
													<span class="menu-text">예약목록</span>
													<span class="menu-desc"></span>
													<i class="menu-arrow"></i>
												</a>
											
											</li>
											
										<li class="menu-item menu-item-submenu" aria-haspopup="true"
									data-menu-toggle="hover"><a href="/admin/blackList"
									class="menu-link "> 
													<span class="menu-text">블랙고객</span>
													<span class="menu-desc"></span>
													<i class="menu-arrow"></i>
												</a>
											</li>
											
								<li class="menu-item menu-item-submenu" aria-haspopup="true"
									data-menu-toggle="hover"><a href="/admin/timeline"
									class="menu-link ">
													<span class="menu-text">시스템 로그</span>
													<span class="menu-desc"></span> 
													<i class="menu-arrow"></i>
												</a>
											</li>
											<!-- 띄어쓰기함  -->  
											<li class="menu-item menu-item-submenu menu-item-rel" data-menu-toggle="click" aria-haspopup="true">
												<a href="javascript:;" class="menu-link menu-toggle">
													<span class="menu-text"></span>
													<span class="menu-desc"></span> 
													
												</a>    
											</li>
					
             
										<%
            // 로그인 안되었을 경우 - 로그인, 회원가입 버튼을 보여준다.    
            if(session.getAttribute("sUserId")==null){ 
        %>
     
<li class="menu-item menu-item-submenu" aria-haspopup="true"
									data-menu-toggle="hover"><a href="/signup_main"
									class="menu-link ">
													<span class="menu-text">회원가입</span>
													<span class="menu-desc"></span>
													<i class="menu-arrow"></i>  
												</a>
											</li>
											
	
												<li class="menu-item menu-item-submenu" aria-haspopup="true"
									data-menu-toggle="hover"><a href="/signin"    
									class="menu-link ">
													<span class="menu-text">로그인</span>
													<span class="menu-desc"></span>
													<i class="menu-arrow"></i> 
												</a>
											</li>
											<%
            // 로그인 되었을 경우 - 로그아웃, 내정보 버튼을 보여준다.
            }else{ 
        %>
										<li class="menu-item menu-item-submenu" aria-haspopup="true"
									data-menu-toggle="hover"><a href="/logout.do"    
									class="menu-link ">
													<span class="menu-text">로그아웃</span>
													<span class="menu-desc"></span>
													<i class="menu-arrow"></i> 
												</a>
											</li>
											<% } %>
											
	</ul>
									

										<!--end::Header Nav-->
									</div>

									<!--end::Header Menu-->
								</div>

								<!--end::Header Menu Wrapper-->
							</div>

							<!--end::Left-->

							<!--begin::Topbar-->
				 

							<!--end::Topbar-->
						</div>

						<!--end::Container-->
					</div>

					<!--end::Header-->