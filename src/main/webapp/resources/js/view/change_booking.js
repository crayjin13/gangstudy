
const bookingForm = document.getElementById("bookingForm")

const amountPaid = document.getElementById("amountPaid")
const amountToBeCharge = document.getElementById("amountToBeCharge")
const amountPerHour = document.getElementById("amountPerHour")
//const amount = parseInt(addedAmount.textContent)

const dInput = document.getElementById("dateInput")
const sInput = document.getElementById("start-time-input")
const eInput = document.getElementById("end-time-input")
const pInput = document.getElementById("people-input")
const sURL = "/booking/startTime"
const eURL = "/booking/endTime"

//
//

var msg = getParam("msg")
var book_no = getParam("book_no")

$(document).ready(function() {
	// error message
	if(msg != "") { alert(msg) }
})

var bf = new BookForm({
	book_no: book_no,
	dInput: dInput,
	sInput: sInput,
	eInput: eInput,
	pInput: pInput,
	sURL: sURL,
	eURL: eURL
})
window.onpageshow = function() { bf.onPagesShow() }

document.getElementById("payments").addEventListener("click", function() {
	if(amountToBeCharge.value == "") {
		alert('시간이 변경되지 않았습니다.')
		return
	}
	
	if(amountPaid.value == amountToBeCharge.value) {
		alert('추가된 금액이 없어 결제없이 변경됩니다.')
	} else {
		alert(amountPaid.value + '원 취소 후 ' + amountToBeCharge.value + '원 결제하기')
	}
	bookingForm.submit()
})

document.getElementById("bookingForm").addEventListener("change", function() {
	if(bf.validation()) {
		amountToBeCharge.value = bf.getInterval() * amountPerHour.textContent
	} else {
		amountToBeCharge.value = ""
	}
})






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