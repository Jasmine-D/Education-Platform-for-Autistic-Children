import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Home from '../views/Home.vue'
import SceneList from '../views/SceneList.vue'
import WordList from '../views/WordList.vue'
import Scene from '../views/Scene.vue'
import WordDetail from '../views/WordDetail.vue'
import Account from '../views/Account.vue'
import Record from '../views/Record.vue'
import MyWordList from '../views/MyWordList.vue'
import MyScene from '../views/MyScene.vue'
import MyCollection from '../views/MyCollection.vue'
import MyWordDetail from '../views/MyWordDetail.vue'

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/Login',
  },
  {
    path: '/Login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/Register',
    name: 'Register',
    component: Register,
  },
  {
    path: '/Home',
    name: 'Home',
    component: Home,
  },
  {
    path: '/SceneList',
    name: 'SceneList',
    component:SceneList
  },
  {
    path: '/WordList',
    name: 'WordList',
    component:WordList
  },
  {
    path:'/Scene',
    name:'Scene',
    component:Scene
  },
  {
    path:'/WordDetail',
    name:'WordDetail',
    component:WordDetail
  },
  {
    path:'/Account',
    name:'Account',
    component:Account
  },
  {
    path:'/Record',
    name:'Record',
    component:Record
  },
  {
    path:'/MyWordList',
    name:'MyWordList',
    component:MyWordList
  },
  {
    path:'/MyScene',
    name:'MyScene',
    component:MyScene
  },
  {
    path:'/MyCollection',
    name:'MyCollection',
    component:MyCollection
  },
  {
    path:'/MyWordDetail',
    name:'MyWordDetail',
    component:MyWordDetail
  },
]

const router = new VueRouter({
  //mode: 'history',
  //base: process.env.BASE_URL,
  routes
})

export default router
