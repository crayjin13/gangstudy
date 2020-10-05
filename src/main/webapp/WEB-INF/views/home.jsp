<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
	<head>
		<title>Home</title>
	</head>
	<body>
		<h1>
			Hello world!  
		</h1>
		<button onclick="location.href='login'">로그인</button>
		<button onclick="location.href='logout'">로그아웃</button>
		<button onclick="location.href='userInfo'">마이페이지</button>
		<button onclick="location.href='booking'">예약하기</button>
		
		<b><a mid='${member.mId}' id="user_signUp"  href="#" title="">${user.sign_up}</a></b>
		
		<P>  The time on the server is ${serverTime}. </P>
	</body>
	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/home.js"></script>
</html>
