<template>
    <div class="content">
        <div class="board-list-header">
            <h3>게시판</h3>
        </div>
        <div class="board-list-body">
            <div>
                <b-row no-gutters>
                    <b-col class="col-lg-8 col-12 order-lg-1 order-2 mr-auto mt-5">
                        <div class="board-list-posts">
                            <div v-if="allPosts.length">
                                <b-card-group>
                                    <board-list-item
                                        v-for="(post, index) in allPosts" :key="index"
                                        v-bind="post"
                                    ></board-list-item>
                                </b-card-group>
                            </div>
                            <div v-else>등록된 글이 없습니다.</div>
                        </div>
                    </b-col>
                    <b-col class="col-lg-3 col-12 order-lg-2 order-1 ml-auto mt-5">
                        <div class="board-list-user">
                            <b-card>
                                <b-icon icon="person-check-fill" aria-hidden="true" class="h2" ></b-icon>
                                <b-card-text>
                                김싸피 (ssafylove06) <br>
                                서울시 강남구 역삼동
                                </b-card-text>
                                <b-button variant="outline-warning" @click="movePostRegist">문의하기</b-button>
                            </b-card>
                        </div>
                        <div>
                            <board-tag-filter></board-tag-filter>
                        </div>
                    </b-col>
                </b-row>
            </div>
        </div>
    </div>
</template>

<script>
import BoardTagFilter from "@/components/board/BoardTagFilter.vue";
import BoardListItem from "@/components/board/BoardListItem.vue";
import { mapGetters } from 'vuex';
import { listPosts, getPostsByTags } from "@/api/board.js";

export default {
    name: "BoardList",
    components: {
        BoardTagFilter,
        BoardListItem,
    },
    created() {
        if (this.allTags.length==0) {
            this.getAllPosts();
        } else {
            this.tagSearch();
        }
    },
    computed: {
        ...mapGetters(['allPosts', 'allTags']),
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
        tagSearch() {
            getPostsByTags(
                { tag: this.allTags },
                (response) => {
                    this.$store.dispatch('setPosts', response.data);
                },
                (error) => {
                    console.log(error);
                }
            );
        },
        movePostRegist() {
            this.$router.push({name: "BoardPostRegist"});
        },
    }
}
</script>

<style scoped>

</style>