var wsUri = "ws://gangstudy.com/websocket";
var webSocket;
var clientWebSocket = {
	openSocket : function() {
        webSocket = new WebSocket(wsUri);
        webSocket.onopen = function(evt) {
        	// Socket Open
        };
        webSocket.onmessage = function(evt) {
        	// 서버로 부터 메시지 수신
	    	clientWebSocket.handleMessage(evt.data);
        };

        webSocket.onerror = function(evt) {
        	// Socket Error 발생
			alert(evt)
        };
        
        webSocket.onclose = function(event) {
        	// Socket 닫힘
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
clientWebSocket.openSocket();