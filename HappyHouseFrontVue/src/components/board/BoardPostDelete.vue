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
import { removePost } from "@/api/board.js";

export default {
    created() {
        removePost(
            `${this.$route.params.no}`,
            ({data}) => {
                let msg = "삭제 처리 시 문제가 발생했습니다.";
                if (data) {
                    msg = "삭제가 완료되었습니다.";
                }
                alert(msg);
                this.$router.push({name: "BoardList"});
            },
            (error) => {
                alert("삭제 처리 시 문제가 발생했습니다.");
                console.log(error);
            }
        );
    },
}
</script>

<style>

</style>