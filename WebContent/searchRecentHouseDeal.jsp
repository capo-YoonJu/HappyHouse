<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty loginErrorMsgs}">
	<script type="text/javascript">
		alert("${loginErrorMsgs[0]}");
		location.href = "${root}/index.jsp";
	</script>
</c:if>
   
    <div class="slide-one-item home-slider owl-carousel">

      <div class="site-blocks-cover overlay" style="background-image: url(${root}/images/hero_bg_1.jpg);" data-aos="fade" data-stellar-background-ratio="0.5">
        <div class="container">
        </div>
      </div>  

      <div class="site-blocks-cover overlay" style="background-image: url(${root}/images/hero_bg_2.jpg);" data-aos="fade" data-stellar-background-ratio="0.5">
        <div class="container">
          <div class="row align-items-center justify-content-center text-center">
          </div>
        </div>
      </div>  

    </div>
    
    <div class="site-section site-section-sm pb-0">
      <div class="container">
        <div class="row">
        
        <%@ include file="/include/searchForm.jsp" %>
        
        </div>  
        <!-- 지도를 표시할 div 입니다 -->
        <div id="map" style="width:1100px;height:500px;  margin-top: 50px; margin-bottom: 50px;"></div>
		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c1fe7d49bf408b3eec39aecd8fcfb0e2&libraries=services,clusterer,drawing"></script>
		<script>
			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	            mapOption = { 
	                center: new kakao.maps.LatLng(37.5743822, 126.9666618), // 지도의 중심좌표
	                level: 8 // 지도의 확대 레벨
	            };
	  
	        // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
	        var map = new kakao.maps.Map(mapContainer, mapOption);
	
	        var zoomControl = new kakao.maps.ZoomControl();
	        map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
	        
	        // 마커를 표시할 위치와 내용을 가지고 있는 객체 배열입니다 
	        var positions = new Array();
	        </script>
	        <c:forEach items="${search}" var="house">
		        <script>
		        	var obj = {content : '<div>${house.aptName}</div>', latlng : new kakao.maps.LatLng(${house.lat}, ${house.lng})};
		        	positions.push(obj);
		        </script>
			</c:forEach>
			
			<script>
			for (var i = 0; i < positions.length; i ++) {
				// 마커를 생성합니다
				var marker = new kakao.maps.Marker({
				    map: map, // 마커를 표시할 지도
				    position: positions[i].latlng // 마커의 위치
				});
				
				// 마커에 표시할 인포윈도우를 생성합니다 
				var infowindow = new kakao.maps.InfoWindow({
				    content: positions[i].content // 인포윈도우에 표시할 내용
				});
				
				// 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
				// 이벤트 리스너로는 클로저를 만들어 등록합니다 
				// for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
				kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
				kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
			}
			
			// 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
			function makeOverListener(map, marker, infowindow) {
				return function() {
				    infowindow.open(map, marker);
				};
			}
			
			// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
			function makeOutListener(infowindow) {
				return function() {
				    infowindow.close();
				};
			}
	
	    </script>
	    
	    <div align = "center">
	    	<table width ="1100" height="50" align = "center">
	    	<tr align ="center">
	    		<th>동</th>
	    		<th>아파트 명</th>
	    		<th>거래 가격</th>
	    		<th>전용 면적</th>
	    		<th>거래 구분</th>
	    	</tr>
	    	<c:forEach items="${search}" var="deal">
	    		<tr align ="center">
	    		<td>${deal.dong}</td>
	    		<td>${deal.aptName}</td>
	    		<td>${deal.dealAmount} 만원</td>
	    		<td>${deal.area}</td>
	    		<c:if test="${deal.type eq '1'}"><td>아파트 매매<td></c:if>
	    	</tr>
			</c:forEach>
	    	</table>
	    </div>
	    
        <div class="row">
          <div class="col-md-12">
            <div class="view-options bg-white py-3 px-3 d-md-flex align-items-center">
              <div class="ml-auto d-flex align-items-center">
                <div>
                  <a href="#" class="view-list px-3 border-right active">전체</a>
                  <a href="#" class="view-list px-3 border-right">매매</a>
                  <a href="#" class="view-list px-3">전/월세</a>
                </div>

                <div class="select-wrap">
                  <span class="icon icon-arrow_drop_down"></span>
                  <select class="form-control form-control-sm d-block rounded-0">
                    <option value="">Sort by</option>
                    <option value="">Price Ascending</option>
                    <option value="">Price Descending</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
       
      </div>
    </div>

    <footer class="site-footer">
      <div class="container">
        <div class="row">
          <div class="col-lg-4">
            <div class="mb-5">
              <h3 class="footer-heading mb-4">About HAPPY HOUSE</h3>
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
            </p>
          </div>
          
        </div>
      </div>
    </footer>

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

  <script src="${root}/js/main.js"></script>
    
  </body>
</html>