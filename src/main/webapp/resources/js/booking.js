function startDateSelect() {
	var startDate = $("select[name=startDate]");
	var startTime = $("select[name=startTime]");
	var endDate = $("select[name=endDate]");
	var endTime = $("select[name=endTime]");
	
	endDate.empty();
	endTime.empty();
	startTime.empty();
	
	// end date select option
	var date = new Date(startDate.val());
	var formatDate = getFormatDate(date);
	endDate.append($("<option value="+formatDate+">"+formatDate+"</option>"));
	
    date.setDate(date.getDate() + 1);
	formatDate = getFormatDate(date);
	endDate.append($("<option value="+formatDate+">"+formatDate+"</option>"));
	
	// start time select option
	$.ajax({
		url : "../booking/reqStartTime",
		type : "GET",
		data : {
				 "startDate" : startDate.val()
			   },
		success : function(times) {
			for(var i=0; i<times.length; i++) {
				var option = $("<option value="+times[i]+">"+times[i]+"</option>");
				startTime.append(option);
			}
		},
		error : function(){
			alert("startDateSelect error");
		}
	});
}

function getFormatDate(date){
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : "0" + month;
	var day = date.getDate();
	day = day >= 10 ? day : "0" + day;
	return year + "-" + month + "-" + day;
}

function getEndTimeOptions() {
	var startDate = $("select[name=startDate]");
	var startTime = $("select[name=startTime]");
	var endDate = $("select[name=endDate]");
	var endTime = $("select[name=endTime]");
	
	$.ajax({
		url : "../booking/reqEndTime",
		type : "GET",
		data : {
				 "startDate" : startDate.val(),
				 "startTime" : startTime.val(),
				 "endDate" : endDate.val(),
			   },
		success : function(times) {
			endTime.empty();
			for(var i=0; i<times.length; i++) {
				var option = $("<option value="+times[i]+">"+times[i]+"</option>");
				endTime.append(option);
			}
		},
		error : function(){
			alert("getEndTimeOptions error");
		}
	});
}

const confirmButton = document.getElementById("confirmButton");
confirmButton.addEventListener("click", function() {
	var href = document.getElementById("href").value;
	location.href=href;
});

