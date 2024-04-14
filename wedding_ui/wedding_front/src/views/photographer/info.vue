<template>
  <div class="photographer-profile">
    <div class="profile-header">
      <!-- 摄影师头像 -->
      <el-image :src="photographerInfo.photo" style="border-radius: 50%;width: 100px;height: 100px"></el-image>
      <div class="profile-info">
        <!-- 摄影师名称和性别 -->
        <h2>{{ photographerInfo.photographerName }}</h2>
        <p v-if="photographerInfo.sex=='1'"><strong>性别:男</strong></p>
        <p v-else><strong>性别:女</strong></p>
        <p><strong>邮箱:</strong> {{ photographerInfo.photographerEmail }}</p>
        <p><strong>风格:</strong> {{ photographerInfo.style }}</p>
        <p><strong>签名:</strong> {{ photographerInfo.description }}</p>
      </div>
    </div>

    <!-- 介绍卡片 -->
    <el-card class="profile-card">
      <div slot="header">
        <h2>摄影师介绍</h2>
      </div>
      <div class="profile-bio">
        <p>{{ photographerInfo.experience }}</p>
      </div>
    </el-card>

    <!-- 作品集卡片 -->
    <el-card class="portfolio-card">
      <div slot="header">
        <h2>作品集</h2>
      </div>
      <div class="portfolio-images">
        <el-image
            v-for="(image, index) in photographerInfo.portfolio"
            :key="index"
            :src="image.imageUrl"
            fit="cover"
            style="width: 400px; height: 200px; margin-right: 10px; margin-bottom: 10px;"
        ></el-image>
      </div>
    </el-card>
  </div>
</template>

<script>
import {getPhotographerInfo} from "@/api/photographer";
export default {
  data() {
    return {
      photographerInfo:{}
    };
  },
  created() {
    this.getPhotographerInfo()
  },
  methods:{
    getPhotographerInfo(){
      const id = this.getPhotographerId();
      getPhotographerInfo(id).then(res =>{
        console.log('----------------------------摄影师信息---------------------')
        console.log(res)
        this.photographerInfo = res
      }).catch(err =>{
        console.log(err)
      })
    },
    getPhotographerId(){
      return this.$route.params.photographerId
    }
  }
};
</script>

<style scoped>
.photographer-profile {
  max-width: 60vw;
  margin: 0 auto;
  padding: 20px;
  background-color: #dabcd1; /* 背景颜色 */
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  top: 10%;
  left: 0;
  width: 100%;
  background-image: url('~@/assets/background/ph-info.jpg');
  background-size: cover;
  background-position: center;
}
.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.profile-info {
  text-align: left;
  margin-left: 20px;
}

.profile-bio p {
  text-align: left;
  margin-bottom: 10px;
}

.portfolio-images {
  display: flex;
  flex-wrap: wrap;
}

/* 添加动画效果 */
.profile-card,
.portfolio-card {
  transition: transform 0.3s ease;
}

.profile-card:hover,
.portfolio-card:hover {
  transform: translateY(-5px);
}
</style>
