var timeline = document.getElementById("timeline")
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
webSocket.send("request today info")