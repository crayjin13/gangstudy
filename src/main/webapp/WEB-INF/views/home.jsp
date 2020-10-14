<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
	<head>
		<title>Home</title>
	</head>
	<body>
		<h1>
			Hello world!  
		</h1>
		
		<%
            // 로그인 안되었을 경우 - 로그인, 회원가입 버튼을 보여준다.
            if(session.getAttribute("sUserId")==null){ 
        %>
		<button onclick="location.href='login'">로그인</button>
		<%
            // 로그인 되었을 경우 - 로그아웃, 내정보 버튼을 보여준다.
            }else{ 
        %>
		<button onclick="location.href='logout'">로그아웃</button>
		<button onclick="location.href='userInfo'">마이페이지</button>
		<button onclick="location.href='booking'">예약하기</button>
		<button onclick="location.href='admin_cm'">관리자 페이지</button>
		<% } %>
	 	<% 
			//관리자 로그인 
			if(session.getAttribute("sUserId")!=null && session.getAttribute("sUserId").equals("gangstudy")){
		%>
		
				
		<% 	}%> 
		
		
		<b><a mid='${member.mId}' id="user_signUp"  href="#" title="">${user.sign_up}</a></b>
		
		<P>  The time on the server is ${serverTime}. </P>
	</body>
	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/home.js"></script>
</html>
