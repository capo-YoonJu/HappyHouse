<template>
    <div class="post-component mx-auto">
        <b-card class="main-post bg-light my-3">
            <b-media class="p-2">
                <div class="post-header w-100 mb-4">
                    <small>
                        <span
                            class="tag-router" 
                            v-for="(tag, index) in post.tags" 
                            :key="index"
                            @click="setTags(tag)"
                        >
                            #{{ tag }}
                        </span>
                    </small>
                    <h3 class="mt-2 mb-4">{{ post.title }}</h3>
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
import BoardPostCommentList from "@/components/board/BoardPostCommentList.vue";
import { getPostByNo } from "@/api/board.js";

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
        getPostByNo(
            `${this.$route.params.no}`,
            ({ data }) => {
                this.post = data;
            },
            (error) => {
                console.log(error);
            }
        );
    },
    methods: {
        setTags(tag) {
            this.$store.dispatch('removeAllTags');
            this.$store.dispatch('setTags', tag);
            this.$router.push({name: "BoardList"});
        }
    }
}
</script>

<style scoped>
.post-component {
    width: 800px;
}
.post-writer-buttons {
    right: 0;
}
.card {
    border-radius: 1.5em;
    color: #2c3e50;
    box-shadow: 0 0 20px 0 Gainsboro;
    max-width: 800px;
}
.tag-router {
    color: SeaGreen;
    cursor: pointer;
    font-weight: bold;
}
.tag-router:hover {
    color: MediumSeaGreen;
}
</style>