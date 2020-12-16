
const bookingForm = document.getElementById("bookingForm")
const startDateInput = document.getElementById("start-date-input")
const startTimeInput = document.getElementById("start-time-input")
const endDateInput = document.getElementById("end-date-input")
const endTimeInput = document.getElementById("end-time-input")
const people = document.getElementsByName('people')[0]
const modifyButton = document.getElementById("modify")

const startTimeValue = document.getElementById("startTimeValue")
const endTimeValue = document.getElementById("endTimeValue")

var url = new URL(window.location.href)
var book_no = url.searchParams.get("book_no")

var startURL = "/booking/startTime";
var endURL = "/booking/endTime";

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




modifyButton.addEventListener("click", function() {
	validationCheck(startDateInput.value, startTimeInput.value, endDateInput.value, endTimeInput.value, people.value, bookingForm)
})
	
$(document).ready(function() {
	setDateRange(startPicker, new Date(), 7);
	setDateRange(endPicker, new Date(), 7);
	
	requestTimes(startURL, getStartData(), startTimeInput, function() {
		requestTimes(endURL, getEndData(), endTimeInput)
	})
	people.value = people.getAttribute("people");
});



startDateInput.addEventListener("change", function() {
	removeOptions(startTimeInput);
	removeOptions(endTimeInput);
	
	// end date select option
	var date = new Date(startDateInput.value);
	endDateInput.value = getFormatDate(date);
	setDateRange(endPicker, date, 1);
	
	// start time select option
	requestTimes(startURL, getStartData(), startTimeInput);
});

endDateInput.addEventListener("change", function() {
	if(startDateInput.value=="") {
		var date = new Date(endDateInput.value);
		startDateInput.value = getFormatDate(date);
		requestTimes(startURL, getStartData(), startTimeInput)
	} else if(startTimeInput.value=="") {
		
	} else {
		requestTimes(endURL, getEndData(), endTimeInput)
	}
});

startTimeInput.addEventListener("change", function() {
	if(startTimeInput.value == "") {
		removeOptions(endTimeInput)
		return
	} else {
		requestTimes(endURL, getEndData(), endTimeInput)
	}
});