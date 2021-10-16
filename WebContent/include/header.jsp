Í<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Happy House &mdash; SSAFY 6기 관통 프로젝트</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

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
    
    <script type="text/javascript">
	function check() {
		if (!choice.sido.value || !choice.gugun.value || !choice.dong.value)
			alert("검색할 지역(시/구/동)을 모두 선택해주세요");
		else choice.submit();
	}
	</script>
	
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
	              <c:when test="${empty user}">
	              	<!-- 로그인 이전 -->
	              	
	              	<!-- 회원가입 메뉴 -->
	              	<li><a href="#signUpModal" data-toggle="modal" data-target="#signUpModal" >Sign Up</a></li>
	                
	                <!-- 로그인 메뉴 -->
	                <li>
	                  <a href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >Log In</a>
	                    <!-- Log In 드롭다운 시작 -->
					    <ul class="dropdown-menu dropdown-menu-right dropdown-animation" aria-labelledby="header-top-drop-2">
					      <li>
					        <form method="post" action="${root}/user/login.do" class="col-md-12 mb-3 mb-md-0" >
					          <div class="row form-group">
					            <div class="col-md-12 mb-3 mb-md-0">
					              <label class="font-weight-bold" for="userId">아이디</label>
					              <input type="text" id="userId" name="userId" class="form-control" required >
					            </div>
					          </div>
					          <div class="row form-group">
					            <div class="col-md-12 mb-3 mb-md-0">
					              <label class="font-weight-bold" for="userPassword">비밀번호</label>
					              <input type="password" id="userPassword" name="userPassword" class="form-control" placeholder="영문, 숫자 포함 6자리 이상" required >
					            </div>
					          </div>
					          <div class="modal-footer">
					            <button class="btn btn-success text-white btn-block-sm rounded-0" type="submit" >로그인</button>
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
			      	<li style="color:light-gray;">${user.userId}님 반가워요!</li>
			      	<!-- 회원 정보 메뉴 -->
                  	<li>
                  	  <a href="${root}/user/info.do?userId=${user.userId}" >User Info</a>
                  	</li>
                  	<!-- 로그아웃 메뉴 -->
                  	<li>
                  	  <a href="${root}/user/logout.do" >Log Out</a>
                  	</li>
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

          <form method="post" action="${root}/user/register.do" class="p-3 bg-white border" >
          
          <!-- Modal body -->
          <div class="modal-body">
            <div class="row form-group">
              <div class="col-md-12 mb-3 mb-md-0">
                <label class="font-weight-bold" for="userId">아이디</label>
                <input type="text" id="userId" name="userId" class="form-control" required >
             </div>
            </div>
            <div class="row form-group">
             <div class="col-md-12 mb-3 mb-md-0">
                <label class="font-weight-bold" for="userPassword">비밀번호</label>
                <input type="password" id="userPassword" name="userPassword" class="form-control" placeholder="영문, 숫자 포함 6자리 이상" required >
              </div>
            </div>
            <div class="row form-group">
              <div class="col-md-12 mb-3 mb-md-0">
                <label class="font-weight-bold" for="userName">이름</label>
                <input type="text" id="userName" name="userName" class="form-control" placeholder="김싸피" required >
              </div>
            </div>
            <div class="row form-group">
              <div class="col-md-12">
                <label class="font-weight-bold" for="userAddress">주소</label>
                <input type="text" id="userAddress" name="userAddress" class="form-control" placeholder="서울시 강남구 역삼동 멀티캠퍼스" required >
              </div>
            </div>
            <div class="row form-group">
              <div class="col-md-12">
                <label class="font-weight-bold" for="userTel">전화번호</label>
                <input type="tel" id="userTel" name="userTel" class="form-control" placeholder="010-xxxx-xxxx" required >
              </div>
            </div>
          </div>

          <!-- Modal footer -->
          <div class="modal-footer">
            <button class="btn btn-success text-white btn-block-md rounded-0" type="submit" >가입</button>
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
