/**
 * 
 */
// Mobile inApp browser(naver, kakao...) to External browser(chrome, safari...)
(function() {
	var userAgent = navigator.userAgent;
	if (userAgent.match(/KAKAO|NAVER/i)) {					// inapp
		if(userAgent.match(/Android/i)) {					// android
			location.href = 'kakaotalk://inappbrowser/close';
			location.href='intent://ec2-15-165-185-2.ap-northeast-2.compute.amazonaws.com:8080/#Intent;scheme=http;package=com.android.chrome;end'
		} else if (userAgent.match(/iPhone|iPad|iPod/i)) {	// ios
			location.href='ftp://15.165.185.2/pub/bridge.html'
		}
	}
}());