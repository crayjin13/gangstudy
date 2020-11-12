  $(function(){
	  $('#pay').on('click',function(e){
        var IMP = window.IMP; // 생략가능
        IMP.init('imp63891351'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
        var msg;

        IMP.request_pay({
            pg : 'kakaopay',
            pay_method : 'card',
            merchant_uid : 'merchant_' + new Date().getTime(),
            name : '갱스터디',
            amount : 29500,
            buyer_email : 'GANGSTUDY@gmail.com',
            buyer_name : '갱학생',
            buyer_tel : '010-1234-5678',
            buyer_addr : '서울특별시 강남구 삼성동',
            buyer_postcode : '123-456',
            //m_redirect_url : 'http://www.naver.com' 모바일 결제시, 결제가 끝나고 랜딩되는 URL을 지정 (카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐) 
        }, function(rsp) {
            if ( rsp.success ) {
                //[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
                jQuery.ajax({
                    url: "/payments/complete", //cross-domain error가 발생하지 않도록 주의해주세요
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        imp_uid : rsp.imp_uid
                        //기타 필요한 데이터가 있으면 추가 전달
                    }
                }).done(function(data) {
                    //[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
                    if ( everythings_fine ) {
                      var  msg = '결제가 완료되었습니다.';
                        msg += '\n고유ID : ' + rsp.imp_uid;
                        msg += '\n상점 거래ID : ' + rsp.merchant_uid;
                        msg += '\결제 금액 : ' + rsp.paid_amount;
                        msg += '카드 승인번호 : ' + rsp.apply_num;

                        alert(msg);
                    } else {
                    	msg = +rsp.error_msg;
                        //[3] 아직 제대로 결제가 되지 않았습니다.
                        //[4] 결제된 금액이 요청한 금액과 달라 결제를 자동취소처리하였습니다.
                    }
                });
                //성공시 이동할 페이지
               // location.href='<%=request.getContextPath()%>/order/paySuccess?msg='+msg;
               
                window.location.href='/gangstudy/orderDetails';
                alert('결제가 완료되었습니다');
            } else {
                msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
                window.location.href='/gangstudy/shoppingcart';
                //실패시 이동할 페이지
               // location.href="<%=request.getContextPath()%>/order/payFail";
                alert(msg);
            }
        });
	  });

    });