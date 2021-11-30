<template>
  <div>
    <b-form inline>
      <label class="sr-only" for="inline-form-input-dong">지역명</label>
      <b-form-input
        id="inline-form-input-dong"
        class="mb-2 mr-sm-2 mb-sm-0"
        v-model="dong"
        placeholder="지역명을 입력하세요."
      ></b-form-input>

      <label class="sr-only" for="inline-form-input-aptname">아파트명</label>
      <b-input-group class="mb-2 mr-sm-2 mb-sm-0">
        <b-form-input
          id="inline-form-input-aptname"
          v-model="apt"
          placeholder="아파트명을 입력하세요"
        ></b-form-input>
      </b-input-group>

      <b-button variant="primary" @click="search">Search</b-button>
    </b-form>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
export default {
  data() {
    return {
      dong: "",
      apt: "",
    };
  },
  methods: {
    search() {
      if (!this.dong.trim() && !this.apt.trim()) {
        alert("지역명 혹은 아파트명을 입력해주세요.");
      } else {
        this.rankDong();
        this.$emit(
          "search-apt",
          `dong=${this.dong.trim()}&apt=${this.apt.trim()}`
        );
      }
    },
    rankDong() {
      let request = `/house/search`;
      http
        .post(request, { dong: this.dong.trim() })
        .catch((error) => alert(`처리 중 문제가 발생하였습니다. ${error}`));
    },
    rankApt() {},
  },
};
</script>
