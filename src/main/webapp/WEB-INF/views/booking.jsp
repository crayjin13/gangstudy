<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	</head>
	<body>
	
	<form action="/gangstudy/booking/request" method="post">
		예약 목록 <br>
		<c:forEach items="${debug}" var="book">
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
		year : <input type="text" name="year" size="20"><br>
		month : <input type="text" name="month" size="20"><br>
		date : <input type="text" name="date" size="20"><br>
		people : <input type="text" name="people" size="20"><br>
		start : <input type="text" name="ci" size="20"><br>
		end : <input type="text" name="co" size="20"><br>
		<input type="submit" value="예약 신청">
	</form>
	</body>
</html>
