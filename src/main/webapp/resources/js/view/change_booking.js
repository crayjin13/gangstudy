/**
 * 
 */
const cancelAmount = document.getElementById("cancelAmount")
const addedAmount = document.getElementById("addedAmount")
const repayAmount = document.getElementById("repayAmount")
//const amount = parseInt(addedAmount.textContent)
const dateInput = document.getElementById("dateInput")
const startTimeInput = document.getElementById("start-time-input")
const endTimeInput = document.getElementById("end-time-input")

const datePicker = getPikaday(dateInput)
var startURL = "/booking/startTime"
var endURL = "/booking/endTime"

document.getElementById("payments").addEventListener("click", function() {
	requestChange()
})

$(document).ready(function() {
	// init calendar
	setDateRange(datePicker, new Date(), 7)
	
	// error message
	if(msg != "") { alert(msg) }
})
window.onpageshow = function() {
	// info recovery
	if(dateInput.value!="") {
		requestTimes(startURL, getStartData(), startTimeInput, function() {
			startTimeInput.options[0] = new Option("시작시간을 선택해주세요.", "")
			if(startTimeInput.value!="") {
				requestTimes(endURL, getEndData(), endTimeInput)
				endTimeInput.options[0] = new Option("종료시간을 선택해주세요.", "")
			}
		})
		
		peopleInput.value = peopleInput.getAttribute("people")
	} else {
		dateInput.value = "이용날짜"
	}
}

// input change
dateInput.addEventListener("change", function() {
	removeOptions(startTimeInput)
	removeOptions(endTimeInput)
	
	// start time select option
	requestTimes(startURL, getStartData(), startTimeInput)
	startTimeInput.options[0] = new Option("시작시간을 선택해주세요.", "")
})

startTimeInput.addEventListener("change", function() {
	if(startTimeInput.value == "") {
		removeOptions(endTimeInput)
		return
	} else {
		requestTimes(endURL, getEndData(), endTimeInput)
		endTimeInput.options[0] = new Option("종료시간을 선택해주세요.", "")
	}
});


/*
const pointMaxUseBtn = document.getElementById("pointMaxUseBtn");
const pointMax = document.getElementById("pointMax");
const pointUse = document.getElementById("kt_touchspin");

pointMaxUseBtn.addEventListener("click", function() {
	pointMaxUseBtnEvent();
});
pointMaxUseBtn.addEventListener("touchend", function() {
	pointMaxUseBtnEvent();
});

pointUse.addEventListener("change", function() {
	if(parseInt(pointUse.value) > amount) {									// 사용포인트 > 금액
		pointUse.value = amount;
	}
	
	if(amount < 0) {														// 금액 < 0
		pointUse.value = 0;
	} else if(!isInt(pointUse.value)) {										// 사용포인트 != int
		pointUse.value = 0;
	} else if(parseInt(pointUse.value) < 0) {								// 사용포인트 < 0
		pointUse.value = 0;
	} else if(parseInt(pointUse.value) > parseInt(pointMax.textContent)) {	// 사용포인트 > 최대 포인트
		pointUse.value = pointMax.textContent;
	}
	
	addedAmount.textContent = amount - pointUse.value;
	repayAmount.textContent = parseInt(cancelAmount.textContent) + parseInt(addedAmount.textContent);
});
function pointMaxUseBtnEvent() {
	if(amount < 0) {
		pointUse.value = 0;
	} else if(parseInt(pointMax.textContent) > amount) {
		pointUse.value = amount;
	} else {
		pointUse.value = pointMax.textContent;
	}
	addedAmount.textContent = amount - pointUse.value;
	repayAmount.textContent = parseInt(cancelAmount.textContent) + parseInt(addedAmount.textContent);
}
*/

function requestChange() {
	$.ajax({
		url : "/booking/change",
		type : "POST",
		data :	{
			        point : pointUse.value
				},
		success : function(url) {
			if(url == '/booking/check') {
				alert('추가된 금액이 없어 결제없이 변경됩니다.')
			} else if(url == '/payment/cancelAndBooking') {
				alert('추가 결제하기')
			} else if(url == '/payment/cancelAndChange') {
				alert(cancelAmount.textContent + '원 취소 후 ' + repayAmount.textContent + '원 결제하기')
			}
			window.location.href = url;
		},
		error:function(request,status,error){
//	    	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			alert("modify error")
		}
	});
}