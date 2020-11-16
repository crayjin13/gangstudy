
const bookingButton = document.getElementById("bookingButton");
const startDateInput = document.getElementById("start-date-input");
const startTimeInput = document.getElementById("start-time-input");
const endDateInput = document.getElementById("end-date-input");
const endTimeInput = document.getElementById("end-time-input");
const userCountInput = document.getElementById("user-count-input");
const bookingForm = document.getElementById("bookingForm");
	
$(document).ready(function() {
	setMinMaxDate(startDateInput, new Date(), 0, 7);
	setMinMaxDate(endDateInput, new Date(), 0, 7);
});

bookingButton.addEventListener("click", function() {
	// validation check
	if(startDateInput.value == "") {
		alert("시작일을 선택해주세요.");
		return;
	}
	if(startTimeInput.value == "") {
		alert("시작시간을 선택해주세요.");
		return;
	}
	if(endDateInput.value == "") {
		alert("종료일을 선택해주세요.");
		return;
	}
	if(endTimeInput.value == "") {
		alert("종료시간을 선택해주세요.");
		return;
	}
	if(userCountInput.value == "") {
		alert("사용인원을 선택해주세요.");
		return;
	}
	
	// send form
	bookingForm.submit();
});


startDateInput.addEventListener("change", function() {
	removeOptions(startTimeInput);
	removeOptions(endTimeInput);
	
	// end date select option
	var date = new Date(startDateInput.value);
	endDateInput.value = getFormatDate(date);
	setMinMaxDate(endDateInput, date, 0, 1);
	
	// start time select option
	requestStartTime();
});

endDateInput.addEventListener("change", function() {
	if(startDateInput.value=="") {
		var date = new Date(endDateInput.value);
		startDateInput.value = getFormatDate(date);
		removeOptions(startTimeInput);
		requestStartTime();
	} else {
		requestEndTime();
	}
});

startTimeInput.addEventListener("change", function() {
	if(startTimeInput.value == "") {
		removeOptions(endTimeInput);
		return;
	} else {
		requestEndTime();
	}
});

function requestStartTime() {
	$.ajax({
		url :  getContextPath()+"/booking/reqStartTime",
		type : "GET",
		data : {
				 "startDate" : startDateInput.value
			   },
		success : function(times) {
			var option = document.createElement("option");
			option.text = "시간을 선택해주세요.";
			option.value = "";
			startTimeInput.options.add(option);
			for(var i = 0; i < times.length; i++) {
				var option = document.createElement("option");
				option.text = times[i];
				option.value = times[i];
				startTimeInput.options.add(option);
			}
		},
		error : function(){
			alert("getStartTimeOptions error");
		}
	});
}

function requestEndTime() {
	removeOptions(endTimeInput);
	$.ajax({
		url : getContextPath()+"/booking/reqEndTime",
		type : "GET",
		data : {
				 "startDate" : startDateInput.value,
				 "startTime" : startTimeInput.value,
				 "endDate" : endDateInput.value,
			   },
		success : function(times) {
			var option = document.createElement("option");
			option.text = "시간을 선택해주세요.";
			option.value = "";
			endTimeInput.options.add(option);
			for(var i = 0; i < times.length; i++) {
				var option = document.createElement("option");
				option.text = times[i];
				option.value = times[i];
				endTimeInput.options.add(option);
			}
		},
		error : function(){
			alert("getEndTimeOptions error");
		}
	});
};

//////////////////////////////////////
//			util functions			//
//////////////////////////////////////

function removeOptions(selectElement) {
   var i, L = selectElement.options.length - 1;
   for(i = L; i >= 0; i--) {
      selectElement.remove(i);
   }
}

function getFormatDate(date){
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : "0" + month;
	var day = date.getDate();
	day = day >= 10 ? day : "0" + day;
	return year + "-" + month + "-" + day;
}

function setMinMaxDate(input, date, min, max){
    date.setDate(date.getDate() + min);
	input.min = getFormatDate(date);
    date.setDate(date.getDate() - min + max);
	input.max = getFormatDate(date);
}
