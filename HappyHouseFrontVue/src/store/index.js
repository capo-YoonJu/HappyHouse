import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    postComments: [],
  },
  getters: {
    allPostComments(state) {
      return state.postComments;
    }
  },
  mutations: {
    SET_COMMENTS(state, commentList) {
      state.postComments = commentList;
    },
    CREATE_COMMENT(state, commentItem) {
      state.postComments.push(commentItem);
    },
    DELETE_COMMENT(state, commentItem) {
      const idx = state.postComments.map(postComment => postComment.no).indexOf(commentItem.no);
      state.postComments.splice(idx, 1);
    },
    MODIFY_COMMENT(state, commentItem) {
      const idx = state.postComments.map(postComment => postComment.no).indexOf(commentItem.no);
      if (idx !== -1) state.postComments.splice(idx, 1, commentItem);
    },
  },
  actions: {
    setComments({ commit }, commentList) {
      commit('SET_COMMENTS', commentList);
    },
    createComment({ commit }, commentItem) {
      commit('CREATE_COMMENT', commentItem);
    },
    deleteComment({ commit }, commentItem) {
      commit('DELETE_COMMENT', commentItem);
    },
    modifyComment({ commit }, commentItem) {
      commit('MODIFY_COMMENT', commentItem);
    },
  },
  modules: {},
});
