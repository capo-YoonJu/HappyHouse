<template>
  <div>
    <h4>어떤 지역의 매매 거래 정보를 찾고 계세요?</h4>
    <div class="search-bar">
      <div class="input-wrapper">
        <b-form-input type="search" placeholder="원하시는 지역명을 입력해주세요." v-model="word"></b-form-input>
        <b-button variant="outline-primary" @click="searchApt">
          <b-icon icon="search"></b-icon>
        </b-button>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        word: ""
      }
    },
    methods: {
      searchApt() {
        this.$router.push({ name: "Map", params: {region: this.word} });
      }
    },
  }
</script>

<style scoped>
  h4 {
    font-weight: bold;
  }
  .search-bar {
    display: flex;
    height: 50px;
    border: 2px solid #dfe1e5;
    width: 380px;
    border-radius: 24px;
    padding: 0 6px 0 6px;
    margin: 2rem auto;
  }
  .input-wrapper{
    display: flex;
    padding: 6px 7px 0 10px;
    width: 100%;
  }
  .button-wrapper{
  }
  input {
    height: 34px;
    border: none;
    margin-right: 0.5rem;
  }
  button {
    height: 34px;
    border: none;
  }
</style>