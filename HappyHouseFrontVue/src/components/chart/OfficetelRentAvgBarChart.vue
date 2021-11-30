<template>
    <div class="officetel-rent-avg-bar-chart m-4">
        <span>
            <bar-chart :chart-data="datacollection" :options="options"></bar-chart>
        </span>
        
    </div>
</template>

<script>
import BarChart from '@/chartModules/BarChart.vue'
import { mapState } from 'vuex'

const chartStore = "chartStore";

export default {
    name: "OfficetelRentAvgBarChart",
    components: {
        BarChart
    },
    data() {
        return {
            datacollection: {},
            options: {
                spanGaps: true,
                interaction: {
                    mode: 'index',
                },
                scales: {
                    yAxes: [{
                        axis: 'y',
                        position: "right",
                        ticks: {
                            beginAtZero: true,
                        }
                    }],
                },
            },
        }
    },
    computed: {
        ...mapState(chartStore, ["guOfficetels"]),
    },
    watch: {
        guOfficetels() {
            if (this.guOfficetels.length == 12) {
                let labelList = [];
                let monthlyDataset = [];
                for (let index = 0; index < 12; index++) {
                    labelList.push(this.guOfficetels[index].period);
                    monthlyDataset.push(this.guOfficetels[index].monthAvg);
                }
                this.fillData(labelList, monthlyDataset);
            }
        },
    },
    methods: {
        fillData (labelList, monthlyDataset) {
            this.datacollection = {
                labels: labelList,
                datasets: [
                    {
                        label: '월 평균 월세',
                        backgroundColor: '#f87979',
                        data: monthlyDataset,
                    },
                ]
            }
        },
    }
}
</script>

<style scoped>

</style>