/**
 * 
 */
const totalAmount = document.getElementById("totalAmount");
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
	totalAmount.textContent = (chargePerPeople.textContent * people.value - pointUse.value)+ "원";
});
pointMaxUseBtn.addEventListener("touchend", function() {
	pointUse.value = pointMax.textContent;
	totalAmount.textContent = (chargePerPeople.textContent * people.value - pointUse.value)+ "원";
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

function isInt(value) {
	var regex = /^-?[0-9]+$/;
	return regex.test(value);
}