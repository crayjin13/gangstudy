$(function() {
   
	
	
	
	// 비밀번호 찾기
	$("button[name=forgotbtn]").click(function() {

		var fpwArray = $('#kt_login_forgot_form').serialize();
		console.log("r값이들어오는가--->" + fpwArray);
		$.ajax({
			url : 'findPw_action',
			method : 'POST',
			data : fpwArray,
			dataType : 'text',
			success : function(textData) {
				if (textData.trim() == "good") {
					alert(" 새로운 비밀번호로 다시 로그인해주세요.");
					window.location.href = "/";

				} else if (textData.trim() == "false") {

					Swal.fire({
						text : "입력하신 정보를 다시 확인해주세요.",
						icon : "error",
						buttonsStyling : false,
						confirmButtonText : "다시 확인하기",
						customClass : {
							confirmButton : "btn font-weight-bold btn-light"
						}
					}).then(function() {
						KTUtil.scrollTop();
					});
					return false;

				} else {
					alert(" 비밀번호 변경에 실패하였습니다.");
				}
			}
		});

	});

	// ************** 비번 업뎃 *************
	$("#updatePw")
			.click(
					function() {

						if (!$('#changePw [name="newPw"]').val()) {
							alert("새 비밀번호를 입력해주세요.");
							$("newPw").focus();
							return false;
						}

						if (!$('#changePw [name="newPw2"]').val()) {
							alert("새 비밀번호 확인을 입력해주세요.");
							$("newPw2").focus();
							return false;
						}

						var pws = $('#changePw').serialize();
						console.log("값들어오는지 확인 " + pws);

						var newPw = $('#changePw [name="newPw"]').val();
						var newPw2 = $('#changePw [name="newPw2"]').val();

						if (newPw.length >= 6 && newPw2.length >= 6) {

							if (newPw == newPw2) {
								$
										.ajax({
											url : 'updatePw',
											method : 'POST',
											data : pws,
											dataType : 'text',
											success : function(textData) {
												console.log(textData);
												if (textData.trim() == "done") {
													Swal
															.fire({
																text : "비밀번호가 변경되었습니다.",
																icon : "success",
																buttonsStyling : false,
																confirmButtonText : "확인",
																customClass : {
																	confirmButton : "btn font-weight-bold btn-primary",
																	cancelButton : "btn font-weight-bold btn-default"
																}
															});

													location.href = "/";

												} else if (textData.trim() == "pwUnmatch") {
													console.log("비번변경실패 ");
													alert('기존 비밀번호가 일치하지않습니다.');

												} else {
													alert(' 변경에 실패하였습니다.')
												}

											}
										});
							} else {
								alert('새 비밀번호가 서로 일치하는지 확인 해주세요.');
							}

						} else {
							alert('비밀번호는 6자리 이상 입력해주세요')
						}

					});

});