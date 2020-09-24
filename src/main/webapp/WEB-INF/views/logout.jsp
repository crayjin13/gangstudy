<%@ page contentType="text/html; charset=utf-8"%>                                        
 
 
<html>
<head>
</head>
<body>
<%
    HttpSession userSession = request.getSession();
    userSession.invalidate();
    out.println("<h1>로그아웃 되었습니다.</h1>");
%>
<h2>로그아웃 되었습니다.</h2>

</body>
</html>


