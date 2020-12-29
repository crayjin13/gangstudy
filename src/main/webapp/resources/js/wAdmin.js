$(function() {
	
	
	
	
	// *********** 관리자 로그인 처리 *****************
	$("button[name=adminlogin]").click(function() {
		var mlafArray = $('#kt_login_singin_form').serialize();
		console.log("---- 로그인 값이 들어오는가  ---" + mlafArray);
		$.ajax({
			url : '/admin/sign_in_admin',
			method : 'POST',
			data : mlafArray,
			dataType : 'text',
			success : function(textData) {
				console.log("ajax 타는지");
				if (textData.trim() == "true") {

					location.href = '/jts';

				} else if (textData.trim() == "false1") {
					alert('아이디를 다시 확인해주세요');
					id_check();

				} else if (textData.trim() == "false2") {
					alert('비밀번호를 다시 확인해주세요');
					password_check();
				} else if (textData.trim() == "false3") {
					alert('비활성화된 계정입니다. 활성화 상태창으로 이동합니다.');

				}
			}
		});
	});         
	
	
	
	
	
});