/**
* Template Name: MyBiz - v2.2.1
* Template URL: https://bootstrapmade.com/mybiz-free-business-bootstrap-theme/
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/
!(function($) {
	
	var isId = false;
    var isPw = false;
    
    /* 아이디 중복검사 */
    $("#signUpUserId").keyup(function () {
        var ckid = $("#signUpUserId").val();
        if(ckid.length < 4 || ckid.length > 16) {
            $(".idresult").text("아이디는 4자이상 16자이하입니다.").removeClass('text-primary').removeClass('text-danger').addClass('text-gray');
            isId = false;
        } else {
            if (ckid.includes(" ")){
                $(".idresult").text("아이디는 공백을 포함할 수 없습니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                isId = false;
            } else {
                $.ajax({
                    url: '/user',
                    data: {'idCheck': "",
                    	   'id': ckid},
                    type: 'GET',
                    dataType: 'json',
                    statusCode: {
                    	200: function (response) {
                    		$(".idresult").text(ckid + "는 사용할 수 없습니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                            isId = false;
                    	},
                    	204: function (response){
                    		$(".idresult").text(ckid + "는 사용가능합니다.").removeClass('text-danger').removeClass('text-gray').addClass('text-primary');
                            isId = true;
                    	},
                    	500 : function (msg) {
                    		window.alert(msg);
                    		window.location.replace("./index.html");
                    	}
                    }
                });
            }
        }
    });
    
    /* 비밀번호 유효성 검사 */
    $("#signUpUserPw").keyup(function () {
        var ckpw = $("#signUpUserPw").val();
        
        if(ckpw.length < 5 || ckpw.length > 16) {
            $("#pwresult").text("비밀번호는 5자리 이상 16자리 이하입니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
            isPw = false;
        } else {
            var num = ckpw.search(/[0-9]/g);
            var lowereng = ckpw.search(/[a-z]/ig);
            var uppereng = ckpw.search(/[A-Z]/gi);
            if (ckpw.includes(" ")) {
                $("#pwresult").text("비밀번호는 공백을 포함할 수 없습니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                isPw = false;
            } else {
                if (num < 0 || (lowereng < 0 || uppereng < 0)) {
                    $("#pwresult").text("비밀번호는 영문, 숫자 포함 5자리 이상입니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                    isPw = false;
                } else {
                    $("#pwresult").text("사용가능한 비밀번호입니다.").removeClass('text-gray').removeClass('text-danger').addClass('text-primary');
                    isPw = true;
                }
            }
        }
    });
    
    /* 회원가입 ajax */
    $("#signUpBtn").click(function () {
        if (!$("#signUpUserId").val()) {
            alert("아이디를 입력해주세요.");
            return;
        } else if (!isId) {
            alert("사용할 수 없는 아이디입니다.");
            return;
        } else if (!$("#signUpUserPw").val()) {
            alert("비밀번호를 입력해주세요.");
            return;
        } else if (!isPw) {
            alert("사용할 수 없는 비밀번호입니다.");
            return;
        } else if (!$("#signUpUserName").val()) {
            alert("이름을 입력해주세요.");
            return;
        } else {
        	let registerInfo = JSON.stringify({
				"id" : $("#signUpUserId").val(),
				"pw" : $("#signUpUserPw").val(),
				"name" : $("#signUpUserName").val(),
				"email" : $("#signUpUserEmail").val(),
				"address" : $("#signUpUserAddr").val()
			});
        	
        	$.ajax({
                url: '/user',
                type: 'POST',
                contentType : 'application/json;charset=utf-8',
                data: registerInfo,
                dataType: 'json',
                statusCode: {
                	200: function (loginSession) {
                		window.alert("회원가입 성공");
                		$("#signUpCloseBtn").trigger("click");
                		sessionStorage.setItem("userId", loginSession.id);
                		$('.before_login').css('display', 'none');
            	        $('.after_login').css('display', '');
            	        
            	        let sessionId = sessionStorage.getItem("userId");
            	        $('#userSessionId').text(sessionId);
                	},
                	500 : function (msg) {
                		window.alert(msg);
                		window.location.replace("./index.html");
                	}
                }
            });
        }
    });

})(jQuery);