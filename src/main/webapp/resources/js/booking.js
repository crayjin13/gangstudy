function startDateSelect() {
	var startDate = $('select[name=startDate]');
	var startTime = $('select[name=startTime]');
	var endDate = $('select[name=endDate]');
	var endTime = $("select[name=endTime]");
	
	endDate.empty();
	endTime.empty();
	startTime.empty();
	
	// end date select option
	var date = new Date(startDate.val());
	var format = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
	endDate.append($("<option value="+format+">"+format+"</option>"));
	format = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + (date.getDate()+1);
	endDate.append($("<option value="+format+">"+format+"</option>"));
	
	// start time select option
	$.ajax({
		url : '../booking/reqStartTime',
		type : 'GET',
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


function getEndTimeOptions() {
	var startDate = $("select[name=startDate]");
	var startTime = $("select[name=startTime]");
	var endDate = $("select[name=endDate]");
	var endTime = $("select[name=endTime]");
	
	$.ajax({
		url : '../booking/reqEndTime',
		type : 'GET',
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

function confirm() {
	var href = $("input[name=href]").val();
	$.ajax({
		url : '../booking/'+href,
		type : 'POST',
		success : function(result) {
			if(result == "true") {
				location.replace("/booking");
			} else {
				alert(result);
				location.replace("/booking");
			}
		},
		error:function(request, error){
		    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	   }
	});
}

window.onbeforeunload = function(e){
    
}
