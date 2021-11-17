<template>
    <div class="content">
        <div class="board-list-header">
            <h3>게시판</h3>
        </div>
        <div class="board-list-body">
            <b-row no-gutters>
                <b-col md="9">
                    <div class="board-list-list">
                        <div v-if="posts.length">
                            <b-card-group>
                                <board-list-item
                                    v-for="(post, index) in posts" :key="index"
                                    v-bind="post"
                                ></board-list-item>
                            </b-card-group>
                        </div>
                        <div v-else>등록된 글이 없습니다.</div>
                    </div>
                </b-col>
                <b-col md="3">
                    <div class="board-list-user ml-5">
                        <b-card>
                            <b-icon icon="person-check-fill" aria-hidden="true" class="h2"></b-icon>
                            <b-card-text>
                            김싸피 (ssafylove06) <br>
                            서울시 강남구 역삼동
                            </b-card-text>
                            <b-button variant="outline-warning" @click="movePostRegist">문의하기</b-button>
                        </b-card>
                    </div>
                </b-col>
            </b-row>
        </div>
    </div>
</template>

<script>
import http from "@/util/http-common.js";
import BoardListItem from "@/components/board/BoardListItem.vue";

export default {
    name: "BoardList",
    components: {
        BoardListItem,
    },
    data() {
        return {
            posts: []
        };
    },
    created() {
        http.get("/board")
        .then(({ data }) => {
            this.posts = data;
        });
    },
    methods: {
        movePostRegist() {
            this.$router.push({name: "BoardPostRegist"});
        },
    }
}
</script>

<style scoped>
.board-list-header {
    text-align: center;
}
</style>