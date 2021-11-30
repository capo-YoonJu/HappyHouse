<template>
    <div class="calendar-component mx-3 mt-3">
        <h5 class="calendar-title">청약 분양 일정 캘린더</h5>
        <div class="mt-3">
            <b-calendar 
                @context="onContext"
                v-model="today" 
                :date-info-fn="dateClass" 
                locale="kor"
                class="border rounded p-2 bg-dark"
            ></b-calendar>
        </div>
        <div class="mt-3">
            <b-card bg-variant="light">
                <div v-if="!rawLots || rawLots.length==0" class="calendar-ment">
                    <small v-bind="context">
                        {{ context.activeYMD.slice(0, 4) }}년 {{ context.activeYMD.slice(5, 7) }}월, 분양중인 부동산이 없습니다.
                    </small>
                </div>
                <div v-else>
                    <div class="calendar-ment">
                        <small 
                            :class="visible ? null : 'collapsed'"
                            :aria-expanded="visible ? 'true' : 'false'"
                            aria-controls="collapse-4"
                            @click="visible = !visible"
                        >
                            {{ context.activeYMD.slice(0, 4) }}년 {{ context.activeYMD.slice(5, 7) }}월, <strong>{{ rawLots.length }}</strong>개의 부동산이 분양중입니다.
                        </small>
                    </div>
                    <b-collapse id="collapse-4" v-model="visible" class="mt-2 scroll">
                        <hr class="hr-width">
                        <a
                            :href="`https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=${lots.houseNm}`"
                            v-for="(lots, index) in rawLots" 
                            :key="index"
                            :style="coloredLots[index]"
                        >
                            {{lots.houseNm}}
                            <hr class="my-2">
                        </a>
                    </b-collapse>
                </div>
            </b-card>
        </div>
        
    </div>
</template>

<script>
import { lotsList } from "@/api/chart/lotsCalendar.js";

export default {
    name: "ChartLotsCalendar",
    data() {
        return {
            today: '',
            context: {activeYMD: "2021-11-18"},
            rawLots: [],
            coloredLots: [],
            isChangeLots: false,
            isChangeCal: false,
            visible: true,
        }
    },
    computed: {
        thisMonth() {
            return this.context.activeYMD.substring(0, 7);
        }
    },
    watch: {
        thisMonth() {
            this.getLotsList();
            this.isChangeLots = false;
        },
        isChangeCal() {
            this.changeColoredLotsClass();
        },
        rawLots() {
            this.isChangeLots = true;
        }
    },
    created() {
        this.getLotsList();
    },
    methods: {
        getLotsList() {
            let searchYear = this.context.activeYMD.substring(0, 4);
            let searchMonth = this.context.activeYMD.substring(5, 7);
            let searchDate = searchYear + searchMonth;

            const SERVICE_KEY = process.env.VUE_APP_LOTS_API_KEY;
            const params = {
                ServiceKey: decodeURIComponent(SERVICE_KEY),
                startmonth: searchDate,
                endmonth: searchDate,
                numOfRows: 100,
            };
            lotsList(
                params,
                (response) => {
                    this.rawLots = response.data.response.body.items.item.reverse();
                },
                (error) => {
                    console.log(error);
                }
            );
        },
        onContext(ctx) {
            this.context = ctx
        },
        dateClass(ymd) {
            this.isChangeCal += 1;
            if (this.findSameLots(ymd)) return 'table-info';
            else return '';
        },
        findSameLots(curDate) {
            let isFind = false;
            if (!this.rawLots) return isFind;
            for (let index = 0; index < this.rawLots.length; index++) {
                let curLot = this.rawLots[index];
                if (curLot.rceptBgnde === curDate && curLot.houseNm != '') isFind = true;
            }
            return isFind;
        },
        changeColoredLotsClass() {
            this.coloredLots = [];
            
            if (!this.isChangeLots || !this.rawLots) {
                this.rawLots = [];
                return;
            } else {
                for (let index = 0; index < this.rawLots.length; index++) {
                    let newLot = this.rawLots[index];
                    if (newLot.rceptBgnde === this.context.activeYMD) {
                        this.coloredLots[index] = "background-color: LightBlue;";
                    }
                }
            }
        },
    },
}
</script>

<style scoped>
.calendar-component {
    width: 287px;
}
.calendar-title {
    text-align: center;
}
.calendar-ment {
    cursor: pointer;
    text-align: center;
}
.scroll {
    max-height: 200px;
    overflow-y: auto;
}
.hr-width {
    width: 40%;
}
a {
    all: unset;
    font-size: small;
    cursor: pointer;
}
a:hover {
    color: DarkSlateGrey;
    font-weight: bold;
}
</style>