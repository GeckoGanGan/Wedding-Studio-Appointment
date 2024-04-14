<template>
    <div class="container">
        <!-- 导航栏 -->
        <div class="home-nav">
            <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" background-color="#545c64"
                text-color="#fff" active-text-color="#ffd04b" router>
                <el-menu-item index="1" :to="{ path: '/admin/dashboard' }">
                    <div class="user-info">
                        <!-- 用户登录信息：头像和用户名 -->
                        <el-avatar size="large" :src="userInfo.avatar"></el-avatar>
                        欢迎<span style="color: aqua;">{{ userInfo.userRole }}</span>登录
                    </div>
                </el-menu-item>
                <el-submenu index="2">
                    <el-menu-item index="/admin/user" :to="{ path: '/admin/user' }">
                        <img class="icon" :src="require('@/assets/icons/用户管理.svg')">
                        用户管理
                    </el-menu-item>
                    <el-menu-item index="/admin/role" :to="{ path: '/admin/user' }">
                        <img class="icon" :src="require('@/assets/icons/影楼管理.svg')">
                        影楼管理
                    </el-menu-item>
                    <el-menu-item index="/admin/role" :to="{ path: '/admin/user' }">
                        <img class="icon" :src="require('@/assets/icons/摄影师管理.svg')">
                        摄影师管理
                    </el-menu-item>
                    <el-menu-item index="/admin/role" :to="{ path: '/admin/user' }">
                        <img class="icon" :src="require('@/assets/icons/评论管理.svg')">
                        评论管理
                    </el-menu-item>
                    <el-menu-item index="/admin/role" :to="{ path: '/admin/role' }">
                        <img class="icon" :src="require('@/assets/icons/角色管理.svg')">
                        角色管理
                    </el-menu-item>
                    <el-menu-item index="2-3" :to="{ path: '/admin/permission' }">
                        <img class="icon" :src="require('@/assets/icons/权限管理.svg')">
                        权限管理
                    </el-menu-item>
                </el-submenu>

                <el-menu-item  @click="logout" :to="{ path: '/' }">
                    <img class="icon" :src="require('@/assets/icons/退出登录.svg')">
                    <span slot="title">
                        退出
                    </span>
                </el-menu-item>
            </el-menu>
        </div>
        <!-- 内容 -->
        <div class="home-content">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import { userInfo } from '@/utils/UserUtils'
import { logout } from "@/api/user";
export default {
    name: 'Home',
    data() {
        return {
            activeIndex: '1',// 设置默认的导航栏活动项
            userInfo: userInfo() // 获取用户登录信息
        }
    },
    methods: {
        logout() {
            this.$confirm('确认退出登录吗？')
                .then(() => {
                    const token = localStorage.getItem('token')
                    logout().then(res => {
                        console.log(res)
                        localStorage.removeItem('token');
                        // sessionStorage.removeItem('userInfo');
                        localStorage.removeItem('userInfo');
                        this.$router.push('/')
                        this.$message.success(res)
                    }).catch(err => {
                        console.log(err)
                    })
                }).catch(() => {
                    this.$message.info('已取消')
                })
        
        }
    }
}
</script>


<style scoped>
.container {
    height: 100%;
    width: 100%;
}

.home-nav {
    height: 100%;
    width: 200px;
    position: fixed;
    top: 0;
    left: 0;
    background-color: #304156;
    color: #fff;
    transition: all 0.3s ease;
}
.icon{
    width: 30px;
    height: 30px;
    margin-right: 10px;
}
.el-menu-vertical-demo {
    width: 200px;
}

.el-menu-item:hover {
    background-color: #409eff;
}


.el-submenu__title:hover {
    background-color: #409eff;
}


.el-menu-item,
.el-submenu__title {
    height: 70px;
    line-height: 20px;
    padding-left: 20px;
}

.el-menu-item {
    font-size: 14px;
}

.el-submenu__title {
    font-size: 16px;
}

.el-icon-setting {
    margin-right: 10px;
}

.el-icon-menu {
    margin-right: 10px;
}

.el-icon-switch-button {
    margin-right: 10px;
}

.home-content {
    margin-left: 200px;
    padding: 20px;
    background-color: #f0f2f5;
    min-height: 100vh;
}

.user-info {
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 5px;
    text-align: right;
}

.el-avatar {
    margin-left: 0px;
    margin-right: 10px;
}
</style>
