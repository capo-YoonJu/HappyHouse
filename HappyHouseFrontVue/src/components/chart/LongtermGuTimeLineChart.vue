<template>
    <div class="m-4">
        <span>
            <line-chart :options="options" :chart-data="datacollection" :width="200" :height="90"></line-chart>
        </span>
        <small class="mt-1">단위 : 만원</small>
    </div>
</template>

<script>
import LineChart from '@/chartModules/LineChart.vue'
import { mapState } from 'vuex'

const chartStore = "chartStore";

export default {
    name: "LongtermGuTimeLineChart",
    components: {
        LineChart
    },
    props: {
        isPossibleRent: Boolean,
    },
    data() {
        return {
            guName: '',
            datacollection: {},
            options: {},
        }
    },
    computed: {
        ...mapState(chartStore, ["guOfficetels", "dongOfficetels", "envResult"]),
    },
    watch: {
        isPossibleRent() {
            if (this.isPossibleRent) {
                this.guName = this.envResult.gugunName;

                this.options = {
                    spanGaps: true,
                    responsive: true,
                    interaction: {
                        mode: 'index',
                    },
                    title: {
                        display: true,
                        text: this.guName + " 월 평균 전세 보증금 변화 추이",
                        fontSize: 17,
                    },
                };

                let labelList = [];
                let longtermDataset = [];

                for (let index = 0; index < 12; index++) {
                    labelList.push(this.guOfficetels.items[index].period);
                    longtermDataset.push(this.guOfficetels.items[index].longtermAvg);
                }
                this.fillData(labelList, longtermDataset);
            } else {
                this.fillData([], []);
            }
        },
    },
    methods: {
        fillData (labelList, longtermDataset) {
            this.datacollection = {
                labels: labelList,
                datasets: [
                    {
                        type: 'line',
                        label: this.guName + ' 월 평균 전세 보증금',
                        fill: false,
                        borderColor: "MediumPurple",
                        data: longtermDataset,
                        tension: 0.1,
                    },
                ]
            }
        },
    }
}
</script>

<style scoped>
small {
    color: gray;
}
</style>