<template>
    <b-card class="comment my-3 mx-3">
        <b-media>
            <template #aside>
                <b-icon icon="person-circle" class="h4"></b-icon>
            </template>
            <div class="d-flex w-100 justify-content-between">
                <h5 class="mt-0 d-inline">{{ writer }}</h5>
                <small>{{ regDate }}</small>
            </div>
            <b-form-group>
                <b-form-textarea
                    id="comment-modify-textarea"
                    class="bg-light border-0"
                    v-model="content"
                    ref="content"
                    placeholder="답변 수정하기"
                    rows="3"
                    no-resize
                    :disabled="!modifyToggle"
                >{{ content }}
                </b-form-textarea>
            </b-form-group>
        </b-media>
        <div class="post-writer-buttons float-right mr-3">
            <b-button-group>
                <b-button variant="light" size="sm" @click="activateModify">
                    <b-icon :icon="modifyIcon" aria-hidden="true"></b-icon>
                </b-button>
                <b-button variant="light" size="sm" @click="deleteComment">
                    <b-icon icon="trash" aria-hidden="true"></b-icon>
                </b-button>
            </b-button-group>
        </div>
    </b-card>
</template>

<script>
import { getCommentsByPostNo, updateComment, removeComment } from "@/api/board.js";

export default {
    name: "BoardPostCommentItem",
    props: {
        no: Number,
        post_no: Number,
        title: String,
        content: String,
        writer: String,
        regDate: String,
    },
    data() {
        return {
            modifyToggle: false,
            modifyIcon: "pencil",
        };
    },
    methods: {
        activateModify() {
            if (!this.modifyToggle) {
                this.modifyToggle = true;
                this.modifyIcon = "check2-circle";
            } else {
                this.modifyComment();
                this.modifyToggle = false;
                this.modifyIcon = "pencil";
            }
        },
        modifyComment() {
            updateComment(
                `${this.no}`,
                {
                    no: this.no,
                    post_no: this.post_no,
                    title: this.title,
                    content: this.content,
                    writer: this.writer,
                    regDate: this.regDate
                },
                (response) => {
                    let msg = "댓글 수정 처리시 문제가 발생했습니다.";
                    if (response.data!=null) {
                        msg = "댓글이 수정되었습니다.";
                        this.setComment();
                    }
                    alert(msg);
                },
                (error) => {
                    alert("댓글 수정 처리 시 문제가 발생했습니다.");
                    console.log(error);
                }
            );
        },
        deleteComment() {
            removeComment(
                `${this.no}`,
                ({data}) => {
                    let msg = "댓글 삭제 처리 시 문제가 발생했습니다.";
                    if (data) {
                        msg = "댓글이 삭제되었습니다.";
                        this.setComment();
                    }
                    alert(msg);
                },
                (error) => {
                    alert("댓글 삭제 처리 시 문제가 발생했습니다.");
                    console.log(error);
                }
            );
        },
        setComment() {
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
    }
}
</script>

<style>

</style>