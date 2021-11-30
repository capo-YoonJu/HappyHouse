import qs from "qs";
import { apiInstance } from "./index.js";

/**
 * board 컴포넌트 통신을 위한 api 메서드
 */

const api = apiInstance();

/**
 * [GET] 백엔드 서버에서 게시글 리스트 조회
 * @param {function} success - 게시글 조회 성공
 * @param {function} fail - 게시글 조회 실패
 */
function listPosts(success, fail) {
    api.get(`/board`).then(success).catch(fail);
}

/**
 * [GET] 백엔드 서버에서 게시글 번호로 특정 게시글 조회
 * @param {int} postNo - 게시글 번호
 * @param {function} success - 게시글 조회 성공
 * @param {function} fail - 게시글 조회 실패
 */
 function getPostByNo(postNo, success, fail) {
    api.get(`/board/${postNo}`).then(success).catch(fail);
}

/**
 * [POST] 백엔드 서버에 게시글 등록
 * @param {Object} post - 게시글 객체
 * @param {function} success - 게시글 등록 성공
 * @param {function} fail - 게시글 등록 실패
 */
function writePost(post, success, fail) {
    api.post(`/board`, JSON.stringify(post)).then(success).catch(fail);
}

/**
 * [PUT] 백엔드 서버의 게시글 수정
 * @param {int} postNo - 게시글 번호
 * @param {Object} post - 게시글 객체
 * @param {function} success - 게시글 수정 성공
 * @param {function} fail - 게시글 수정 실패
 */
function updatePost(postNo, post, success, fail) {
    api.put(`/board/${postNo}`, JSON.stringify(post)).then(success).catch(fail);
}

/**
 * [DELETE] 백엔드 서버의 게시글 삭제
 * @param {int} postNo - 게시글 번호
 * @param {function} success - 게시글 삭제 성공
 * @param {function} fail - 게시글 삭제 실패
 */
function removePost(postNo, success, fail) {
    api.delete(`/board/${postNo}`).then(success).catch(fail);
}

/**
 * [GET] 백엔드 서버에서 게시글 댓글 조회
 * @param {int} postNo - 게시글 번호
 * @param {function} success - 댓글 조회 성공
 * @param {function} fail - 댓글 조회 실패
 */
function getCommentsByPostNo(postNo, success, fail) {
    api.get(`/board/${postNo}/comments`).then(success).catch(fail);
}

/**
 * [POST] 백엔드 서버에 특정 게시글 댓글 등록
 * @param {int} postNo - 게시글 번호
 * @param {Object} comment - 댓글 객체
 * @param {function} success - 댓글 등록 성공
 * @param {function} fail - 댓글 등록 실패
 */
function writeComment(postNo, comment, success, fail) {
    api.post(`/board/${postNo}/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

/**
 * [PUT] 백엔드 서버의 특정 게시글 댓글 수정
 * @param {int} postNo - 게시글 번호
 * @param {Object} comment - 댓글 객체
 * @param {function} success - 댓글 수정 성공
 * @param {function} fail - 댓글 수정 실패
 */
function updateComment(postNo, comment, success, fail) {
    api.put(`/board/${postNo}/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

/**
 * [DELETE] 백엔드 서버의 게시글 댓글 삭제
 * @param {int} commentNo - 댓글 번호
 * @param {function} success - 댓글 삭제 성공
 * @param {function} fail - 댓글 삭제 실패
 */
function removeComment(commentNo, success, fail) {
    api.delete(`/board/${commentNo}/comment`).then(success).catch(fail);
}

/**
 * [GET] 백엔드 서버에서 태그로 게시글 조회
 * @param {Array} tagList - 태그 리스트
 * @param {function} success - 게시글 조회 성공
 * @param {function} fail - 게시글 조회 실패
 */
function getPostsByTags(tagList, success, fail) {
    api.get(`/board/hashtags`, {
            params: tagList,
            paramsSerializer: params => {
                return qs.stringify(params, { arrayFormat : 'brackets' })
            }
        })
        .then(success).catch(fail);
}
  
export { listPosts, getPostByNo, writePost, updatePost, removePost,
         getCommentsByPostNo, writeComment, updateComment, removeComment,
         getPostsByTags };
  