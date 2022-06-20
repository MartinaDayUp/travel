import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import SaveInfo from '../views/SaveInfo.vue'
import Search from '../views/Search.vue'
import Blogs from '../views/Blogs.vue'
import BlogEdit from '../views/BlogEdit.vue'
import BlogDetail from '../views/BlogDetail.vue'
import HotRecommend from '../views/HotRecommend.vue'
import LikeRecommend from '../views/LikeRecommend.vue'
import LocalRecommend from '../views/LocalRecommend.vue'
import LikeRecommendError from '../views/LikeRecommendError.vue'
import LocalRecommendError from '../views/LocalRecommendError.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect: {name: "Blogs"}
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/Register',
    name: 'Register',
    component: Register
  },
  {
    path: '/SaveInfo',
    name: 'SaveInfo',
    component: SaveInfo
  },
  {
    path: '/Search',
    name: 'Search',
    component: Search
  },
  {
    path: '/HotRecommend',
    name: 'HotRecommend',
    component: HotRecommend
  },
  {
    path: '/LikeRecommend',
    name: 'LikeRecommend',
    component: LikeRecommend
  },
  {
    path: '/LocalRecommend',
    name: 'LocalRecommend',
    component: LocalRecommend
  },
  {
    path: '/LikeRecommendError',
    name: 'LikeRecommendError',
    component: LikeRecommendError
  },
  {
    path: '/LocalRecommendError',
    name: 'LocalRecommendError',
    component: LocalRecommendError
  },
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEdit,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/blog/:blogId',
    name: 'BlogDetail',
    component: BlogDetail
  },
  {
    path: '/blog/:blogId/edit',
    name: 'BlogEdit',
    component: BlogEdit,
    meta: {
      requireAuth: true
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
