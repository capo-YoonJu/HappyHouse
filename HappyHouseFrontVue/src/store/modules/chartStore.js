import { gugunList, dongList, officetelRentList } from "@/api/chart/officetelRent.js";
import { guAgeList, dongAgeList } from '@/api/chart/residentsAge.js';
import { serialList, sdotEnvList } from '@/api/chart/sdotEnv.js';

const chartStore = {
    namespaced: true,
    state: {
        guguns: [{ value: null, text: "선택하세요" }],
        dongs: [{ value: null, text: "선택하세요" }],

        guOfficetels: {},
        dongOfficetels: {},

        guYouth: {},
        dongYouth: {},

        rawSdotNight: [],
        rawSdotDay: [],
        sdotEnv: {},
        envResult: {},
    },
    mutations: {
        // 구군, 동 관련 세팅
        SET_GUGUN_LIST: (state, guguns) => {
            guguns.forEach((gugun) => {
                state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
            });
        },
        SET_DONG_LIST: (state, dongs) => {
            dongs.forEach((dong) => {
                state.dongs.push({ value: dong.dongcode, text: dong.dong });
                state.dongOfficetels[`${dong.dong}`] = { 전세 : [], 월세 : [] };
            });
        },
        CLEAR_GUGUN_LIST: (state) => {
            state.guguns = [{ value: null, text: "선택하세요" }];
        },
        CLEAR_DONG_LIST: (state) => {
            state.dongs = [{ value: null, text: "선택하세요" }];
        },

        // 오피스텔 전월세 관련 세팅
        SET_GU_OFFICETELS: (state, calcAvg) => {
            state.guOfficetels.items.push(calcAvg);
        },
        CLEAR_GU_OFFICETELS: (state, gugunCode) => {
            state.guOfficetels = { gugunCode: gugunCode, items: [] };
        },
        SORT_GU_OFFICETELS_AVG: (state) => {
            state.guOfficetels.items.sort(function(a, b) {
                let x = a['period'];
                let y = b['period'];
                return x < y ? -1 : x > y ? 1 : 0;
            });
        },

        GET_DONG_OFFICETELS: (state) => {
            console.log(state.dongOfficetels);
        },
        SET_DONG_LONGTERM: (state, dongDeposit) => {
            state.dongOfficetels[dongDeposit.dongName].전세.push(dongDeposit.deposit);
        },
        SET_DONG_MONTHLY: (state, dongMonthly) => {
            state.dongOfficetels[dongMonthly.dongName].월세.push(dongMonthly.monthly);
        },
        CLEAR_DONG_OFFICETEL: (state) => {
            state.dongOfficetels = {};
        },
        CALC_DONG_OFFICETELS_AVG: (state) => {
            for (let dongName in state.dongOfficetels) {
                let longRentList = state.dongOfficetels[dongName]['전세'];
                const longResult = longRentList.reduce(function add(sum, currValue) {
                    return sum + currValue;
                }, 0);
                state.dongOfficetels[dongName]['전세평균'] = Math.round(longResult / longRentList.length);

                let monthlyRentList = state.dongOfficetels[dongName]['월세'];
                const monthlyResult = monthlyRentList.reduce(function add(sum, currValue) {
                    return sum + currValue;
                }, 0);
                state.dongOfficetels[dongName]['월세평균'] = Math.round(monthlyResult / monthlyRentList.length);
            }
        },

        // 주/야간 생활인구 관련 세팅
        CLEAR_GU_YOUTH: (state) => {
            state.guYouth = { day: { 청년인구: [], 전체인구: [] }, night: { 청년인구: [], 전체인구: [] } };
        },
        SET_GU_YOUTH: (state, data) => {
            state.guYouth[`${data.time}`].청년인구.push(data.youths);
            state.guYouth[`${data.time}`].전체인구.push(data.allAge);
        },
        CALC_GU_YOUTH_AVG: (state) => {
            for (let time in state.guYouth) {
                let youthList = state.guYouth[time]['청년인구'];
                const youthResult = youthList.reduce(function add(sum, currValue) {
                    return sum + currValue;
                }, 0);
                state.guYouth[time]['청년평균'] = Math.round(youthResult / youthList.length);

                let totalList = state.guYouth[time]['전체인구'];
                const totalResult = totalList.reduce(function add(sum, currValue) {
                    return sum + currValue;
                }, 0);
                state.guYouth[time]['전체평균'] = Math.round(totalResult / totalList.length);

                state.guYouth[time]['청년비율'] = ((youthResult / totalResult) * 100).toFixed(2);
            }
        },
        CLEAR_DONG_YOUTH: (state) => {
            state.dongYouth = { day: { 청년인구: [], 전체인구: [] }, night: { 청년인구: [], 전체인구: [] } };
        },
        SET_DONG_YOUTH: (state, data) => {
            state.dongYouth[`${data.time}`].청년인구.push(data.youths);
            state.dongYouth[`${data.time}`].전체인구.push(data.allAge);
        },
        CALC_DONG_YOUTH_AVG: (state) => {
            for (let time in state.dongYouth) {
                let youthList = state.dongYouth[time]['청년인구'];
                const youthResult = youthList.reduce(function add(sum, currValue) {
                    return sum + currValue;
                }, 0);
                state.dongYouth[time]['청년평균'] = Math.round(youthResult / youthList.length);

                let totalList = state.dongYouth[time]['전체인구'];
                const totalResult = totalList.reduce(function add(sum, currValue) {
                    return sum + currValue;
                }, 0);
                state.dongYouth[time]['전체평균'] = Math.round(totalResult / totalList.length);

                state.dongYouth[time]['청년비율'] = ((youthResult / totalResult) * 100).toFixed(2);
            }
        },

        // S-DOT 환경 정보 세팅
        CLEAR_RAW_SDOT_NIGHT: (state) => {
            state.rawSdotNight = [];
        },
        CLEAR_RAW_SDOT_DAY: (state) => {
            state.rawSdotDay = [];
        },
        CLEAR_SDOT_ENVS: (state) => {
            state.sdotEnv = {};
        },
        SET_RAW_SDOT_ENV_NIGHT: (state, response) => {
            response.forEach(item => {
                state.rawSdotNight.push(item);
            });
        },
        SET_RAW_SDOT_ENV_DAY: (state, response) => {
            response.forEach(item => {
                state.rawSdotDay.push(item);
            });
        },
        SET_SDOT_ENV_SERIAL_NOS: (state, serials) => {
            serials.forEach(serial => {
                state.sdotEnv[`${serial.serialNo}`] = serial;
            });
        },
        SET_SDOT_ENV: (state) => {
            for (let index = 0; index < 2000; index++) {
                const rawDay = state.rawSdotDay[index];
                const rawNight = state.rawSdotNight[index];
                if (state.sdotEnv[rawDay.COLUMN1]) {
                    state.sdotEnv[rawDay.COLUMN1].dust = rawDay.COLUMN4;
                    state.sdotEnv[rawDay.COLUMN1].light = rawDay.COLUMN11;
                }
                if (state.sdotEnv[rawNight.COLUMN1]) {
                    state.sdotEnv[rawNight.COLUMN1].noise = rawNight.COLUMN13;
                }
            }
        },
        CALC_SDOT_ENV_AVG: (state, dong) => {
            state.envResult = {};

            let totalNoiseCnt = 0;
            let dongNoiseCnt = 0;
            let totalNoise = 0;
            let dongNoise = 0;

            let totalDustCnt = 0;
            let dongDustCnt = 0;
            let totalDust = 0;
            let dongDust = 0;

            let totalLightCnt = 0;
            let dongLightCnt = 0;
            let dongLight = 0;
            let totalLight = 0;
            
            let dongName = '';
            let guName = '';
            for (let dongSerial in state.sdotEnv) {
                let serial = state.sdotEnv[dongSerial];

                if (serial.noise) {
                    if (serial.dongCode === dong) {
                        dongName = serial.dongName;
                        dongNoiseCnt += 1;
                        dongNoise += parseInt(serial.noise);
                    }
                    totalNoiseCnt += 1;
                    totalNoise += parseInt(serial.noise);
                }
                if (serial.dust) {
                    if (serial.dongCode === dong) {
                        dongDustCnt += 1;
                        dongDust += parseInt(serial.dust);
                    }
                    totalDustCnt += 1;
                    totalDust += parseInt(serial.dust);
                }
                if (serial.light) {
                    if (serial.dongCode === dong) {
                        dongLightCnt += 1;
                        dongLight += parseInt(serial.light);
                    }
                    totalLightCnt += 1;
                    totalLight += parseInt(serial.light);
                }
                guName = serial.gugunName
            }
            state.envResult = { 
                'gugunName': guName,
                'dongName': dongName,
                'guNoise': Math.round(totalNoise / totalNoiseCnt).toFixed(2),
                'dongNoise': Math.round(dongNoise / dongNoiseCnt).toFixed(2),
                'guDust': Math.round(totalDust / totalDustCnt).toFixed(2),
                'dongDust': Math.round(dongDust / dongDustCnt).toFixed(2),
                'guLight': Math.round(totalLight / totalLightCnt).toFixed(2),
                'dongLight': Math.round(dongLight / dongLightCnt).toFixed(2),
            };
        }
    },
    actions: {
        getGugun: ({ commit }) => {
            gugunList(
                ({ data }) => {
                    commit("SET_GUGUN_LIST", data);
                },
                (error) => {
                    console.log(error);
                }
            );
        },
        getDong: ({ commit }, gugunCode) => {
            commit('CLEAR_DONG_LIST');
            commit('CLEAR_DONG_OFFICETEL');
            const params = {
                gugun: gugunCode,
            };
            dongList(
                params,
                ({ data }) => {
                    commit("SET_DONG_LIST", data);
                },
                (error) => {
                    console.log(error);
                }
            );
        },
        
        /**
         * 선택된 구의 12개월간 오피스텔 전월세 데이터 가져와서 월 평균 데이터를 구하고,
         * 동별 정보를 따로 저장한다.
         */
        getOfficetelList: ({ commit }, gugunCode) => {
            // 기존에 저장된 구와 다른 구를 선택하면 동 오피스텔 데이터 갱신한다.
            commit("CLEAR_GU_OFFICETELS", gugunCode);
            const SERVICE_KEY = process.env.VUE_APP_OFFICETEL_RENT_API_KEY;
            let now = new Date();
            
            // 공공데이터 포털에서 현재 날짜로부터 12개월 이전 동안의 오피스텔 전월세 데이터를 받아온다.
            for (let index = 0; index < 12; index++) {
                now = new Date(now.setMonth(now.getMonth() - 1));
                let searchMonth = (now.getFullYear() + ("0" + (now.getMonth() + 1)).slice(-2));
                
                const params = {
                    serviceKey: decodeURIComponent(SERVICE_KEY),
                    LAWD_CD: gugunCode,
                    DEAL_YMD: searchMonth,
                    numOfRows: 1000,
                };
                // api 호출
                officetelRentList(
                    params,
                    (response) => {
                        let longtermAvg = 0;
                        let monthDepositAvg = 0;
                        let monthAvg = 0;
                        let longtermCnt = 0;
                        let monthCnt = 0;
                        const responseData = response.data.response.body.items.item;

                        // 한달치 데이터 중 하나의 거래 정보
                        for (let index = 0; index < responseData.length; index++) {
                            const rent = responseData[index];
                            let dongName = rent.법정동.trim();
                            let deposit = parseInt(rent.보증금.toString().replace(/,/g, ""));
                            let monthly = parseInt(rent.월세.toString().replace(/,/g, ""));
                            
                            if (monthly == 0) {         // 전세
                                longtermCnt += 1;
                                longtermAvg += deposit;
                                commit('SET_DONG_LONGTERM',  { dongName: dongName, deposit: deposit});
                            } else {                    // 월세
                                monthCnt += 1;
                                monthDepositAvg += deposit;
                                monthAvg += monthly;
                                commit('SET_DONG_MONTHLY', { dongName: dongName, monthly: monthly});
                            }
                        }

                        // 한달치 전세, 월세 보증금, 월세 평균 계산하여 store에 저장
                        let calcAvg = {
                            period: searchMonth,
                            longtermAvg: Math.round(longtermAvg / longtermCnt),
                            monthDepositAvg: Math.round(monthDepositAvg / monthCnt),
                            monthAvg: Math.round(monthAvg / monthCnt),
                        }
                        commit("SET_GU_OFFICETELS", calcAvg);
                    },
                    (error) => {
                        console.log(error);
                    }
                );
            }
        },
        calcDongOfficetelListAvg: ({ commit }) => {
            commit('CALC_DONG_OFFICETELS_AVG');
        },
        sortGuOfficetelList: ({ commit }) => {
            commit('SORT_GU_OFFICETELS_AVG');
        },
        
        /**
         * 서울 열린데이터 광장 api로부터 선택한 구의 주간/야간 청년층, 전체 생활인구 데이터 받아온다.
         */ 
        getGuYouthList: ({ commit }, gugunCode) => {
            commit('CLEAR_GU_YOUTH');

            // 한달 전 지점 선택
            let now = new Date();
            let searchDate = (now.getFullYear() + ("0" + now.getMonth()).slice(-2) + ("0" + now.getDate()).slice(-2));

            for (let index = 0; index <= 23; index++) {
                let searchTime = ("0" + index).slice(-2);
                let searchParam = searchDate + "/" + searchTime + "/" + gugunCode;

                // api 통신
                guAgeList(
                    searchParam,
                    (response) => {
                        let youths = 0;
                        let allAge = 0;

                        let item = response.data.SPOP_LOCAL_RESD_JACHI.row['0'];
                        
                        youths += parseFloat(item.MALE_F20T24_LVPOP_CO);
                        youths += parseFloat(item.MALE_F25T29_LVPOP_CO);
                        youths += parseFloat(item.MALE_F30T34_LVPOP_CO);
                        youths += parseFloat(item.MALE_F35T39_LVPOP_CO);
                        youths += parseFloat(item.FEMALE_F20T24_LVPOP_CO);
                        youths += parseFloat(item.FEMALE_F25T29_LVPOP_CO);
                        youths += parseFloat(item.FEMALE_F30T34_LVPOP_CO);
                        youths += parseFloat(item.FEMALE_F35T39_LVPOP_CO);
                        allAge += parseFloat(item.TOT_LVPOP_CO);
                        
                        if (index >= 9 && index <= 18) {        // 주간
                            commit('SET_GU_YOUTH', { time: "day", youths: youths, allAge: allAge });
                        } else {                                // 야간
                            commit('SET_GU_YOUTH', { time: "night", youths: youths, allAge: allAge });
                        }
                    },
                    (error) => {
                        console.log(error);
                    }
                );
            }
        },
        calcGuYouthAvg: ({ commit }) => {
            commit('CALC_GU_YOUTH_AVG');
        },

        getDongYouthList: ({ commit }, dongCode) => {
            commit('CLEAR_DONG_YOUTH');
            
            // 한달 전 지점 선택
            let now = new Date();
            let searchDate = (now.getFullYear() + ("0" + now.getMonth()).slice(-2) + ("0" + now.getDate()).slice(-2));

            for (let index = 0; index <= 23; index++) {
                let searchTime = ("0" + index).slice(-2);
                let searchParam = searchDate + "/" + searchTime + "/" + dongCode;

                // api 통신
                dongAgeList(
                    searchParam,
                    (response) => {
                        let youths = 0;
                        let allAge = 0;
                        
                        let item = response.data.SPOP_LOCAL_RESD_DONG.row['0'];
                        
                        youths += parseFloat(item.MALE_F20T24_LVPOP_CO);
                        youths += parseFloat(item.MALE_F25T29_LVPOP_CO);
                        youths += parseFloat(item.MALE_F30T34_LVPOP_CO);
                        youths += parseFloat(item.MALE_F35T39_LVPOP_CO);
                        youths += parseFloat(item.FEMALE_F20T24_LVPOP_CO);
                        youths += parseFloat(item.FEMALE_F25T29_LVPOP_CO);
                        youths += parseFloat(item.FEMALE_F30T34_LVPOP_CO);
                        youths += parseFloat(item.FEMALE_F35T39_LVPOP_CO);
                        allAge += parseFloat(item.TOT_LVPOP_CO);
                        
                        if (index >= 9 && index <= 18) {        // 주간
                            commit('SET_DONG_YOUTH', { time: "day", youths: youths, allAge: allAge });
                        } else {                                // 야간
                            commit('SET_DONG_YOUTH', { time: "night", youths: youths, allAge: allAge });
                        }
                    },
                    (error) => {
                        console.log(error);
                    }
                );
            }
        },
        calcDongYouthAvg: ({ commit }) => {
            commit('CALC_DONG_YOUTH_AVG');
            return true;
        },

        /**
         * 서울시 S-DOT 환경정보 API 가져온다. (낮)
         */
        getSdotEnvDayList: ({ commit }) => {
            // commit('CLEAR_RAW_SDOT_DAY');

            for (let index = 30001; index <= 31001; index+=1000) {
                let pageNo = "/" + index + "/" + parseInt(index+999);

                sdotEnvList(
                    pageNo, 
                    (response) => {
                        commit("SET_RAW_SDOT_ENV_DAY", response.data.IotVdata017.row);
                    },
                    (error) => {
                        console.log(error);
                    }
                )
            }
        },
        /**
         * 서울시 S-DOT 환경정보 API 가져온다. (밤)
         */
        getSdotEnvNightList: ({ commit }) => {
            // commit('CLEAR_RAW_SDOT_NIGHT');

            for (let index = 1; index <= 1001; index+=1000) {
                let pageNo = "/" + index + "/" + parseInt(index+999);

                sdotEnvList(
                    pageNo, 
                    (response) => {
                        commit("SET_RAW_SDOT_ENV_NIGHT", response.data.IotVdata017.row);
                    },
                    (error) => {
                        console.log(error);
                    }
                )
            }
        },
        // S-DOT 시리얼 넘버 백에서 가져온다.
        getSerialNos: ({ commit }, gugunCode) => {
            commit('CLEAR_SDOT_ENVS');

            const params = {
                gugun: gugunCode,
            };
            serialList(
                params,
                (response) => {
                    commit('SET_SDOT_ENV_SERIAL_NOS', response.data);
                    commit('SET_SDOT_ENV');
                },
                (error) => {
                    console.log(error);
                }
            )
        },
        calcEnvAvg: ({ commit }, dongCode) => {
            commit('CALC_SDOT_ENV_AVG', dongCode);
        },
    },
};

export default chartStore;