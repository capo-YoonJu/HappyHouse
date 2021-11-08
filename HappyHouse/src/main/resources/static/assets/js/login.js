/**
* Template Name: MyBiz - v2.2.1
* Template URL: https://bootstrapmade.com/mybiz-free-business-bootstrap-theme/
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/
!(function($) {

	/* 로그인 ajax */
    $("#loginBtn").click(function () {
        if (!$("#loginId").val()) {
            alert("아이디를 입력해주세요.");
            return;
        } else if (!$("#loginPw").val()) {
            alert("비밀번호를 입력해주세요.");
            return;
        } else {
        	let loginInfo = JSON.stringify({
				"id" : $("#loginId").val(),
				"pw" : $("#loginPw").val()
			});
        	
        	$.ajax({
                url: '/user',
                type: 'GET',
                data: {
                	"id" : $("#loginId").val(),
    				"pw" : $("#loginPw").val()},
                dataType: 'json',
                statusCode: {
                	200: function (loginSession) {
                		if(loginSession != null){
                			window.alert("로그인 성공!");
                			sessionStorage.setItem("userId", loginSession.id);
                	        $('.before_login').css('display', 'none');
                	        $('.after_login').css('display', '');
                	        
                	        let sessionId = sessionStorage.getItem("userId");
                	        $('#userSessionId').text(sessionId);
                	    } else{
                	    	window.alert("로그인 실패!");
                	        $('.after_login').css('display', 'none');
                	    }
                	},
                	204: function (response){
                		window.alert("아이디 혹은 비밀번호가 틀립니다. 다시 시도해주세요.");
                	},
                	500 : function (msg) {
                		window.alert(msg);
                		window.location.replace("./index.html");
                	}
                }
            });
        }
    });
    
    /* 로그아웃 */
    $("#logoutBtn").click(function () {
    	sessionStorage.removeItem('userId');
    });
    
    /* nav바 로그인 세션 아이디 출력부분(main에 합쳐질 부분) */
    $(document).ready(function () {
    	let sessionId = sessionStorage.getItem("userId");
        $('#userSessionId').text(sessionId);
        
     // 로그인 여부 확인
	    let userId = sessionStorage.getItem("userId");
	    sessionStorage.setItem("contextpath", "");
	
	    if(userId != null){
	        $('.before_login').css('display', 'none');
	    } else{
			sessionStorage.removeItem('userId');
	        $('.after_login').css('display', 'none');
	    }
    });

})(jQuery);