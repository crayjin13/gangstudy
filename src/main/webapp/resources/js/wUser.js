$(function() {
	
	// radio   
	
	function hasClass(target, className) {
		if( (' ' + target.className + ' ').replace(/[\n\t]/g, ' ').indexOf(' ' + className + ' ') > -1 ) return true;
		return false;
	}
	function removeClass(target, className){
	    var elClass = ' ' + target.className + ' ';
	    while(elClass.indexOf(' ' + className + ' ') !== -1){
	         elClass = elClass.replace(' ' + className + ' ', '');
	    }
	    target.className = elClass;
	}
	function addClass(target, className){
	    target.className += ' ' + className;   
	}
	  


	
	
	// **************회원 탈퇴 *************
	$("#deletebtn").click(function() {
		var idpw = $('#delete_User').serialize();
		console.log("값들어오는지 확인 " + idpw);
		$.ajax({
			url : 'deleteUser',
			method : 'POST',    
			data : idpw,                     
			dataType : 'json',
			success : function(data) {
				console.log("ajax 거쳤는지  ");
				if (data == false) {
					console.log("false 일때 "+idpw);
					alert('회원 정보를 다시 확인해주세요');
					return;

				} else {
					console.log("true 일떄 "+idpw);
				 alert('탈퇴하셨습니다.');
				 window.location.href="/";  
				} 
			      
			}
		});
	});
	


 
	 
	      
	// ***** 회원가입 ************

	
	$("#kt_login_signup_form_submit_button").click(function() {
		 
		if(!$('#kt_login_signup_form [name="name"]').val()){  
			$("name").focus();
			alert("실명을 입력해주세요"); 
			return false;
		}
		var emailPattern = /[a-z0-9A-Z]{2,}@[a-z0-9-]{2,}\.[a-z0-9]{2,}/;
		if(!$('#kt_login_signup_form [name="email"]').val() || !emailPattern.test($('#kt_login_signup_form [name="email"]').val() ) ){
			alert("올바른 이메일주소를 입력해주세요");
			$("email").focus();  
			return false;
		}    
		
		 var isPhoneNum = /([09]{9})/;     
		
		if(!$('#kt_login_signup_form [name="phone"]').val() || !isPhoneNum.test($('#kt_login_signup_form [name="phone"]').val() )){      
			$("phone").focus();
			alert("핸드폰 번호를 입력해주세요(9자리이상)");    
			return false;
		}     
		
		var min = /[a-zA-Z0-9_]{3,}/;   
		
		if(! $('#kt_login_signup_form [name="id"]').val() || !min.test($('#kt_login_signup_form [name="id"]').val() )){     
			$("id").focus();    
			alert("아이디를 입력해주세요(3글자이상)");  
			return false;  
		}
		
		 var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{6,40}/;
		
		if(! $('#kt_login_signup_form [name="pw"]').val() || !pwPattern.test( $('#kt_login_signup_form [name="pw"]').val()) ){
			alert("비밀번호를 입력해주세요(6자리 이상)");   
			$("pw").focus();
			return false;
		}
	
		if(! $('#kt_login_signup_form [name="pw2"]').val()){
			alert("비밀번호 재확인을 입력해주세요");   
			$("pw2").focus();
			return false;
		}
		 
		if( $('#kt_login_signup_form [name="pw2"]').val() !== $('#kt_login_signup_form [name="pw"]').val() ){
			alert("비밀번호가 일치하지않습니다. ");   
			$("pw2").focus();       
			return false;
		}  
		
		
		
		if(! $('#kt_login_signup_form [name="bod"]').val()){
			alert("생년월일을 입력해주세요");   
			$("bod").focus();
			return false;  
		}  
		
		 
		
		  
		
		
		
		var userArray = $('#kt_login_signup_form').serialize();
		
		    
		
		console.log("#값이 오는지 확인 ---" + userArray);
		// select option 으로 가져올때 이 문법으로 보내려면
		// select name="" 네임 인지 확인하기
		// https://java119.tistory.com/27
		$.ajax({
			url : 'signUp',
			data : userArray,  
			method : 'POST',
			dataType : 'text',
			success : function(textData) {
				console.log(textData);
				if (textData.trim() == "true") {
						
					kt_login_signup_form.name.value = textData.name;
					kt_login_signup_form.phone.value = textData.phone;
					kt_login_signup_form.id.value = textData.id;
					kt_login_signup_form.pw.value = textData.pw;
					kt_login_signup_form.pw2.value = textData.pw2;
					kt_login_signup_form.email.value = textData.email;
					kt_login_signup_form.bod.value = textData.bod;
					kt_login_signup_form.gender.value = textData.gender;
					      
				alert("회원가입이 완료되었습니다.");     
					location.href = '/signin';    
   
				}else {
					alert("죄송합니다. 입력하신 값이 올바르지 않습니다.  ");    
				}

			},
			
		});

	});

});

// DOM Tree 로딩 후 이벤트 처리
$(function() {
	$('#msg1').hide();
	$('#msg2').hide();

	// 회원가입 유효성 검증
	$('#kt_form').validate({
		rules : {
			name : {
				required : true,
				rangelength : [ 2, 10 ]
			},

			phone : {
				required : true,
				minlength : 9,
				digits : true
			},

			id : {
				required : true,
				rangelength : [ 3, 12 ],
				remote : {
					url : "duplicate_check",
					method : "GET",
					type : "text",
					data : {
						id : function() {
							return $('#id').val();
						}
					}
				}
			},

			pw : {
				required : true,
				rangelength : [ 6, 20 ],

			},
			pw2 : {
				required : true,
				rangelength : [ 6, 20 ],

			},
			email : {
				required : true,
				email : true
			},
			gender : {
				required : true,
				rangelength : [ 1, 1 ],
			}
		},
		messages : {
			id : {
				required : "아이디를 입력해주세요",
				rangelength : "아이디는 3글자 이상 12글자 이내 입니다",

				remote : "{0}는 이미 존재하는 아이디입니다.",
			},
			pw : {
				required : "비밀번호를 입력해주세요",
				rangelength : "6~20글자 내외 영어 대소문자, 특수문자, 숫자 사용가능합니다."
			},
			name : {
				required : "이름을 입력해주세요",
				rangelength : "이름은 최소 2글자 이상 10글자 이내 입니다",
			},
			email : {
				required : "이메일을 입력해주세요",
				email : "올바른 형식이 아닙니다 ex) corona2020@naver.com"
			},
			phone : {
				required : "휴대폰 번호를 입력해주세요",
				digits : "-을 제외한 숫자만 입력해주세요",
				minlength : "전화번호는 최소 9자리 이상입니다."
			},
			gender : {
				required : "남성이면 M, 여성이면 F 를 입력해 주세요",
				rangelength : "M 이나 F 만 입력해 주세요."
			}

		},
		submitHandler : function() {
			signUp_function();
		},
		errorClass : "error",
		validClass : "valid"
	});

})


// 로그인 form 처리
function user_login_action_function() {

};

/*
 * 2) Id, Password 체크
 */
function id_check() {
	var mlafArray = $('#kt_login_singin_form').serialize();
	for (var i = 0; i < mlafArray.length; i++) {
		if (mlafArray[i].name != 'id' && mlafArray[i].name == 'pw') {
			$('#i-error').text('아이디를 다시 확인해주세요.').show();
			// validate 활용
			$('#id').focus();
		}
	}
}
function password_check() {
	var mlafArray = $('#kt_login_singin_form').serialize();
	for (var i = 0; i < mlafArray.length; i++) {
		if (mlafArray[i].name != 'pw' && mlafArray[i].name == 'id') {
			$('#p-error').text('비밀번호가 틀렸습니다.').show();
			$('#pw').focus();
		}
	}
}
