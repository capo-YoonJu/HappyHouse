<template>
    <div class="mt-3">
        <b-skeleton-wrapper :loading="loading">
            <template #loading>
                <b-card style="text-align: center;">
                    <div class="mt-5">
                        <h6 class="mt-3 mr-2 d-inline">실시간 지역 별 환경 데이터를 수집하고 있습니다...</h6>
                        <b-spinner label="Spinning" variant="secondary" small class="mb-1"></b-spinner>
                    </div>
                    <b-skeleton width="85%" height="50px" class="m-3 mt-5"></b-skeleton>
                    <b-skeleton width="95%" height="200px" class="m-3"></b-skeleton>
                    <b-skeleton width="95%" height="200px" class="m-3"></b-skeleton>
                    <b-skeleton width="95%" height="200px" class="m-3"></b-skeleton>
                </b-card>
            </template>

            <div class="select-menu mb-5 p-2">
                <b-row class="my-2 justify-content-center">
                    <b-col class="col-md-3">
                        <b-form-select
                            v-model="gugunCode"
                            :options="guguns"
                            @change="[getDongList(), getSerialNoList(), getGuYouths()]"
                            class="select-form"
                        ></b-form-select>
                    </b-col>
                    <b-col class="col-md-3">
                        <b-form-select
                            v-model="dongCode"
                            :options="dongs"
                            @change="[calcGuYouthListAvg(), getDongYouths(), calcDongEnvAvg(), searchOfficetels()]"
                            class="select-form"
                        ></b-form-select>
                    </b-col>
                    <b-col class="col-md-3" style="text-align: center;">
                        <b-button class="search-btn" @click="[calcDongYouthListAvg(), calcRentAvgNSort()]">조회</b-button>
                    </b-col>
                </b-row>
            </div>
            <b-card class="dashboard-card border-0">
                <b-row class="justify-content-center">
                    <b-col class="col-md-12 mt-2">
                        <div>
                            <b-tabs align="right">
                                <b-tab title="전세" active>
                                    <b-row class="mt-4">
                                        <b-col class="col-md-12">
                                            <b-card class="chart-card bg-light mx-4 mt-2">
                                                <longterm-gu-time-line-chart :isPossibleRent="isPossibleRent"></longterm-gu-time-line-chart>
                                            </b-card>
                                        </b-col>
                                    </b-row>
                                    <b-row class="mt-4">
                                        <b-col class="col-md-12">
                                            <b-card class="chart-card bg-light mx-4 mt-2">
                                                <longterm-dong-avg-bar-chart :isPossibleRent="isPossibleRent"></longterm-dong-avg-bar-chart>
                                            </b-card>
                                        </b-col>
                                    </b-row>
                                </b-tab>
                                <b-tab title="월세">
                                    <b-row class="mt-4">
                                        <b-col class="col-md-12">
                                            <b-card class="chart-card bg-light mx-4 mt-2">
                                                <monthly-gu-time-line-chart :isPossibleRent="isPossibleRent"></monthly-gu-time-line-chart>
                                            </b-card>
                                        </b-col>
                                    </b-row>
                                    <b-row class="mt-4">
                                        <b-col class="col-md-12">
                                            <b-card class="chart-card bg-light mx-4 mt-2">
                                                <monthly-dong-avg-bar-chart :isPossibleRent="isPossibleRent"></monthly-dong-avg-bar-chart>
                                            </b-card>
                                        </b-col>
                                    </b-row>
                                </b-tab>
                            </b-tabs>
                        </div>
                    </b-col>
                </b-row>
                <hr class="mt-5">
                <b-row class="justify-content-center my-2 mb-4">
                    <b-col class="col-md-12 mt-4">
                        <b-card class="chart-card bg-light mx-4 mt-2">
                            <seoul-radar-chart :isPossibleDong="isPossibleDong"></seoul-radar-chart>
                        </b-card>
                    </b-col>
                </b-row>
            </b-card>
        </b-skeleton-wrapper>
    </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import { hDong } from '@/api/chart/residentsAge.js';
import LongtermGuTimeLineChart from "@/components/chart/LongtermGuTimeLineChart.vue";
import LongtermDongAvgBarChart from "@/components/chart/LongtermDongAvgBarChart.vue";
import MonthlyGuTimeLineChart from "@/components/chart/MonthlyGuTimeLineChart.vue";
import MonthlyDongAvgBarChart from "@/components/chart/MonthlyDongAvgBarChart.vue";
import SeoulRadarChart from "@/components/chart/SeoulRadarChart.vue";

const chartStore = "chartStore";

export default {
    name: "OfficetelRentDashBoard",
    components: {
        LongtermGuTimeLineChart,
        LongtermDongAvgBarChart,
        MonthlyGuTimeLineChart,
        MonthlyDongAvgBarChart,
        SeoulRadarChart
    },
    data() {
        return {
            gugunCode: null,
            dongCode: null,
            dongChangeFlag: false,
            isPossibleRent: false,
            isPossibleEnv: false,
            isPossibleDong: false,

            loading: true,
            loadingTime: 0,
            maxLoadingTime: 3
        };
    },
    computed: {
        ...mapState(chartStore, ["guguns", "dongs", "guOfficetels", "dongOfficetels", "guYouth", "dongYouth", "rawSdotNight","rawSdotDay", "sdotEnv", "envResult"]),
    },
    watch: {
        rawSdotNight() {
            if (this.rawSdotNight.length >= 2000 && !this.isPossibleEnv) {
                this.clearLoadingTimeInterval();
                this.loading = false;
                this.isPossibleEnv = true;
            }
        },
        rawSdotDay() {
            if (this.rawSdotDay.length >= 2000 && !this.isPossibleEnv) {
                this.clearLoadingTimeInterval();
                this.loading = false;
                this.isPossibleEnv = true;
            }
        },
        loading(newValue, oldValue) {
            if (newValue !== oldValue) {
                this.clearLoadingTimeInterval();

                if (newValue) {
                    this.$_loadingTimeInterval = setInterval(() => {
                    this.loadingTime++
                    }, 1000)
                }
            }
        },
        loadingTime(newValue, oldValue) {
            if (newValue !== oldValue) {
                if (newValue === this.maxLoadingTime) {
                    this.loading = false
                }
            }
        }
    },
    created() {
        this.getSdotEnvDayList();
        this.getSdotEnvNightList();
        this.CLEAR_GUGUN_LIST();
        this.getGugun();
        this.dongChangeFlag = false;
        this.isPossibleRent = false;
        this.isPossibleEnv = false;
        this.isPossibleDong = false;

        this.$_loadingTimeInterval = null;
    },
    methods: {
        ...mapActions(chartStore, ["getGugun", "getDong", 
                                   "getOfficetelList", "calcDongOfficetelListAvg", "sortGuOfficetelList",
                                   "getGuYouthList", "calcGuYouthAvg", "getHDong", "getDongYouthList", "calcDongYouthAvg",
                                   "getSdotEnvDayList", "getSdotEnvNightList", "getSerialNos", "getGuNoiseList", "calcEnvAvg"]),
        ...mapMutations(chartStore, ["CLEAR_GUGUN_LIST"]),

        clearLoadingTimeInterval() {
            clearInterval(this.$_loadingTimeInterval)
            this.$_loadingTimeInterval = null
        },
        startLoading() {
            this.loading = true
            this.loadingTime = 0
        },

        getDongList() {
            this.dongCode = null;
            this.isPossibleRent = false;
            if (this.gugunCode) {
                this.getDong(this.gugunCode);
                this.dongChangeFlag = false;
            }
        },
        searchOfficetels() {
            // 이전에 선택한 구와 다른 구를 선택했을 때만 오피스텔 동 거래 데이터를 새롭게 받아온다.
            if (this.gugunCode && (this.guOfficetels.gugunCode !== this.gugunCode)) {
                this.getOfficetelList(this.gugunCode);
                this.dongChangeFlag = true;
            }
        },
        calcRentAvgNSort() {
            // 이전에 선택한 구와 다른 구를 선택했을 때만 새롭게 평균 구하고 정렬한다.
            if (this.dongChangeFlag) {
                this.calcDongOfficetelListAvg();
                this.sortGuOfficetelList();
                this.isPossibleRent = true;
            }
            this.dongChangeFlag = false;
        },
        getGuYouths() {
            this.getGuYouthList(this.gugunCode);
        },
        calcGuYouthListAvg() {
            this.calcGuYouthAvg();
        },
        getDongYouths() {
            this.isPossibleDong = false;
            const params = {
                "dongCode": this.dongCode,
            };
            hDong(
                params,
                (response) => {
                    this.getDongYouthList(response.data.hdongCode);
                },
                (error) => {
                    console.log(error);
                }
            )
        },
        async calcDongYouthListAvg() {
            let res = await this.calcDongYouthAvg();
            if (res) this.isPossibleDong = true;
        },
        getSerialNoList() {
            this.getSerialNos(this.gugunCode);
        },
        calcDongEnvAvg() {
            this.calcEnvAvg(this.dongCode);
        }
    },
}
</script>

<style scoped>
.dashboard-card {
    box-shadow: 0 0 13px 0 Gainsboro;
}
.select-menu {
    border: solid 3px LightSteelBlue;
    border-radius: 3em;
    background-color: GhostWhite;
    box-shadow: 0 0 13px 0 Silver;
}
.select-form {
    border-radius: 3em;
    background-color: GhostWhite;
}
.search-btn {
    border-radius: 3em;
    background-color: CornflowerBlue;
    border: none;
}
.search-btn:hover {
    background-color: BurlyWood;
}
.chart-card {
    border-radius: 1em;
    color: #2c3e50;
    box-shadow: 0 0 10px 0 Gainsboro;
    max-width: 800px;
}
</style>