
const totalAmount = document.getElementById("totalAmount");
const people = document.getElementById("touchspin");
const chargePerPeople = document.getElementById("chargePerPeople");
const pointMaxUseBtn = document.getElementById("pointMaxUseBtn");
const pointMax = document.getElementById("pointMax");
const pointUse = document.getElementById("kt_touchspin");

//
//
//

var msg = getParam("msg")

$(document).ready(function() {
	if(msg != "") {
		alert(msg);
	}
})
document.getElementById("payments").addEventListener("click", function() {
	$.post("/booking/make", {
        people : people.value,
        point : pointUse.value
	},function(jqXHR) {
		// always
	},'text' /* xml, text, script, html */)
	.done(function(data) {
		window.location.href = data;
	})
	.fail(function(jqXHR) {
	})
	.always(function(jqXHR) {
	});
});

pointMaxUseBtn.addEventListener("click", function() {
	if(parseInt(pointMax.textContent) > parseInt(totalAmount.textContent)) {
		pointUse.value = totalAmount.textContent;
	} else {
		pointUse.value = pointMax.textContent;
	}
	totalAmount.textContent = (chargePerPeople.textContent * people.value - pointUse.value);
});
pointMaxUseBtn.addEventListener("touchend", function() {
	if(parseInt(pointMax.textContent) > parseInt(totalAmount.textContent)) {
		pointUse.value = totalAmount.textContent;
	} else {
		pointUse.value = pointMax.textContent;
	}
	totalAmount.textContent = (chargePerPeople.textContent * people.value - pointUse.value);
});


pointUse.addEventListener("change", function() {
	if(parseInt(pointUse.value) > parseInt(pointMax.textContent)) {
		pointUse.value = pointMax.textContent;
	}
	if(parseInt(pointUse.value) < 0) {
		pointUse.value = 0;
	}
	if(!isInt(pointUse.value)) {
		pointUse.value = 0;
	}
	totalAmount.textContent = (chargePerPeople.textContent * people.value - pointUse.value);
});

people.addEventListener("change", function() {
	if(people.value > 6){
		people.value = 6;
	}
	if(people.value < 1) {
		people.value = 1;
	}
	
	totalAmount.textContent = chargePerPeople.textContent * people.value;
});

window.addEventListener('load', function(){
	$('#touchspin').TouchSpin({
            buttondown_class: 'btn btn-secondary',
            buttonup_class: 'btn btn-secondary',

            min: 1,
            max: 6,
            step: 1,  
           // decimals: 2, 소수점 몇자리
            boostat: 5,
            maxboostedstep: 10000000,
            
        });

	document.getElementsByClassName("bootstrap-touchspin-up")[0].addEventListener("click", function() {
		totalAmount.textContent = (chargePerPeople.textContent * people.value - pointUse.value);
	});
	
	document.getElementsByClassName("bootstrap-touchspin-down")[0].addEventListener("click", function() {
		totalAmount.textContent = (chargePerPeople.textContent * people.value - pointUse.value);
	});
	document.getElementsByClassName("bootstrap-touchspin-up")[0].addEventListener("touchend", function() {
		totalAmount.textContent = (chargePerPeople.textContent * people.value - pointUse.value);
	});
	document.getElementsByClassName("bootstrap-touchspin-down")[0].addEventListener("touchend", function() {
		totalAmount.textContent = (chargePerPeople.textContent * people.value - pointUse.value);
	});
});

function isInt(value) {
	var regex = /^-?[0-9]+$/;
	return regex.test(value);
}