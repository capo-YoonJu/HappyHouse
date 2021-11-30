<template>
    <div class="chart-root-component">
        <div class="chart-main-body">
            <div class="recommend-calendar-tab">
                <b-row no-gutters>
                    <b-col md="9" class="mr-5">
                        <!-- <b-card> -->
                            <officetel-rent-dash-board>
                            </officetel-rent-dash-board>
                        <!-- </b-card> -->
                    </b-col>
                    <b-col md="2">
                        <chart-lots-calendar>
                        </chart-lots-calendar>
                    </b-col>
                </b-row>
            </div>
        </div>
    </div>
</template>

<script>
import OfficetelRentDashBoard from "@/components/chart/OfficetelRentDashBoard.vue";
import ChartLotsCalendar from "@/components/chart/ChartLotsCalendar.vue";

export default {
    name: "ChartMain",
    components: {
        OfficetelRentDashBoard,
        ChartLotsCalendar
    },
}
</script>

<style scoped>
.chart-root-component {
    margin-top: 100px;
    text-align: left;
}
.card {
    background-color: WhiteSmoke;
}
</style>