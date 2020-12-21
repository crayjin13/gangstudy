function getParam(sname) {
	var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";
    params = params.split("&");
    for (var i = 0; i < params.length; i++) {
        temp = params[i].split("=");
        if ([temp[0]] == sname) { sval = temp[1]; }
    }
    return sval;
}

function requestTimes(url, data, target, callback) {
	removeOptions(target)
	$.ajax({
		url : url,
		type : "GET",
		data : data,
		success : function(response) {
			display(response, target, callback)
		},
		error : function(){
			alert("requestTimes error")
		}
	})
}

function display(times, target, callback) {
	if(times.length == 0) {
		addOption(target, "해당일에 예약할 수 있는 시간이 없습니다.", "")
	} else {
		addOption(target, "시간을 선택해주세요.", "")
		for(var i = 0; i < times.length; i++) {
			addOption(target, times[i], times[i])
		}
	}
	target.value = target.getAttribute("time")
	if(callback!=null) { callback() }
}

//////////////////////////////////////
//			Validation Check		//
//////////////////////////////////////
function validationCheck(startDate, startTime, endDate, endTime, people, form) {
	if(startDate == "") {
		alert("시작일을 선택해주세요.")
	} else if(startTime == "") {
		alert("시작시간을 선택해주세요.")
	} else if(endDate == "") {
		alert("종료일을 선택해주세요.")
	} else if(endTime == "") {
		alert("종료시간을 선택해주세요.")
	} else if(people == "") {
		alert("사용인원을 선택해주세요.")
	} else {
		form.submit()
	}
}

//////////////////////////////////////
//				Pikaday				//
//////////////////////////////////////
function getPikaday(field) {
	return new Pikaday({
	    field: field,
	    format: 'YYYY-MM-DD',
	    toString : function(date) {
	        return getFormatDate(date)
		}
	})
}

//////////////////////////////////////
//			util functions			//
//////////////////////////////////////

function addOption(target, text, value) {
	var option = document.createElement("option")
	option.text = text
	option.value = value
	option.name = value
	target.options.add(option)
}

function removeOptions(selectElement) {
   var i, L = selectElement.options.length - 1
   for(i = L; i >= 0; i--) {
      selectElement.remove(i)
   }
}

function getFormatDate(date){
	var year = date.getFullYear()
	var month = (1 + date.getMonth())
	month = month >= 10 ? month : "0" + month
	var day = date.getDate()
	day = day >= 10 ? day : "0" + day
	return year + "-" + month + "-" + day
}

function setDateRange(target, minDate, offset) {
	var maxDate = new Date()
	maxDate.setDate(minDate.getDate()+offset)
	target.setMinDate(minDate)
	target.setMaxDate(maxDate)
}
