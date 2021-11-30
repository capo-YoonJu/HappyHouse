import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "@/store/index.js";
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';

// Import Bootstrap an BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  async beforeCreate() {
    if (
      sessionStorage.getItem("access-token") &&
      (store.state.userStore.userInfo == null || store.state.userStore.userInfo == undefined)
    ) {
      await this.$store.dispatch("userStore/getUserInfo");
    }
  },
  render: (h) => h(App),
}).$mount("#app");
