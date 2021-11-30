<template>
    <div class="post-comments mt-5 mx-auto">
        <b-card-group>
            <b-card>
                <div class="board-comment-header">
                    <board-post-comment-regist></board-post-comment-regist>
                </div>
                <div class="board-comment-body">
                    <div class="board-comment-list">
                        <div v-if="allPostComments.length">
                                <board-post-comment-item
                                    v-for="(postComment, index) in allPostComments" :key="index"
                                    v-bind="postComment"
                                ></board-post-comment-item>
                        </div>
                        <div v-else>
                            <div class="comments-none my-4">
                                등록된 답변이 없습니다.
                            </div>
                        </div>
                    </div>
                </div>
            </b-card>
        </b-card-group>
    </div>
</template>

<script>
import BoardPostCommentRegist from "@/components/board/BoardPostCommentRegist.vue";
import BoardPostCommentItem from "@/components/board/BoardPostCommentItem.vue";
import { getCommentsByPostNo } from "@/api/board.js";
import { mapGetters } from 'vuex';

export default {
    name: "BoardPostCommentList",
    components: {
        BoardPostCommentRegist,
        BoardPostCommentItem,
    },
    created() {
        getCommentsByPostNo(
            `${this.$route.params.no}`,
            (response) => {
                this.$store.dispatch('setComments', response.data);
            },
            (error) => {
                console.log(error);
            }
        );
    },
    computed: {
        ...mapGetters(['allPostComments']),
    },
}
</script>

<style scoped>
.comments-none {
    text-align: center;
}
</style>