import { apiInstance, officetelRentApiInstance } from "../index.js";

const baseApi = apiInstance();
const officetelRentApi = officetelRentApiInstance();

/**
 * 오피스텔 전월세 데이터 활용을 위한 API 통신
 */

/**
 * [GET] 백엔드 서버에서 서울시 자치구 리스트 조회
 * @param {function} success - 서울시 자치구 리스트 조회 성공
 * @param {function} fail - 서울시 자치구 리스트 조회 실패
 */
function gugunList(success, fail) {
    baseApi.get(`/address/gugun`).then(success).catch(fail);
}

/**
 * [GET] 백엔드 서버에서 구군코드로 서울시 동 리스트 조회
 * @param {Object} gugun - 구군코드 정보를 담은 json 객체
 * @param {function} success - 서울시 동 리스트 조회 성공
 * @param {function} fail - 서울시 동 리스트 조회 실패
 */
function dongList(gugun, success, fail) {
    baseApi.get(`/address/dong`, { params: gugun }).then(success).catch(fail);
}

/**
 * [GET] 공공데이터포털(국토교통부_오피스텔 전월세 신고 조회 서비스) 오피스텔 전월세 리스트 조회
 * @param {Object} param - 지역코드, 계약월, 인증키 정보를 담은 json 객체
 * @param {function} success - 오피스텔 전월세 정보 조회 성공
 * @param {function} fail - 오피스텔 전월세 정보 조회 실패
 */
function officetelRentList(param, success, fail) {
    officetelRentApi.get(``, { params: param }).then(success).catch(fail);
}

export { gugunList, dongList, officetelRentList };