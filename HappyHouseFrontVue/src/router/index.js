import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Board from "@/views/Board.vue";
import BoardList from "@/components/board/BoardList.vue";
import BoardPostContent from "@/components/board/BoardPostContent.vue";
import BoardPostRegist from "@/components/board/BoardPostRegist.vue";
import BoardPostModify from "@/components/board/BoardPostModify.vue";
import BoardPostDelete from "@/components/board/BoardPostDelete.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
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
        component: BoardList,
      },
      {
        path: "/board/post",
        name: "BoardPostContent",
        component: BoardPostContent,
      },
      {
        path: "/board/post/regist",
        name: "BoardPostRegist",
        component: BoardPostRegist,
      },
      {
        path: "/board/post/modify",
        name: "BoardPostModify",
        component: BoardPostModify,
      },
      {
        path: "/board/post/delete",
        name: "BoardPostDelete",
        component: BoardPostDelete,
      },
    ]
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
