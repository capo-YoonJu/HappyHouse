# 청년 맞춤형 부동산 실거래가 조회 서비스 - Happy House 🏘

<br>

## 🤓 프로젝트 참여자

- 배문규
- 김도현
- 하윤주


## 📌 목차

1. 프로젝트 소개
2. 활용 데이터 및 API 소개
3. BackEnd & FrontEnd 소개
4. 시연

<br>

## 💡 1. 프로젝트 소개

Happy House는 청년들이 거주 지역을 쉽게 선택할 수 있도록 서울시 지역 별 환경 데이터와 오피스텔 데이터를 한 눈에 확인할 수 있는 프로그램입니다. 오피스텔 및 아파트 세부 정보와 함께 거래 금액과 거래 종류를 확인할 수 있고, 또한 법정동과 아파트 명으로 특정 부동산 정보를 검색할 수도 있습니다. 검색 결과는 지도 상에서 확인할 수 있습니다. 서울시 자치구와 행정동을 선택하면 해당 지역의 실시간 환경 정보를 차트로 조회할 수 있어 직접 거주지를 비교하고 선택할 수 있습니다.

## 💡 2. 활용 데이터 및 API 소개

1. 활용 데이터

1) 공공데이터 포털(https://www.data.go.kr)

웹 사이트 상에서 전국 지역명을 입력하면 공공데이터 포털과 비동기 통신을 통해 2021년 10월 아파트매매 실거래 데이터를 받아옵니다. REST 방식으로 통신하고 XML 형태의 데이터로 받아와 JSON 형태로 파싱합니다.

받은 실거래 데이터 중 핵심 정보를 리스트 형태로 출력하고, 각 아파트의 위치를 지도 상에 표현합니다.

이외에도 다음과 같은 데이터를 API로 수집합니다.

- 한국부동산원_전국 청약 분양정보 조회 서비스
- 국토교통부_아파트매매 실거래 상세 자료
- 국토교통부_오피스텔 전월세 신고 조회 서비스

2) 서울 열린 데이터 광장(https://data.seoul.go.kr/index.do)

웹 사이트 상에서 서울시의 자치구, 법정동을 입력하면 서울 열린 데이터 광장과 비동기 통신을 통해 실시간 환경 정보 데이터를 받아옵니다. REST 방식으로 통신하고 JSON 형태의 데이터로 받아와 필요한 부분을 파싱합니다.

받은 데이터는 chart.js를 활용하여 꺾은선 그래프와 레이더 차트로 표현합니다.

- 자치구단위 서울생활인구(내국인) 데이터 조회
- 행정동단위 서울생활인구(내국인) 데이터 조회
- 스마트서울 도시데이터 센서(S-DoT) 환경정보 데이터 조회

1. 활용 API

- 카카오맵 API(https://apis.map.kakao.com/web/)

공공데이터 포털에서 받은 실거래 데이터 중 주택의 위치 좌표(위도, 경도)를 파싱하여 카카오맵에 마커로 표현합니다.

또한 실거래 데이터 리스트의 각 아파트를 선택하면 지도 상에서 상세 정보를 추가적으로 확인할 수 있습니다.

## 💡 3. 활용한 BackEnd & FrontEnd 프레임워크 소개

> BackEnd

- BackEnd는 Java와 Spring Boot 프레임워크로 구축하였습니다.
- Front와 REST 방식으로 통신하여 JSON 데이터만을 전달합니다.
- MyBatis 프레임워크를 사용하여 객체와 SQL을 매핑합니다.
- Global Exception Advice를 통해 예외를 전역에서 처리합니다.

> FrontEnd

- FrontEnd는 Vue 프레임워크로 구축해 SPA(Single Page Application) 구조에서 컴포넌트를 변경하는 방식으로 구현하였습니다.
- 화면은 정적 리소스(HTML, CSS)로 구성되어 있습니다.
- Back과 ajax 방식으로 비동기 통신하여 동적으로 데이터를 받아옵니다.
- 로그인 세션 관리를 위해 Session Storage를 이용해 사용자의 아이디만을 저장합니다.

## 💡 4. 시연
