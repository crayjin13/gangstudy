/**
 * 
 */
const totalAmount = document.getElementById("totalAmount");
const people = document.getElementById("kt_touchspin_1");
const chargePerPeople = document.getElementById("chargePerPeople");
const pointMaxUseBtn = document.getElementById("pointMaxUseBtn");
const pointMax = document.getElementById("pointMax");
const pointUse = document.getElementById("kt_touchspin");

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
	pointUse.value = pointMax.textContent;
});

pointUse.addEventListener("change", function() {
	if(pointUse.value > pointMax.textContent) {
		pointUse.value = pointMax.textContent;
	}
	if(pointUse.value < 0) {
		pointUse.value = 0;
	}
	if(!isInt(pointUse.value)) {
		pointUse.value = 0;
	}
});


window.addEventListener('load', function(){
	document.getElementsByClassName("bootstrap-touchspin-up")[0].addEventListener("click", function() {
		totalAmount.textContent = chargePerPeople.textContent * people.value;
	});
	document.getElementsByClassName("bootstrap-touchspin-down")[0].addEventListener("click", function() {
		totalAmount.textContent = chargePerPeople.textContent * people.value;
	});
});

function isInt(value) {
	var regex = /^-?[0-9]+$/;
	return regex.test(value);
}