<%@page import="com.jts.gangstudy.repository.UserDao"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>






</head>
<body>







	<form id="modify_action" method="POST">


	<h2> 나의 회원 정보 수정  </h2>


		<!-- 이름 변경 불가(Hidden) 기존 값 전송 -->
		<div class="cp-field">
			<h5>이름(수정 불가)</h5>
			<div class="cpp-fiel">
				<input type="text" name="name" value="${sUserId.name}" readonly
					placeholder="수정불가"> <i class="la la-user"></i>
			</div>
		</div>

		<!-- 전화번호 변경 -->
		<div class="cp-field">
			<h5>
				전화번호<br> "-" 없이 번호만 입력 ex)01012345678
			</h5>
			<div class="cpp-fiel">
				<input type="text" name="phone" class="required"
					value="${sUserId.phone}" placeholder="'-'없이 번호만 입력"> <i
					class="la la-user"></i>
			</div>
		</div>

		<!-- 아이디 변경 불가(Hidden) 기존 값 전송 -->
		<div class="cp-field">
			<h5>아이디(수정 불가)</h5>
			<div class="cpp-fiel">
				<input type="text" name="id" value="${sUserId.id} " readonly
					placeholder="수정불가"> <i class="la la-user"></i>
			</div>
		</div>
		<!-- 비밀번호 변경 -->
		<div class="cp-field">
			<h5>새로운 패스워드</h5>
			<div class="cpp-fiel">
				<input type="password" name="pw" class="required" value="${sUserId.pw}"
					placeholder="새로운 패스워드"> <i class="la la-lock"></i>
			</div>
		</div>

		<!-- 이메일 변경 -->
		<div class="cp-field">
			<h5>
				새로운 이메일<br> ex) corona2020@world.com
			</h5>
			<div class="cpp-fiel">
				<input type="text" name="email" class="required"
					value="${sUserId.email}" placeholder="새로운 이메일"> <i
					class="la la-user"></i>
			</div>
		</div>

		<!-- 생년월일 -->
		<div class="cp-field">
			<h5>
				생년월일<br>
			</h5>
			<div class="cpp-fiel">
				<input type="text" name="bod" class="required"
					value="${sUserId.bod}" placeholder="생년월일 수정"> <i
					class="la la-user"></i>
			</div>
		</div>

		<!-- 성별 -->
		<div class="cp-field">
			<h5>
				성별 <br>
			</h5>
			<div class="cpp-fiel">
				<input type="text" name="gender" class="required"
					value="${sUserId.gender}" placeholder="성전환"> <i
					class="la la-user"></i>
			</div>
		</div><br>


		<div >
			
			
	<input type="button" id="modifybtn" value="수정"> 
			
		</div>
	</form>



	









</body>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
 <script type="text/javascript" src="resources/js/wUser.js"></script>
</html>
