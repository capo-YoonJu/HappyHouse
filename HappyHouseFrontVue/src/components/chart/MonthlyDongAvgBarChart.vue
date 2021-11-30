<template>
    <div class="m-4">
        <span>
            <bar-chart :options="options" :chart-data="datacollection" :width="200" :height="90"></bar-chart>
        </span>
        <small class="mt-1">단위 : 만원 | 기간 : 1년</small>
        <small class="mt-1 d-block">* 1년간 거래가 없는 동의 데이터는 제외되었습니다.</small>
    </div>
</template>

<script>
import { mapState } from 'vuex'
import BarChart from '@/chartModules/BarChart.vue'

const chartStore = "chartStore";

export default {
    name: "MonthlyDongAvgBarChart",
    components: {
        BarChart
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
                        text: this.guName + " 동별 월 평균 월세",
                        fontSize: 17,
                    },
                };

                let labelList = [];
                let monthlyDataset = [];
                let guAvgDataset = [];
                
                let guAvg = 0;
                let cnt = 0;

                for (let dongName in this.dongOfficetels) {
                    let avg = this.dongOfficetels[dongName]['월세평균'];
                    if(avg) {
                        labelList.push(dongName);
                        monthlyDataset.push(avg);
                        guAvg += avg;
                        cnt += 1;
                    }
                }

                guAvg = (guAvg / cnt).toFixed(1);
                for (let index = 0; index < cnt; index++) {
                    guAvgDataset.push(guAvg);
                }

                this.fillData(labelList, monthlyDataset, guAvgDataset);
            } else {
                this.fillData([], []);
            }
        },
    },
    methods: {
        fillData (labelList, monthlyDataset, guAvgDataset) {
            this.datacollection = {
                labels: labelList,
                datasets: [
                    {
                        type: 'line',
                        label: this.guName + ' 월 평균 월세',
                        fill: false,
                        backgroundColor: "Sienna",
                        data: guAvgDataset,
                        tension: 0.1,
                    },
                    {
                        type: 'bar',
                        label: '동별 월 평균 월세',
                        backgroundColor: 'Gold',
                        hoverBackgroundColor: 'GoldenRod',
                        data: monthlyDataset,
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