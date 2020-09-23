<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Home</title>
</head>
<body>




	<form id="logout" action="/logout" method="POST">
		<c:if test="${sUserId != null }">
			<h1>로그인 성공 ! ${sUserId.name}님, 반갑습니다.</h1>
			<input type="button" onclick="location.href='./logout'" value="로그아웃">
		</c:if>
		<c:if test="${sUserId = null }">
			<h1>로그아웃 되었습니다.</h1>
		</c:if>
	</form>







	<script type="text/javascript" src="js/wUser.js"></script>
</body>
</html>
