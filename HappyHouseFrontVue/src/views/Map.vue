<template>
  <div class="map_wrap">
    <div id="map" style="float: left" @click="sync()"></div>
    <ul id="category" style="display: none">
      <li id="BK9" data-order="0">
        <span class="category_bg bank"></span>은행
      </li>
      <li id="MT1" data-order="1">
        <span class="category_bg mart"></span>마트
      </li>
      <li id="PM9" data-order="2">
        <span class="category_bg pharmacy"></span>약국
      </li>
      <li id="OL7" data-order="3">
        <span class="category_bg oil"></span>주유소
      </li>
      <li id="CE7" data-order="4">
        <span class="category_bg cafe"></span>카페
      </li>
      <li id="CS2" data-order="5">
        <span class="category_bg store"></span>편의점
      </li>
    </ul>

    <div id="menu_wrap" style="display: none" class="bg_white">
      <ul id="placesList" @click="sync()"></ul>
    </div>

    <div class="tableList"></div>
    <div class="lastSearch">
      <div data-last="noname"></div>
    </div>
    <div style="float: left; text-align: center">
      <p id="aptName" style="font-size: 30px; font-weight: bold"></p>
      <br />
      <b-container fluid="xl">
        <map-scatter-chart :modalhouse="modalhouse"></map-scatter-chart>
      </b-container>
      <b-container fluid="xl">
        <!-- Main table element -->
        <b-table
          :items="modalhouse"
          :fields="fields"
          :current-page="currentPage"
          :per-page="perPage"
          :filter="filter"
          :filter-included-fields="filterOn"
          :sort-by.sync="sortBy"
          :sort-desc.sync="sortDesc"
          :sort-direction="sortDirection"
          stacked="md"
          show-empty
          large
          @filtered="onFiltered"
        >
          <template #cell(name)="row">
            {{ row.value.first }} {{ row.value.last }}
          </template>
        </b-table>

        <!-- User Interface controls -->
        <b-row>
          <b-col sm="7" md="6" class="my-1">
            <b-pagination
              v-model="currentPage"
              :total-rows="totalRows"
              :per-page="perPage"
              align="fill"
              size="sm"
              class="my-0"
            ></b-pagination>
          </b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import $ from "jquery";
import MapScatterChart from "@/components/map/MapScatterChart.vue";

export default {
  components: {
    MapScatterChart,
  },
  data() {
    return {
      fields: [
        {
          key: "dealAmount",
          label: "거래가",
          sortable: true,
          sortDirection: "desc",
        },
        {
          key: "area",
          label: "평수",
          sortable: true,
          class: "text-center",
        },
        {
          key: "pricePerArea",
          label: "평당가",
          sortable: true,
          sortDirection: "desc",
        },
        {
          key: "buildYear",
          label: "건축일",
          sortable: true,
          sortDirection: "desc",
        },
        {
          key: "dealYear",
          label: "거래년",
          sortable: true,
          sortDirection: "desc",
        },
        {
          key: "dealMonth",
          label: "거래월",
          sortable: true,
          sortDirection: "desc",
        },
        {
          key: "dealDay",
          label: "거래일",
          sortable: true,
          sortDirection: "desc",
        },
      ],
      totalRows: 1,
      currentPage: 1,
      perPage: 5,
      pageOptions: [5, 10, 15, { value: 100, text: "Show a lot" }],
      sortBy: "",
      sortDesc: false,
      sortDirection: "asc",
      filter: null,
      filterOn: [],
      house: [],
      showModal: false,
      modalhouse: [],
    };
  },
  props: ["params"],
  mounted() {
    window.kakao && window.kakao.maps ? this.initMap() : this.addScript();
    // Set the initial number of items
  },
  methods: {
    initMap() {
      let container = document.getElementById("map");
      let options = {
        center: new kakao.maps.LatLng(37.566828, 126.9788),
        level: 4,
      };
      let map = new kakao.maps.Map(container, options);

      // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      var zoomControl = new kakao.maps.ZoomControl();
      map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
    },
    addScript() {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=c1fe7d49bf408b3eec39aecd8fcfb0e2&libraries=services,clusterer,drawing";
      document.head.appendChild(script);
    },
    searchDeal(params) {
      let data = document.getElementById("menu_wrap");
      data.setAttribute("style", "display:inline");
      data = document.getElementById("category");
      data.setAttribute("style", "display:inline");
      let request = `/house/search?${params}`;
      http
        .get(request)
        .then(({ data }) => {
          this.house = [];
          this.items = [];

          let apt, maxAmount, minAmount, maxArea, minArea;
          if (data.length != 0) {
            apt = data[0].aptName;
            maxAmount = minAmount = data[0].dealAmount.replace(",", "");
            maxArea = minArea = (data[0].area / 3.3).toFixed(2);
          }
          for (let i = 0; i < data.length; i++) {
            let obj = {
              sido: data[i].sido,
              gugun: data[i].gugun,
              dong: data[i].dong,
              aptName: data[i].aptName,
              dealYear: data[i].dealYear,
              dealMonth: data[i].dealMonth,
              dealDay: data[i].dealDay,
              buildYear: data[i].buildYear,
              jibun: data[i].jibun,
              dealAmount: data[i].dealAmount.replace(",", ""),
              area: (data[i].area / 3.3).toFixed(2),
              pricePerArea: (
                parseInt(data[i].dealAmount) / parseFloat(data[i].area / 3.3)
              ).toFixed(2),
              latlng: new kakao.maps.LatLng(data[i].lat, data[i].lng),
            };
            if (obj.aptName == apt) {
              if (obj.dealAmount > maxAmount) {
                maxAmount = obj.dealAmount;
              }
              if (obj.dealAmount < minAmount) {
                minAmount = obj.dealAmount;
              }
              if (parseFloat(obj.area) > parseFloat(maxArea)) {
                maxArea = obj.area;
              }
              if (parseFloat(obj.area) < parseFloat(minArea)) {
                minArea = obj.area;
              }
              if (i == data.length - 1) {
                obj.maxAmount = maxAmount;
                obj.minAmount = minAmount;
                obj.maxArea = maxArea;
                obj.minArea = minArea;
              }
            } else {
              this.house[i - 1].maxAmount = maxAmount;
              this.house[i - 1].minAmount = minAmount;
              this.house[i - 1].maxArea = maxArea;
              this.house[i - 1].minArea = minArea;
              apt = obj.aptName;
              maxAmount = minAmount = obj.dealAmount;
              maxArea = minArea = obj.area;
            }
            this.house.push(obj);
          }
          var placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 }),
            contentNode1 = document.createElement("div"), // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
            contentNode2 = document.createElement("div"), // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
            markers = [], // 마커를 담을 배열입니다
            currCategory = "", // 현재 선택된 카테고리를 가지고 있을 변수입니다
            mapContainer = document.getElementById("map"), // 지도를 표시할 div
            mapOption = {
              center: new kakao.maps.LatLng(37.5668152, 126.9787216), // 지도의 중심좌표
              level: 5, // 지도의 확대 레벨
            };
          // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
          var map = new kakao.maps.Map(mapContainer, mapOption);
          if (this.house.length > 0) map.panTo(this.house[0].latlng);
          var ps = new kakao.maps.services.Places(map);

          // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
          var infowindow = new kakao.maps.CustomOverlay({ zIndex: 1 });

          // 지도에 idle 이벤트를 등록합니다
          kakao.maps.event.addListener(map, "idle", searchPlaces);
          // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
          contentNode1.className = "placeinfo_wrap";
          contentNode2.className = "placeinfo_wrap";
          // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
          // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
          addEventHandle(
            contentNode1,
            "mousedown",
            kakao.maps.event.preventMap
          );
          addEventHandle(
            contentNode1,
            "touchstart",
            kakao.maps.event.preventMap
          );
          // 커스텀 오버레이 컨텐츠를 설정합니다
          placeOverlay.setContent(contentNode1);
          infowindow.setContent(contentNode2);
          // 각 카테고리에 클릭 이벤트를 등록합니다
          addCategoryClickEvent();

          var zoomControl = new kakao.maps.ZoomControl();
          map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

          var listEl = document.getElementById("placesList"),
            menuEl = document.getElementById("menu_wrap"),
            fragment = document.createDocumentFragment(),
            bounds = new kakao.maps.LatLngBounds();

          // 검색 결과 목록에 추가된 항목들을 제거합니다
          removeAllChildNods(listEl);

          for (var i = 0; i < this.house.length; i++) {
            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
              map: map, // 마커를 표시할 지도
              position: this.house[i].latlng, // 마커의 위치
            });

            var itemEl = getListItem(i, this.house[i]);

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
            // LatLngBounds 객체에 좌표를 추가합니다
            bounds.extend(this.house[i].latlng);

            // 마커와 검색결과 항목에 mouseover 했을때
            // 해당 장소에 인포윈도우에 장소명을 표시합니다
            // mouseout 했을 때는 인포윈도우를 닫습니다
            (function (marker, house) {
              kakao.maps.event.addListener(marker, "click", function () {
                temp(house);
              });

              kakao.maps.event.addListener(marker, "mouseover", function () {
                displayInfowindow(house);
              });

              kakao.maps.event.addListener(marker, "mouseout", function () {
                infowindow.setMap(null);
              });

              itemEl.onmouseover = function () {
                map.setLevel(2);
                map.setCenter(house.latlng);
                displayInfowindow(house);
              };

              itemEl.onmouseout = function () {
                infowindow.setMap(null);
              };
            })(marker, this.house[i]);

            if (this.house[i].maxAmount != null) fragment.appendChild(itemEl);
          }
          // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
          listEl.appendChild(fragment);
          menuEl.scrollTop = 0;

          // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
          map.setBounds(bounds);

          function temp(house) {
            let data = document.createElement("div");
            data.setAttribute("data-dong", house.dong);
            data.setAttribute("data-aptName", house.aptName);
            data.setAttribute("data-latlng", house.latlng);
            data.setAttribute("style", "display:none");
            $(".tableList").empty();
            $(".tableList").append(data);
          }

          // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
          // 인포윈도우에 장소명을 표시합니다
          function displayInfowindow(place) {
            var content =
              '<div class="placeinfo">' +
              '<span class="title2">' +
              place.aptName +
              "</span>" +
              '<span class="jibun">' +
              place.sido +
              " " +
              place.gugun +
              " " +
              place.dong +
              " " +
              place.jibun +
              ")</span>" +
              "</div>";

            contentNode2.innerHTML = content;
            infowindow.setPosition(place.latlng);
            infowindow.setMap(map);
          }

          function removeAllChildNods(el) {
            while (el.hasChildNodes()) {
              el.removeChild(el.lastChild);
            }
          }

          // 검색결과 항목을 Element로 반환하는 함수입니다
          function getListItem(index, places) {
            let el = document.createElement("li"),
              itemStr =
                `<button type="button" class="modalView" data-latlng="${places.latlng}" data-aptName="${places.aptName}" data-address="${places.dong}">` +
                `<div class="info" style="margin-top:3px">` +
                `<span class="title">` +
                places.aptName +
                "</span>" +
                '<span class="jibun gray">' +
                places.sido +
                " " +
                places.gugun +
                " " +
                places.dong +
                "</span>" +
                `<span class="amount">` +
                parseInt(places.minAmount / 10000) +
                "억" +
                (parseInt(places.minAmount) % 10000) +
                "만원" +
                " ~ " +
                parseInt(places.maxAmount / 10000) +
                "억" +
                (parseInt(places.maxAmount) % 10000) +
                "만원" +
                " </span>" +
                `<span class="area">` +
                places.minArea +
                "평 ~ " +
                places.maxArea +
                "평 </span>" +
                "</div>" +
                "</button>" +
                "<hr/>";
            el.innerHTML = itemStr;
            el.className = "item";
            return el;
          }

          $(".modalView").on("click", function () {
            let data = document.createElement("div");
            data.setAttribute("data-aptName", this.dataset.aptname);
            data.setAttribute("data-dong", this.dataset.address);
            data.setAttribute("data-latlng", this.dataset.latlng);
            data.setAttribute("style", "display:none");
            $(".tableList").empty();
            $(".tableList").append(data);
          });

          // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
          function addEventHandle(target, type, callback) {
            if (target.addEventListener) {
              target.addEventListener(type, callback);
            } else {
              target.attachEvent("on" + type, callback);
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
            ps.categorySearch(currCategory, placesSearchCB, {
              useMapBounds: true,
            });
          }

          // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
          function placesSearchCB(data, status) {
            if (status === kakao.maps.services.Status.OK) {
              // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
              displayPlaces(data);
              // 페이지 번호를 표출합니다
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
            var order = document
              .getElementById(currCategory)
              .getAttribute("data-order");

            for (var i = 0; i < places.length; i++) {
              // 마커를 생성하고 지도에 표시합니다

              var placePosition = new kakao.maps.LatLng(
                  places[i].y,
                  places[i].x
                ),
                marker = addMarker(placePosition, order);

              // 마커와 검색결과 항목을 클릭 했을 때
              // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
              (function (marker, place) {
                kakao.maps.event.addListener(marker, "click", function () {
                  displayPlaceInfo(place);
                });
              })(marker, places[i]);
            }
          }

          // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
          function addMarker(position, order) {
            var imageSrc =
                "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
              imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
              imgOptions = {
                spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
                spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
                offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
              },
              markerImage = new kakao.maps.MarkerImage(
                imageSrc,
                imageSize,
                imgOptions
              ),
              marker = new kakao.maps.Marker({
                position: position, // 마커의 위치
                image: markerImage,
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

          // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
          function displayPlaceInfo(place) {
            var content =
              '<div class="placeinfo">' +
              '   <a class="title" href="' +
              place.place_url +
              '" target="_blank" title="' +
              place.place_name +
              '">' +
              place.place_name +
              "</a>";

            if (place.road_address_name) {
              content +=
                '    <span title="' +
                place.road_address_name +
                '">' +
                place.road_address_name +
                "</span>" +
                '  <span class="jibun" title="' +
                place.address_name +
                '">(지번 : ' +
                place.address_name +
                ")</span>";
            } else {
              content +=
                '    <span title="' +
                place.address_name +
                '">' +
                place.address_name +
                "</span>";
            }

            content +=
              '    <span class="tel">' +
              place.phone +
              "</span>" +
              "</div>" +
              '<div class="after"></div>';

            contentNode1.innerHTML = content;
            placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
            placeOverlay.setMap(map);
          }

          // 각 카테고리에 클릭 이벤트를 등록합니다
          function addCategoryClickEvent() {
            var category = document.getElementById("category"),
              children = category.children;
            for (var i = 0; i < children.length; i++) {
              children[i].onclick = onClickCategory;
            }
          }

          // 카테고리를 클릭했을 때 호출되는 함수입니다
          function onClickCategory() {
            var id = this.id,
              className = this.className;
            placeOverlay.setMap(null);

            if (className === "on") {
              currCategory = "";
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
            var category = document.getElementById("category"),
              children = category.children,
              i;

            for (i = 0; i < children.length; i++) {
              children[i].className = "";
            }

            if (el) {
              el.className = "on";
            }
          }
        })
        .catch((error) => alert(`처리 중 문제가 발생하였습니다.${error}`));
    },
    resetInfoModal() {
      this.infoModal.title = "";
      this.infoModal.content = "";
    },
    onFiltered(filteredItems) {
      // Trigger pagination to update the number of buttons/pages due to filtering
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
    sync() {
      let house = document.getElementsByClassName("tableList")[0].children[0];
      let exHouse =
        document.getElementsByClassName("lastSearch")[0].children[0];
      if (house != undefined && house.dataset.aptname != exHouse.dataset.last) {
        let aptTitle = document.getElementById("aptName");
        aptTitle.innerText =
          house.dataset.dong + " " + house.dataset.aptname + "아파트";
        let params = `dong=${house.dataset.dong}&apt=${house.dataset.aptname}`;
        let request = `/house/search?${params}`;
        this.modalhouse = [];
        http.get(request).then(({ data }) => {
          for (let i = 0; i < data.length; i++) {
            let obj = {
              sido: data[i].sido,
              gugun: data[i].gugun,
              dong: data[i].dong,
              aptName: data[i].aptName,
              dealYear: data[i].dealYear,
              dealMonth: data[i].dealMonth,
              dealDay: data[i].dealDay,
              buildYear: data[i].buildYear,
              jibun: data[i].jibun,
              dealAmountNum: data[i].dealAmount.replace(",", ""),
              dealAmount:
                Math.floor(data[i].dealAmount.replace(",", "") / 10000) +
                "억" +
                (data[i].dealAmount.replace(",", "") % 10000) +
                "만원",
              area: (data[i].area / 3.3).toFixed(2) + "평",
              pricePerArea:
                Math.round(
                  (parseInt(data[i].dealAmount) /
                    parseFloat(data[i].area / 3.3)) *
                    1000
                ) + "만원",
              latlng: new kakao.maps.LatLng(data[i].lat, data[i].lng),
            };
            this.modalhouse.push(obj);
          }
          this.totalRows = data.length;
        });
        let data = document.createElement("div");
        data.setAttribute("data-last", house.dataset.aptname);
        $(".lastSearch").empty();
        $(".lastSearch").append(data);
      }
    },
  },
  computed: {
    sortOptions() {
      // Create an options list from our fields
      return this.fields
        .filter((f) => f.sortable)
        .map((f) => {
          return { text: f.label, value: f.key };
        });
    },
  },
};
</script>

<style scope>
#map {
  width: 600px;
  height: 800px;
  position: relative;
  overflow: hidden;
}
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap a,
.map_wrap a:hover,
.map_wrap a:active {
  color: #000;
  text-decoration: none;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 800px;
}
#category {
  position: absolute;
  top: 20px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 57px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  text-align: center;
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 17px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 18px;
  text-align: center;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .title2 {
  font-weight: bold;
  font-size: 20px;
  text-align: center;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #37c1d3;
  background: #37c1d3 no-repeat right 14px center;
}
.placeinfo .tel {
  text-align: center;
  font-size: 15px;
  color: #0f7833;
}
.placeinfo .jibun {
  margin-top: 100px;
  color: rgb(121, 121, 121);
  text-align: center;
  font-size: 16px;
  margin-top: 0;
}
#placesList li {
  list-style: none;
}
#placesList .item {
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 65px;
}
#placesList .item button {
  border: 0;
  outline: 0;
  background-color: transparent;
  width: 310px;
}
#placesList .item span {
  display: block;
  margin-top: 4px;
}
#placesList .item .info {
  font-size: 18px;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  padding: 5px 0px 15px 0px;
}
#placesList .item .info .title {
  font-size: 18px;
  font-weight: bold;
  padding-bottom: 5px;
}
#placesList .item .info .gray {
  color: #8a8a8a;
}
#placesList .item .info .jibun {
  font-size: 15px;
  padding-left: 40px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png)
    no-repeat 40px center;
  background-size: 40px;
}
#placesList .item .info .amount,
#placesList .item .info .area {
  font-size: 15px;
}
#menu_wrap {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 350px;
  margin: 100px 0 30px 10px;
  padding: 5px;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.7);
  z-index: 1;
  font-size: 12px;
  border-radius: 10px;
}
.bg_white {
  background: #fff;
}
#menu_wrap .option {
  text-align: center;
}
#menu_wrap .option p {
  margin: 10px 0;
}
#menu_wrap .option button {
  margin-left: 5px;
}
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}
.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
.modal-container {
  width: 300px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}
.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}
.modal-body {
  margin: 20px 0;
}
.modal-default-button {
  float: right;
}
/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */
.modal-enter {
  opacity: 0;
}
.modal-leave-active {
  opacity: 0;
}
.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
