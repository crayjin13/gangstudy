var outlog = document.getElementById('outlog');
var inlog = document.getElementById('inlog');
var messageSelect = document.getElementById('messageSelect');
var signbtns = document.getElementsByClassName('signbtn');

$(document).ready(function() {
	getReserveList();
	for (var i = 0; i < signbtns.length; i++) {
	     signbtns[i].addEventListener('click', sendMessage);
	}
});

function sendMessage(){
	var message = '<C01' + this.getAttribute('data-value') + '>';
	outlog.textContent = outlog.textContent + message;
	$.ajax({
		url : '/remote/sign',
		type : 'GET',
		data : {
				 'message' : message
			   },
		success : function(response) {
			inlog.textContent = inlog.textContent + response;
		},
		error : function(){
			alert('script error(ajax sign btn)');
		}
	});
}
function removeReserve(){
	var command_no = this.getAttribute('command_no');
	$.ajax({
		url : '/remote/removeCommand',
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
		url : '/remote/insertCommand',
		type : 'GET',
		data : {
				 'ipAdress' 	: document.getElementById('ipInput').value,
				 'portNumber' 	: document.getElementById('portInput').value,
				 'message' 		: document.getElementById('messageInput').value,
				 'reserveTime' 	: document.getElementById('kt_timepicker_1_validate').value,
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
		url : '/remote/reqCommandList',
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
function appendChildTo(target, data) {
	var li = document.createElement('li');
  	li.appendChild(document.createTextNode(data.ipAdress+', '+data.portNumber+', '+data.message+', '+data.reserveTime));

	var button = document.createElement('button');
	button.innerHTML = '제거';
	button.setAttribute('command_no', data.command_no);
	button.setAttribute('class', 'removeBtn');
	li.appendChild(button);
	
	target.appendChild(li);
	
}