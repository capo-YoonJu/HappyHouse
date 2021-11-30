/**
 * Template Name: MyBiz - v2.2.1 Template URL:
 * https://bootstrapmade.com/mybiz-free-business-bootstrap-theme/ Author:
 * BootstrapMade.com License: https://bootstrapmade.com/license/
 */
  
 !(function($) {
	"use strict";
  
	// Smooth scroll for the navigation menu and links with .scrollto classes
	var scrolltoOffset = $('#header').outerHeight() - 1;
	$(document).on('click', '.nav-menu a, .mobile-nav a, .scrollto', function(e) {
	  if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
		var target = $(this.hash);
		if (target.length) {
		  e.preventDefault();
  
		  var scrollto = target.offset().top - scrolltoOffset;
  
		  if ($(this).attr("href") == '#header') {
			scrollto = 0;
		  }
  
		  $('html, body').animate({
			scrollTop: scrollto
		  }, 1500, 'easeInOutExpo');
  
		  if ($(this).parents('.nav-menu, .mobile-nav').length) {
			$('.nav-menu .active, .mobile-nav .active').removeClass('active');
			$(this).closest('li').addClass('active');
		  }
  
		  if ($('body').hasClass('mobile-nav-active')) {
			$('body').removeClass('mobile-nav-active');
			$('.mobile-nav-toggle i').toggleClass('icofont-navigation-menu icofont-close');
			$('.mobile-nav-overly').fadeOut();
		  }
		  return false;
		}
	  }
	});
	
	// /////////////// 검색 버튼 클릭 이벤트
	$('#searchBtn').click(function(){
		var aptName = $('#aptName').val();
		var dongCode = $('#dong').val();
		var gucode = $('#gu').val();
		
		// 지도에 표시되고 있는 마커를 제거합니다
		removeMarker();
		jsonArray = [];
		$("#aptList").empty();
		
		if(dongCode != 'all'){
			selectAptList('202110', gucode, dongCode, aptName);
		  // selectAptList('202109', gucode, dongCode, aptName);
		  // selectAptList('202108', gucode, dongCode, aptName);
		  // selectAptList('202107', gucode, dongCode, aptName);
		  // selectAptList('202106', gucode, dongCode, aptName);
		}
	})
	
	function selectAptList(date, gugunCode, dongCode, name){
		var param = {
				ServiceKey:'ENTER YOUR SERVICE KEY HERE',
				pageNo:encodeURIComponent('1'),
				numOfRows:encodeURIComponent('200'),
				LAWD_CD:encodeURIComponent(gugunCode),
				DEAL_YMD:encodeURIComponent(date)
		};
		$.ajax({
			type : 'GET',
			data: param,
			url: 'http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev', 
			dataType: 'xml', 
			async: false, 
			success: function(data) {
				var items = $(data).find('item');
				var total = 0;
				
				items.each(function() {
					var jsonObj = new Object();
					jsonObj.dongCode = $(this).find('지역코드').text() + $(this).find('법정동읍면동코드').text();
					jsonObj.aptName = $(this).find('아파트').text();
					jsonObj.dongName = $(this).find('법정동').text();
					
					if(jsonObj.dongCode == dongCode && jsonObj.aptName.includes(name)){
						jsonObj.buildYear = $(this).find('건축년도').text();
						jsonObj.recentPrice = $(this).find('거래금액').text().trim();
						jsonObj.roadName = $(this).find('도로명').text();
						jsonObj.roadNumber = String(Number($(this).find('도로명건물본번호코드').text()));
						jsonObj.roadSubNumber = String(Number($(this).find('도로명건물부번호코드').text()));
						jsonObj.aptCode = $(this).find('일련번호').text();
  //					  jsonObj.area = $(this).find('전용면적').text();
						console.log(Number($(this).find('전용면적').text()));
						jsonObj.area = String((Number($(this).find('전용면적').text())/3.305785).toFixed(2));
						
						$.ajax({
							type : 'GET',
							data: {dongcode: $("#dong").val()},
							url: "/house/address", 
							dataType: 'json', 
							async: false, 
							success: function(data) {
								// 아파트 리스트에 추가하기 
								jsonObj.address = data.message+" "+jsonObj.roadName+" "+jsonObj.roadNumber;
								if(jsonObj.roadSubNumber != 0){
									jsonObj.address += "-"+jsonObj.roadSubNumber;
								}
								
								jsonObj = JSON.stringify(jsonObj);
								//String 형태로 파싱한 객체를 다시 json으로 변환
								jsonArray.push(JSON.parse(jsonObj));
								total++;
								}
							});
						}
					})
					
					var count = 0;
				  jsonArray.forEach(function(obj, index) {
					  geocoder.addressSearch(obj.address, function(result, status) {
						  if (status === daum.maps.services.Status.OK) {
							  obj.lat = result[0].y;
							  obj.lng = result[0].x;
							  
							  count++;
							  if(count == total){
								  // 아파트 리스트에 해당하는 마커 띄우기
								  displayMarkers(jsonArray);
							  }
						  } 
					  });
				  });
			  }
		});
	}
	
	// /////////////////////////////시, 도 선택 이벤트 처리
	$('#city').change(function () {
		var guContents = $('#gu');
		guContents.empty();
		$('<option value="all">구/군</option>').appendTo(guContents)
		
		var dongContents = $('#dong');
		dongContents.empty();
		$('<option value="all">동</option>').appendTo(dongContents)
		
		if(city != 'all'){
			$.get(root + "/house/gugun"
					,{sido: $("#city").val()}
			,function(data, status){
				$.each(data, function(index, vo) {
					$("#gu").append("<option value='"+vo.gugunCode+"'>"+vo.gugunName+"</option>");
				});
			}
			, "json"
			);
		}
	})
	
	// /////////////////////////////구, 군 선택 이벤트 처리
	$('#gu').change(function () {
		var dongContents = $('#dong');
		dongContents.empty();
		$('<option value="all">동</option>').appendTo(dongContents)
		
		if(city != 'all' && gu != 'all'){
			$.get(root + "/house/dong"
					,{gugun: $("#gu").val()}
			,function(data, status){
				$.each(data, function(index, vo) {
					$("#dong").append("<option value='"+vo.dongCode+"'>"+vo.dongName+"</option>");
				});
			}
			, "json"
			);
		}
		
	})
	
	/////////// 지도 생성
	// 마커를 담을 배열입니다
	var markers = [];
	var jsonArray = [];
	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
	mapOption = { 
		center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		level: 3 // 지도의 확대 레벨
	};
  
	// 지도를 표시할 div와 지도 옵션으로 지도를 생성합니다
	var map = new kakao.maps.Map(mapContainer, mapOption); 
	var ps = new kakao.maps.services.Places();
  
	// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
	var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
	
	// 커스텀 오버레이를 생성합니다
	var customOverlay;
	
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	//검색 결과 목록과 마커를 표출하는 함수입니다
	function displayMarkers(places) {
		var fragment = document.getElementById('aptList');
	  var bounds = new kakao.maps.LatLngBounds();
	  var listStr = "";
		
		// 지도에 표시되고 있는 마커를 제거합니다
		removeMarker();
		
		
		for(var i=0; i<places.length; i++) {
			var placePosition = new kakao.maps.LatLng(places[i].lat, places[i].lng);
			var marker = addMarker(placePosition, i);
			var itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
			
			// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			// LatLngBounds 객체에 좌표를 추가합니다
			bounds.extend(placePosition);
			
			// 마커와 검색결과 항목에 mouseover 했을때
			// 해당 장소에 인포윈도우에 장소명을 표시합니다
			// mouseout 했을 때는 인포윈도우를 닫습니다
			(function (marker, title, code, place) {
				itemEl.onmouseover = function () {
					displayInfowindow(marker, title, place);
				};
  
				itemEl.onmouseout = function () {
					customOverlay.setMap(null);
				};
			})(marker, places[i].aptName, places[i].aptCode, places[i]);
  
			fragment.append(itemEl);
		}
		// 마커를 생성하고 지도에 표시합니다 
  
		// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
		map.setBounds(bounds);
	}
	  
	  // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
	  function addMarker(position, idx) {
  //		var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png",
  //			imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
  //			imgOptions = {
  //				spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
  //				spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
  //				offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
  //			},
  //			markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
  //			marker = new kakao.maps.Marker({
  //				position: position, // 마커의 위치
  //				image: markerImage,
  //		});
		  
		  var marker = new kakao.maps.Marker({
			  position: position
		  });
	  
		  marker.setMap(map); // 지도 위에 마커를 표출합니다
		  markers.push(marker); // 배열에 생성된 마커를 추가합니다
		  return marker;
	  }
	  
	  // 지도 위에 표시되고 있는 마커를 모두 제거합니다
	  function removeMarker() {
		  for (var i = 0; i < markers.length; i++) {
			  markers[i].setMap(null);
		  }
		  markers = [];
	  }
	  
	  //검색결과 항목을 Element로 반환하는 함수입니다
	  function getListItem(index, place) {
		  var el = document.createElement("div");
		  var itemStr = `
			  <i class="bx bx-building-house"/>
			  <div class="aptTitle" id="aptName">${place.aptName}</div>
			  <div class="aptContent" id="buildYear"> 건축년도 : ${place.buildYear}</div>
			  <div class="aptContent" id="recentPrice"> 거래금액 : ${place.recentPrice}</div>
			  <div class="aptContent" id="area"> 평수(전용면적) : ${place.area}</div>
			  <div class="aptContent" id="aptAddress"> 주소 : ${place.address}</div>
		  `;
		  el.innerHTML = itemStr;
		  el.className = "icon-box mt-5 mt-lg-3";
  
		  return el;
	  }
	  
	  //검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
	  //인포윈도우에 장소명을 표시합니다
	  function displayInfowindow(marker, title, place) {
		  var content = `
			  <div class="overlaybox">
				  <div class="boxtitle">${title}</div>
				  <ul>
					  <li class="up">
						  <span class="title">건축년도</span>
						  <span class="count">${place.buildYear}</span>
					  </li>
					  <li>
						  <span class="title">최신거래금액</span>
						  <span class="count">${place.recentPrice}</span>
					  </li>
					  <li>
						  <span class="title">주소</span>
						  <span class="count">${place.address}</span>
					  </li>
				  </ul>
			  </div>
		  `;
		  var position = new kakao.maps.LatLng(marker.getPosition().getLat()+0.00033, marker.getPosition().getLng()-0.00003);
		  customOverlay = new kakao.maps.CustomOverlay({
			  position: position,
			  content: content,
			  xAnchor: 0.3,
			  yAnchor: 0.91,
		  });
		  customOverlay.setMap(map);
	  }
	  
	let root = sessionStorage.getItem("contextpath");
  
	// Activate smooth scroll on page load with hash links in the url
	$(document).ready(function () {
		  // 로그인 여부 확인
		  let userId = sessionStorage.getItem("userId");
		  sessionStorage.setItem("contextpath", "");
	  
		  if(userId != null){
			  $('.before_login').css('display', 'none');
		  } else{
			  sessionStorage.removeItem('userId');
			  $('.after_login').css('display', 'none');
		  }
		  
		  // 페이지가 로딩되면 시/도 리스트 받아오기
		  $.get(root + "/house/sido"
			  ,function(data, status){
				  $.each(data, function(index, vo) {
					  $("#city").append("<option value='"+vo.sidoCode+"'>"+vo.sidoName+"</option>");
				  });
			  }
			  , "json"
		  );
		  
		  // 템플릿 js code
		  if (window.location.hash) {
			var initial_nav = window.location.hash;
			if ($(initial_nav).length) {
			  var scrollto = $(initial_nav).offset().top - scrolltoOffset;
			  $('html, body').animate({
				scrollTop: scrollto
			  }, 1500, 'easeInOutExpo');
			}
		  }
		});
	  
		// Mobile Navigation
		if ($('.nav-menu').length) {
		  var $mobile_nav = $('.nav-menu').clone().prop({
			class: 'mobile-nav d-lg-none'
		  });
		  $('body').append($mobile_nav);
		  $('body').prepend('<button type="button" class="mobile-nav-toggle d-lg-none"><i class="icofont-navigation-menu"></i></button>');
		  $('body').append('<div class="mobile-nav-overly"></div>');
	  
		  $(document).on('click', '.mobile-nav-toggle', function(e) {
			$('body').toggleClass('mobile-nav-active');
			$('.mobile-nav-toggle i').toggleClass('icofont-navigation-menu icofont-close');
			$('.mobile-nav-overly').toggle();
		  });
	  
		  $(document).on('click', '.mobile-nav .drop-down > a', function(e) {
			e.preventDefault();
			$(this).next().slideToggle(300);
			$(this).parent().toggleClass('active');
		  });
	  
		  $(document).click(function(e) {
			var container = $(".mobile-nav, .mobile-nav-toggle");
			if (!container.is(e.target) && container.has(e.target).length === 0) {
			  if ($('body').hasClass('mobile-nav-active')) {
				$('body').removeClass('mobile-nav-active');
				$('.mobile-nav-toggle i').toggleClass('icofont-navigation-menu icofont-close');
				$('.mobile-nav-overly').fadeOut();
			  }
			}
		  });
		} else if ($(".mobile-nav, .mobile-nav-toggle").length) {
		  $(".mobile-nav, .mobile-nav-toggle").hide();
		}
	  
		// Navigation active state on scroll
		var nav_sections = $('section');
		var main_nav = $('.nav-menu, .mobile-nav');
	  
		$(window).on('scroll', function() {
		  var cur_pos = $(this).scrollTop() + 200;
	  
		  nav_sections.each(function() {
			var top = $(this).offset().top,
			  bottom = top + $(this).outerHeight();
	  
			if (cur_pos >= top && cur_pos <= bottom) {
			  if (cur_pos <= bottom) {
				main_nav.find('li').removeClass('active');
			  }
			  main_nav.find('a[href="#' + $(this).attr('id') + '"]').parent('li').addClass('active');
			}
			if (cur_pos < 300) {
			  $(".nav-menu ul:first li:first, .mobile-menu ul:first li:first").addClass('active');
			}
		  });
		});
	  
		// Toggle .header-scrolled class to #header when page is scrolled
		$(window).scroll(function() {
		  if ($(this).scrollTop() > 100) {
			$('#header').addClass('header-scrolled');
		  } else {
			$('#header').removeClass('header-scrolled');
		  }
		});
	  
		if ($(window).scrollTop() > 100) {
		  $('#header').addClass('header-scrolled');
		}
	  
		// Stick the header at top on scroll
		$("#header").sticky({
		  topSpacing: 0,
		  zIndex: '50'
		});
	  
		// Intro carousel
		var heroCarousel = $("#heroCarousel");
		var heroCarouselIndicators = $("#hero-carousel-indicators");
		heroCarousel.find(".carousel-inner").children(".carousel-item").each(function(index) {
		  (index === 0) ?
		  heroCarouselIndicators.append("<li data-target='#heroCarousel' data-slide-to='" + index + "' class='active'></li>"):
			heroCarouselIndicators.append("<li data-target='#heroCarousel' data-slide-to='" + index + "'></li>");
		});
	  
		heroCarousel.on('slid.bs.carousel', function(e) {
		  $(this).find('h2').addClass('animate__animated animate__fadeInDown');
		  $(this).find('p, .btn-get-started').addClass('animate__animated animate__fadeInUp');
		});
  })(jQuery);