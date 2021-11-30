<template>
  <div>
    <starter></starter>
    <section class="main-section">
      <b-container>
        <div class="sub-section map">
          <search-bar></search-bar>
        </div>
        <div class="sub-section chart">
          <chart-card></chart-card>
        </div>
        <div class="sub-section board">
          <board-table></board-table>
        </div>
      </b-container>
    </section>
  </div>
</template>

<script>
  import Starter from '@/components/index/Starter.vue';
  import SearchBar from '@/components/index/SearchBar.vue';
  import ChartCard from '@/components/index/ChartCard.vue';
  import BoardTable from '@/components/index/BoardTable.vue';
  export default {
    components: {
      Starter,
      SearchBar,
      ChartCard,
      BoardTable,
    }
  }
</script>

<style scoped>
  .main-section {
    padding: 3.5rem 0 1rem 0;
  }
  .sub-section {
    margin-bottom: 70px;
  }
</style>