<template>
  <div>
    <div class="wrapper">
      <Search @search-apt="receiveQuery" />
    </div>
    <div class="wrapper">
      <Map ref="mapComponent" />
    </div>
  </div>
</template>

<script>
import Search from "@/components/map/Search.vue";
import Map from "@/components/map/Map.vue";

export default {
  data() {
    return {
      query: "",
    };
  },
  mounted() {
    if (this.$route.params.region != undefined) {
      this.$refs.mapComponent.searchDeal(`dong=${this.$route.params.region}&apt=`);
    }
  },
  methods: {
    receiveQuery(query) {
      this.$refs.mapComponent.searchDeal(query);
    },
  },
  components: {
    Search,
    Map,
  },
};
</script>

<style scoped>
  .wrapper {
    margin: 1.5rem 0 1.5rem 2rem
  }
</style>
