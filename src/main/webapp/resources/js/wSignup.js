
	


$(function() {
	
  
	//  회원가입전 이용약관 동의 여부 
	$("button[name=next]").click(function() {
	
											if ($('#c1').prop('checked') == false
													) {   

												Swal
														.fire(
																{
																	text : "필수 약관에 동의 하셔야 합니다.",
																	icon : "error",
																	buttonsStyling : false,
																	confirmButtonText : "다시 확인하기",
																	customClass : {
																		confirmButton : "btn font-weight-bold btn-light"
																	}
																})
														.then(function() {
															KTUtil.scrollTop();
														});
												return false; // Do not change
												// wizard step,
												// further
												// action will
												// be handled by
												// he validator
											} else {
												window.location.href = "/signup";       
											}

											
	})
	

// ***** 회원가입 ************

	
	$("#kt_login_signup_form_submit_button").click(function() {
		
		if(!$('#kt_login_signup_form [name="name"]').val()){
			$("name").focus();
			alert("이름을 입력해주세요");
			return false;
		}
		if(!$('#kt_login_signup_form [name="phone"]').val()){      
			$("phone").focus();
			alert("휴대전화번호를 입력해주세요");
			return false;
		}     
		
		if(! $('#kt_login_signup_form [name="id"]').val()){     
			$("id").focus();
			alert("아이디를 입력해주세요");
			return false;
		}
		if(! $('#kt_login_signup_form [name="pw"]').val()){
			alert("비밀번호를 입력해주세요");   
			$("pw").focus();
			return false;
		}
		if(! $('#kt_login_signup_form [name="pw2"]').val()){
			alert("비밀번호 재확인을 입력해주세요");   
			$("pw2").focus();
			return false;
		}
		  
		if(!$('#kt_login_signup_form [name="email"]').val()){
			alert("이메일을 입력해주세요");
			$("email").focus();
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
					      
					Swal.fire({
						text: "갱스터디 회원이 되신걸 축하합니다. !",
						icon: "success",
						buttonsStyling: false,
						confirmButtonText: "로그인하러가기",
						customClass: {
							confirmButton: "btn font-weight-bold btn-primary",
							cancelButton: "btn font-weight-bold btn-default"
						}
					});
					
				}else {
					alert("죄송합니다. 입력하신 값이 올바르지 않습니다.(입력한 글 사이즈가 너무 크거나 작을경우)  ");    
				}

			},
			
		});

	});
	
	
	
	
	
//	
	});

     
