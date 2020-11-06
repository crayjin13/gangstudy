<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>GangStudy</title>
	</head>
	<body>
		예약 신청 페이지
		<hr>
		<form action="${pageContext.request.contextPath}/booking/check" method="GET">
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
			<input type="hidden" name="href" value="make">
			<input type="submit" value="예약 신청">
		</form>
	</body>
	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/booking.js"></script>
</html>
