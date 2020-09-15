// 회원가입 

function signUp_function(){
	var userArray = $('#sign_up').serializeArray();
	
	$.ajax({
		url : 'signUp',
		data : userArray,
		method : 'POST',
		dataType : 'text',
		success : function(textData) {
			if (textData.trim() == "true"){
				sign_up.name.value = textData.name;
				sign_up.phone.value = textData.phone;
				sign_up.id.value = textData.id;
				sign_up.pw.value = textData.pw;
				sign_up.email.value = textData.email;
				sign_up.bod.value = textData.bod;
				sign_up.gender.value = textData.gender;
						
				//location.href = '/gangstudy/signUp';
			} else if (textData.trim() == "false"){
				
			}
		}
	})
	e.preventDefault();
	

	
}









