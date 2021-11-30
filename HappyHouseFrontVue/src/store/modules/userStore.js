import { getUser, deleteUser, logout } from "@/api/user.js";

const userStore = {
  namespaced: true,
  state: {
    userInfo: null
  },
  mutations: {
    SET_USER_INFO: (state, user) => {
      state.userInfo = user;
    },
  },
  actions: {
    async getUserInfo({ commit }) {
      await getUser(
        (response) => {
          commit("SET_USER_INFO", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async deleteUserInfo({ commit }) {
      await deleteUser(
        (response) => {
          console.log(response);
          logout();
          commit("SET_USER_INFO", null);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    resetUserInfo({ commit }) {
      logout();
      commit("SET_USER_INFO", null);
    }
  },
};

export default userStore;
