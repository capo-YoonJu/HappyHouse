import { apiInstance } from "./index.js";

/**
 * user 컴포넌트 통신을 위한 api 메서드
 */

const api = apiInstance();

/**
 * [GET] 아이디 중복 체크
 */
async function idCheck(userid, success, fail) {
  await api.get(`/user/${userid}`).then(success).catch(fail);
}

/**
 * [POST] 회원 가입
 */
async function registerUser(user, success, fail) {
  await api.post(`/user`, JSON.stringify(user)).then(success).catch(fail);
}

/**
 * [POST] 로그인
 */
async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

/**
 * [GET] 사용자 정보 조회
 */
async function getUser(success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/auth`).then(success).catch(fail);
}

/**
 * [PUT] 회원 정보 수정
 */
async function updateUser(user, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.put(`/user/auth`, JSON.stringify(user)).then(success).catch(fail);
}

/**
 * [DELETE] 회원탈퇴
 */
async function deleteUser(success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.delete(`/user/auth`).then(success).catch(fail);
}

/**
 * [] 로그아웃
 */
async function logout() {
  sessionStorage.removeItem("access-token");
}

export { idCheck, registerUser, login, getUser, updateUser, deleteUser, logout };