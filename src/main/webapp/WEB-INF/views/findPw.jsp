
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>


<html>
<head>
<title>패스워드 찾기 </title>

<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<h4 style="border-bottom: 1px solid #c5c5c5;">
				<i class="glyphicon glyphicon-user"> </i> 갱스터디 계정
			</h4>
				<h4 class="">패스워드를 잊으셨나요?</h4>
				<form id="findPw_action" method="post">
					<fieldset>
						<span class="help-block"> 
						사용자 아이디와 이메일 주소를 입력하세요.
						</span>
						<div class="form-group input-group">
							<span class="input-group-addon"> 아이디 </span> <input
								class="form-control" placeholder="아이디" id="id" name="id"
								type="text" required>
						</div>
						<div class="form-group input-group">
							<span class="input-group-addon"> 이메일 </span> <input
								class="form-control" placeholder="이메일" id="email" name="email"
								type="email" required>
						</div>
						<button type="submit" value="submit" class="btn btn-primary btn-block">계속</button>
					</fieldset>
				</form> 
			</div>
		</div>
	</div>
<script type="text/javascript">
//비밀번호 찾기 이벤트
$('#findPw_action').submit(function(e){
	findPw();
	e.preventDefault();
});
/*
6) 비밀번호 찾기 
*/
function findPw(){
	var fpwArray = $('#findPw_action').serializeArray();
	$.ajax({
		url : 'findPw_action',
		method : 'POST',
		data : fpwArray,
		dataType : 'text',
		success : function(textData) {
			if (textData.trim() !=null) {
				alert(findPw_action.id.value+"님의 비밀번호는 "+textData+"입니다.");
				location.href = '/login';
			}
		}
	})	
}
</script>