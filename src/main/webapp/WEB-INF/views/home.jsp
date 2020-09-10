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
	테스트 페이지!   
</h1>
<form method="post" action="searchDB.jsp">
검색할 아이디:<input type ="text" name="name">
<input type="submit" value="검색">
</form>





<P>  The time on the server is ${serverTime}. </P>



<script type="text/javascript" src="js/wUser.js"></script>
</body>
</html>
