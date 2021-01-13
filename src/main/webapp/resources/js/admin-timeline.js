var wsUri = "ws://gangstudy.com/websocket";
var webSocket;
var clientWebSocket = {
	openSocket : function() {
        webSocket = new WebSocket(wsUri);
        webSocket.onopen = function(evt) {
        	// Socket Open
			webSocket.send("request today info")
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

var timeline = document.getElementById("timeline")
$(document).ready(function() {
	clientWebSocket.openSocket();
});
function writeToScreen(message) {
	// 서버로 부터 수신된 메시지를 출력한다.
	var jm = JSON.parse(message)
    var item = document.createElement("div")
	item.className = "timeline-item align-items-start"
	
		var time = document.createElement("div")
		time.className = "timeline-label font-weight-bolder text-dark-75 font-size-lg"
		time.innerHTML = jm.time
		
		var badge = document.createElement("div")
		badge.className = "timeline-badge";
		
			var type = document.createElement("i")
			type.className = "fa fa-genderless text-success icon-xl"
			//text-warning text-success text-danger text-primary
		
		var content = document.createElement("div")
		content.className = "timeline-content d-flex"
		
			var contentText = document.createElement("span")
			contentText.className = "font-weight-bolder text-dark-75 pl-3 font-size-lg"
			contentText.innerHTML = jm.message
			
	content.appendChild(contentText)
	badge.appendChild(type)
	item.appendChild(time)
	item.appendChild(badge)
	item.appendChild(content)
	timeline.appendChild(item)
	
	timeline.scrollTop = timeline.scrollHeight;
}