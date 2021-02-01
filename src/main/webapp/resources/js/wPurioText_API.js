$(document).ready(function() {

	$('.text').on('click', function() {
		var value = '' + $(this).data("value") + '';
		$('#outlog').text($('#outlog').text() + value);
		$.ajax({
			url : "/send",
			type : "post",
			dataType : "json",
			data : {
				command : value
			},
		// succes: function(text){     
		//console.log(text);      
		//}
		}).done(function(data) {
			$('#inlog').text($('#inlog').text() + data);
		});
	});

	     
	      
	$('#mmsBtn').click(function() {
		var numbers = $('#mms').serialize();
		console.log(numbers);

		$.ajax({
			url : "/admin/mmschange",          
			type : "post",
			dataType : "text",    
			data : numbers,
			success : function(textData) {
				console.log(textData);
				if (textData.trim() == "1") {
					alert("변경완료");
				        
				}else { 
					alert("변경 실패"); 
				}       

				
			}
		});
	});      

});