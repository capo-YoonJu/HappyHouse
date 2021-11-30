<template>
  <div>
    <footer>
      <div class="container">
        <div class="footer-info">
          <div class="footer-info-top">
            <router-link to="/">
              <strong @click="clickToUp">Happy House</strong>
            </router-link><br>
          </div>
          <div class="footer-info-center">
            <span>SSAFY | 공동대표: 배문규, 김도현, 하윤주 | 사업자등록번호: xxx-xx-xxxxx</span><br>
            <span>통신판매업: 제2021-서울강남-0000호 | 서비스 이용문의: xxxx-xxxx | 이메일: ssafy@ssafy.com</span><br>
            <span>주소: 서울 강남구 테헤란로 212</span>
          </div>
          <div class="footer-info-bottom">
            Copyright © SSAFY. All Rights Reserved.
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
  export default {
    methods: {
      clickToUp() {
        if (this.$router.currentRoute.path == "/") {
          window.scrollTo(0, 0);
        }
      }
    },
  }
</script>

<style scoped>
  footer {
    background-color: #303740;
    font-size: 13px;
    color: #bdbdbd;
    padding: 2rem;
    padding-bottom: 3rem;
  }
  .footer-info-top,.footer-info-center,.footer-info-bottom {
    margin-bottom: 8px;
  }
  footer a {
    font-weight: bold;
    color: #bdbdbd;
  }
</style>