<template>
    <b-card class="comment-regist-card bg-light border-0 mx-3 mt-3">
        <b-form-group>
            <b-form-textarea
                id="comment-regist-textarea"
                class="bg-light border-0"
                v-model="content"
                ref="content"
                placeholder="답변 등록하기"
                rows="3"
                no-resize
            ></b-form-textarea>
            <b-button 
                squared
                size="sm" 
                variant="outline-dark" 
                class="mr-2 float-right"
                @click="checkValue"
            >등록</b-button>
        </b-form-group>
    </b-card>
</template>

<script>
import http from "@/util/http-common.js";

export default {
    name: "BoardPostCommentRegist",
    data() {
        return {
            no: 0,
            title: "답변 제목",
            content: "",
            writer: "admin",
            regDate: "",
        };
    },
    methods: {
        checkValue() {
            let err = true;
            let msg = "";

            !this.content && ((msg = "내용을 입력해주세요"), (err = false), this.$refs.content.focus());
            
            if (!err) alert(msg);
            else this.registPostComment();
        },
        registPostComment() {
            http
            .post(`/board/${this.$route.params.no}/comments`, {
                title: this.title,
                content: this.content,
                writer: this.writer,
            })
            .then(({ data }) => {
                let msg = "등록 처리 시 문제가 발생했습니다.";
                if (data!=null) {
                    msg = "등록이 완료되었습니다.";
                }
                alert(msg);
                this.createComment(data);
            });
        },
        createComment(data) {
            this.$store.dispatch('createComment', data);
            this.content = '';
        },
    },
}
</script>

<style>

</style>