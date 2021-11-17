<template>
    <div class="post-component mx-auto">
        <b-card class="main-post bg-light my-3">
            <b-media>
                <div class="post-header w-100 mb-4">
                    <small>
                        <router-link :to="{name: 'Home'}" class="tag-router router-link" v-for="(tag, index) in post.tags" :key="index">
                            #{{ tag }}
                        </router-link>
                    </small>
                    <h3 class="mt-1 mb-4">{{ post.title }}</h3>
                    <div class="text-muted">
                        <span>{{ post.type }}</span> | 
                        <span>{{ post.writer }}</span> | 
                        <span>{{ post.regDate }}</span>
                    </div>
                </div>

                <hr class="my-4">
                
                <div class="post-body">
                    <div class="post-content">
                        <p>{{ post.content }}</p>
                    </div>
                    <div class="post-writer-buttons float-right mr-3">
                        <b-button-group>
                            <router-link :to="{name: 'BoardPostModify', params: {no: post.no}}">
                                <b-button variant="light" size="sm">
                                    <b-icon icon="pencil-fill" aria-hidden="true"></b-icon>
                                </b-button>
                            </router-link>
                            <router-link :to="{name: 'BoardPostDelete', params: {no: post.no}}">
                                <b-button variant="light" size="sm">
                                    <b-icon icon="trash-fill" aria-hidden="true"></b-icon>
                                </b-button>
                            </router-link>
                            <router-link :to="{name: 'BoardList'}">
                                <b-button variant="light" size="sm">
                                    <b-icon icon="list-ul" aria-hidden="true"></b-icon>
                                </b-button>
                            </router-link>
                        </b-button-group>
                    </div>
                </div>
            </b-media>
        </b-card>
        <board-post-comment-list>
        </board-post-comment-list>
    </div>
</template>

<script>
import http from "@/util/http-common.js";
import BoardPostCommentList from "@/components/board/BoardPostCommentList.vue";

export default {
    name: "BoardPostContent",
    components: {
        BoardPostCommentList,
    },
    data() {
        return {
            post: {}
        };
    },
    created() {
        http
        .get(`/board/${this.$route.params.no}`)
        .then(({ data }) => {
            this.post = data;
            // this.post.content.replace("\r\n","<br>");
        });
    },
}
</script>

<style scoped>
.post-component {
    width: 800px;
}
.post-writer-buttons {
    /* position: absolute; */
    right: 0;
}
</style>