<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>회원 상세 정보</title>

<h3>회원 상세 정보</h3>
<br>
<form id="click_user" method="POST">
<div class="click_user"
	style="width: 300px; margin-left: 10px; margin-top: 10px;">

		<table class="table">
			<tr>
				<td>유저번호</td>
				<td>${user.user_no }</td>
			<tr>
				<td>이름</td>
				<td>${user.name }</td>
			</tr>
			<tr>
				<td>연락처</td>
				<td>${user.phone }</td>
			</tr>
			<tr>
				<td>아이디</td>
				<td>${user.id }</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>${user.pw }</td>
			</tr>
			
			<tr>
				<td>이메일</td>
				<td>${user.email }</td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td>${user.bod }</td>
			</tr>
			 
			<tr>
				<td>성별</td>
				<td>${user.gender }</td>
			</tr>
			<tr>
				<td>평가점수</td>
				<td>${user.rate }</td>
			</tr>
			<tr>
				<td>포인트</td>
				<td>${user.points }</td>
			</tr>
			<tr>
				<td>노트</td>
				<td>${user.note }</td>
			</tr>
			</table>
			<div style="margin-top : 20px">

				<button type="button" class="btn btn-sm btn-primary" id="btnUpdate">수정</button>

				<button type="button" class="btn btn-sm btn-primary" id="btnDelete">삭제</button>

				<button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>

			</div>




			
		
</div>
</form>