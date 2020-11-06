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
		<c:forEach items="${books}" var="book" varStatus="status">
		    <tr>
		        <td>예약 번호: <c:out value="${book.book_no}"/></td>
		        <td>사용자 이름: <c:out value="${names[status.index]}"/></td>
		        <td>방 번호: <c:out value="${book.room_no}"/></td>
		        <td>시작시간: <c:out value="${book.check_in}"/></td>
		        <td>종료시간: <c:out value="${book.check_out}"/></td>
		        <td>인원: <c:out value="${book.people}"/></td>
		        <td>비용: <c:out value="${costs[status.index]}"/></td>
		        <td>예약상태: <c:out value="${book.state}"/></td>
		        <td>신청일: <c:out value="${book.request_dt}"/></td>
		    </tr>
	        <br>
		</c:forEach>
		<hr>
		<a href="${pageContext.request.contextPath}/booking/make">
			<button>예약신청</button>
		</a>
		<button onClick="location.href='${pageContext.request.contextPath}/booking/make'">예약신청</button>
		<button onClick="location.href='${pageContext.request.contextPath}/booking/edit'">예약수정</button>
		<button onClick="location.href='${pageContext.request.contextPath}/booking/view'">예약확인</button>
		<button onClick="location.href='${pageContext.request.contextPath}/booking/cancel'">예약취소</button>
	</body>
	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/booking.js"></script>
</html>
