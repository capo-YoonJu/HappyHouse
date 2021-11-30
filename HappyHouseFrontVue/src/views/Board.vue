<template>
    <div class="container board-root-container">
        <div class="section mt-5 mb-5">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
export default {
    name: "Board",
}
</script>

<style scoped>
.board-root-container {
    margin-top: 80px;
    text-align: left;
}
</style>