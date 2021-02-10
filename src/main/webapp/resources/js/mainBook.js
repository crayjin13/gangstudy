
const bookingForm = document.getElementById("bookingForm")
const startDateInput = document.getElementById("start-date-input")
const startTimeInput = document.getElementById("start-time-input")
const endDateInput = document.getElementById("end-date-input")
const endTimeInput = document.getElementById("end-time-input")
const people = document.getElementById("people-input")
const bookingButton = document.getElementById("bookingButton")

//
//

var msg = getParam("msg")
var book_no = getParam("book_no")

var startURL = "/booking/startTime"
var endURL = "/booking/endTime"

function getStartData() {
	return {
		"startDate" : startDateInput.value,
		"book_no" : book_no
	}
}

function getEndData() {
	return {
		"startDate" : startDateInput.value,
		"startTime" : startTimeInput.value,
		"endDate" : endDateInput.value,
		"book_no" : book_no
	}
}

var startPicker = getPikaday(startDateInput)
var endPicker = getPikaday(endDateInput)




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
	validationCheck(startDateInput.value, startTimeInput.value, endDateInput.value, endTimeInput.value, people.value, bookingForm)
})

$(document).ready(function() {
	if(msg != "") { alert(msg); }
	
	setDateRange(startPicker, new Date(), 7)
	setDateRange(endPicker, new Date(), 7)
	
	if(startDateInput.value!="") {
		setDateRange(endPicker, new Date(startDateInput.value), 1)
		
		requestTimes(startURL, getStartData(), startTimeInput, function() {
			if(startTimeInput.value!="") {
				requestTimes(endURL, getEndData(), endTimeInput)
			}
		})
		
		people.value = people.getAttribute("people")
	} else {
		startDateInput.value = "이용날짜"
		endDateInput.value = "시작일을 선택해주세요."
	}
})



startDateInput.addEventListener("change", function() {
	removeOptions(startTimeInput)
	removeOptions(endTimeInput)
	
	// end date select option
	var date = new Date(startDateInput.value)
	endDateInput.value = getFormatDate(date)
	setDateRange(endPicker, date, 1)
	
	// start time select option
	requestTimes(startURL, getStartData(), startTimeInput)
})

endDateInput.addEventListener("change", function() {
	if(startDateInput.value=="") {
		var date = new Date(endDateInput.value)
		startDateInput.value = getFormatDate(date)
		requestTimes(startURL, getStartData(), startTimeInput)
	} else {
		requestTimes(endURL, getEndData(), endTimeInput)
	}
});

startTimeInput.addEventListener("change", function() {
	if(startTimeInput.value == "") {
		removeOptions(endTimeInput)
		return
	} else {
		var time = getTimeObject(startTimeInput.value)
		if(time.hour >= 22) {
			var date = new Date(startDateInput.value)
			date.setDate(date.getDate()+1)
			endDateInput.value = getFormatDate(date)
		}
		requestTimes(endURL, getEndData(), endTimeInput)
	}
});