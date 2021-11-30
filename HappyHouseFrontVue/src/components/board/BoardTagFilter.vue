<template>
    <div class="board-tag-filter flex-column align-items-start mx-auto my-2">
        <b-card>
            <div class="mb-3">
                <b-icon icon="tags-fill" aria-hidden="true"></b-icon>
                tags
            </div>
            <b-badge
                v-for="(tag, index) in allTags" 
                :key="index"
                class="p-1 m-1"
            >
                #{{ tag }} 
                <button @click="removeTag(tag)"> &times; </button>
            </b-badge>
        </b-card>
    </div>
</template>

<script>
import { listPosts, getPostsByTags } from "@/api/board.js";
import { mapGetters } from 'vuex';

export default {
    name: "BoardTagFilter",
    computed: {
        ...mapGetters(['allTags']),
    },
    methods: {
        removeTag(tag) {
            this.$store.dispatch('removeTag', tag);
            if (this.allTags.length==0) this.getAllPosts();
            else this.tagSearch();
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
        getAllPosts() {
            listPosts(
                (response) => {
                    this.$store.dispatch('setPosts', response.data);
                },
                (error) => {
                    console.log(error);
                }
            );
        }
    }
}
</script>

<style scoped>
.board-tag-filter {
    text-align: left;
    max-width: 800px;
}
.badge {
    background-color: darkseagreen;
}
.badge:hover {
    background-color: seagreen;
}
button {
    all: unset;
}
</style>