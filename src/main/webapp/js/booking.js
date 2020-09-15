function chageDateSelect() {
	var date = $("select[name=book_dt]");
	var ci = $("select[name=ci]");
	var co = $("select[name=co]");
	$.ajax({
		url : 'booking/getCI',
		type : 'GET',
		data : {
				 "date" : date.val()
			   },
		success : function(times) {
			ci.empty();
			co.empty();
			for(var i=0; i<times.length; i++) {
				var option = $("<option value="+times[i]+">"+times[i]+"</option>");
				ci.append(option);
			}
		},
		error : function(){
			alert("chageDateSelect error");
		}
	});
}

function chageCiSelect() {
	var ci = $("select[name=ci]");
	var co = $("select[name=co]");
	var options = ci.find('option').map(function() {
		return $(this).val();
	}).get();
	console.log('options', options);
	
	$.ajax({
		url : 'booking/getCO',
		type : 'GET',
	    traditional : true,
		data : {
				 "ci" : ci.val(),
				 "options" : options
			   },
		success : function(times) {
			co.empty();
			for(var i=0; i<times.length; i++) {
				var option = $("<option value="+times[i]+">"+times[i]+"</option>");
				co.append(option);
			}
		},
		error : function(){
			alert("chageCiSelect error");
		}
	});
}