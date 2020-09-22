//회원가입 
$(document).ready(function() {
	signUp_function();

});
function signUp_function() {
	$("#btn").click(function() {
		var userArray = $('#sign_up').serialize();
		console.log("#값이 오는지 확인 ---" + userArray);

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
				rangelength : [ 3, 12 ],

			},
			pw2 : {
				required : true,
				rangelength : [ 3, 12 ],

			},
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			id : {
				required : "아이디를 입력해주세요",
				rangelength : "아이디는 3글자 이상 10글자 이내 입니다",

				remote : "{0}는 이미 존재하는 아이디입니다.",
			},
			pw : {
				required : "비밀번호를 입력해주세요",
				rangelength : "비밀번호는 1글자 이상 10글자 이내 입니다"
			},
			name : {
				required : "이름을 입력해주세요",
				rangelength : "이름은 최소 2글자 이상 10글자 이내 입니다",
			},
			email : {
				required : "이메일을 입력해주세요",
				email : "올바른 형식이 아닙니다 - bluepk2034@naver.com"
			},
			phone : {
				required : "휴대폰 번호를 입력해주세요",
				digits : "-을 제외한 숫자만 입력해주세요",
				minlength : "전화번호는 최소 9자리 이상입니다."
			},

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
