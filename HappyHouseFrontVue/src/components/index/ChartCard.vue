<template>
  <div>
    <h4>오피스텔 전/월세 차트</h4>
    <div class="card-wrapper">
      <div class="filter">
        <h3 class="title">
          <span>오피스텔 전세와 월세를</span><br>
          <span>차트로 확인해보세요.</span>
        </h3>
        <h4 class="sub-title">
          지역별 전/월세의 평균가와 기간별<br>
          추이를 확인할 수 있습니다.
        </h4>
        <router-link to="/chart/main" class="btn btn-primary btn-start"><span>전/월세 알아보기</span></router-link>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    
  }
</script>

<style scoped>
  .title,.sub-title {
    margin-bottom: 2rem;
  }
  h3,h4 {
    font-weight: bold;
  }
  .card-wrapper {
    border-radius: 30px;
    width: 900px;
    height: 300px;
    background-image: url("../../assets/image/chart.jpg");
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
    margin: 2rem auto 0 auto;
  }
  .filter {
    padding: 2.5rem;
    background-color: rgba(255, 2555, 255, 0.6);
  }
  .btn {
    font-weight: bold;
    min-width: 10rem;
  }
</style>