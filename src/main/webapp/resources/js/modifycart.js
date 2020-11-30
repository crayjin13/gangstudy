/**
 * 
 */
const cancelAmount = document.getElementById("cancelAmount");
const addedAmount = document.getElementById("addedAmount");
const repayAmount = document.getElementById("repayAmount");
const amount = parseInt(addedAmount.textContent);

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
	if(parseInt(pointMax.textContent) > amount) {
		pointUse.value = amount;
	} else {
		pointUse.value = pointMax.textContent;
	}
	addedAmount.textContent = amount - pointUse.value;
	repayAmount.textContent = parseInt(cancelAmount.textContent) + parseInt(addedAmount.textContent);
});
pointMaxUseBtn.addEventListener("touchend", function() {
	if(parseInt(pointMax.textContent) > amount) {
		pointUse.value = amount;
	} else {
		pointUse.value = pointMax.textContent;
	}
	addedAmount.textContent = amount - pointUse.value;
	repayAmount.textContent = parseInt(cancelAmount.textContent) + parseInt(addedAmount.textContent);
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
	if(parseInt(pointUse.value) > amount) {
		pointUse.value = amount;
	}
	addedAmount.textContent = amount - pointUse.value;
	repayAmount.textContent = parseInt(cancelAmount.textContent) + parseInt(addedAmount.textContent);
});

function isInt(value) {
	var regex = /^-?[0-9]+$/;
	return regex.test(value);
}