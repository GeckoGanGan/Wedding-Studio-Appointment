<template>
  <div class="photographer-list">
    <div v-for="photographer in photographers" :key="photographer.id" class="photographer-card">
      <div>
        <div class="card-header">
          <img :src="photographer.photo" class="avatar" alt="Avatar"  @click="this.$router.push({name:'phtographerIngfo',params: {photographerId:photographer.id}})">
          <div class="info">
            <h2>姓名：{{ photographer.photographerName }}</h2>
            <h2>摄影风格：{{ photographer.style }}</h2>
            <h2>邮箱：{{ photographer.photographerEmail }}</h2>
            <h2>签名：{{ photographer.description }}</h2>
          </div>
        </div>
        <span>【作品集】</span>
        <div class="photo-gallery">
          <div v-for="photo in photographer.portfolio" :key="photo.id" class="photo-item">
            <img :src="photo.imageUrl" alt="Photo">
          </div>
        </div>
        <div class="actions">
          <img :src="require('@/assets/icon/收藏.svg')" alt="Heart" class="collect-icon"
               @click="collect(photographer.id,photographer.photographerName)">
          <img :src="require('@/assets/icon/评论区.svg')" class="comment-icon" @click="openComment(photographer)">
          <img :src="require('@/assets/icon/评论点赞.svg')" alt="Heart" class="heart-icon" @click="like(photographer)" :class="{ 'clicked': photographer.clicked }">
          {{ photographer.likes }}
        </div>
        <div v-if="photographer.showCommentList" class="comment">
          <comment1 :v-if="photographer.id" :sId="photographer.id" :commentType="1"></comment1>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Comment1 from "@/components/Comment.vue";
import { listPhotographer,likePhotographer } from "@/api/photographer";
import {collect} from "@/api/studios";

export default {
  data() {
    return {
      photographers: [],
      photographersLik:Map,
      param:{
        id:Number,
        type:'1',
        name:''
      },
    };
  },
  components: {
    Comment1
  },
  created() {
    // 模拟获取摄影师列表数据
    this.getPhotographers();
  },
  methods: {
    openComment(photographer) {
      photographer.showCommentList = !photographer.showCommentList;
    },
    getPhotographers() {
      // 获取摄影师列表
      listPhotographer().then(res => {
        this.photographers = res.map(photographer => ({
          ...photographer,
          showCommentList: false // 初始化评论列表的展开状态为 false
        }));
      }).catch(err => {
        console.log(err)
      })
      // 查询点赞量

    },
    like(photographer) {
      console.log('---------------likePhotographer--------------')
      console.log(photographer.id)
      likePhotographer(photographer.id).then(res =>{
        console.log(res)
        // 模拟点赞功能
        photographer.likes++;
        // 添加动画类名
        photographer.clicked = true;
        // 1秒后移除动画类名，以便下次点击时再次触发动画
        setTimeout(() => {
          photographer.clicked = false;
        }, 1000);
      }).catch(err =>{
        console.log(err)
      })

    },

      collect(id,photographerName) {
        console.log('-----------------------收藏----------------------')
        this.param.id = id
        this.param.name = photographerName
        collect(this.param).then(res => {
          console.log('-----------------------收藏res----------------------')
          console.log(res)
          if (res == 'NOT_ALLOW_COLLECT_REPEATABLE'){
            this.$message({
              message: '您已经收藏过啦~不可重复收藏哟！',
              type: 'warning'
            });
            return
          }else {
            this.$message({
              message: res,
              type: 'success'
            });
          }
        }).catch(err=>{
          console.log(err)
        })
      }
  }
};
</script>

<style scoped>
.photographer-list {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.photographer-card {
  background-color: #f7f7f7;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  padding: 20px;
}

.card-header {
  display: flex;
  align-items: center;
}

.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  margin-right: 20px;
}

.info {
  text-align: left;
  flex: 1;
}

.photo-gallery {
  display: flex;
  flex-wrap: wrap;
  margin-top: 20px;
}

.photo-item {
  flex: 0 0 calc(33.3333% - 10px);
  margin-right: 10px;
  margin-bottom: 10px;
}

.photo-item img {
  width: 100%;
  border-radius: 5px;
}

.actions {
  margin-top: 20px;
  text-align: right;
}

.heart-icon, .comment-icon, .collect-icon {
  width: 30px; /* 设置图标宽度 */
  height: 30px; /* 设置图标高度 */
  margin-right: 10px;
  cursor: pointer;
  transition: transform 0.2s ease; /* 添加过渡动画 */
}

.heart-icon.clicked {
  transform: scale(1.2); /* 点击后放大图标 */
}

.comment-icon:hover {
  transform: rotate(360deg); /* 鼠标悬停时旋转图标 */
}
.comment{
  text-align: left;
}
</style>
