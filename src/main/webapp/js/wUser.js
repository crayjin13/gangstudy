// 회원가입 

function user_signUp_action_function(){
	var userArray = $('#user_signUp').serializeArray();
	
	$.ajax({
		url : 'sign_up',
		data : userArray,
		method : 'POST',
		dataType : 'text',
		success : function(textData) {
			if (textData.trim() == "true"){
				user_signup.name.value = textData.name;
				user_signup.phone.value = textData.phone;
				user_signup.id.value = textData.id;
				user_signup.pw.value = textData.pw;
				user_signup.email.value = textData.email;
				user_signup.bod.value = textData.bod;
				user_signup.gender.value = textData.gender;
				user_signup.rate.value = textData.rate;
				user_signup.points.value = textData.points;
				user_signup.note.value = textData.note;
				
				location.href = '/gangstudy/sign_up';
			} else if (textData.trim() == "false"){
				
			}
		}
	})
	e.preventDefault();
	

	
}

$(function(){
	var idArray=$('#selectById').serializeArray();
	//검색 결과 여부
	if (mIdArray.length==0) {
		alert("결과없음");
		location.href="profiles";
	}
	}); 