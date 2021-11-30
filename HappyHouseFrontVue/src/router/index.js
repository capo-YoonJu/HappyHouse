import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import User from "@/views/User.vue";
import MapView from "@/views/MapView.vue";
import Board from "@/views/Board.vue";
import Chart from "@/views/Chart.vue";
import store from "@/store/index.js";

Vue.use(VueRouter);

const onlyAuthUser = async (to, from, next) => {
  console.log(store._actions["userStore/getUserInfo"]);
  let getUserInfo = store._actions["userStore/getUserInfo"];
  if (
    sessionStorage.getItem("access-token") &&
    (store.state.userStore.userInfo == null || store.state.userStore.userInfo == undefined)
  ) {
    await getUserInfo[0]();
  }
  if (
    store.state.userStore.userInfo == null || store.state.userStore.userInfo == undefined
  ) {
    sessionStorage.removeItem("access-token");
    alert("로그인이 필요한 페이지입니다.");
    router.push({ name: "LogIn" });
  } else {
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/user",
    name: "User",
    component: User,
    redirect: "/",
    children: [
      {
        path: "signup",
        name: "SignUp",
        component: () => import("@/components/user/signup.vue"),
      },
      {
        path: "login",
        name: "LogIn",
        component: () => import("@/components/user/login.vue"),
      },
      {
        path: "info",
        name: "UserInfo",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/info.vue"),
      },
    ],
  },
  {
    path: "/map",
    name: "Map",
    component: MapView,
  },
  {
    path: "/board",
    name: "Board",
    component: Board,
    redirect: "/board/list",
    children: [
      {
        path: "/board/list",
        name: "BoardList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "/board/post",
        name: "BoardPostContent",
        component: () => import("@/components/board/BoardPostContent.vue"),
      },
      {
        path: "/board/post/regist",
        name: "BoardPostRegist",
        component: () => import("@/components/board/BoardPostRegist.vue"),
      },
      {
        path: "/board/post/modify",
        name: "BoardPostModify",
        component: () => import("@/components/board/BoardPostModify.vue"),
      },
      {
        path: "/board/post/delete",
        name: "BoardPostDelete",
        component: () => import("@/components/board/BoardPostDelete.vue"),
      },
    ],
  },
  {
    path: "/chart",
    name: "Chart",
    component: Chart,
    redirect: "/chart/main",
    children: [
      {
        path: "/chart/main",
        name: "ChartMain",
        component: () => import("@/components/chart/ChartMain.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() {
    return { x: 0, y: 0 };
  },
});

export default router;
