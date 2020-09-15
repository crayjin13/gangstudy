<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	</head>
	<body>
		전체 예약 목록 <br>
		<c:forEach items="${debug}" var="debug">
		    <tr>
		        <td>예약 번호: <c:out value="${debug.book_no}"/></td>
		        <td>사용자 번호: <c:out value="${debug.user_no}"/></td>
		        <td>방 번호: <c:out value="${debug.room_no}"/></td>
		        <td>예약일: <c:out value="${debug.book_dt}"/></td>
		        <td>시작시간: <c:out value="${debug.ci}"/></td>
		        <td>종료시간: <c:out value="${debug.co}"/></td>
		        <td>인원: <c:out value="${debug.people}"/></td>
		        <td>예약상태: <c:out value="${debug.state}"/></td>
		        <td>신청일: <c:out value="${debug.request_dt}"/></td>
		        <br>
		    </tr>
		</c:forEach>
		<hr>
		예약 목록<br>
		<c:forEach items="${data}" var="book">
		    <tr>
		        <td>예약 번호: <c:out value="${book.book_no}"/></td>
		        <td>사용자 번호: <c:out value="${book.user_no}"/></td>
		        <td>방 번호: <c:out value="${book.room_no}"/></td>
		        <td>예약일: <c:out value="${book.book_dt}"/></td>
		        <td>시작시간: <c:out value="${book.ci}"/></td>
		        <td>종료시간: <c:out value="${book.co}"/></td>
		        <td>인원: <c:out value="${book.people}"/></td>
		        <td>예약상태: <c:out value="${book.state}"/></td>
		        <td>신청일: <c:out value="${book.request_dt}"/></td>
		        <br>
		    </tr>
		</c:forEach>
		<hr>
		<form action="/gangstudy/booking/request" method="post">
			<label>Choose a booking </label><br>
			date :
			<select name="book_dt" onchange="chageDateSelect()">
				<option value="false">날짜를 선택해주세요</option>
				<c:forEach items="${dates}" var="date">
					<option value="${date}">${date}</option>
				</c:forEach>
			</select><br>
			people :
			<select name="people">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
			</select><br>
			start :
			<select name="ci" onchange="chageCiSelect()">
			</select><br>
			end :
			<select name="co">
			</select><br>
			<input type="submit" value="예약 신청">
		</form>
	</body>
	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="js/booking.js"></script>
</html>
