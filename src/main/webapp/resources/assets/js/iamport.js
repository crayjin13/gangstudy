$(function() {
	$('#pay').on('click', function(e) {
		var IMP = window.IMP; // 생략가능
		IMP.init('imp20137588'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
		var msg;
		const pointUse = document.getElementById("kt_touchspin");
		const people = document.getElementById("touchspin");

		$.post("/booking/make", {
			people : people.value,
			point : pointUse.value,
			pg_name : "Danal"
		}, function(jqXHR) {
			// always

		}, 'text' /* xml, text, script, html */).done(function(data) {
			merchant_uid = data;

			console.log(data);

			// begin: IMP.request_pay 다날 결제화면으로 넘어가는 부분
			IMP.request_pay({
				pg : 'Danal',
				pay_method : 'card',
				merchant_uid : merchant_uid /* + new Date().getTime() */,
				name : '갱스터디',
				amount : document.getElementById("totalAmount").textContent,
				buyer_email : '',
				buyer_name : document.getElementById("name").textContent,
				buyer_tel : '',
			// m_redirect_url : 'http://www.naver.com' 모바일 결제시, 결제가 끝나고 랜딩되는
			// URL을 지정 (카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐)
			}, function(rsp) {
				if (rsp.success) {
					// [1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
					console.log('2 차 지나고 ');
					jQuery.ajax({
						url : "/payment/beready", // cross-domain error가 발생하지 않도록 주의해주세요
						type : 'GET',   
						dataType : 'json',
						data : {
							imp_uid : rsp.imp_uid
						// 기타 필요한 데이터가 있으면 추가 전달
						}
					}).done(function(data) {
						// [2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
						if (everythings_fine) {
							var msg = '결제가 완료되었습니다.';
							msg += '\n고유ID : ' + rsp.imp_uid;
							msg += '\n상점 거래ID : ' + rsp.merchant_uid;
							msg += '\결제 금액 : ' + rsp.paid_amount;  
							msg += '카드 승인번호 : ' + rsp.apply_num;   
							
							alert(msg);        
							      
						
						} else {               
							msg = +rsp.error_msg;
							alert(msg);
							// [3] 아직 제대로 결제가 되지 않았습니다.     
							// [4] 결제된 금액이 요청한 금액과 달라 결제를 자동취소처리하였습니다.
		    				}    
					});  
					// 성공시 이동할 페이지           
					alert("결제가 완료되었습니다.");      
					window.location.href = '/booking/check';    
				} else {                   
					msg = '결제에 실패하였습니다.';  
					msg += '에러내용 : ' + rsp.error_msg;
					// window.location.href='/';
					// 실패시 이동할 페이지
					// location.href="<%=request.getContextPath()%>/order/payFail";
					alert(msg);
					window.location.href = '/';
				}
				
      				
			}); // end: IMP.request_pay 다날 결제화면 종료   

		}); // end: 비동기 $.post( paybydanal) 매소드 종료
	      

	         
	}); // end: #pay button 클릭했을떄 실행되는 메소드
});