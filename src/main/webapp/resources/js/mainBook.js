
const bookingForm = document.getElementById("bookingForm")
const inputs = document.getElementsByClassName("form-control")

const dateInput = document.getElementById("dateInput")
const startTimeInput = document.getElementById("start-time-input")
const endTimeInput = document.getElementById("end-time-input")
const peopleInput = document.getElementById("people-input")
const bookingButton = document.getElementById("bookingButton")

const datePicker = getPikaday(dateInput)

//
//

var msg = getParam("msg")
var book_no = getParam("book_no")

var startURL = "/booking/startTime"
var endURL = "/booking/endTime"

$(document).ready(function() {
	// init calendar
	setDateRange(datePicker, new Date(), 7)
	
	// error message
	if(msg != "") { alert(msg); }
})
window.onpageshow = function() {
	// info recovery
	if(dateInput.value!="") {
		requestTimes(startURL, getStartData(), startTimeInput, function() {
			if(startTimeInput.value!="") {
				requestTimes(endURL, getEndData(), endTimeInput)
			}
		})
		
		peopleInput.value = peopleInput.getAttribute("people")
	} else {
		dateInput.value = "이용날짜"
	}
}

function getStartData() {
	return {
		"date" : dateInput.value,
		"book_no" : book_no
	}
}

function getEndData() {
	return {
		"date" : dateInput.value,
		"startTime" : startTimeInput.value,
		"book_no" : book_no
	}
}

// submit
bookingButton.addEventListener("click", function() {
	$.ajax({
		url : "/booking/remove_uncharge",
		type : "GET",
		success : function() {
			console.log("ajax(remove_uncharge) success")
		},
		error : function(){
			console.log("ajax(remove_uncharge) error")
		}
	})
	validationCheck(dateInput.value, startTimeInput.value, endTimeInput.value, peopleInput.value, bookingForm)
})




// input change
dateInput.addEventListener("change", function() {
	removeOptions(startTimeInput)
	removeOptions(endTimeInput)
	
	// start time select option
	requestTimes(startURL, getStartData(), startTimeInput)
})

startTimeInput.addEventListener("change", function() {
	if(startTimeInput.value == "") {
		removeOptions(endTimeInput)
		return
	} else {
		requestTimes(endURL, getEndData(), endTimeInput)
	}
});



// input focus
var onFocued = function(i) {
	var icon = document.getElementsByClassName("icon")
	var o = icon[i].children[0].contentDocument
	var s = o.getElementsByTagName("svg")[0]

	var items = s.getElementsByClassName("paint")
	for(var i = 0; i < items.length; i++) {
		var item = items[i]
		if(item.classList.contains("normal")) {
			item.classList.remove("normal")
		}
		if(item.classList.contains("active")) {
			item.classList.remove("active")
		}
		item.classList.add("clicked")
	}
	
}
dateInput.addEventListener("focus", function() {
	onFocued(0)
	dateInput.classList.add("active")
})
startTimeInput.addEventListener("focus",  function() {
	onFocued(1)
	startTimeInput.classList.add("active")
})
endTimeInput.addEventListener("focus",  function() {
	onFocued(2)
	endTimeInput.classList.add("active")
})
peopleInput.addEventListener("focus",  function() {
	onFocued(3)
	peopleInput.classList.add("active")
})


// input blur
var onBlur = function(i) {
	var icon = document.getElementsByClassName("icon")
	var o = icon[i].children[0].contentDocument
	var s = o.getElementsByTagName("svg")[0]
	
	var items = s.getElementsByClassName("paint")
	for(var i = 0; i < items.length; i++) {
		var item = items[i]
		if(item.classList.contains("normal")) {
			item.classList.remove("normal")
		}
		if(item.classList.contains("clicked")) {
			item.classList.remove("clicked")
		}
		item.classList.add("active")
	}
}
dateInput.addEventListener("blur", function() {
	onBlur(0)
})
startTimeInput.addEventListener("blur",  function() {
	onBlur(1)
})
endTimeInput.addEventListener("blur", function() {
	onBlur(2)
})
peopleInput.addEventListener("blur", function() {
	onBlur(3)
})