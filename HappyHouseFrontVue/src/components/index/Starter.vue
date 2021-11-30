<template>
  <header class="hero-section">
    <swiper class="swiper" :options="swiperOption">
      <swiper-slide>
        <div class="slide-wrapper house">
          <div class="house-slide">
            <h5 class="cap">아파트/주택 실거래 정보 플랫폼</h5>
            <h1 class="title">
              <span>전세/월세의 끝은</span><br>
              <span>결국 나락이니까</span>
            </h1>
            <h4 class="sub-title">
              원하는 지역의 매매 거래 정보를<br>
              Happy House에서 만나보세요.
            </h4>
            <router-link to="/user/login" class="btn btn-primary btn-start"><span>시작하기</span></router-link>
          </div>
        </div>
      </swiper-slide>
      <swiper-slide>
        <div class="slide-wrapper frog">
          <b-container>
              <b-row>
                <b-col>
                  <div class="frog-slide-left">
                    <h5 class="cap">아파트/주택 실거래 정보 플랫폼</h5>
                    <h2 class="title">
                      <span>지금 당신에게 필요한건</span><br>
                      <span>의식酒입니다.</span>
                    </h2>
                    <h4 class="sub-title">
                      원하는 지역의 매매 거래 정보를<br>
                      Happy House에서 만나보세요.
                    </h4>
                    <router-link to="/user/login" class="btn btn-primary btn-start"><span>시작하기</span></router-link>
                  </div>
                </b-col>
                <b-col class="frog-slide-right">
                  <img src="../../assets/image/frog.jpg" alt="">
                </b-col>
              </b-row>
            </b-container>
        </div>
      </swiper-slide>
      <swiper-slide>
        <div class="slide-wrapper tag">
          <div class="tag-slide">
            <h5 class="cap">아파트/주택 실거래 정보 플랫폼</h5>
            <h1 class="title">
              <span>이번 생에 내집 마련은</span><br>
              <span>宅도 없지!!</span>
            </h1>
            <h4 class="sub-title">
              원하는 지역의 매매 거래 정보를<br>
              Happy House에서 만나보세요.
            </h4>
            <router-link to="/user/login" class="btn btn-primary btn-start"><span>시작하기</span></router-link>
          </div>
        </div>
      </swiper-slide>
      <div class="swiper-pagination" slot="pagination"></div>
      <div class="swiper-button-prev" slot="button-prev"></div>
      <div class="swiper-button-next" slot="button-next"></div>
    </swiper>
  </header>
</template>

<script>
  import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
  import 'swiper/css/swiper.css'
  export default {
    name: 'swiper-example-loop',
    title: 'Loop mode / Infinite loop',
    components: {
      Swiper,
      SwiperSlide
    },
    data() {
      return {
        swiperOption: {
          slidesPerView: 1,
          spaceBetween: 0,
          loop: true,
          autoplay: {
            delay: 10000,
            disableOnInteraction: false
          },
          pagination: {
            el: '.swiper-pagination',
            clickable: true
          },
          navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
          }
        }
      }
    }
  }
</script>

<style scoped>
  .slide-wrapper {
    color: white;
    padding: 4rem 0 2.5rem 0;
    height: 450px;
  }
  .slide-wrapper::before {
    content: "";
    opacity: 0.3;
    position: absolute;
    top: 0px;
    left: 0px;
    right: 0px;
    bottom: 0px;
    background-color: black;
  }
  .slide-wrapper.house {
    background-image: url("../../assets/image/house.jpg");
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
  }
  .slide-wrapper.frog,.frog-slide-right {
    background-color: #0099dc;
  }
  .frog-slide-right::before {
    content: "";
    opacity: 0.3;
    position: absolute;
    top: 0px;
    left: 0px;
    right: 0px;
    bottom: 0px;
    background-color: black;
  }
  .slide-wrapper.tag {
    background-image: url("../../assets/image/tag.jpg");
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
  }
  .house-slide,.frog-slide-left,.tag-slide {
    position: relative;
  }
  .cap,.title,.sub-title,.btn {
    font-weight: bold;
    margin-bottom: 2rem;
  }
  .btn-start {
    min-width: 9rem;
  }
</style>