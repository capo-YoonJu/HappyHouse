<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Happy House &mdash; SSAFY 6기 관통 프로젝트</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <script src="${root}/js/jquery-3.3.1.min.js"></script>
        <script src="${root}/js/jquery-migrate-3.0.1.min.js"></script>
        <script src="${root}/js/jquery-ui.js"></script>
        <script src="${root}/js/popper.min.js"></script>
        <script src="${root}/js/bootstrap.min.js"></script>
        
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito+Sans:200,300,400,700,900|Roboto+Mono:300,400,500"> 
        <link rel="stylesheet" href="${root}/fonts/icomoon/style.css">
        <link rel="stylesheet" href="${root}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${root}/css/magnific-popup.css">
        <link rel="stylesheet" href="${root}/css/jquery-ui.css">
        <link rel="stylesheet" href="${root}/css/owl.carousel.min.css">
        <link rel="stylesheet" href="${root}/css/owl.theme.default.min.css">
        <link rel="stylesheet" href="${root}/css/bootstrap-datepicker.css">
        <link rel="stylesheet" href="${root}/css/mediaelementplayer.css">
        <link rel="stylesheet" href="${root}/css/animate.css">
        <link rel="stylesheet" href="${root}/fonts/flaticon/font/flaticon.css">
        <link rel="stylesheet" href="${root}/css/fl-bigmug-line.css">
        <link rel="stylesheet" href="${root}/css/aos.css">
        <link rel="stylesheet" href="${root}/css/style.css">
        
        <!-- 회원 가입, 로그인 이벤트 처리 js -->
        <script type="text/javascript">
            $(document).ready(function () {
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
                                url: '${root}/user/idCheck.do',
                                data: {'ckid': ckid},
                                type: 'GET',
                                dataType: 'json',
                                success: function (response) {
                                    if(response == 0) {
                                        $(".idresult").text(ckid + "는 사용가능합니다.").removeClass('text-danger').removeClass('text-gray').addClass('text-primary');
                                        isId = true;
                                    } else {
                                        $(".idresult").text(ckid + "는 사용할 수 없습니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                                        isId = false;
                                    }
                                }
                            });
                        }
                    }
                });
                
                /* 비밀번호 유효성 검사 */
                $("#signUpUserPw").keyup(function () {
                    var ckpw = $("#signUpUserPw").val();
                    
                    if(ckpw.length < 6 || ckpw.length > 16) {
                        $("#pwresult").text("비밀번호는 6자리 이상 16자리 이하입니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
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
                                $("#pwresult").text("비밀번호는 영문, 숫자 포함 6자리 이상입니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
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
                        $("#signUpForm").attr("action", "${root}/user/register.do").submit();
                    }
                });
             
            });
        </script>
        
        <!-- 부동산 조회 처리 js -->
        <script type="text/javascript">
            function check() {
                if (!choice.sido.value || !choice.gugun.value || !choice.dong.value)
                    alert("검색할 지역(시/구/동)을 모두 선택해주세요");
                else searchDeal();
            }
        </script>
        
        <style>
        .map_wrap, .map_wrap * {margin:0; padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
        .map_wrap {position:relative; width:100%; height:700px; margin-top: 30px; margin-bottom: 30px;}
        #category {position:absolute;top:10px;left:10px;border-radius: 5px; border:1px solid #909090;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);background: #fff;overflow: hidden;z-index: 2;}
        #category li {float:left;list-style: none;width:50px;px;border-right:1px solid #acacac;padding:6px 0;text-align: center; cursor: pointer;}
        #category li.on {background: #eee;}
        #category li:hover {background: #ffe6e6;border-left:1px solid #acacac;margin-left: -1px;}
        #category li:last-child{margin-right:0;border-right:0;}
        #category li span {display: block;margin:0 auto 3px;width:27px;height: 28px;}
        #category li .category_bg {background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png) no-repeat;}
        #category li .bank {background-position: -10px 0;}
        #category li .mart {background-position: -10px -36px;}
        #category li .pharmacy {background-position: -10px -72px;}
        #category li .oil {background-position: -10px -108px;}
        #category li .cafe {background-position: -10px -144px;}
        #category li .store {background-position: -10px -180px;}
        #category li.on .category_bg {background-position-x:-46px;}
        .placeinfo_wrap {position:absolute;bottom:28px;left:-150px;width:300px;}
        .placeinfo {position:relative;width:100%;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;padding-bottom: 10px;background: #fff;}
        .placeinfo:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}
        .placeinfo_wrap .after {content:'';position:relative;margin-left:-12px;left:50%;width:22px;height:12px;background:url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
        .placeinfo a, .placeinfo a:hover, .placeinfo a:active{color:#fff;text-decoration: none;}
        .placeinfo a, .placeinfo span {display: block;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
        .placeinfo span {margin:5px 5px 0 5px;cursor: default;font-size:13px;}
        .placeinfo .title {font-weight: bold; font-size:14px;border-radius: 6px 6px 0 0;margin: -1px -1px 0 -1px;padding:10px; color: #fff;background: #d95050;background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}
        .placeinfo .tel {color:#0f7833;}
        .placeinfo .jibun {color:#999;font-size:11px;margin-top:0;}
        </style>

    </head>
    <body>
    
    <div class="site-loader"></div>
    
    <div class="site-wrap">

        <div class="site-mobile-menu">
	        <div class="site-mobile-menu-header">
	            <div class="site-mobile-menu-close mt-3">
	            	<span class="icon-close2 js-menu-toggle"></span>
	            </div>
	        </div>
	        <div class="site-mobile-menu-body"></div>
        </div> <!-- .site-mobile-menu -->
        
        <div class="site-navbar mt-4">
	        <div class="container py-1">
	            <div class="row align-items-center">
		            <div class="col-8 col-md-8 col-lg-4">
		                <h1 class="mb-0"><a href="${root}/index.jsp" class="text-white h2 mb-0"><strong>Happy House<span class="text-danger">.</span></strong></a></h1>
		            </div>
	            
		            <!-- 메뉴 네비게이션 바 시작 -->
		            <div class="col-4 col-md-4 col-lg-8">
		                <nav class="site-navigation text-right text-md-right" role="navigation">
			                <div class="d-inline-block d-lg-none ml-md-0 mr-auto py-3"><a href="#" class="site-menu-toggle js-menu-toggle text-white"><span class="icon-menu h3"></span></a></div>
			                <ul class="site-menu js-clone-nav d-none d-lg-block">
			                        
			                    <c:choose>
				                    <c:when test="${empty userSession}">
				                        <!-- 로그인 이전 -->
				                        
				                        <!-- 회원가입 메뉴 -->
				                        <li><a href="#signUpModal" data-toggle="modal" data-target="#signUpModal" >Sign Up</a></li>
				                        
				                        <!-- 로그인 메뉴 -->
				                        <li><a href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >Log In</a>
				                            <!-- Log In 드롭다운 시작 -->
				                            <ul class="dropdown-menu dropdown-menu-right dropdown-animation" aria-labelledby="header-top-drop-2">
					                            <li>
					                                <form id="loginForm" method="post" action="${root}/user/login.do" class="col-md-12 mb-3 mb-md-0" >
					                                <div class="row form-group">
					                                    <div class="col-md-12 mb-3 mb-md-0">
					                                    <label class="font-weight-bold" for="loginId">아이디</label>
					                                    <input type="text" id="loginId" name="userId" class="form-control" >
					                                    </div>
					                                </div>
					                                <div class="row form-group">
					                                    <div class="col-md-12 mb-3 mb-md-0">
					                                    <label class="font-weight-bold" for="loginPw">비밀번호</label>
					                                    <input type="password" id="loginPw" name="userPassword" class="form-control" placeholder="영문, 숫자 포함 6자리 이상" >
					                                    </div>
					                                </div>
					                                <div class="modal-footer">
					                                    <button id="loginBtn" class="btn btn-success text-white btn-block-sm rounded-0" type="submit" >로그인</button>
					                                    <button class="btn btn-outline-primary rounded-0 py-1.5 px-2" type="button" data-toggle="modal" data-target="#findPWModal" >비밀번호 찾기</button>
					                                </div>
					                                </form>
					                            </li>
				                            </ul>
				                            <!-- Log In 드롭다운 종료 -->
				                        </li>
				                    </c:when>
			                    
				                    <c:otherwise>
				                        <!-- 로그인 이후 -->
				                        
				                        <!-- 회원 이름 -->
				                        <li class="navUserId" style="color:light-gray;">${userSession.userId}님 반가워요!</li>
				                        <!-- 회원 정보 메뉴 -->
				                        <li><a href="${root}/user/info.do?userId=${userSession.userId}" >User Info</a></li>
				                        <!-- 로그아웃 메뉴 -->
				                        <li><a href="${root}/user/logout.do" >Log Out</a></li>
				                    </c:otherwise>
			                    </c:choose>
			                </ul>
		                </nav>
		            </div>
		            <!-- 메뉴 네비게이션 바 종료 -->
	            
	            </div>
	        </div>
        </div>
        
        <!-- Sign Up Modal 시작 -->
        <div class="modal fade" id="signUpModal">
	        <div class="modal-dialog modal-md modal-dialog-centered">
	            <div class="modal-content" >
	        
	            <!-- Modal Header -->
	            <div class="modal-header">
	                <h3 class="modal-title">Happy House와 함께하기</h3>
	                <button class="close" data-dismiss="modal">&times;</button>
	            </div>
	
	            <form id="signUpForm" method="post" action="" class="p-3 bg-white border" >
	            
	            <!-- Modal body -->
	            <div class="modal-body">
	                <div class="row form-group">
		                <div class="col-md-12 mb-3 mb-md-0">
		                    <label class="font-weight-bold" for="signUpUserId">아이디</label>
		                    <input type="text" id="signUpUserId" name="userId" class="form-control" required >
		                    <div class="idresult"></div>
		                </div>
	                </div>
	                <div class="row form-group">
		                <div class="col-md-12 mb-3 mb-md-0">
		                    <label class="font-weight-bold" for="signUpUserPw">비밀번호</label>
		                    <input type="password" id="signUpUserPw" name="userPassword" class="form-control" placeholder="영문, 숫자 포함 6자리 이상 16자리 이하" required >
		                    <div id="pwresult"></div>
		                </div>
	                </div>
	                <div class="row form-group">
		                <div class="col-md-12 mb-3 mb-md-0">
		                    <label class="font-weight-bold" for="signUpUserName">이름</label>
		                    <input type="text" id="signUpUserName" name="userName" class="form-control" placeholder="김싸피" required >
		                </div>
	                </div>
	                <div class="row form-group">
		                <div class="col-md-12">
		                    <label class="font-weight-bold" for="signUpUserAddr">주소</label>
		                    <input type="text" id="signUpUserAddr" name="userAddress" class="form-control" placeholder="서울시 강남구 역삼동 멀티캠퍼스" required >
		                </div>
	                </div>
	                <div class="row form-group">
		                <div class="col-md-12">
		                    <label class="font-weight-bold" for="signUpUserTel">전화번호</label>
		                    <input type="tel" id="signUpUserTel" name="userTel" class="form-control" placeholder="010-xxxx-xxxx" required >
		                </div>
	                </div>
	            </div>
	
	            <!-- Modal footer -->
	            <div class="modal-footer">
	                <button id="signUpBtn" class="btn btn-success text-white btn-block-md rounded-0" type="button" >가입</button>
	                <button class="btn btn-outline-secondary rounded-0 py-1.5 px-2.5" data-dismiss="modal">닫기</button>
	            </div>
	
	            </form>
	
	            </div>
	        </div>
        </div>
        <!-- Sign Up Modal 종료 -->
        
        <!-- 비밀번호 찾기 Modal 시작 -->
        <div class="modal fade" id="findPWModal">
	        <div class="modal-dialog modal-md modal-dialog-centered">
	            <div class="modal-content">
	            <!-- Modal Header -->
	            <div class="modal-header">
	                <h3 class="modal-title">비밀번호 찾기</h3>
	                <button class="close" data-dismiss="modal">&times;</button>
	            </div>
	
	            <form action="${root}/user/login" method="get" class="p-3 bg-white border">
	            
	            <!-- Modal body -->
	            <div class="modal-body">
	                <div class="row form-group">
		                <div class="col-md-12 mb-3 mb-md-0">
		                    <label class="font-weight-bold" for="username">이름</label>
		                    <input type="text" id="username" class="form-control" placeholder="김싸피">
		                </div>
	                </div>
	                <div class="row form-group">
		                <div class="col-md-12 mb-3 mb-md-0">
		                    <label class="font-weight-bold" for="userid">아이디</label>
		                    <input type="text" id="userid" class="form-control">
		                </div>
	                </div>
	                <div class="row form-group">
		                <div class="col-md-12">
		                    <label class="font-weight-bold" for="usertel">전화번호</label>
		                    <input type="tel" id="usertel" class="form-control" placeholder="010-xxxx-xxxx">
		                </div>
	                </div>
	            </div>
	
	            <!-- Modal footer -->
	            <div class="modal-footer">
	                <button class="btn btn-success text-white btn-block-md rounded-0" type="submit" data-dismiss="modal">비밀번호 찾기</button>
	                <button class="btn btn-outline-secondary rounded-0 py-1.5 px-2.5" data-dismiss="modal">닫기</button>
	            </div>
	
	            </form>
	
	            </div>
	        </div>
        </div>
        <!-- 비밀번호 찾기 Modal 종료 -->