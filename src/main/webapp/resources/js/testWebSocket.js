var testlog = document.getElementById('testlog');
var twsUri = "ws://gangstudy.com/testsocket";
var testWebSocket;
var testClientWebSocket = {
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
			testlog.textContent = testlog.textContent + "websocket.onerror!"
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
			testWrite(data);
		}
	}
}

function testWrite(message) {
	var jm = JSON.parse(message)
	var symbolMessage = jm.message.toString().replace("&#60;", "<").replace("&#62;", ">")
	var logText = '[' + jm.time + ']' + symbolMessage
	testlog.textContent = testlog.textContent + logText
}

document.getElementById('testSend').addEventListener('click', function() {
	$.ajax({
		url : '/remote/send',
		type : 'GET',
		data : {
				 'ipAdress' 	: document.getElementById('testIP').value,
				 'portNumber' 	: document.getElementById('testPort').value,
				 'message' 		: document.getElementById('testMessage').value
			   },
		success : function(response) {
			if(response == "fail") alert("전송 실패")
			else alert("전송한 메세지 : " + response)
		},
		error : function(){
			alert('testSend >> ajax error');
		}
	})
})