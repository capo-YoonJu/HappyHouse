<template>
    <div class="post-component mx-auto">
        <b-card bg-variant="light" class="border-0">
            <div class="mb-5">
                <h3>질문 등록하기</h3>
                <p>자유롭게 물어보세요!</p>
            </div>
            <b-form-group>
                <b-form-group
                    label-cols-sm="3"
                    label-align-sm="right"
                >
                    <b-form-select 
                        v-model="type" 
                        :options="typeOptions" 
                        size="sm" 
                        class="col-md-3" 
                        style="width: 150px;"
                    ></b-form-select>
                    <!-- <b-form-input 
                        id="modify-post-title"
                        placeholder="제목을 입력하세요."
                        size="sm"
                        class="col-md-3"
                        style="width: 150px;"
                    ></b-form-input> -->
                </b-form-group>

                <b-form-group
                    label-cols-sm="3"
                    label-align-sm="right"
                >
                    <b-form-input 
                        id="regist-post-title"
                        v-model="title"
                        ref="title" 
                        placeholder="제목을 입력하세요."
                    ></b-form-input>
                </b-form-group>

                <b-form-group
                    label-cols-sm="3"
                    label-align-sm="right"
                >
                    <b-form-textarea
                        id="textarea-no-resize"
                        v-model="content"
                        ref="content"
                        placeholder="질문을 입력하세요"
                        rows="10"
                        no-resize
                    ></b-form-textarea>
                </b-form-group>

                <b-form-group
                    label-for="regist-post-tags-input"
                    label-cols-sm="3"
                    label-align-sm="right"
                >
                    <b-form-tags
                        input-id="regist-post-tags-input"
                        v-model="tags"
                        separator=" ,;"
                        :tag-validator="tagValidator"
                        placeholder=" 태그를 입력하세요."
                        remove-on-delete
                    ></b-form-tags>
                </b-form-group>

                <b-form-group
                    class="float-right mt-4 mx-3"
                >
                    <b-button 
                        squared
                        size="sm" 
                        variant="outline-primary" 
                        class="mx-1"
                        @click="checkValue"
                    >등록</b-button>
                    <b-button 
                        squared
                        size="sm" 
                        variant="outline-secondary" 
                        class="mx-1"
                        @click="moveBoardList"
                    >목록</b-button>
                </b-form-group>
            </b-form-group>
        </b-card>
    </div>
</template>

<script>
import http from "@/util/http-common.js";

export default {
    name: "BoardPostRegist",
    data() {
        return {
            no: 0,
            type: null,
            typeOptions: [
                { value: null, text: '선택' },
                { value: "공지", text: '공지사항' },
                { value: "문의", text: '문의사항' },
                { value: "게시글", text: '게시글' },
            ],
            title: "",
            content: "",
            writer: "ssafy",
            regDate: "",
            tags: [],
        };
    },
    methods: {
        tagValidator(tag) {
            return tag.length >= 2 && tag.length < 6
        },
        checkValue() {
            let err = true;
            let msg = "";

            !this.type && ((msg = "분류 선택은 필수입니다!"), (err = false), this.$refs.type.focus());
            err && !this.title && ((msg = "제목을 입력해주세요"), (err = false), this.$refs.title.focus());
            err && !this.content && ((msg = "내용을 입력해주세요"), (err = false), this.$refs.content.focus());
            
            if (!err) alert(msg);
            else this.registPost();
        },

        registPost() {
            http
            .post("/board", {
                type: this.type,
                title: this.title,
                content: this.content,
                writer: this.writer,
                tags: this.tags,
            })
            .then(({ data }) => {
                let msg = "등록 처리 시 문제가 발생했습니다.";
                if (data) {
                    msg = "등록이 완료되었습니다.";
                }
                alert(msg);
                this.moveBoardList();
            });
        },
        moveBoardList() {
            this.$router.push({name: "BoardList"});
        }
    },
}
</script>

<style>
.post-component {
    width: 800px;
}
</style>