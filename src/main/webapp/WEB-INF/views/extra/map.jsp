<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<style>
  table {
    border: 1px solid #444444;
    border-collapse: collapse;
  }
  th, td {
    border: 1px solid #444444;
  }
</style>

<head>
  <meta charset="utf-8">
  <title>Google Map</title>
</head>

<body>
	<div style="width: 80%; margin: 0 auto;">
		<div id="map" style="height: 60vh; margin: 0 auto;"></div>
		<table style="margin: 0 auto;">
			<tr>
				<th>표시</th>
				<th>지점</th>
				<th>경도</th>
				<th>위도</th>
				<th>조작</th>
			</tr>
			<tr>
				<td>파란원<input type="radio" name="marker" value="sMarker" checked="checked"></td>
				<td>출발지 : </td>
				<td><input id="sLat"></td>
				<td><input id="sLng"></td>
				<td><button id="sButton">이동하기</button></td>
			</tr>
			<tr>
				<td>빨간원<input type="radio" name="marker" value="eMarker"></td>
				<td>목적지 : </td>
				<td><input id="eLat"></td>
				<td><input id="eLng"></td>
				<td><button id="eButton">이동하기</button></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="4">
					출발지와 목적지의 
					<span id="direction">    </span>
					입니다.
				</td>
			</tr>
		</table>
	</div>
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCGgw2rmLQm9r8Dagq2osKfrotaX0uQqTs&callback=initMap&region=kr"></script>
	<script src="${pageContext.request.contextPath}/resources/js/extra/map.js"></script>
</body>
</html>