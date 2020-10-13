<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<html>
	<head>
		<title>Home</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	 <script>
	 
	 $(document).on('click', '#btnSearch', function(e){
			console.log(e);
			e.preventDefault();

			var url = "${pageContext.request.contextPath}/admin_cm";

			url = url + "?searchType=" + $('#searchType').val();

			url = url + "&search=" + $('#search').val();

			location.href = url;

			console.log(url);

		});	
	 
</script>	
	
	</head>
	<body>
		
	<!-- 관리자 입장 유저목록  --> 
	<form id="/admin_cm" method="post">
		유저목록 <br>
		<div class="w300" style="padding-right:10px">
		<input type="text" class="searchUser" name="search" id="search"  >
		<button class="btnsearch" name="btnSearch" id="btnSearch">아이디 검색</button>
		</div>
		<div>
		</div>
		<c:forEach items="${userList}" var="userList">
			<tr>
				<td>유저 번호: <c:out value="${userList.user_no}" /></td>
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
		
		
		
		
		
		
		
	</body>
	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
	
</html>