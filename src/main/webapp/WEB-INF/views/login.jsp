<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> <script>
$(document).ready(function(){
	signUp_function();

});
function signUp_function(){
	$("#btn").click(function(){
	var userArray = $('#sign_up').serialize();
console.log("#값이 오는지 확인 ---"+userArray);
	
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
	});
	// e.preventDefault();
}
)};

</script>
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
		
		<form id="sign_up" method="post">
			이름 :   <input type="text" id="name" name="name" class="required" size="20"><br>
			번호 :   <input type="text" id="phone" name="phone" class="required" size="20"><br>
			아이디 : <input type="text" id="id" name="id" class="required" size="20"><br>
			비번 :   <input type="text" id="pw" name="pw" class="required" size="20"><br>
			이메일 : <input type="text" id="email" name="email" class="required" size="20"><br>
			생년월일:<input type="text" id="bod" name="bod" class="required" size="20"><br>
			성별 :   <input type="text" id="gender" name="gender" class="required" size="20"><br>

			<input type="button" id="btn" onclick="signUp_function();" value="회원가입">

		</form>
	</td>

	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/wUser.js"></script> --%>

</body>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
 
 <!--  <script type="text/javascript" src="js/wUser.js"></script>
 -->
</html>
<!-- https://stackoverflow.com/questions/10340392/javascript-with-spring-mvc-doesnt-work  -->
