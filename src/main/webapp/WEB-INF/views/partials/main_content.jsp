<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
  img { display : block;

                    margin : auto;}


@font-face { 
    font-family: 'Cafe24Oneprettynight';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.1/Cafe24Oneprettynight.woff') format('woff');
    font-weight: normal;
    font-style: normal;
</style>

<!--begin::Entry-->


<!--begin::Container-->
<div class="container">


	<!--begin::Content-->
	<div class="content d-flex flex-column flex-column-fluid"
		id="kt_content">



		<div class="row">
			<div class="col-md-8">
				<!--begin::Card-->
				<div class="card card-custom gutter-b example example-compact">
					<div class="card-header">
						<h3 class="card-title">
							<!-- 글쓰고 싶으면 여기에 -->
							시범운영중이며 2시간 무료 이용가능합니다. <br>
							홈페이지 리뉴얼중입니다. <br>
							예약은 하단의 "카톡으로 문의하기"를 클릭하시면 도와드리겠습니다.
						</h3>   
							

					

							<img src="${pageContext.request.contextPath}/resources/images/freeevent.PNG"   style="max-width:60%; height:auto;" >

					
 

					</div>
					<!--begin::Form-->    
					<form class="form" id="bookingForm" action="${pageContext.request.contextPath}/booking/make">
						<div class="card-body">
							<div class="card-footer">
								<div class="form-group form-group-last">
									<%--<div class="alert alert-custom alert-default" role="alert">
										<div class="alert-icon">
											<span class="svg-icon svg-icon-primary svg-icon-xl"> <!--begin::Svg Icon | path:${pageContext.request.contextPath}/resources/assets/media/svg/icons/Tools/Compass.svg-->
												<span class="svg-icon svg-icon-primary svg-icon-2x">
													<!--begin::Svg Icon | path:C:\wamp64\www\keenthemes\themes\metronic\theme\html\demo1\dist/../src/media/svg/icons\Devices\Display1.svg-->
													<svg xmlns="http://www.w3.org/2000/svg"
														xmlns:xlink="http://www.w3.org/1999/xlink" width="24px"
														height="24px" viewBox="0 0 24 24" version="1.1">
    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
        <rect x="0" y="0" width="24" height="24" />
        <path
															d="M11,20 L11,17 C11,16.4477153 11.4477153,16 12,16 C12.5522847,16 13,16.4477153 13,17 L13,20 L15.5,20 C15.7761424,20 16,20.2238576 16,20.5 C16,20.7761424 15.7761424,21 15.5,21 L8.5,21 C8.22385763,21 8,20.7761424 8,20.5 C8,20.2238576 8.22385763,20 8.5,20 L11,20 Z"
															fill="#000000" opacity="0.3" />
        <path
															d="M3,5 L21,5 C21.5522847,5 22,5.44771525 22,6 L22,16 C22,16.5522847 21.5522847,17 21,17 L3,17 C2.44771525,17 2,16.5522847 2,16 L2,6 C2,5.44771525 2.44771525,5 3,5 Z M4.5,8 C4.22385763,8 4,8.22385763 4,8.5 C4,8.77614237 4.22385763,9 4.5,9 L13.5,9 C13.7761424,9 14,8.77614237 14,8.5 C14,8.22385763 13.7761424,8 13.5,8 L4.5,8 Z M4.5,10 C4.22385763,10 4,10.2238576 4,10.5 C4,10.7761424 4.22385763,11 4.5,11 L7.5,11 C7.77614237,11 8,10.7761424 8,10.5 C8,10.2238576 7.77614237,10 7.5,10 L4.5,10 Z"
															fill="#000000" />
    </g>
</svg> <!--end::Svg Icon-->
											</span>
											</span>
										</div>
										 <div class="alert-text">
											갱스터디는
											<code>24시간 언제든지</code>
											원하는 시간에 자유롭게 이용할 수 있는 공간입니다.
											<code>스터디, 사무 , 모임, 강의 녹화 </code>
											등 원하시는 시간을 충분히 가질 수 있도록 도와드리겠습니다.

										</div> --%>
										</div>
								</div>
								
								<div class="form-group cafe24">
									<label> 시작 시간</label>
										<input class="form-control" type="text" id="start-date-input" name="startDateInput"
										value = ${startDate}>
										<select class="form-control" id="start-time-input" name="startTimeInput" form="bookingForm"
										time = ${startTime}>
											<option value="">날짜를 선택해주세요.</option>
										</select>
								</div>
								
								<div class="form-group cafe24">
									<label> 종료 시간</label>
										<input class="form-control" type="text" id="end-date-input" name="endDateInput"
										value = ${endDate}>
										<select class="form-control" id="end-time-input" name="endTimeInput" form="bookingForm"
										time = ${endTime}>
											<option value="">날짜를 선택해주세요.</option>
										</select>
								</div>
								
								<div class="form-group cafe24">
									<label for="exampleTextarea">인원수</label> <select
										class="form-control" id="user-count-input" name="userCountInput"
										people=${people}>
										<option value="">인원을 선택해주세요.</option>
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
										class="btn btn-outline-primary btn-lg btn-block mr-2 cafe24 " id="bookingButton">예약
										하기</button>
								</div>
								<div class="form-group">


									<input type="button"
										class="btn btn-outline-secondary btn-lg btn-block mr-2 cafe24" onClick="location.href='/notice'" value="이용하기"/>
								    
								</div>

								<div class="form-group cafe24">  

									<button type="button"
										onclick="location.href='http://pf.kakao.com/_xbgCJxb'"
										class="btn btn-outline-warning btn-lg btn-block mr-2 ">
										카톡으로 문의하기</button>
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
				<!--end::Card-->


			</div>
		</div>
		<!--end::Container-->
	</div>
	<!--end::Entry-->
</div>
<!--end::Content-->

<!--[html-partial:begin:{"id":"demo1/dist/inc/view/demos/pages/index","page":"index"}]/-->

<!--[html-partial:begin:{"id":"demo1/dist/inc/view/partials/content/dashboards/demo1","page":"index"}]/-->

<!--end::Entry-->
