
const bookingForm = document.getElementById("bookingForm")
const bookingButton = document.getElementById("bookingButton")

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
	if(dInput.value == "") {
		alert("시작일을 선택해주세요.")
	} else if(sInput.value == "") {
		alert("시작시간을 선택해주세요.")
	} else if(eInput.value == "") {
		alert("종료시간을 선택해주세요.")
	} else if(pInput.value == "") {
		alert("사용인원을 선택해주세요.")
	} else {
		bookingForm.submit()
	}
})