<template>
    <div class="post-component mx-auto">
        <b-card bg-variant="light" class="border-0">
            <div class="mb-5">
                <h3>게시글 수정하기</h3>
            </div>
            <b-form-group class="mx-auto px-3">
                <b-form-group>
                    <b-form-select 
                        v-model="type" 
                        :options="typeOptions" 
                        size="sm" 
                        class="col-md-3" 
                        style="width: 150px;"
                    ></b-form-select>
                </b-form-group>

                <b-form-group>
                    <b-form-input 
                        id="modify-post-title"
                        v-model="title"
                        ref="title" 
                        placeholder="제목을 입력하세요."
                    ></b-form-input>
                </b-form-group>

                <b-form-group>
                    <b-form-textarea
                        id="textarea-no-resize"
                        v-model="content"
                        ref="content"
                        placeholder="질문을 입력하세요"
                        rows="10"
                        no-resize
                    ></b-form-textarea>
                </b-form-group>

                <b-form-group label-for="modify-post-tags-input">
                    <b-form-tags
                        input-id="modify-post-tags-input"
                        v-model="tags"
                        separator=" ,;"
                        :tag-validator="tagValidator"
                        placeholder=" 태그를 입력하세요."
                        remove-on-delete
                    ></b-form-tags>
                </b-form-group>

                <b-form-group class="float-right mt-4 mx-3">
                    <b-button 
                        squared
                        size="sm" 
                        variant="outline-primary" 
                        class="mx-1"
                        @click="checkValue"
                    >수정</b-button>
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
import { getPostByNo, updatePost } from "@/api/board.js";

export default {
    name: "BoardPostModify",
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
            writer: "",
            regDate: "",
            tags: [],
        };
    },
    created() {
        getPostByNo(
            `${this.$route.params.no}`,
            ({ data }) => {
                this.no = data.no;
                this.type = data.type;
                this.title = data.title;
                this.content = data.content;
                this.writer = data.writer;
                this.regDate = data.regDate;
                this.tags = data.tags;
            },
            (error) => {
                console.log(error);
            }
        );
    },
    methods: {
        tagValidator(tag) {
            return tag.length >= 2 && tag.length < 6
        },
        checkValue() {
            let err = true;
            let msg = "";

            !this.type && ((msg = "분류 선택은 필수입니다!"), (err = false), this.$refs.writer.focus());
            err && !this.title && ((msg = "제목을 입력해주세요"), (err = false), this.$refs.title.focus());
            err && !this.content && ((msg = "내용을 입력해주세요"), (err = false), this.$refs.content.focus());

            if (!err) alert(msg);
            else this.modifyArticle();
        },
        modifyArticle() {
            updatePost(
                `${this.$route.params.no}`,
                {
                    no: this.no,
                    type: this.type,
                    title: this.title,
                    content: this.content,
                    writer: this.writer,
                    regDate: this.regDate,
                    tags: this.tags
                },
                ({ data }) => {
                    let msg = "수정 처리 시 문제가 발생했습니다.";
                    if (data) {
                        msg = "수정이 완료되었습니다.";
                    }
                    alert(msg);
                    this.moveBoardList();
                },
                (error) => {
                    alert("수정 처리 시 문제가 발생했습니다.");
                    console.log(error);
                }
            );
        },
        moveBoardList() {
            this.$router.push({name: "BoardList"});
        }
    }
}
</script>

<style scoped>
.post-component {
    width: 600px;
}
.card {
    border-radius: 1.5em;
    color: #2c3e50;
    box-shadow: 0 0 20px 0 Gainsboro;
    max-width: 800px;
}
</style>