<template>
    <div class="board-list-item flex-column align-items-start mb-3">
        <router-link :to="{name: 'BoardPostContent', params: {no: no}}" class="router-link">
            <b-card no-body class="overflow-hidden">
                <b-row no-gutters>
                    <b-col md="3">
                        <b-card-img src="https://picsum.photos/400/400/?image=20" alt="Image" class="rounded-0"></b-card-img>
                    </b-col>
                    <b-col md="9">
                        <b-card-body>
                            <div class="d-flex w-100 justify-content-between mb-2">
                                <div class="card-header">
                                    <span class="type-badge">
                                        <b-icon icon="volume-up-fill" aria-hidden="true"></b-icon>
                                        {{ type }}
                                    </span>
                                    <h4 class="card-title d-inline ml-3">{{ title }}</h4>
                                </div>
                                <small>{{ regDate }}</small>
                            </div>
                            <b-card-text>
                                {{ content }}
                            </b-card-text>
                            <div class="mt-5 align-self-end">
                                <b-badge  
                                    class="mx-1" 
                                    v-for="(tag, index) in tags" 
                                    :key="index"
                                    @click.prevent="setTags(tag)"
                                >
                                    #{{ tag }}
                                </b-badge>
                            </div>
                        </b-card-body>
                    </b-col>
                </b-row>
            </b-card>
        </router-link>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { getPostsByTags } from "@/api/board.js";

export default {
    name: "BoardListItem",
    props: {
        no: Number,
        type: String,
        title: String,
        content: String,
        writer: String,
        regDate: String,
        tags: Array,
    },
    computed: {
        ...mapGetters(['allTags']),
    },
    methods: {
        setTags(tag) {
            if (this.allTags.includes(tag)) return;
            this.$store.dispatch('setTags', tag);
            this.tagSearch();
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
    }
}
</script>

<style scoped>
.board-list-item {
    text-align: left;
    width: 800px;
}
.card:hover {
    box-shadow: 0 0 3px 0 Gainsboro;
}
.card {
    border-radius: 1em;
    color: #2c3e50;
    box-shadow: 0 0 10px 0 Gainsboro;
    max-width: 800px;
}
.card-img {
    height: 100%;
}
.card-header {
    display:table;
    background-color: white;
    border: none;
}
.type-badge {
    display:table-cell;
    vertical-align:middle;
    border-radius: 0.5em;
    font-size: smaller;
    color: rgb(60, 118, 167);
}
.badge {
    background-color: darkseagreen;
}
.badge:hover {
    background-color: seagreen;
}
.tag-div {
    position: absolute;
    bottom: 0;
}
.tag-router, .tag-router:hover {
    color: seashell;
}
.router-link:hover {
    text-decoration: none;
}
</style>