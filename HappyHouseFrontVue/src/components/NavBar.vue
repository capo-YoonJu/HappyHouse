<template>
  <div>
    <b-navbar toggleable="md" type="dark" variant="dark" fixed="top">
      <b-navbar-brand to="/">
        <b-icon-house-door class="mr-2" @click="clickToUp"></b-icon-house-door>
        <strong @click="clickToUp">Happy House</strong>
      </b-navbar-brand>
      <!-- navbar toggler setting -->
      <b-navbar-toggle target="nav-collapse">
        <template #default="{ expanded }">
        <b-icon v-if="expanded" icon="x" style="color: #B2C0CC"></b-icon>
        <b-icon v-else icon="list" style="color: #B2C0CC"></b-icon>
      </template>
      </b-navbar-toggle>
      <!-- navbar items -->
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav class="ml-auto">
          <b-nav-item to="/map">실거래가 정보</b-nav-item>
          <b-nav-item to="/chart/main">오피스텔 전/월세 차트</b-nav-item>
          <b-nav-item to="/board/list">게시판</b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto">
          <div v-if="loginStatus">
            <b-nav-item to="/user/info">마이페이지</b-nav-item>
            <b-nav-item @click="onlogout">로그아웃</b-nav-item>
          </div>
          <div v-else>
            <b-nav-item to="/user/signup">회원가입</b-nav-item>
            <b-nav-item to="/user/login">로그인</b-nav-item>
          </div>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
  export default {
    computed: {
      loginStatus: function() {
        return this.$store.state.userStore.userInfo != null && this.$store.state.userStore.userInfo != undefined;
      }
    },
    methods: {
      onlogout() {
        this.$store.dispatch('userStore/resetUserInfo');
        alert("로그아웃 되었습니다.");
        if (this.$router.currentRoute.name != "Home") {
          this.$router.push({ name: "Home" });
        }
      },
      clickToUp(){
        if (this.$router.currentRoute.path == "/") {
          window.scrollTo(0, 0);
        }
      }
    },
  }
</script>

<style scoped>
  .navbar.navbar-dark.bg-dark {
    background-color: #0C151C !important;
  }
  .nav-link {
    font-family: Arial,"맑은 고딕" !important;
    color: #B2C0CC !important;
    font-weight: bold !important;
  }
  .nav-item {
    display: inline-block;
    margin-right: 0.5rem;
  }
</style>