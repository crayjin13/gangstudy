
const bookingButton = document.getElementById("bookingButton");
const startDateInput = document.getElementById("start-date-input");
const startTimeInput = document.getElementById("start-time-input");
const endDateInput = document.getElementById("end-date-input");
const endTimeInput = document.getElementById("end-time-input");
const userCountInput = document.getElementById("user-count-input");
const bookingForm = document.getElementById("bookingForm");
var startPicker = new Pikaday({
    field: startDateInput,
    format: 'YYYY-MM-DD',
    toString(date) {
        return getFormatDate(date);
    }
});
var endPicker = new Pikaday({
    field: endDateInput,
    format: 'YYYY-MM-DD',
    toString(date) {
        return getFormatDate(date);
    }
});

$(document).ready(function() {
	setDateRange(startPicker, new Date(), 7);
	setDateRange(endPicker, new Date(), 7);
	
	// 로그인 전에 예약정보를 입력했다면 불러온다
	if(startDateInput.value!="") {
		setDateRange(endPicker, new Date(startDateInput.value), 1);
		
		requestStartTime(function() {
			startTimeInput.value = startTimeInput.getAttribute("time");
			requestEndTime(function() {
				endTimeInput.value = endTimeInput.getAttribute("time");
			});
		});
		userCountInput.value = userCountInput.getAttribute("people");
	} else {
		startDateInput.value = "시작일을 선택해주세요.";
		endDateInput.value = "시작일을 선택해주세요.";
	}
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
	setDateRange(endPicker, date, 1);
	
	// start time select option
	requestStartTime();
});

endDateInput.addEventListener("change", function() {
	if(startDateInput.value=="" || startTimeInput.value=="") {
		var date = new Date(endDateInput.value);
		startDateInput.value = getFormatDate(date);
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

function requestStartTime(callback) {
	removeOptions(startTimeInput);
	$.ajax({
		url :  getContextPath()+"/booking/reqStartTime",
		type : "GET",
		data : {
				 "startDate" : startDateInput.value
			   },
		success : function(times) {
			if(times.length == 0) {
				var option = document.createElement("option");
				option.text = "해당일에 예약할 수 있는 시간이 없습니다.";
				option.value = "";
				startTimeInput.options.add(option);
				return ;
			}
			addOption(startTimeInput, "시간을 선택해주세요.", "");
			for(var i = 0; i < times.length; i++) {
				addOption(startTimeInput, times[i], times[i]);
			}
			callback();
		},
		error : function(){
			alert("getStartTimeOptions error");
		}
	});
}

function requestEndTime(callback) {
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
			if(times.length == 0) {
				var option = document.createElement("option");
				option.text = "해당일에 예약할 수 있는 시간이 없습니다.";
				option.value = "";
				endTimeInput.options.add(option);
				return ;
			}
			addOption(endTimeInput, "시간을 선택해주세요.", "");
			for(var i = 0; i < times.length; i++) {
				addOption(endTimeInput, times[i], times[i]);
			}
			callback();
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

function setDateRange(target, minDate, offset) {
	var maxDate = new Date();
	maxDate.setDate(minDate.getDate()+offset);
	target.setMinDate(minDate);
	target.setMaxDate(maxDate);
}

function addOption(target, text, value) {
	var option = document.createElement("option");
	option.text = text;
	option.value = value;
	option.name = value;
	target.options.add(option);
}