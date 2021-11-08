/**
* Template Name: MyBiz - v2.2.1
* Template URL: https://bootstrapmade.com/mybiz-free-business-bootstrap-theme/
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/
!(function($) {
	
	/* 관심 지역 추가 ajax */
    $("#favoriteBtn").click(function () {
    	let sessionId = sessionStorage.getItem("userId");
    	if (sessionId == null) {
    		window.alert("관심 지역 추가는 로그인 후 이용 가능합니다.")
    	} else {
    		var selectedDongCode = $('#dong').val();
        	var selectedSidoName = $('#city option:selected').text();
      	  	var selectedGuName = $('#gu option:selected').text();
      	  	var selectedDongName = $('#dong option:selected').text();
      	  	
      	  	if (selectedDongCode=='all'){
      	  		window.alert("동까지 선택해주세요!");
      	  	} else {
	      	  	let favoriteRegisterInfo = JSON.stringify({
	      	  			"userId" : sessionId,
	      	  			"dongcode" : selectedDongCode,
	      	  			"sido" : selectedSidoName,
	      	  			"gugun" : selectedGuName,
	      	  			"dong" : selectedDongName
	      	  		});
	      	  	
    	    	$.ajax({
    	            url: '/user/' + sessionId,
    	            type: 'POST',
    	            contentType : 'application/json;charset=utf-8',
    	            data: favoriteRegisterInfo,
    	            dataType: 'json',
    	            statusCode: {
    	            	200: function (loginSession) {
    	            		window.alert("관심지역이 등록되었습니다.");
    	            		console.log(loginSession.name);
    	            	},
    	            	500: function (msg) {
    	            		window.alert("이미 등록된 지역입니다.");
    	            	}
    	            }
    	        });
      	  	}
    	}
    });

})(jQuery);