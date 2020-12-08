document.getElementById("cancel").addEventListener('click', function() {
	if(confirm('예약을 취소하시겠습니까?')) {
		$.get("/booking/canCheck", {
			// data null
		},function(jqXHR) {
			// always
		},'text' /* xml, text, script, html */)
		.done(function(canCheck) {
			if(canCheck) {
				window.location.href = '/payment/cancel'
				alert('예약이 취소되었습니다.')
			} else {
				alert('24시간 이내에는 예약을 취소할 수 없습니다.')
			}
		})
		.fail(function(jqXHR) {
		})
		.always(function(jqXHR) {
		});
	}
});