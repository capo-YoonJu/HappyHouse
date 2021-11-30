<template>
    <div class="m-2" style="text-align: right;">
        <span>
            <scatter-chart :options="options" :chart-data="datacollection"></scatter-chart>
        </span>
        <small class="mt-1">단위 : 만원</small>
    </div>
</template>

<script>
import ScatterChart from '@/chartModules/ScatterChart.vue'

export default {
    name: "MapScatterChart",
    components: {
        ScatterChart
    },
    props: {
        modalhouse: Array,
    },
    data() {
        return {
            colors: ['Gold', 'LightCoral', 'DarkMagenta', 'Aquamarine', 'Maroon', 'Peru', 'SeaGreen', 'CornflowerBlue', 'Chocolate', 'DarkBlue', 'IndianRed'],
            datacollection: {},
            options: {
                spanGaps: true,
                responsive: true,
                interaction: {
                    mode: 'index',
                },
                title: {
                    display: true,
                    text: "연간 주택 실거래가 추이",
                    fontSize: 17,
                },
                scales: {   
                    xAxes: [{
                        type: 'time',
                        display: true,
                        distribution: 'series',
                        time: {
                            unit:"month",
                            displayFormats:{month:'MMM'},
                        }
                    }]
                },
            },
        }
    },
    computed: {
    },
    watch: {
        modalhouse() {
            let labelList = [];
            let houseDataset = [];

            for (let index = 0; index < this.modalhouse.length; index++) {
                let house = this.modalhouse[index];
                if (houseDataset[house.area] == undefined) {
                    houseDataset[house.area] = [];
                }
                labelList.push(house.aptName);
                houseDataset[house.area].push({ 'x': new Date(parseInt(house.dealYear), parseInt(house.dealMonth-1)), 'y': house.dealAmountNum });
            }

            let resultDataset = []
            let keys = Object.keys(houseDataset);

            for (let index = 0; index < keys.length; index++) {
                let areaSize = keys[index];
                resultDataset.push({
                    'type': 'scatter',
                    'label': areaSize,
                    'backgroundColor': this.colors[index],
                    'data': houseDataset[areaSize],
                });
            }

            console.log(resultDataset);
            this.fillData(labelList, resultDataset);
        },
    },
    methods: {
        fillData (labelList, resultDataset) {
            this.datacollection = {
                labels: labelList,
                datasets: resultDataset
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