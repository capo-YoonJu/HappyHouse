<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
	<!-- 페이지 커버 부분 -->
    <div class="site-blocks-cover inner-page-cover overlay" style="background-image: url(${root}/images/hero_bg_2.jpg);" data-aos="fade" data-stellar-background-ratio="0.5">
      <div class="container">
        <div class="row align-items-center justify-content-center text-center">
          <div class="col-md-10">
            <h1 class="mb-2">회원 정보 조회</h1>
          </div>
        </div>
      </div>
    </div>
    <!-- 페이지 상단 커버 부분 종료 -->
    
    <c:choose>
      <c:when test="${empty user}">
        <script type="text/javascript">
			alert("로그인 후 이용 가능한 페이지입니다.");
			location.href = "${root}/index.jsp";
		</script>
      </c:when>
      <c:otherwise>
      	<!-- 페이지 중간 회원 정보 관리 부분 -->
      	<!-- 수정 및 삭제 버튼 이벤트 -->
	    
	    <div class="site-section">
	      <div class="container">
	        <div class="row">
	          
	          <!-- 회원 이미지 부분 -->
	          <div class="col-md-3 ml-3 mt-4 mb-5" data-aos="fade-up" data-aos-delay="100">
	            <img src="${root}/images/person_1.jpg" alt="Image" class="img-fluid">
	            <div class="mt-3 text">
	              <p>
	                <a href="#" class="text-black p-2"><span class="icon-facebook"></span></a>
	                <a href="#" class="text-black p-2"><span class="icon-twitter"></span></a>
	                <a href="#" class="text-black p-2"><span class="icon-linkedin"></span></a>
	              </p>
	            </div>
	          </div>
	          <!-- 회원 이미지 부분 종료 -->
	          
	          <!-- 회원 정보 부분 -->
	          <div class="col-md-8 ml-auto" data-aos="fade-up" data-aos-delay="200">
	            
	            <!-- 회원 이름 환영 부분 -->
	            <div class="site-section-title">
	              <h2>${user.userName}님 안녕하세요!</h2>
	            </div>
	
				<!-- 회원 정보 수정 폼 부분 -->
	            <form id="userInfoForm" method="post" action="">
	              <input type="hidden" id="curUserId" name="curUserId" value="${user.userId}">
	              <input type="hidden" id="curUserName" name="curUserName" value="${user.userName}">
	              <div class="col-md-12 mt-4">
	                <div class="row">
	                  <!-- 아이디 -->
	                  <div class="col-md mt-4">
	                    <div class="text">
	                      <h3 class="mb-2 font-weight-light text-black h4">아이디</h3>
	                      <hr align="left" style="width:20%"/>
	                      <span class="d-block mb-3 text-white-opacity-05">
	                      	<input type="text" id="userId" name="userId" value="${user.userId}" placeholder="${user.userId}" >
	                      </span>
	                    </div>
	                  </div>
	                  <!-- 비밀번호 -->
	                  <div class="col-md mt-4">
	                    <div class="text">
	                      <h3 class="mb-2 font-weight-light text-black h4">비밀 번호</h3>
	                      <hr align="left" style="width:20%"/>
	                      <span class="d-block mb-3 text-white-opacity-05">
	                        <input type="password" id="userPassword" name="userPassword" value="${user.userPassword}" placeholder="${user.userPassword}" >
	                      </span>
	                    </div>
	                  </div>
	                </div>
	                <!-- 주소 -->
	                <div class="row">
	                  <div class="col-md mt-4">
	                    <div class="text">
	                      <h3 class="mb-2 font-weight-light text-black h4">주소</h3>
	                      <hr align="left" style="width:20%"/>
	                      <span class="d-block mb-3 text-white-opacity-05">
	                      	<input type="text" id="userAddress" name="userAddress" value="${user.userAddress}" placeholder="${user.userAddress}">
	                      </span>
	                    </div>
	                  </div>
	                  <!-- 전화번호 -->
	                  <div class="col-md mt-4">
	                    <div class="text">
	                      <h3 class="mb-2 font-weight-light text-black h4">전화 번호</h3>
	                      <hr align="left" style="width:20%"/>
	                      <span class="d-block mb-3 text-white-opacity-05">
	                      	<input type="tel" id="userTel" name="userTel" value="${user.userTel}" placeholder="${user.userTel}" >
	                      </span>
	                    </div>
	                  </div>
	                </div>
	                
	                <!-- 주소 -->
	                <div class="row">
	                  <div class="col-md mt-4">
	                    <div class="text">
	                      <h3 class="mb-2 font-weight-light text-black h4">관심 지역</h3>
	                      <hr align="left" style="width:20%"/>
	                      <span class="d-block mb-3 text-white-opacity-05">
	                      	<ul>
	                      	  <c:forEach items="${favoriteList}" var="favorite">
	                      	  	<li>${favorite.gu} ${favorite.dong}</li>
	                      	  </c:forEach>
	                      	</ul>
	                      </span>
	                    </div>
	                  </div>
	                </div>
	                <!-- 회원 정보 수정 폼 버튼 -->
	                <div class="mt-4">
	                  <button class="btn btn-primary text-white btn-block-lg rounded-0 py-1.5 px-4 mr-3" type="submit" id="updateBtn" onclick="javascript: form.action='${root}/user/update.do';" >수정</button>
	                  <button class="btn btn-outline-danger rounded-0 py-1.5 px-4" type="submit" id="deleteBtn" onclick="javascript: form.action='${root}/user/delete.do';" >탈퇴</button>     
	                </div>
	              </div>
	            </form>
				<!-- 회원 정보 수정 폼 부분 종료 -->
	          </div>
	
	        </div>
	      </div>
	    </div>
		<!-- 페이지 중간 회원 정보 관리 부분 종료 -->
      </c:otherwise>
    </c:choose>

	<!-- 페이지 하단 푸터 -->
    <footer class="site-footer">
      <div class="container">
        <div class="row">
          <div class="col-lg-4">
            <div class="mb-5">
              <h3 class="footer-heading mb-4">About Happy House</h3>
              <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Saepe pariatur reprehenderit vero atque, consequatur id ratione, et non dignissimos culpa? Ut veritatis, quos illum totam quis blanditiis, minima minus odio!</p>
            </div>
          </div>
          <div class="col-lg-4 mb-5 mb-lg-0">
            <div class="row mb-5">
              <div class="col-md-12">
                <h3 class="footer-heading mb-4">Navigations</h3>
              </div>
              <div class="col-md-6 col-lg-6">
                <ul class="list-unstyled">
                  <li><a href="#">Home</a></li>
                  <li><a href="#">Buy</a></li>
                  <li><a href="#">Rent</a></li>
                  <li><a href="#">Properties</a></li>
                </ul>
              </div>
              <div class="col-md-6 col-lg-6">
                <ul class="list-unstyled">
                  <li><a href="#">About Us</a></li>
                  <li><a href="#">Privacy Policy</a></li>
                  <li><a href="#">Contact Us</a></li>
                  <li><a href="#">Terms</a></li>
                </ul>
              </div>
            </div>


          </div>

          <div class="col-lg-4 mb-5 mb-lg-0">
            <h3 class="footer-heading mb-4">Follow Us</h3>

                <div>
                  <a href="#" class="pl-0 pr-3"><span class="icon-facebook"></span></a>
                  <a href="#" class="pl-3 pr-3"><span class="icon-twitter"></span></a>
                  <a href="#" class="pl-3 pr-3"><span class="icon-instagram"></span></a>
                  <a href="#" class="pl-3 pr-3"><span class="icon-linkedin"></span></a>
                </div>

            

          </div>
          
        </div>
        <div class="row pt-5 mt-5 text-center">
          <div class="col-md-12">
            <p>
            <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
            Copyright &copy;<script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script><script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart text-danger" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank" >Colorlib</a>
            <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
            </p>
          </div>
          
        </div>
      </div>
    </footer>

  </div>

  <script src="${root}/js/jquery-3.3.1.min.js"></script>
  <script src="${root}/js/jquery-migrate-3.0.1.min.js"></script>
  <script src="${root}/js/jquery-ui.js"></script>
  <script src="${root}/js/popper.min.js"></script>
  <script src="${root}/js/bootstrap.min.js"></script>
  <script src="${root}/js/owl.carousel.min.js"></script>
  <script src="${root}/js/mediaelement-and-player.min.js"></script>
  <script src="${root}/js/jquery.stellar.min.js"></script>
  <script src="${root}/js/jquery.countdown.min.js"></script>
  <script src="${root}/js/jquery.magnific-popup.min.js"></script>
  <script src="${root}/js/bootstrap-datepicker.min.js"></script>
  <script src="${root}/js/aos.js"></script>
  <script src="${root}/js/circleaudioplayer.js"></script>

  <script src="${root}/js/main.js"></script>
    
  </body>
</html>