import { apiInstance, sdotEnvApiInstance } from "../index.js";

const baseApi = apiInstance();
const sdotEnvApi = sdotEnvApiInstance();

/**
 * S-DOT 환경 데이터 활용을 위한 API 통신
 */

/**
 * [GET] 백엔드 서버에서 구군코드로 동 S-DOT 시리얼넘버 리스트 조회
 * @param {Object} gugun - 구군코드로 정보를 담은 json 객체
 * @param {function} success - 동 S-DOT 시리얼넘버 리스트 조회 성공
 * @param {function} fail - 동 S-DOT 시리얼넘버 리스트 조회 실패
 */
function serialList(gugun, success, fail) {
    baseApi.get(`/address/sdot`, { params: gugun }).then(success).catch(fail);
}

/**
 * [GET] 서울 열린 데이터 광장 스마트서울 도시데이터 센서(S-DoT) 환경정보 데이터 조회
 * @param {String} pageNo - 조회 페이지
 * @param {function} success - 환경정보 조회 성공
 * @param {function} fail - 환경정보 조회 실패
 */
 function sdotEnvList(pageNo, success, fail) {
    sdotEnvApi.get(`` + pageNo)
              .then(success).catch(fail);
}

export { serialList, sdotEnvList }