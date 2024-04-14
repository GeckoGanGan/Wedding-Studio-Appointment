import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path:'/',
    name:'menu',
    component:() => import('../views/wedding_menu.vue'),
    children:[
      {
        path:'/',
        name:'home',
        component:() => import('../views/home.vue')
      },
      {
        path:'/studios',
        name:'studios',
        component:() => import('../views/studios/index.vue')
      },
      {
        path:'/user/userInfo',
        name:'userInfo',
        component:() => import('../views/user/UserInfo.vue')
      },
      {
        path: '/studios/details/:id',
        name:'details',
        component:() => import('../views/studios/details.vue')
      },
      {
        path: '/user/login',
        name:'login',
        component:() => import('../views/user/index.vue')
      },
      {
        path: '/comment1',
        name:'comment1',
        component:() => import('../components/Comment.vue')
      },
      {
        path: '/package/:studioId',
        name:'package',
        component:() => import('../views/studios/StudioPackage.vue')
      },
      {
        path:'/appointment',
        name:'appointment',
        component :() => import('../views/appoint/index.vue')
      },
      {
        path:'/chat/:studioId',
        name:'chat',
        component: () => import('../views/chat/Chat.vue')
      },
      {
        path:'/photographer',
        name:'photographer',
        component: () => import('../views/photographer/index.vue')
      },
      {
        path:'/hotTop',
        name:'hotTop',
        component: () => import('../components/HotTop.vue')
      },
      {
        path:'/collect',
        name:'collect',
        component: () => import('../views/collect/index.vue')
      },
      {
        path:'/phtographerIngfo:photographerId',
        name :'phtographerIngfo',
        component: () => import('../views/photographer/info.vue')
      }
    ]
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
