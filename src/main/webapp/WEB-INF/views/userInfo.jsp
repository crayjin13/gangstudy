<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
</head>
<body>


	<form id="userInfo" method="post">
		<br>


		<tr>

			<form name="form1" method="post">
				<table border="3" width="500px">
					<tr>
						<label> 나의 정보  </label>
					</tr>
					<tr>
						<td>이름</td>
						<td><input name="name" value="${sUserId.name}"
							readonly="readonly"></td>
					</tr>
					<tr>
						<td>아이디</td>
						<td><input name="id" value="${sUserId.id}"
							readonly="readonly"></td>
					</tr>
					<%-- <tr>
						<td>폰 번호</td>
						<td><input name="phone" value="${sUserId.phone}"
							readonly="readonly"></td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td><input type="password" name="pw" value="${sUserId.pw}"
							readonly="readonly"></td>
					</tr>
					<tr>
						<td>이메일</td>
						<td><input name="email" value="${sUserId.email}"
							readonly="readonly"></td>
					</tr>
					<tr>
						<td>생년월일</td>
						<td><input name="bod" value="${sUserId.bod}"
							readonly="readonly"></td>
					</tr>
					<tr>
						<td>성별</td>
						<td><input name="gender" value="${sUserId.gender}"
							readonly="readonly"></td>
					</tr> --%>
					<tr>
						<td>포인트</td>
						<td><input name="points" value="${sUserId.points}"
							readonly="readonly"></td>
					</tr>


				</table>

			</form>
		</tr>
		<button onclick="location.href='logOn'">정보수정</button>
		<button onclick="location.href='logout'">로그아웃</button>
		<button onclick="location.href='booking'">예약하기</button>

	</form>


</body>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="resources/js/wUser.js"></script>
</html>