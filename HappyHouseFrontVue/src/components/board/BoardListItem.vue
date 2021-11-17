<template>
    <div class="flex-column align-items-start mx-auto my-2" style="width: 800px">
        <router-link :to="{name: 'BoardPostContent', params: {no: no}}" class="router-link">
            <b-card no-body class="overflow-hidden" style="max-width: 800px;">
                <b-row no-gutters>
                    <b-col md="3">
                        <b-card-img src="https://picsum.photos/400/400/?image=20" alt="Image" class="rounded-0"></b-card-img>
                    </b-col>
                    <b-col md="9">
                        <b-card-body>
                            <div class="d-flex w-100 justify-content-between">
                                <div>
                                    <b-badge variant="primary" class="p-1 mb-3 mr-3 ">{{ type }}</b-badge>
                                    <h4 class="card-title d-inline">{{ title }}</h4>
                                </div>
                                <small>{{ regDate }}</small>
                            </div>
                            <b-card-text>
                                {{ content }}
                            </b-card-text>
                            <div class="float-bottom w-100 mb-0">
                                <b-badge pill variant="secondary" class="tag-badge mx-1" v-for="(tag, index) in tags" :key="index">
                                    <router-link :to="{name: 'Home'}" class="tag-router router-link">
                                        {{ tag }}
                                    </router-link>
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
}
</script>

<style scoped>
.card:hover {
    background-color: rgb(233, 233, 233);
}
.card {
    color: #2c3e50;
}
.tag-div {
    position: absolute;
    bottom: 0;
}
.tag-router, .tag-router:hover {
    color: seashell;
}
.tag-badge:hover {
    background-color: darkgray;
}
.router-link:hover {
    text-decoration: none;
}
</style>