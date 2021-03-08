$(function() {

	// ******* 회원 정보 수정 ********************
	$("#modifybtn").click(function() {
		var asArray = $('#kt_form').serialize();  
	                
		var pw = $('#kt_form [name="pw"]').val();    
	            if(pw){         
		console.log("*****회원정보수정 값들어오는지 체크  " + asArray);

			$.ajax({    
				url : 'modifyInfo',   
				method : 'POST',
				data : asArray,
				dataType : 'text',  
				success : function(textData) {              
					console.log(textData);  
					if(textData.trim() == "null"){      
						
						alert("기존 비밀번호를 입력해 주세요");
					}else if(textData.trim() == "signin"){
						console.log("정보수정 성공했음 ");   
						alert('수정 되었습니다. 다시 로그인 해주세요');
						location.href = '/signin';
						
					}else if(textData.trim() == "pwfalse") {
						
						alert(" 비밀번호가 일치하지 않습니다. ");
					}else {
						
						alert(" 수정에 실패하였습니다. ");  
					}
					
					        
					
					
				}     
			});
		
	            }else {
	            	alert("비밀번호를 입력해주세요.");
	            }
			
		});

	 


}); 
