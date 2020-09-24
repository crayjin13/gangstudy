<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
</head>
<body>


	<form action="/userInfo" method="post">
		<br>
		<c:forEach items="${userInfo}" var="info">
			<tr>


				<form name="form1" method="post">
					<table border="1" width="400px">
						<tr>
							<td>이름</td>
							<td><input name="name" value="${user.name}"></td>
						</tr>
						<tr>
							<td>폰 번호</td>
							<td><input name="phone" value="${user.phone}"></td>
						</tr>
						<tr>
							<td>아이디</td>
							<td><input name="id" value="${user.id}" readonly="readonly"></td>
						</tr>
						<tr>
							<td>비밀번호</td>
							<td><input name="pw" value="${user.pw}"></td>
						</tr>
						<tr>
							<td>이메일</td>
							<td><input name="email" value="${user.email}"></td>
						</tr>
						<tr>
							<td>생년월일</td>
							<td><input name="bod" value="${user.bod}"></td>
						</tr>
						<tr>
							<td>성별</td>
							<td><input name="gender" value="${user.gender}"></td>
						</tr>
						<tr>
							<td>포인트</td>
							<td><input name="points" value="${user.points}"
								readonly="readonly"></td>
						</tr>


					</table>

				</form>
			</tr>
		</c:forEach>

	</form>


</body>
</html>