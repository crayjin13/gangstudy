<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	</head>
	<body>
	
	<form action="/gangstudy/login" method="post">
		유저목록 <br>
		<c:forEach items="${list}" var="userList" >
		    <tr>
		        <td>사용자 번호: <c:out value="${userList.user_no}"/></td>
		        <td>이름: <c:out value="${userList.name}"/></td>
		        <td>폰 번호: <c:out value="${userList.phone}"/></td>
		        <td>아이디: <c:out value="${userList.id}"/></td>
		        <td>이메일: <c:out value="${userList.email}"/></td>
		        <td>생년월일: <c:out value="${userList.bod}"/></td>
		        <td>성별: <c:out value="${userList.gender}"/></td>
		        <td>평점: <c:out value="${userList.rate}"/></td>
		        <td>포인트: <c:out value="${userList.points}"/></td>
		        <td>노트 : <c:out value="${userList.note}"/></td>
		     
		        <br>
		    </tr>
		</c:forEach>
		<hr>
		이름  : <input type="text" name="name" size="20"><br>
		번호 : <input type="text" name="phone" size="20"><br>
		아이디 : <input type="text" name="id" size="20"><br>
		이메일 : <input type="text" name="email" size="20"><br>
		생년월일 : <input type="text" name="bod" size="20"><br>
		성별 : <input type="text" name="gender" size="20"><br>
		평점 : <input type="text" name="rate" size="20"><br>
		포인트 : <input type="text" name="points" size="20"><br>
		노트 : <input type="text" name="note" size="20"><br>
		<input type="submit" value="회원가입">
	</form>
	</body>
</html>