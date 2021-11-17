<template>
    <div>
        <div class="d-flex justify-content-center mt-5 mb-3">
            <b-spinner label="Loading..."></b-spinner>
        </div>
        <div class="d-flex justify-content-center mt-4 mb-3">
            <strong>삭제중...</strong>
        </div>
    </div>
</template>

<script>
import http from "@/util/http-common.js";

export default {
    created() {
        http
        .delete(`/board/${this.$route.params.no}`)
        .then(({data}) => {
            let msg = "삭제 처리시 문제가 발생했습니다.";
            if (data) {
                msg = "삭제가 완료되었습니다.";
            }
            alert(msg);
            this.$router.push({name: "BoardList"});
        })
        .catch(({error}) => {
            console.log("delete problem");
            console.log(error.response.status);
        });
    },
}
</script>

<style>

</style>