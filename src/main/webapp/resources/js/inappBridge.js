/**
 * 
 */
// Mobile inApp browser(naver, kakao...) to External browser(chrome, safari...)
(function() {
	var userAgent = navigator.userAgent;
	if (userAgent.match(/KAKAO/i)) {					// inapp |NAVER
		if(userAgent.match(/Android/i)) {					// android
			location.href='kakaotalk://inappbrowser/close'
			location.href='intent://gangstudy.com/#Intent;scheme=http;package=com.android.chrome;end'
		} else if (userAgent.match(/iPhone|iPad|iPod/i)) {	// ios
//			location.href='kakaoweb://closeBrowser'
//			location.href='ftp://15.165.185.2/pub/bridge.html'
		}
	} else if(userAgent.match(/NAVER/i)) {
//		if(userAgent.match(/Android/i)) {					// android
//			location.href='intent://gangstudy.com/#Intent;scheme=http;package=com.android.chrome;end'
//			setTimeout( function() {
//				window.close();
//			}, 500);
//		}
	} else if(document.location.hostname == "ec2-15-165-185-2.ap-northeast-2.compute.amazonaws.com") {
		location.href="http://gangstudy.com"
	}
}());