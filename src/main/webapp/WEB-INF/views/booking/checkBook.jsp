<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="com.jts.gangstudy.domain.Booking" %>
<html>
	<head>
		<title>GangStudy</title>
	</head>
	<body>
		예약 정보 <br>
        사용자 아이디: <span>${userID}</span><br>
        <input readonly>${book.room_no}<br>
        방 번호: <span id="room_no" class = "book">${book.room_no}</span><br>
        시작일: <span id="check_in" class = "book">${book.check_in}</span><br>
        종료일: <span id="check_out" class = "book">${book.check_out}</span><br>
        인원: <span id="people" class = "book">${book.people}</span><br>
        금액: <span>${charge}</span><br>
		<input type="hidden" id="href" value="${pageContext.request.contextPath}/payment/choose">
	    <button type="submit" id="confirmButton">확인</button>
	</body>
	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/booking.js"></script>
</html>
