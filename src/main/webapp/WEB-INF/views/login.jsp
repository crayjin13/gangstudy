<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <script>
</script>

<style type="text/css">




</style>



<!-- <link rel="stylesheet" href="resources/css/signUp.css"> -->
</head>

<body>
<!-- 관리자 입장 유저목록  -->
	 <form id="/login" method="post">
		유저목록 <br>
		<c:forEach items="${list}" var="userList">
			<tr>
				<td>사용자 번호: <c:out value="${userList.user_no}" /></td>
				<td>이름: <c:out value="${userList.name}" /></td>
				<td>폰 번호: <c:out value="${userList.phone}" /></td>
				<td>아이디: <c:out value="${userList.id}" /></td>
				<td>이메일: <c:out value="${userList.email}" /></td>
				<td>생년월일: <c:out value="${userList.bod}" /></td>
				<td>성별: <c:out value="${userList.gender}" /></td>
				<td>평점: <c:out value="${userList.rate}" /></td>
				<td>포인트: <c:out value="${userList.points}" /></td>
				<td>노트 : <c:out value="${userList.note}" /></td>

				<br>
			</tr>
		</c:forEach>
		
	</form>
		<!-- 회원가입  -->
	<hr>
	<td>
		
		<form id="sign_up" method="post">
			이름 :   <input type="text" id="name" name="name" class="required" size="20"><br>
			번호 :   <input type="text" id="phone" name="phone" class="required" size="20"><br>
			아이디 : <input type="text" id="id" name="id" class="required" size="20"><br>
			비번 :   <input type="text" id="pw" name="pw" class="required" size="20"><br>
		비번 재확인: <input type="text" id="pw2" name="pw2" class="int" size="20"><span class="error"></span><br>
			이메일 : <input type="text" id="email" name="email" class="required" size="20"><br>
			생년월일 : <input type="date" id="bod" name="bod" class="required" size="20"><br>
			
			성별 : <select name="gender">
							<option>성별</option>
							<option value="M">M</option>
							<option value="F">F</option>
					</select>
<br><br>
			<input type="button" id="btn"  value="회원가입">

		</form>
	</td>
		
		<h2>Login</h2>

<!-- 로그인  -->
<form id="user_login_action" action="/logOn" method="post">
  <label for="fname">ID:</label><br>
  <input type="text" id="i" class="required" name="id" value=""><br>
  <label for="lname">PW:</label><br>
  <input type="text" id="p" class="required" name="pw" value=""><br>
  <input type="button" id="loginbtn"  value="로그인">
<a href="findPw" title=""> 비밀번호를 잊으셨나요 ?</a>

</form> 
	

</body>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
 <script src="resources/js/jquery.validate.min.js"></script>
 <script type="text/javascript" src="resources/js/wUser.js"></script>
 
</html>
<!-- https://stackoverflow.com/questions/10340392/javascript-with-spring-mvc-doesnt-work  -->
