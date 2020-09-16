<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- <script>
$(document).ready(function(){
    var userArray = $('#sign_up').text($("form").serialize());
    $.ajax({
		url : 'signUp',
		data : userArray,
		method : 'POST',
		dataType : 'text',
		success : function(textData) {
			console.log(textData);
			if (textData.trim() == "true"){
				sign_up.name.value = textData.name;
				sign_up.phone.value = textData.phone;
				sign_up.id.value = textData.id;
				sign_up.pw.value = textData.pw;
				sign_up.email.value = textData.email;
				sign_up.bod.value = textData.bod;
				sign_up.gender.value = textData.gender;
						
				location.href = '/gangstudy/login';
			} else if (textData.trim() == "false"){
				
			}
		}
	
	})
    
  });

</script> -->
</head>

<body>

	<form action="/gangstudy/login" method="post">
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
	<hr>
	<td>
		<!-- <form action="/gangstudy/signUp" method="post"> -->
		<form action="" method="post">
			이름 : <input type="text" id="name" name="name" class="required" size="20"><br>
			번호 : <input type="text" id="phone" name="phone" class="required" size="20"><br>
			아이디 : <input type="text" id="id" name="id" class="required" size="20"><br>
			비번 : <input type="text" id="pw" name="pw" class="required" size="20"><br>
			이메일 : <input type="text" id="email" name="email" class="required" size="20"><br>
			생년월일 : <input type="text" id="bod" name="bod" class="required" size="20"><br>
			성별 : <input type="text" id="gender" name="gender" class="required" size="20"><br>
	<!-- 		<input type="submit" value="회원가입"> 
<input type="submit"  onclick="location.href='signUp.jsp'">회원가입</button>
	-->
			<button id="sign_up" >회원가입</button> 	

		</form>
	</td>

	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/wUser.js"></script> --%>
	<%--  <script type="text/javascript" src="/webapp/js/wUser.js"></script> --%>

</body>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/wUser.js"></script>
</html>
<!-- https://stackoverflow.com/questions/10340392/javascript-with-spring-mvc-doesnt-work  -->
