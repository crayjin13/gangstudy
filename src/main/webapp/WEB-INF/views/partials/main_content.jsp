<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
img {
	display: block;
	margin: auto;
}

@font-face {
	font-family: 'Oneprettynight';
	src:
		url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.1/Oneprettynight.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}
</style>

<!--begin::Entry-->


<!--begin::Container-->
<div class="container">


	<!--begin::Content-->
	<div class="content d-flex flex-column flex-column-fluid"
		id="kt_content">



		<div class="row">      
			<div class="col-md-6">                
				<!--begin::Card-->
				   
					<!--begin::Form-->
					
					
					<form class="form" id="bookingForm"    
						action="/booking/make">   
						       <div class = "col-md-4">
					<p class = "main_text">갱스터디에서 공부하고<br/>   공부깡패 되자!</p>
							</div>
							<div class="card-footer">        
								<div class="form-group form-group-last">
									
										
										<div class="alert-text" style ="text-align: center "> 
										   
											
							</div>
   

									   
								</div>        
                         
								<div class="form-group ">
									 <input class="form-control col-md-10 select booking-input ic-calendar" type="text"
										id="start-date-input" name="startDateInput"  
										value="${startDate}">
								</div>
								<div class="form-group ">
									 <select class="form-control col-md-10 select booking-input ic-time"
										id="start-time-input" name="startTimeInput" form="bookingForm" 
										time="${startTime}">
										<option value="" > 시작시간</option>
									</select>
								</div>

								<div class="form-group ">
									 <select class="form-control col-md-10 select booking-input ic-time"
										id="end-time-input" name="endTimeInput" form="bookingForm"
										time="${endTime}">
										<option value=""> 종료시간</option> 
									</select>
								</div> 

								<div class="form-group ">
									 <select class="form-control col-md-10 select booking-input ic-member"
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

								<div class="form-group">    
                      
							<button type="button"                       
										onclick="location.href='http://pf.kakao.com/_xbgCJxb'"     
										class="btn btn-kakao btn-lg text-dark col-md-5"  >                 
							<img src="${pageContext.request.contextPath}/resources/images/ic-kakao.svg" class="ic_kakao" >카카오톡 문의하기</button>      
                               
									<button type="button"              
										class="btn btn-booking btn-lg col-md-5 "               
										id="bookingButton">예약하기</button>        
									 
									  
							      
							                                
								</div>       
							

								<div class="form-group ">

									
								</div>
								<!--begin: Code-->  
								<div class="example-code mt-10">
									<div class="example-highlight"></div>
								</div>
								<!--end: Code-->
							</div>
					</form>
					<!--end::Form-->
				
				<!--end::Card-->
  

			      
		</div>    
		 
			<div class="col-md-4 d-none d-xl-block "> 
			    
		<img src="${pageContext.request.contextPath}/resources/images/img-main.svg" class="img_main ">
			
		</div>
		 
		
		
		
		<!--end::Container-->
	</div>
	<!--end::Entry-->
</div>
<!--end::Content-->

<!--[html-partial:begin:{"id":"demo1/dist/inc/view/demos/pages/index","page":"index"}]/-->

<!--[html-partial:begin:{"id":"demo1/dist/inc/view/partials/content/dashboards/demo1","page":"index"}]/-->

<!--end::Entry-->
