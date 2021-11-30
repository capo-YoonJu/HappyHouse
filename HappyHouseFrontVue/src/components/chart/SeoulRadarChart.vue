<template>
    <div class="seoul-radar-chart m-5">
        <span>
            <radar-chart :options="options" :chart-data="datacollection" class="m-5"></radar-chart>
        </span>
        <hr>
        <small class="mt-1">- 주간 : 09시~18시 | 야간 : 19시~08시</small>
        <small class="mt-1">- 청년 생활인구(비율) : 20~30대 생활인구 수 / 전체 연령대 생활인구 수</small>
        <small class="mt-1">- 미세먼지(㎍/㎥), 조도(lux/400), 소음(dB) : 해당 시간대 측정 값 평균</small>
    </div>
</template>

<script>
import { mapState } from "vuex";
import RadarChart from '@/chartModules/RadarChart.vue';

const chartStore = "chartStore";

export default {
    name: "SeoulRadarChart",
    components: {
        RadarChart
    },
    props: {
        isPossibleDong: Boolean,
    },
    data() {
        return {
            message: '',
            guName: '',
            dongName: '',
            datacollection: {},
            options: {},
            counter: 0,
        }
    },
    computed: {
        ...mapState(chartStore, ["guYouth", "dongYouth", "envResult"]),
    },
    watch: {
        isPossibleDong() {
            if (this.isPossibleDong) {
                this.guName = this.envResult.gugunName;
                this.dongName = this.envResult.dongName;

                this.options = {
                    spanGaps: true,
                    responsive: true,
                    interaction: {
                        mode: 'index',
                    },
                    title: {
                        display: true,
                        text: this.dongName +" 생활 환경 데이터",
                        fontSize: 17,
                    },
                };

                let labelList = ['주간 청년 생활인구', '야간 청년 생활인구', '주간 미세먼지', '주간 조도', '야간 소음'];
                let guDataset = [];
                let dongDataset = [];

                if (!this.dongName) this.toast('b-toaster-top-right');

                guDataset.push(this.guYouth.day.청년비율);
                dongDataset.push(this.dongYouth.day.청년비율);

                guDataset.push(this.guYouth.night.청년비율);
                dongDataset.push(this.dongYouth.night.청년비율);

                guDataset.push(this.envResult.guDust);
                dongDataset.push(this.envResult.dongDust);

                guDataset.push(this.envResult.guLight/400);
                dongDataset.push(this.envResult.dongLight/400);

                guDataset.push(this.envResult.guNoise);
                dongDataset.push(this.envResult.dongNoise);

                this.fillData(labelList, guDataset, dongDataset);
            } else {
                this.fillData([], []);
            }
        }
    },
    methods: {
        fillData (labelList, guDataset, dongDataset) {
            this.datacollection = {
                labels: labelList,
                datasets: [{
                    label: this.guName,
                    data: guDataset,
                    fill: true,
                    backgroundColor: 'rgb(65, 105, 225, 0.2)',
                    borderColor: 'rgb(65, 105, 225)',
                    pointBackgroundColor: 'rgb(54, 162, 235)',
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: 'rgb(54, 162, 235)'
                }, {
                    label: this.dongName,
                    data: dongDataset,
                    fill: true,
                    backgroundColor: 'rgb(255, 99, 71, 0.2)',
                    borderColor: 'rgb(255, 99, 71)',
                    pointBackgroundColor: 'rgb(255, 99, 132)',
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: 'rgb(255, 99, 132)'
                }]
            }
        },
        toast(toaster, append = false) {
            this.counter++
            this.$bvToast.toast(`선택한 동의 측정 데이터가 부족하여 그래프 그리기에 실패했습니다.`, {
                title: `생활 환경 데이터가 없습니다.`,
                toaster: toaster,
                solid: true,
                appendToast: append
            })
        }
    }
}
</script>

<style scoped>
small {
    color: gray;
    display: block;
}
</style>