/**
 * 
 */
const totalAmount = document.getElementById("totalAmount");
const pointMaxUseBtn = document.getElementById("pointMaxUseBtn");
const pointMax = document.getElementById("pointMax");
const pointUse = document.getElementById("kt_touchspin");

document.getElementById("payments").addEventListener("click", function() {
	$.ajax({
		url : "/booking/change",
		type : "POST",
		data :	{
			        point : pointUse.value
				},
		success : function(url) {
			window.location.href = url;
		},
		error : function() {
			alert("modify error");
		}
	});
});

pointMaxUseBtn.addEventListener("click", function() {
	if(pointUse.value > totalAmount.textContent) {
		pointUse.value = totalAmount.textContent;
	}
	pointUse.value = pointMax.textContent;
	totalAmount.textContent = (chargePerPeople.textContent * people.value - pointUse.value)+ "원";
});
pointMaxUseBtn.addEventListener("touchend", function() {
	if(pointUse.value > totalAmount.textContent) {
		pointUse.value = totalAmount.textContent;
	}
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
	if(pointUse.value > totalAmount.textContent) {
		pointUse.value = totalAmount.textContent;
	}
});

function isInt(value) {
	var regex = /^-?[0-9]+$/;
	return regex.test(value);
}