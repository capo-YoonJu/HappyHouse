<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/include/header.jsp" />
    
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
            <c:when test="${empty userSession}">
                <!-- 로그인 세션 종료 시 경고창 처리 -->
                <script type="text/javascript">
                    alert("로그인 후 이용 가능한 페이지입니다.");
                    location.href = "${root}/index.jsp";
                </script>
            </c:when>
            <c:otherwise>
                <!-- 로그인 세션 유지 중 -->
                <c:if test="${errorMessage!=null}">
                    <!-- 이외 에러 케이스 경고창 처리 -->
                    <script type="text/javascript">
                        alert("${errorMessage}");
                        location.href = "${root}/index.jsp";
                    </script>
                </c:if>
                
                <c:if test="${successMessage!=null}">
                    <!-- 이외 에러 케이스 경고창 처리 -->
                    <script type="text/javascript">
                        alert("${successMessage}");
                    </script>
                </c:if>
                
                <!-- 페이지 중간 회원 정보 관리 부분 -->
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
                        
                        <!-- 회원 정보 수정, 관심지역 해제 이벤트 처리 js -->
                        <script type="text/javascript">
                            $(document).ready(function () {
                            	var isId = true;
                                var isPw = true;
                                
                                /* 아이디 중복검사 */
                                $("#userIdInfo").keyup(function () {
                                    var ckid = $("#userIdInfo").val();
                                    if(ckid.length < 4 || ckid.length > 16) {
                                        $(".idresult").text("아이디는 4자이상 16자이하입니다.").removeClass('text-primary').removeClass('text-danger').addClass('text-gray');
                                        isId = false;
                                    } else {
                                        if (ckid.includes(" ")){
                                            $(".idresult").text("아이디는 공백을 포함할 수 없습니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                                            isId = false;
                                        } else {
                                        	if (ckid == '${user.userId}') {
                                        		$(".idresult").text("").removeClass('text-danger').removeClass('text-gray').addClass('text-primary');
                                                isId = true;
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
                                    }
                                });
                                
                                /* 비밀번호 유효성 검사 */
                                $("#userPasswordInfo").keyup(function () {
                                    var ckpw = $("#userPasswordInfo").val();
                                    
                                    if(ckpw.length < 6 || ckpw.length > 16) {
                                        $(".pwresult").text("비밀번호는 6자리 이상 16자리 이하입니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
                                        isPw = false;
                                    } else {
                                    	if (ckpw == '${user.userPassword}') {
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
                                                    $(".pwresult").text("비밀번호는 영문, 숫자 포함 6자리 이상입니다.").removeClass('text-primary').removeClass('text-gray').addClass('text-danger');
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
                                	if (!$("#userIdInfo").val()) {
                                        alert("아이디를 입력해주세요.");
                                        return;
                                    } else if (!isId) {
                                        alert("사용할 수 없는 아이디입니다.");
                                        return;
                                    } else if (!$("#userPasswordInfo").val()) {
                                        alert("비밀번호를 입력해주세요.");
                                        return;
                                    } else if (!isPw) {
                                        alert("사용할 수 없는 비밀번호입니다.");
                                        return;
                                    } else {
                                    	var curUserId = "${user.userId}";
                                        var userId = $('#userIdInfo').val();
                                        var userPassword = $('#userPasswordInfo').val();
                                        var curUserName = "${user.userName}";
                                        var userAddress = $('#userAddress').val();
                                        var userTel = $('#userTel').val();
                                        
                                        $('#userInfoForm').attr('action', '${root}/user/update.do');
                                    }
                                });
                                
                                /* 관심 지역 삭제 */
                                $(".deleteBtn").click(function() {
                                    var clickedId = $(this).attr("id");
                                    var len = clickedId.length;
                                    var idx = clickedId.substr(7, len+1);
                                    var listId = "#favli-" + idx;
                                    var favInfo = $(listId).children('#favInfo').text().split(' ');
                                    
                                    $.ajax({
                                        url: '${root}/favorite/delete.do',
                                        data: {'favoriteGugun': favInfo[0], 'favoriteDong':favInfo[1]},
                                        type: 'POST',
                                        dataType: 'json',
                                        success: function (response) {
                                        	if (response == 'success') {
                                        		$(listId).remove();
                                            	alert("관심 지역이 삭제되었습니다.");
                                        	} else {
                                        		alert("관심 지역 삭제에 실패했습니다.");
                                        	}
                                        }
                                    });

                                });
                                
                                /* 회원 탈퇴 */
                                $("#userDeleteBtn").click(function() {
                                    if (confirm("진심으로 탈퇴하시겠습니까....? 믿을 수 없어요...")) {
                                    	$('#userInfoForm').attr('action', '${root}/user/delete.do');
                                    } else {
                                    	return;
                                    }
                                });
                            });
                        </script>
                        
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
                                        <input type="text" id="userIdInfo" name="userId" value="${user.userId}" placeholder="${user.userId}" >
                                    </span>
                                    <div class="idresult"></div>
                                    </div>
                                </div>
                                
                                <!-- 비밀번호 -->
                                <div class="col-md mt-4">
                                    <div class="text">
                                    <h3 class="mb-2 font-weight-light text-black h4">비밀 번호</h3>
                                    <hr align="left" style="width:20%"/>
                                    <span class="d-block mb-3 text-white-opacity-05">
                                        <input type="password" id="userPasswordInfo" name="userPassword" value="${user.userPassword}" placeholder="${user.userPassword}" >
                                    </span>
                                    <div class="pwresult"></div>
                                    </div>
                                </div>
                                </div>
                                
                                <div class="row">
                                <!-- 주소 -->
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
                                
                                <!-- 관심 지역 -->
                                <div class="row">
                                <div class="col-md mt-4">
                                    <div class="text">
                                    <h3 class="mb-2 font-weight-light text-black h4">관심 지역</h3>
                                    <hr align="left" style="width:20%"/>
                                    <span class="d-block mb-3 text-white-opacity-05">
                                        <!-- 관심 지역 리스트 -->
                                        <ul>
                                        <c:forEach items="${user.userFavList}" var="favorite" varStatus="status">
                                            <li id="favli-${status.index}">
                                                <span id="favInfo">${favorite.gu} ${favorite.dong}</span>
                                                <button id="favbtn-${status.index}" class="btn close deleteBtn" type="button" >&times;</button>
                                            </li>
                                        </c:forEach>
                                        </ul>
                                    </span>
                                    </div>
                                </div>
                                </div>
                                
                                <!-- 회원 정보 수정 폼 버튼 -->
                                <div class="mt-4">
                                <button class="btn btn-primary text-white btn-block-lg rounded-0 py-1.5 px-4 mr-3" type="submit" id="userUpdateBtn" >수정</button>
                                <button class="btn btn-outline-danger rounded-0 py-1.5 px-4" type="submit" id="userDeleteBtn" >탈퇴</button>     
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

        <footer class="site-footer">
            <div class="container">
                <div class="row">
                    <div class="col-lg-4">
                        <div class="mb-5">
                            <h3 class="footer-heading mb-4">About HAPPY HOUSE</h3>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                                Saepe pariatur reprehenderit vero atque, consequatur id ratione,
                                et non dignissimos culpa? Ut veritatis, quos illum totam quis
                                blanditiis, minima minus odio!</p>
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
                        <p></p>
                    </div>
                </div>
            </div>
        </footer>
    </div>

    <script src="${root}/js/owl.carousel.min.js"></script>
    <script src="${root}/js/mediaelement-and-player.min.js"></script>
    <script src="${root}/js/jquery.stellar.min.js"></script>
    <script src="${root}/js/jquery.countdown.min.js"></script>
    <script src="${root}/js/jquery.magnific-popup.min.js"></script>
    <script src="${root}/js/bootstrap-datepicker.min.js"></script>
    <script src="${root}/js/aos.js"></script>
    <script src="${root}/js/main.js"></script>

    </body>
</html>