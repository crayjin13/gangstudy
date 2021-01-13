



$(function(){
	
	$('#token').on('click', function() {
		
		console.log("토큰 매소드");
		$.ajax({
			url: '/token',
			method : 'POST',
			dataType : 'json',
			success : function(textData){
				
				alert('토큰발급되었습니다.')
			}
		});           
	});
});
	
	
