
const people = document.getElementById("touchspin");
const peopleDisplay = document.getElementById("peopleDisplay");
const totalAmount = document.getElementById("totalAmount");
const singlePrice = document.getElementById("singlePrice");

//
//
//

var msg = getParam("msg")

$(document).ready(function() {
	if(msg != "") {
		msg = decodeURI(msg).replace('+', ' ')
		alert(msg);
	}
})
document.getElementById("payments").addEventListener("click", function() {
	$.post("/booking/make", {
        people : people.value,
        point : "0",
		pg_name : "KakaoPay"
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

/*
const pointMaxUseBtn = document.getElementById("pointMaxUseBtn");
const pointMax = document.getElementById("pointMax");
const pointUse = document.getElementById("kt_touchspin");
pointMaxUseBtn.addEventListener("click", function() {
	if(parseInt(pointMax.textContent) > parseInt(totalAmount.textContent)) {
		pointUse.value = totalAmount.textContent;
	} else {
		pointUse.value = parseInt(pointMax.textContent);
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
*/

function displayUpdate() {
	totalAmount.value = singlePrice.textContent * people.value;
	peopleDisplay.textContent = people.value + "명";
}
people.addEventListener("change", function() {
	if(people.value > 6){
		people.value = 6;
	}
	if(people.value < 1) {
		people.value = 1;
	}
	displayUpdate();
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
		displayUpdate();
	});
	document.getElementsByClassName("bootstrap-touchspin-down")[0].addEventListener("click", function() {
		displayUpdate();
	});
	document.getElementsByClassName("bootstrap-touchspin-up")[0].addEventListener("touchend", function() {
		displayUpdate();
	});
	document.getElementsByClassName("bootstrap-touchspin-down")[0].addEventListener("touchend", function() {
		displayUpdate();
	});
	displayUpdate();
});


function isInt(value) {
	var regex = /^-?[0-9]+$/;
	return regex.test(value);
}