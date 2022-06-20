import Vue from 'vue'
import App from './App.vue'

import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"

import store from './store'
import axios from 'axios'
import router from './router'

import "./axios"
import "./permission"

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

Vue.use(Element)
Vue.config.productionTip = false
Vue.prototype.$axios = axios //

Vue.use(mavonEditor)

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
