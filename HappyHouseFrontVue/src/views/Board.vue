<template>
    <div class="container">
        <h2>[Board]</h2>
        <div class="section mb-5">
            <router-view></router-view>
            <!-- <free-main></free-main> -->
        </div>
    </div>
</template>

<script>
// import BoardList from "@/components/board/BoardList.vue";

export default {
    name: "Board",
    data() {
        return {
        };
    },
}
</script>

<style>

</style>