import { apiInstance, guAgeApiInstance, dongAgeApiInstance } from "../index.js";

const baseApi = apiInstance();
const guAgeApi = guAgeApiInstance();
const dongAgeApi = dongAgeApiInstance();

/**
 * 서울 생활 인구 데이터 활용을 위한 API 통신
 */

/**
 * [GET] 백엔드 서버에서 동코드로 서울시 행정동코드 조회
 * @param {Object} dongCode - 동코드 정보를 담은 json 객체
 * @param {function} success - 행정동코드 조회 성공
 * @param {function} fail - 행정동코드 조회 실패
 */
function hDong(dongCode, success, fail) {
   baseApi.get(`/address/hdong`, { params: dongCode }).then(success).catch(fail);
}

/**
 * [GET] 서울 열린 데이터 광장 자치구단위 서울생활인구(내국인) 데이터 조회
 * @param {String} date - 기준일
 * @param {function} success - 생활인구 정보 조회 성공
 * @param {function} fail - 생활인구 정보 조회 실패
 */
function guAgeList(date, success, fail) {
   guAgeApi.get(``+date)
       .then(success).catch(fail);
}

/**
 * [GET] 서울 열린 데이터 광장 행정동단위 서울생활인구(내국인) 데이터 조회
 * @param {String} date - 기준일
 * @param {function} success - 생활인구 정보 조회 성공
 * @param {function} fail - 생활인구 정보 조회 실패
 */
function dongAgeList(date, success, fail) {
   dongAgeApi.get(``+date)
       .then(success).catch(fail);
}

export { hDong, guAgeList, dongAgeList }