<%@page language="java" import="java.io.*,com.jts.gangstudy.domain.SendMmsMessage" contentType="text/html;charset=euc-kr" %>
<%


/*
 * 요청값
 */
String userid = "malp86";   // [필수] 뿌리오 아이디
String msgid = "123456789"; // [필수] 발송 msgid


try {
    SendMmsMessage sendMmsMessage = new SendMmsMessage();

    String response_str = sendMmsMessage.cancel( userid, msgid);
	
	//response 
	System.out.println("=============================");
	System.out.println(response_str);
	System.out.println("=============================");

} catch (IOException localIOException) {
    out.println(localIOException.toString());
}


/*
 * 응답값
 *
 *  <성공시>
 *    "ok|1"               - 취소결과|취소건수
 *
 *  <실패시>
 *    "invalid_member"     - 연동서비스 신청이 안 됐거나 없는 아이디
 *    "under_maintenance"  - 요청시간에 서버점검중인 경우
 *    "allow_https_only"   - http 요청인 경우
 *    "invalid_ip"         - 등록된 접속가능 IP가 아닌 경우
 *    "invalid_msgid"      - 발송 msgid에 오류가 있는 경우
 *    "master_not_exist"   - 취소 할 발송요청이 없는 경우
 *    "not_update_time"    - 예약시간이 1분이내여서 취소가 불가능한 경우
 *    "ing_master"         - 이미 전송중인 경우
 *    "server_error"       - 기타 서버 오류
 */

%>