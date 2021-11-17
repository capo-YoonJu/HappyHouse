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
import http from "@/util/http-common.js";
import BoardPostCommentRegist from "@/components/board/BoardPostCommentRegist.vue";
import BoardPostCommentItem from "@/components/board/BoardPostCommentItem.vue";
import { mapGetters } from 'vuex';

export default {
    name: "BoardPostCommentList",
    components: {
        BoardPostCommentRegist,
        BoardPostCommentItem,
    },
    // data() {
    //     return {
    //         postComments: []
    //     };
    // },
    created() {
        http.get(`/board/${this.$route.params.no}`, {
            params: {comments: ""}
        })
        .then(({ data }) => {
            this.$store.dispatch('setComments', data);
            this.postComments = data;
        });
    },
    computed: {
        ...mapGetters(['allPostComments']),
        // postComments() {
        //     return this.$store.state.postComments;
        // }
    },
    methods: {
        
    }
}
</script>

<style>
.comments-none {
    text-align: center;
}
</style>