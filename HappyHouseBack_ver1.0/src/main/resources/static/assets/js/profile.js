/**
* Template Name: MyBiz - v2.2.1
* Template URL: https://bootstrapmade.com/mybiz-free-business-bootstrap-theme/
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/
!(function($) {
	
	$(document).ready(function () {
		let sessionId = sessionStorage.getItem("userId");
		let profilePw = $("#profileUserPw").val();
		
		/* 회원 정보 조회 및 수정 페이지 데이터 로드 */
		$.ajax({
            url: '/user/' + sessionId,
            type: 'GET',
            data: {"favorite" : ""},
            dataType: 'json',
            statusCode: {
            	200: function (user) {
            		if(user != null){
            			loadProfile(user);
            			profilePw = user.pw;
            	    } else{
            	    	window.alert("로그인 후 이용가능한 서비스입니다.");
            	    	window.location.replace("./index.html");
            	    }
            	},
            	204: function (response){
            		window.alert("로그인 후 이용가능한 서비스입니다.");
        	    	window.location.replace("./index.html");
            	},
            	500 : function (msg) {
            		window.alert(msg);
            		window.location.replace("./index.html");
            	}
            }
        });
		
    	var isId = true;
        var isPw = true;
        /* 아이디 중복검사 */
        $("#profileUserId").keyup(function () {
            var ckid = $("#profileUserId").val();
            if(ckid.length < 4 || ckid.length > 16) {
                $(".idresult").text("아이디는 4자이상 16자이하입니다.").removeClass('text-primary').removeClass('text-danger').addClass('text-gray');
                isId = false;
            } else {
                if (ckid.includes(" ")){
                    $(".idresult").text("아이디는 공백을 포함할 수 없습니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                    isId = false;
                } else {
                	if (ckid == sessionId) {
                		$(".idresult").text("").removeClass('text-danger').removeClass('text-gray').addClass('text-primary');
                        isId = true;
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
            }
        });
        
        /* 비밀번호 유효성 검사 */
        $("#profileUserPw").keyup(function () {
            var ckpw = $("#profileUserPw").val();
            
            if(ckpw.length < 5 || ckpw.length > 16) {
                $(".pwresult").text("비밀번호는 5자리 이상 16자리 이하입니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                isPw = false;
            } else {
            	if (ckpw == profilePw) {
            		$(".pwresult").text("").removeClass('text-danger').removeClass('text-gray').addClass('text-primary');
            		isPw = true;
            	} else {
            		var num = ckpw.search(/[0-9]/g);
                    var lowereng = ckpw.search(/[a-z]/ig);
                    var uppereng = ckpw.search(/[A-Z]/gi);
                    if (ckpw.includes(" ")) {
                        $(".pwresult").text("비밀번호는 공백을 포함할 수 없습니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                        isPw = false;
                    } else {
                        if (num < 0 || (lowereng < 0 || uppereng < 0)) {
                            $(".pwresult").text("비밀번호는 영문, 숫자 포함 5자리 이상입니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                            isPw = false;
                        } else {
                            $(".pwresult").text("사용가능한 비밀번호입니다.").removeClass('text-gray').removeClass('text-danger').addClass('text-primary');
                            isPw = true;
                        }
                    }
            	}
            }
        });
        
        /* 회원 정보 수정 */
        $("#userUpdateBtn").click(function () {
        	if (!$("#profileUserId").val()) {
                alert("아이디를 입력해주세요.");
                return;
            } else if (!isId) {
                alert("사용할 수 없는 아이디입니다.");
                return;
            } else if (!$("#profileUserPw").val()) {
                alert("비밀번호를 입력해주세요.");
                return;
            } else if (!isPw) {
                alert("사용할 수 없는 비밀번호입니다.");
                return;
            } else {
                let sessionId = sessionStorage.getItem("userId");
                let modifyInfo = JSON.stringify({
    				"id" : $("#profileUserId").val(),
    				"pw" : $("#profileUserPw").val(),
    				"name" : $("#profileUserName").text(),
    				"email" : $("#profileUserEmail").val(),
    				"address" : $("#profileUserAddr").val()
    			});
        		
        		$.ajax({
                    url: '/user/' + sessionId,
                    contentType : 'application/json;charset=utf-8',
                    data: modifyInfo,
                    type: 'PUT',
                    dataType: 'json',
                    statusCode: {
                    	200: function (user) {
                    		window.alert("회원 정보가 수정되었습니다.");
                    		sessionStorage.setItem("userId", user.id);
                    		$('#userSessionId').text(user.id);
                    		profilePw = user.pw;
                    		
                    		$(".idresult").text("").removeClass('text-danger').removeClass('text-gray').addClass('text-primary');
                    		$(".pwresult").text("").removeClass('text-danger').removeClass('text-gray').addClass('text-primary');
                    	},
                    	500 : function (msg) {
                    		window.alert(msg);
                    		window.location.replace("./index.html");
                    	}
                    }
                });
            }
        });
        
        /* 회원 탈퇴 */
        $("#userDeleteBtn").click(function() {
        	let sessionId = sessionStorage.getItem("userId");
            if (confirm("진심으로 탈퇴하시겠습니까....? 믿을 수 없어요...")) {
            	$.ajax({
                    url: '/user/' + sessionId,
                    type: 'DELETE',
                    dataType: 'json',
                    statusCode: {
                    	200: function (user) {
                    		sessionStorage.removeItem("userId");
                    		window.location.replace("./index.html");
                    		window.alert("안녕히 가세요... 다음에 또만나요!");
                    	},
                    	500 : function (msg) {
                    		window.alert(msg);
                    		window.location.replace("./index.html");
                    	}
                    }
                });
            } else {
            	return;
            }
        });
    });
	
	/* 회원 정보 출력 */
	function loadProfile(user) {
		$('#profileUserName').empty();
		$('#profileUserName').text(user.name);
		
		$('#profileUserId').empty();
		$('#profileUserId').val(user.id).attr("placeholder", user.id);
		$('#profileUserPw').empty();
		$('#profileUserPw').val(user.pw);
		$('#profileUserEmail').empty();
		$('#profileUserEmail').val(user.email).attr("placeholder", user.email);
		$('#profileUserAddr').empty();
		$('#profileUserAddr').val(user.address).attr("placeholder", user.address);
		
		$("#favListUl").empty();
		let userFavorites = user.favorites;
		
		$.each (userFavorites, function (index, favorite) {
			let li = $("<li>").attr("id", "favli-" + favorite.dongcode);
			
			let span = $("<span>").text(favorite.sido + " " + favorite.gugun + " " + favorite.dong);
			
			let button = $("<button>");
			button.attr("id", "favbtn-" + favorite.dongcode);
			button.addClass("btn close deleteBtn");
			button.attr("type", "button");
			button.text("×");
			
			li.append(span).append(button);
			$("#favListUl").append(li);
		});
	}
	
	/* 관심 지역 삭제 */
	$(document).on("click", ".deleteBtn", function() {
		var clickedId = $(this).attr("id");
        var len = clickedId.length;
        var dongcode = clickedId.substr(7, len+1);
        var listId = "#favli-" + dongcode;
        
        let sessionId = sessionStorage.getItem("userId");
        
        $.ajax({
        	url: '/user/' + sessionId,
	        data: {'favorite': '', 
	        	   'dongcode': dongcode},
	        type: 'DELETE',
	        dataType: 'json',
	        statusCode: {
            	200: function (response) {
            		$(listId).remove();
            	},
            	500 : function (msg) {
            		window.alert(msg);
            		window.location.replace("./index.html");
            	}
            }
	    });
	});
	
	/* 로그아웃 */
    $("#logoutBtn").click(function () {
    	window.alert("로그아웃되었습니다.");
    	sessionStorage.removeItem('userId');
    });

})(jQuery);