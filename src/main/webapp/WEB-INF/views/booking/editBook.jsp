<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="com.jts.gangstudy.domain.Booking" %>
<!DOCTYPE html>
<html>
	<head>
		<title>GangStudy</title>
	</head>
	<body>
		예약 수정 페이지
		<hr>
	<c:if test="${empty book}">
		대기중인 예약이 없습니다.
	</c:if>
	<c:if test="${not empty book}">
		기존 예약<br>
        사용자 아이디: ${userID}<br>
        방 번호: ${book.room_no}<br>
        시작시간: ${book.check_in}<br>
        종료시간: ${book.check_out}<br>
        인원: ${book.people}<br>
        금액: ${charge}<br>
        
		<hr>
		
		<form action="/booking/check" method="GET">
			수정사항<br>
			인원 :
			<select name="people">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
			</select><br>
			시작일 :
			<select name="startDate" onchange="startDateSelect()">
				<option value="false">날짜를 선택해주세요</option>
				<c:forEach items="${dates}" var="date">
					<option value="${date}">${date}</option>
				</c:forEach>
			</select><br>
			시작시간 :
			<select name="startTime" onchange="getEndTimeOptions()">
			</select><br>
			종료일 :
			<select name="endDate" onchange="getEndTimeOptions()">
			</select><br>
			종료시간 :
			<select name="endTime">
			</select><br>
			<input type="hidden" name="href" value="edit">
			<input type="submit" value="예약 수정">
		</form>	
	</c:if>
	</body>
	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/booking.js"></script>
</html>