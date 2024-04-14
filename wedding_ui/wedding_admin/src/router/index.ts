import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Login',
    component: () => import('@/views/user/Login.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/index.vue'),
    children: [
      {
        path:'/admin/user',
        name: 'User',
        component: () => import('@/views/user/List.vue')
      },
      {
        path: '/admin/role',
        name: 'Role',
        component: () => import('@/views/roles/Index.vue')
      },
    ]
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
