<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script src="https://code.jquery.com/jquery-1.9.0.js"></script>
</head>
<script type="text/javascript">
    $(document).ready(function() {
    	// Socket 열기
		myWebSocket.openSocket();

    	// Send 버튼 Click Event
		$("#sendBtn").click(function() {
			sendMessage();
	        $('#message').val('');
        });
    	
    	// 메시지 입력시
        $("#message").keydown(function(key) {
	        if (key.keyCode == 13) {// 엔터
	            sendMessage();
	            $('#message').val('');
	        }
        });
 	});	
	
    var wsUri = "ws://localhost:8080/socket1";
	var webSocket;
	var myWebSocket = {
		openSocket : function() {
	        webSocket = new WebSocket(wsUri);
	        webSocket.onopen = function(evt) {
	        	// Socket Open
    			writeToScreen("WebSocket Connection opened!");
	        };
	        webSocket.onmessage = function(evt) {
	        	// 서버로 부터 메시지 수신
		    	myWebSocket.handleMessage(evt.data);
	        };

	        webSocket.onerror = function(evt) {
	        	// Socket Error 발생
				alert(evt)
	        };
	        
	        webSocket.onclose = function(event) {
	        	// Socket 닫힘
	        	writeToScreen("Server Connection closed");
		   };        
		},
		doSend : function(message) {
			// 서버로 메시지 전송
			webSocket.send(message);
		},
		handleMessage : function (data) {
			// 메시지 처리
			if(data != null){
				$("#serverMessage").val(data);
				writeToScreen(data);
			}
		}
	}		
	
    function writeToScreen(message) {
		// 서버로 부터 수신된 메시지를 출력한다.
        var pre =document.createElement("p");
        pre.style.wordWrap = "break-word";
        pre.innerHTML = message;
        output.appendChild(pre);
        output.prepend(pre);
    }

	function sendMessage(){
		if($('#message').val() == '' || $('#message').val() == null)
			return;
		myWebSocket.doSend($("#message").val());
	}

</script>
<body>
    <h1 style="text-align: center;">WebSocket Client Test</h1><br>
    <div style="text-align: center;">
        <form action="">
            <input id="sendBtn" value="Send" type="button">&nbsp;
            <input id="message" name="message" value="Have a good day" type="text"><br>
            Server Message : <input id="serverMessage" name="serverMessage" type="text"><br>
        </form>
    </div>
    <div id="output"></div>
</body>

</html>

