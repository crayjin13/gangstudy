<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>GangStudy</title>
	</head>
	<body>
		<form method="post" action='${pageContext.request.contextPath}/payment/kakaopay'>
		    <button>카카오페이로 결제하기</button>
		</form>
	</body>
</html>
