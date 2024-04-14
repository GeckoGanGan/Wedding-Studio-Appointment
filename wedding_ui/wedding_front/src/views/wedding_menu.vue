<template>
  <div class="box">
    <el-menu ellipsis class="el-menu-popper-demo" mode="horizontal" :popper-offset="19"
             style="position: fixed; top: 0; left: 0; right: 0; z-index: 1000;" background-color="rgba(225, 255, 255, 0.2)"
             router>
      <el-menu-item index="/" class="menu-item">首页</el-menu-item>
      <el-sub-menu index="2" class="menu-item">
        <template #title>
          <p class="menu-item">影楼&摄影师</p>
        </template>
        <el-menu-item index="/studios" class="menu-item">影楼预约</el-menu-item>
        <el-menu-item index="/photographer" class="menu-item">摄影师</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="3" :popper-offset="8">
        <template #title>
          <template v-if="isLogin()">
            <el-avatar :src="getUserInfo().avatar"></el-avatar>
          </template>
          <template v-else>
            <p class="login-text">未登录</p>
          </template>
        </template>
        <el-menu-item v-if="isLogin()" index="/user/userInfo" class="menu-item"><el-icon>
          <User />
        </el-icon>个人中心</el-menu-item>
        <el-menu-item v-if="isLogin()" index="/appointment" class="menu-item"><el-icon>
          <Service />
        </el-icon>我的预约</el-menu-item>
        <el-menu-item v-if="isLogin()" index="/collect" class="menu-item"><el-icon>
<!--          <Message />-->
          <img :src="require('@/assets/icon/收藏 (1).svg')" alt="Heart" class="collect-icon">
        </el-icon>我的收藏</el-menu-item>
        <el-menu-item v-if="isLogin()" class="menu-item" @click="logout()"><el-icon>
          <Back />
        </el-icon>退出登录</el-menu-item>
        <el-menu-item v-else index="/user/login" class="menu-item">登录</el-menu-item>
      </el-sub-menu>
    </el-menu>
    <div class="router-view-container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import '../assets/styles/WeddingFont.css'
import { Message } from "@element-plus/icons-vue";
import {logout} from "@/api/user";

export default {
  name: "test",
  components: {
    Message
  },
  data() {
    return {
    }
  },
  created() {
    this.isLogin()
    console.log(this.isLogin())
  },
  methods: {
    logout() {
      const token = localStorage.getItem('token')
      logout().then(res =>{
        console.log(res)
        localStorage.removeItem('token');
        // sessionStorage.removeItem('userInfo');
        localStorage.removeItem('userInfo');
        this.$router.push('/')
        this.$message.success(res)
        }).catch(err =>{
          console.log(err)
      })
    },
    isLogin() {
      // return localStorage.getItem('token') != null && sessionStorage.getItem('userInfo') != null;
      return localStorage.getItem('token') != null && localStorage.getItem('userInfo') != null;
    },
    getUserInfo() {
     // return JSON.parse(sessionStorage.getItem('userInfo'));
      return JSON.parse(localStorage.getItem('userInfo'));
    }
  }
}
</script>

<style lang="scss">
@import "@/assets/styles/globalStyle.css";

/* 隐藏浏览器的纵向滚动条 */
body::-webkit-scrollbar {
  width: 0;
  /* 隐藏滚动条宽度 */
}

body {
  overflow-y: auto;
  /* 自动显示滚动条，如果内容超出屏幕高度 */
}

.menu-item {
  font-family: wedding_M, sans-serif;
  font-size: 2em;
}

.login-text {
  font-family: wedding_M, sans-serif;
  font-size: 2em;
}

el-menu-item {
  float: right;
}

.nav-container {
  width: 100%;
}

.el-menu-popper-demo {
  max-width: 100vw;
}

.router-view-container {
  margin-top: 60px;
  /* 确保路由页面内容不会被导航栏遮挡 */
}
.collect-icon {
  width: 30px; /* 设置图标宽度 */
  height: 30px; /* 设置图标高度 */
}
</style>
