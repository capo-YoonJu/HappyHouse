<template>
  <div class="table-wrapper">
    <div class="table-header">
      <h4>게시판</h4>
      <b-button variant="primary" @click="moveToBoard">더보기</b-button>
    </div>
    <b-table hover :items="allPosts.slice(start, end)" :fields="fields" @row-clicked="moveToPost"></b-table>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex';
  import { listPosts } from "@/api/board.js";

  export default {
    data() {
      return {
        start: 0,
        end: 10,
        fields: [
          {
            key: 'type',
            label: '유형',
            tdClass: 'col-type'
          },
          {
            key: 'title',
            label: '제목',
            tdClass: 'col-title'
          },
          {
            key: 'content',
            label: '내용',
            tdClass: 'col-content'
          },
          {
            key: 'regDate',
            label: '작성 날짜',
            tdClass: 'col-regDate'
          }
        ]
      }
    },
    created() {
      this.getAllPosts();
    },
    computed: {
      ...mapGetters(['allPosts']),
    },
    methods: {
      getAllPosts() {
        listPosts(
          (response) => {
            this.$store.dispatch('setPosts', response.data);
          },
          (error) => {
            console.log(error);
          }
        );
      },
      moveToBoard() {
        this.$router.push({name: 'BoardList'});
      },
      moveToPost(item) {
        this.$router.push({name: 'BoardPostContent', params: {no: item.no}});
      }
    }
  }
</script>

<style scoped>
  .table-wrapper {
    width: 800px;
    margin: 0 auto;
  }
  .table-header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 1rem;
  }
  h4 {
    text-align: left;
    font-weight: bold;
  }
  button {
    font-weight: bold;
    min-width: 5rem;
  }
</style>

<style>
  td {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .col-content {
    max-width: 150px;
  }
</style>