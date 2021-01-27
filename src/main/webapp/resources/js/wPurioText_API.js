$(document).ready(function(){
	
	 $('.text').on('click', function() {
	        var value = ''+$(this).data("value")+'';
	    		$('#outlog').text($('#outlog').text()+value);
	        $.ajax({
	      		url: "/send",    
	      		type: "post",                    
	      		dataType:"json",              
	      		data: {                        
	            command: value
	          },
	         // succes: function(text){     
	        	  //console.log(text);      
	          //}
	      	}).done(function(data) {
	      		$('#inlog').text($('#inlog').text()+data);
	      	});
	      });
	
	
});