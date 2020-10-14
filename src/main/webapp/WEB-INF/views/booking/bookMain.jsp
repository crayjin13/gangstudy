<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>GangStudy</title>
	</head>
	<body>
		전체 예약 목록 <br>
		<c:forEach items="${debug}" var="debug">
		    <tr>
		        <td>예약 번호: <c:out value="${debug.book_no}"/></td>
		        <td>사용자 번호: <c:out value="${debug.user_no}"/></td>
		        <td>방 번호: <c:out value="${debug.room_no}"/></td>
		        <td>시작시간: <c:out value="${debug.check_in}"/></td>
		        <td>종료시간: <c:out value="${debug.check_out}"/></td>
		        <td>인원: <c:out value="${debug.people}"/></td>
		        <td>예약상태: <c:out value="${debug.state}"/></td>
		        <td>신청일: <c:out value="${debug.request_dt}"/></td>
		        <br>
		    </tr>
		</c:forEach>
		<hr>
		<button onClick="location.href='/booking/make'">예약신청</button>
		<button onClick="location.href='/booking/edit'">예약수정</button>
		<button onClick="location.href='/booking/view'">예약확인</button>
		<button onClick="location.href='/booking/cancel'">예약취소</button>
	</body>
	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/booking.js"></script>
</html>
