<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/include/header.jsp" />

        <c:if test="${errorMessage!=null}">
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

        <div class="slide-one-item home-slider owl-carousel">

            <div class="site-blocks-cover overlay"
                style="background-image: url(${root}/images/hero_bg_1.jpg);"
                data-aos="fade" data-stellar-background-ratio="0.5">
                <div class="container"></div>
            </div>

            <div class="site-blocks-cover overlay"
                style="background-image: url(${root}/images/hero_bg_2.jpg);"
                data-aos="fade" data-stellar-background-ratio="0.5">
                <div class="container">
                    <div
                        class="row align-items-center justify-content-center text-center">
                    </div>
                </div>
            </div>

        </div>

        <div class="site-section site-section-sm pb-0">
            <div class="container">
                <div class="row">

                    <%@ include file="/include/searchForm.jsp"%>

                    <!-- 지도를 표시할 div 입니다 -->
                    <div class="map_wrap">
                        <div id="map"
                            style="width: 100%; height: 100%; position: relative; overflow: hidden;"></div>
                        <ul id="category">
                            <li id="BK9" data-order="0"><span class="category_bg bank"></span>
                                은행</li>
                            <li id="MT1" data-order="1"><span class="category_bg mart"></span>
                                마트</li>
                            <li id="PM9" data-order="2"><span class="category_bg pharmacy"></span>
                                약국</li>
                            <li id="OL7" data-order="3"><span class="category_bg oil"></span>
                                주유소</li>
                            <li id="CE7" data-order="4"><span class="category_bg cafe"></span>
                                카페</li>
                            <li id="CS2" data-order="5"><span class="category_bg store"></span>
                                편의점</li>
                        </ul>
                    </div>
                    <script type="text/javascript"
                        src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c1fe7d49bf408b3eec39aecd8fcfb0e2&libraries=services,clusterer,drawing"></script>

                    <script charset="UTF-8">
                        var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
                        mapOption = { 
                            center: new kakao.maps.LatLng(37.56681520, 126.9787216), // 지도의 중심좌표
                            level: 5 // 지도의 확대 레벨
                            };
                        // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
                        var map = new kakao.maps.Map(mapContainer, mapOption);
                        
                        var house = new Array();
                        function searchDeal() {
                            var httpRequest = new XMLHttpRequest();
                            
                            var url = "${root}/house/searchData.do?";
                            var params = "sido=" + document.getElementById("sido").value;
                            params += "&gugun=" + document.getElementById("gugun").value;
                            params += "&dong=" + document.getElementById("dong").value;
                            params += "&apt=" + document.getElementById("apt").value;
                            
                                $.ajax({
                                    type : "GET", 
                                    url : url+params,
                                }).done(function(data){
                                    house = new Array();
                                    for(var i = 0; i < data.length; i++){
                                        var obj = {
                                            content : '<div>'+data[i].aptName+'</div>', 
                                            latlng : new kakao.maps.LatLng(data[i].lat, data[i].lng),
                                            sido : data[i].sido,
                                            gugun : data[i].gugun,
                                            dong : data[i].dong,
                                            aptName : data[i].aptName,
                                            dealAmount : data[i].dealAmount,
                                            area : data[i].area,
                                            type : data[i].type
                                            };
                                        house.push(obj);
                                    }
                                    
                                    // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
                                    var placeOverlay = new kakao.maps.CustomOverlay({zIndex:1}), 
                                        contentNode = document.createElement('div'), // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다 
                                        markers = [], // 마커를 담을 배열입니다
                                        currCategory = ''; // 현재 선택된 카테고리를 가지고 있을 변수입니다
                                    
                                    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
                                    mapOption = { 
                                        center: new kakao.maps.LatLng(37.56681520, 126.9787216), // 지도의 중심좌표
                                        level: 5 // 지도의 확대 레벨
                                    };
                                        
                                    // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
                                    var map = new kakao.maps.Map(mapContainer, mapOption);
                                    
                                    if(house.length > 0) map.panTo(house[0].latlng);
                                    
                                    var ps = new kakao.maps.services.Places(map); 

                                        // 지도에 idle 이벤트를 등록합니다
                                        kakao.maps.event.addListener(map, 'idle', searchPlaces);
                
                                        // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다 
                                        contentNode.className = 'placeinfo_wrap';
                
                                        // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
                                        // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다 
                                        addEventHandle(contentNode, 'mousedown', kakao.maps.event.preventMap);
                                        addEventHandle(contentNode, 'touchstart', kakao.maps.event.preventMap);
                
                                        // 커스텀 오버레이 컨텐츠를 설정합니다
                                        placeOverlay.setContent(contentNode);  
                
                                        // 각 카테고리에 클릭 이벤트를 등록합니다
                                        addCategoryClickEvent();
                
                                        // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
                                        function addEventHandle(target, type, callback) {
                                            if (target.addEventListener) {
                                                target.addEventListener(type, callback);
                                            } else {
                                                target.attachEvent('on' + type, callback);
                                            }
                                        }
                
                                        // 카테고리 검색을 요청하는 함수입니다
                                        function searchPlaces() {
                                            if (!currCategory) {
                                                return;
                                            }
                                            
                                            // 커스텀 오버레이를 숨깁니다 
                                            placeOverlay.setMap(null);
                
                                            // 지도에 표시되고 있는 마커를 제거합니다
                                            removeMarker();
                                            
                                            ps.categorySearch(currCategory, placesSearchCB, {useMapBounds:true}); 
                                        }
                
                                        // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
                                        function placesSearchCB(data, status, pagination) {
                                            if (status === kakao.maps.services.Status.OK) {
                
                                                // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
                                                displayPlaces(data);
                                            } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
                                                // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
                
                                            } else if (status === kakao.maps.services.Status.ERROR) {
                                                // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
                                                
                                            }
                                        }
                
                                        // 지도에 마커를 표출하는 함수입니다
                                        function displayPlaces(places) {
                                            // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
                                            // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
                                            var order = document.getElementById(currCategory).getAttribute('data-order');
                
                                            for ( var i=0; i<places.length; i++ ) {
                
                                                    // 마커를 생성하고 지도에 표시합니다
                                                    var marker = addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);
                
                                                    // 마커와 검색결과 항목을 클릭 했을 때
                                                    // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
                                                    (function(marker, place) {
                                                        kakao.maps.event.addListener(marker, 'click', function() {
                                                            displayPlaceInfo(place);
                                                        });
                                                    })(marker, places[i]);
                                            }
                                        }
                
                                        // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
                                        function addMarker(position, order) {
                                            var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
                                                imageSize = new kakao.maps.Size(27, 28),  // 마커 이미지의 크기
                                                imgOptions =  {
                                                    spriteSize : new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
                                                    spriteOrigin : new kakao.maps.Point(46, (order*36)), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
                                                    offset: new kakao.maps.Point(11, 28) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
                                                },
                                                markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
                                                    marker = new kakao.maps.Marker({
                                                    position: position, // 마커의 위치
                                                    image: markerImage 
                                                });
                
                                            marker.setMap(map); // 지도 위에 마커를 표출합니다
                                            markers.push(marker);  // 배열에 생성된 마커를 추가합니다
                
                                            return marker;
                                        }
                
                                        // 지도 위에 표시되고 있는 마커를 모두 제거합니다
                                        function removeMarker() {
                                            for ( var i = 0; i < markers.length; i++ ) {
                                                markers[i].setMap(null);
                                            }   
                                            markers = [];
                                        }
                
                                        // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
                                        function displayPlaceInfo (place) {
                                            var content = '<div class="placeinfo">' +
                                                            '   <a class="title" href="' + place.place_url + '" target="_blank" title="' + place.place_name + '">' + place.place_name + '</a>';   
                
                                            if (place.road_address_name) {
                                                content += '    <span title="' + place.road_address_name + '">' + place.road_address_name + '</span>' +
                                                            '  <span class="jibun" title="' + place.address_name + '">(지번 : ' + place.address_name + ')</span>';
                                            }  else {
                                                content += '    <span title="' + place.address_name + '">' + place.address_name + '</span>';
                                            }                
                                        
                                            content += '    <span class="tel">' + place.phone + '</span>' + 
                                                        '</div>' + 
                                                        '<div class="after"></div>';
                
                                            contentNode.innerHTML = content;
                                            placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
                                            placeOverlay.setMap(map);  
                                        }
                
                
                                        // 각 카테고리에 클릭 이벤트를 등록합니다
                                        function addCategoryClickEvent() {
                                            var category = document.getElementById('category'),
                                                children = category.children;
                
                                            for (var i=0; i<children.length; i++) {
                                                children[i].onclick = onClickCategory;
                                            }
                                        }
                
                                        // 카테고리를 클릭했을 때 호출되는 함수입니다
                                        function onClickCategory() {
                                            var id = this.id,
                                                className = this.className;
                
                                            placeOverlay.setMap(null);
                
                                            if (className === 'on') {
                                                currCategory = '';
                                                changeCategoryClass();
                                                removeMarker();
                                            } else {
                                                currCategory = id;
                                                changeCategoryClass(this);
                                                searchPlaces();
                                            }
                                        }
                
                                        // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
                                        function changeCategoryClass(el) {
                                            var category = document.getElementById('category'),
                                                children = category.children,
                                                i;
                
                                            for ( i=0; i<children.length; i++ ) {
                                                children[i].className = '';
                                            }
                
                                            if (el) {
                                                el.className = 'on';
                                            } 
                                        }  
                                    
                                    var zoomControl = new kakao.maps.ZoomControl();
                                    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
                                    
                                    
                                    for (var i = 0; i < house.length; i ++) {
                                        // 마커를 생성합니다
                                        var marker = new kakao.maps.Marker({
                                            map: map, // 마커를 표시할 지도
                                            position: house[i].latlng // 마커의 위치
                                        });
                                        
                                        // 마커에 표시할 인포윈도우를 생성합니다 
                                        var infowindow = new kakao.maps.InfoWindow({
                                            content: house[i].content // 인포윈도우에 표시할 내용
                                        });
                                        
                                        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
                                        // 이벤트 리스너로는 클로저를 만들어 등록합니다 
                                        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
                                        kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
                                        kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
                                    }
                                    
                                    makeTable();
                                });
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
                    
                        // 지도 중심을 부드럽게 이동시킵니다
                        // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
                        function panTo() {
                            // 이동할 위도 경도 위치를 생성합니다 
                            var moveLatLon = house[0].latlng;
                            map.panTo(moveLatLon);            
                        } 
                        
                        // 검색 결과 테이블을 생성하는 함수입니다
                        function makeTable() {
                            var tag = '<table width="1100" height="50" align="center">';
                            tag += '<tr align="center">';
                            tag += '<th>특별시/도</th>';
                            tag += '<th>시/구/군</th>';
                            tag += '<th>동</th>';
                            tag += '<th>아파트 명</th>';
                            tag += '<th>거래 가격</th>';
                            tag += '<th>전용 면적</th>';
                            tag += '<th>거래 구분</th>';
                            tag += '</tr>';
                            for (let i = 0; i < house.length; i++) {
                                tag += '<tr align="center">';
                                tag += '<td>' + house[i].sido + '</td>';
                                tag += '<td>' + house[i].gugun + '</td>';
                                tag += '<td>' + house[i].dong + '</td>';
                                tag += '<td>' + house[i].aptName + '</td>';
                                tag += '<td>' + house[i].dealAmount + '만원' + '</td>';
                                tag += '<td>' + house[i].area + '</td>';
                                tag += '<td>';
                                if(house[i].type == '1') tag += '아파트 매매';
                                tag += '</td>';
                                tag += '</tr>';
                            }
                            tag += '</table>';
                            dealTable.innerHTML = tag;
                        }
                    </script>

                    <div id="dealTable" class="col-md-12" align="center" style="margin-bottom: 50px">
	                    <div class="row">
	                        <div class="col-md-12">
	                            <div class="view-options bg-white py-3 px-3 d-md-flex align-items-center">
	                                <div class="ml-auto d-flex align-items-center">
	                                    <div>
	                                        <a href="#" class="view-list px-3 border-right active">전체</a> <a
	                                            href="#" class="view-list px-3 border-right">매매</a> <a href="#"
	                                            class="view-list px-3">전/월세</a>
	                                    </div>
	                                    <div class="select-wrap">
	                                        <span class="icon icon-arrow_drop_down"></span> <select
	                                            class="form-control form-control-sm d-block rounded-0">
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
        	</div>
        </div>

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
    <script src="${root}/js/circleaudioplayer.js"></script>
    <script src="${root}/js/aos.js"></script>
    <script src="${root}/js/main.js"></script>

    </body>
</html>