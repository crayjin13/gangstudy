

// *******  회원 정보 수정  *********
function modify_action() {
	$("#modifybtn").click(function(){
	var asArray = $('#modify_action').serializeArray();
	console.log("*****회원정보수정 값 "+asArray);
	$.ajax({
		url : 'modifyInfo',
		method : 'POST',
		data : asArray,
		dataType : 'text',
		success : function(textData) {
			if (textData.trim() == "true") {

		
				modify_action.name.value = textData.name;
				modify_action.phone.value = textData.phone;
				modify_action.id.value = textData.id;
				modify_action.pw.value = textData.pw;
				modify_action.email.value = textData.email;
				modify_action.bod.value = textData.bod;
				modify_action.gender.value = textData.gender;

				location.href = '/login';
			} else {

			}
		}
	});
	})
}






/*$(document).ready(function() {
	signUp_function();

});*/
//***** 회원가입  ************
function signUp_function() {
	$("#btn").click(function() {
		var userArray = $('#sign_up').serialize();
		console.log("#값이 오는지 확인 ---" + userArray);
		//select option 으로 가져올때 이 문법으로 보내려면 
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
					sign_up.name.value = textData.name;
					sign_up.phone.value = textData.phone;
					sign_up.id.value = textData.id;
					sign_up.pw.value = textData.pw;
					sign_up.email.value = textData.email;
					sign_up.bod.value = textData.bod;
					sign_up.gender.value = textData.gender;
					
					location.href = '/login';
				} else if (textData.trim() == "false") {

				}

			}
		});
		// e.preventDefault();
	})
};

// DOM Tree 로딩 후 이벤트 처리
$(function() {
	$('#msg1').hide();
	$('#msg2').hide();

	// 회원가입 유효성 검증
	$('#sign_up').validate({
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
			gender :{
				required : true,
				rangelength :[1,1],
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

/*/ 비밀번호 유효성체크, 비밀번호 두개 같은지 체크  /*/

var pw = document.querySelector('#pw');
var pw2 = document.querySelector('#pw2');
var error = document.querySelectorAll('.error');
//pw.addEventListener("change", checkPw);
pw2.addEventListener("change", comparePw);
// 비밀번호 재확인 
function comparePw() {
    if(pw2.value === pw.value) {
       
        error[0].style.display = "none";
    } else if(pw2.value !== pw.value) {
       
        error[0].innerHTML = "비밀번호가 일치하지 않습니다.";
        error[0].style.display = "block";
    } 

    if(pw2.value === "") {
        error[0].innerHTML = "필수 정보입니다.";
        error[0].style.display = "block";
    }
}
/*
function checkId() {
    var idPattern = /[a-zA-Z0-9_-]{5,20}/;
    if(id.value === "") {
        error[0].innerHTML = "필수 정보입니다.";
        error[0].style.display = "block";
    } else if(!idPattern.test(id.value)) {
        error[0].innerHTML = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";
        error[0].style.display = "block";
        
    } else {
        error[0].innerHTML = "가능한 아이디입니다!";
        error[0].style.color = "#08A600";
        error[0].style.display = "block";
    }
    
}


function checkPw() {
    var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,16}/;
    if(pw.value === "") {
        error[1].innerHTML = "필수 정보입니다.";
        error[1].style.display = "block";
    } else if(!pwPattern.test(pw.value)) {
        error[1].innerHTML = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
        pwMsg.innerHTML = "사용불가";
        pwMsgArea.style.paddingRight = "93px";
        error[1].style.display = "block";
        
        pwMsg.style.display = "block";
      //  pwImg1.src = "public/images/m_icon_not_use.png";
    } else {
        error[1].style.display = "none";
        pwMsg.innerHTML = "안전";
        pwMsg.style.display = "block";
        pwMsg.style.color = "#03c75a";
      //  pwImg1.src = "public/images/m_icon_safe.png";
    }
}
*/












// 로그인 form 처리

function user_login_action_function() {
	$("#loginbtn").click(function() {
		var mlafArray = $('#user_login_action').serialize();
		console.log("---- 로그인 값이 들어오는가  ---" + mlafArray);
		$.ajax({
			url : 'sign_in_action',
			method : 'POST',
			data : mlafArray,
			dataType : 'text',
			success : function(textData) {
				if (textData.trim() == "true") {
					location.href = '/logOn';
				} else if (textData.trim() == "false1") {
					alert('아이디를 다시 확인해주세요');
					id_check();
				} else if (textData.trim() == "false2") {
					alert('비밀번호를 다시 확인해주세요');
					password_check();
				} else if (textData.trim() == "false3") {
					alert('비활성화된 계정입니다. 활성화 상태창으로 이동합니다.'); 
					location.href = '/login';
				}
			}
		})
	})    
};

/*
 * 2) Id, Password 체크
 */
function id_check() {
	var mlafArray = $('#user_login_action').serialize();
	for (var i = 0; i < mlafArray.length; i++) {
		if (mlafArray[i].name != 'id' && mlafArray[i].name == 'pw') {
			$('#i-error').text('아이디를 다시 확인해주세요.').show();
			// validate 활용
			$('#i').focus();
		}
	}
}
function password_check() {
	var mlafArray = $('#user_login_action').serialize();
	for (var i = 0; i < mlafArray.length; i++) {
		if (mlafArray[i].name != 'pw' && mlafArray[i].name == 'id') {
			$('#p-error').text('비밀번호가 틀렸습니다.').show();
			$('#p').focus();
		}
	}
}
