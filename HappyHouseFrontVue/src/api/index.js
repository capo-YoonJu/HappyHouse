import axios from "axios";
import { API_BASE_URL, LOTS_API_URL, OFFICETEL_RENT_URL, GU_RESIDENTS_AGE_URL, DONG_RESIDENTS_AGE_URL, SDOT_ENV_URL } from "@/config";

/**
 * axios 객체 생성
 */

// BackEnd API
function apiInstance() {
    const instance = axios.create({
        baseURL: API_BASE_URL,
        headers: {
            "Content-type": "application/json",
        },
    });
    return instance;
}

// 국토교통부_오피스텔 전월세 신고 조회 서비스 API
function lotsApiInstance() {
    const instance = axios.create({
        baseURL: LOTS_API_URL,
        headers: {
            "Content-type": "application/json",
        },
    });
    return instance;
}

// 국토교통부 전국 오피스텔 전월세 정보 API
function officetelRentApiInstance() {
    const instance = axios.create({
        baseURL: OFFICETEL_RENT_URL,
        headers: {
            "Content-type": "application/json",
        },
    });
    return instance;
}

// 자치구별 서울생활인구(내국인) API
function guAgeApiInstance() {
    const instance = axios.create({
        baseURL: GU_RESIDENTS_AGE_URL,
        headers: {
            "Content-type": "application/json",
        },
    });
    return instance;
}

// 행정동별 서울생활인구(내국인) API
function dongAgeApiInstance() {
    const instance = axios.create({
        baseURL: DONG_RESIDENTS_AGE_URL,
        headers: {
            "Content-type": "application/json",
        },
    });
    return instance;
}

// 스마트서울 도시데이터 센서(S-DoT) 환경정보 API
function sdotEnvApiInstance() {
    const instance = axios.create({
        baseURL: SDOT_ENV_URL,
        headers: {
            "Content-type": "application/json",
        },
    });
    return instance;
}

export { apiInstance, lotsApiInstance, officetelRentApiInstance, guAgeApiInstance, dongAgeApiInstance, sdotEnvApiInstance };