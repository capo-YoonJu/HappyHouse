import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import userStore from "@/store/modules/userStore.js";
import chartStore from "@/store/modules/chartStore.js";

export default new Vuex.Store({
  state: {
    posts: [],
    postComments: [],
    tags: [],
  },
  getters: {
    allPosts(state) {
      return state.posts;
    },
    allPostComments(state) {
      return state.postComments;
    },
    allTags(state) {
      return state.tags;
    }
  },
  mutations: {
    SET_POSTS(state, postList) {
      state.posts = postList;
    },
    SET_COMMENTS(state, commentList) {
      state.postComments = commentList;
    },
    SET_TAGS(state, tag) {
      state.tags.push(tag);
    },
    REMOVE_TAG(state, tag) {
      const idx = state.tags.map(tagName => tagName).indexOf(tag);
      state.tags.splice(idx, 1);
    },
    REMOVE_ALL_TAGS(state) {
      state.tags = [];
    },
  },
  actions: {
    setPosts({ commit }, postList) {
      commit('SET_POSTS', postList);
    },
    setComments({ commit }, commentList) {
      commit('SET_COMMENTS', commentList);
    },
    setTags({ commit }, tag) {
      commit('SET_TAGS', tag);
    },
    removeTag({ commit }, tag) {
      commit('REMOVE_TAG', tag);
    },
    removeAllTags({ commit }) {
      commit('REMOVE_ALL_TAGS');
    }
  },
  modules: {
    userStore,
    chartStore,
  },
});
