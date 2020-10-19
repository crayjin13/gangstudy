<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>

<style type="text/css">
table {
	margin-right: auto;
	border: 3px solid skyblue;
}

td {
	border: 1px solid skyblue
}

#title {
	background-color: skyblue
}
</style>

<title>Home</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

// 검색 기능
	$(document).on('click', '#btnSearch', function(e) {
		console.log(e);
		e.preventDefault();

		var url = "${pageContext.request.contextPath}/admin_cm";

		url = url + "?searchType=" + $('#searchType').val();

		url = url + "&search=" + $('#search').val();

		location.href = url;

		console.log(url);

	});

	// 검색 창에 기본글 적어놓고 클릭시 글 삭제
	$(function() {
		$(".searchUser").focus(function() {
			$(this).val('');
		}).blur(function() {
			if ($(this).val() == "") {
				$(this).val("이름 또는 아이디로 검색");
			}
		});
	});
	
	
	function fn_userView(id){

		var url = "${pageContext.request.contextPath}/click_user";
 
		url = url + "?id="+id;

		location.href = url;

	}

	

	
	

</script>

</head>
<body>

	<!-- 관리자 입장 유저목록  -->
	<form id="/admin_cm" method="post">
		<div class="w300" style="padding-left: 20px">
			유저목록 <br> <input type="text" class="searchUser" name="search"
				id="search" VALUE="이름 또는 아이디로 검색">
			<button class="btnsearch" name="btnSearch" id="btnSearch">회원
				검색</button>

			<table id="userList">
				<thead>
					<tr>
						<th>유저 번호</th>
						<th>이름</th>
						<th>폰 번호</th>
						<th>아이디</th>
						<th>이메일</th>
						<th>생년월일</th>
						<th>성별</th>
						<th>평점</th>
						<th>포인트</th>
						<th>노트</th>

					</tr>

				</thead>

				<tbody>
				<c:set var="userList" value="${userList }"/>
					<c:forEach items="${userList}" varStatus="vs" var="userList">
						<tr>
							<td><c:out value="${userList.user_no}" /></td>
							<td><a href="#" onClick="fn_userView(<c:out value="${userList.id}"/>)">
							<c:out value="${userList.name}" /></a></td>
							<td><c:out value="${userList.phone}" /></td>
							<td><c:out value="${userList.id}" /></td>
							<td><c:out value="${userList.email}" /></td>
							<td><c:out value="${userList.bod}" /></td>
							<td><c:out value="${userList.gender}" /></td>
							<td><c:out value="${userList.rate}" /></td>
							<td><c:out value="${userList.points}" /></td>
							<td><c:out value="${userList.note}" /></td>
						</tr>
						<br>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<br>

	</form>












</body>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

</html>