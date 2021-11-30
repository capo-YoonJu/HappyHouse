import { lotsApiInstance } from "../index.js";

const lotsApi = lotsApiInstance();

/**
 * 전국 청약, 분양 데이터 활용을 위한 API 통신
 */

/**
 * [GET] 공공데이터포털(한국부동산원_전국 청약 분양정보 조회 서비스) 전국 청약 분양 정보 리스트 조회
 * @param {Object} param - 모집공고 시작, 종료일, 인증키 정보를 담은 json 객체
 * @param {function} success - 청약 분양 정보 조회 성공
 * @param {function} fail - 청약 분양정보 조회 실패
 */
function lotsList(param, success, fail) {
   lotsApi.get(``, { params: param }).then(success).catch(fail);
}

export { lotsList }