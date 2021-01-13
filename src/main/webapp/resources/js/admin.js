var outlog = document.getElementById('outlog');
var inlog = document.getElementById('inlog');
var messageSelect = document.getElementById('messageSelect');
var signbtns = document.getElementsByClassName('signbtn');

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
function writeToScreen(message) {
	var jm = JSON.parse(message)
	var symbolMessage = jm.message.toString().replace("&#60;", "<").replace("&#62;", ">")
	var logText = '[' + jm.time + ']' + symbolMessage + '(' + jm.type + ')'
	inlog.textContent = inlog.textContent + logText;
}

$(document).ready(function() {
	clientWebSocket.openSocket();
	getReserveList();
	for (var i = 0; i < signbtns.length; i++) {
	     signbtns[i].addEventListener('click', sendMessage);
	}
});

function sendMessage(){
	var message = '<C01' + this.getAttribute('data-value') + '>';
	outlog.textContent = outlog.textContent + message;
	$.ajax({
		url : '/admin/remote',
		type : 'GET',
		data : {
				 'message' : message
			   },
		success : function(response) {
		},
		error : function(){
			alert('script error(ajax sign btn)');
		}
	});
}
function removeReserve(){
	var command_no = this.getAttribute('command_no');
	$.ajax({
		url : '/admin/removeCommand',
		type : 'GET',
		data : {
				 'command_no' : command_no
			   },
		success : function() {
			getReserveList();
		},
		error : function(){
			alert('script error(ajax remove btn)');
		}
	});
}

document.getElementById('saveCommand').addEventListener('click', function() {
	$.ajax({
		url : '/admin/addCommand',
		type : 'GET',
		data : {
				 'message' 	: document.getElementById('messageInput').value,
				 'time' 	: document.getElementById('kt_timepicker_1_validate').value
			   },
		success : function() {
			getReserveList();
		},
		error : function(){
			alert('script error(ajax reserve btn)');
		}
	});
});

messageSelect.addEventListener('change', function() {
	document.getElementById('messageInput').value = messageSelect.value;
});

var command_list = document.getElementById('command_list');

function getReserveList() {
	while ( command_list.hasChildNodes() ) { command_list.removeChild( command_list.firstChild ); }
	$.ajax({
		url : '/admin/getCommands',
		type : 'GET',
		data : {
					//nothing
			   },
		success : function(data) {
			for(var i = 0; i < data.length; i++) {
				appendChildTo(command_list, data[i]);
			}
			var removeBtns = document.getElementsByClassName('removeBtn');
			for (var i = 0; i < removeBtns.length; i++) {
			     removeBtns[i].addEventListener('click', removeReserve);
			}
		},
		error : function(){
			alert('script error(ajax reserve btn)');
		}
	});
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

String.prototype.string = function (len) { var s = '', i = 0; while (i++ < len) { s += this; } return s; };
String.prototype.zf = function (len) { return "0".string(len - this.length) + this; };
Number.prototype.zf = function (len) { return this.toString().zf(len); };
function appendChildTo(target, data) {
	var li = document.createElement('li');
	var time = data.reserveTime;
	var date = new Date(0, 0, 0, time.hour, time.minute, time.second, 0);
  	li.appendChild(document.createTextNode(data.message+', '+
											date.getHours().zf(2)+':'+date.getMinutes().zf(2)+':'+date.getSeconds().zf(2)));

	var button = document.createElement('button');
	button.innerHTML = '제거';
	button.setAttribute('command_no', data.command_no);
	button.setAttribute('class', 'removeBtn');
	li.appendChild(button);
	
	target.appendChild(li);
}
