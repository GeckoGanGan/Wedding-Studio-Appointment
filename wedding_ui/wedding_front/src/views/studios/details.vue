<template>
  <div class="studio-card">
    <div class="background"></div> <!-- 新增背景元素 -->
    <div class="content">
      <div class="thumbnail">
        <img :src="studioDetails.studioThumbnail" alt="Thumbnail of the studio">
      </div>
      <div class="details">
        <div>
          <h2 class="name" @click="getId()">{{ studioDetails.studioName }}</h2>
          <div class="description">
            <h3 style="text-align: left; margin-left: 20px">【影楼介绍】</h3>
            <p class="studioDesc">{{ studioDetails.description }}</p>
          </div>
        </div>
        <div class="contact">
          <div class="">
            <h3>【营业时间
              <el-icon>
                <Odometer/>
              </el-icon>
              】
            </h3>
            <el-popover
                placement="top-start"
                title="温馨提示~~"
                :width="200"
                trigger="hover"
                content="只能预约营业时间段噢~"
                auto-close=2
            >
              <template #reference>
                <span style="color:#e998cb ">{{ studioDetails.startTime }} ~ {{ studioDetails.endTime }}</span>
              </template>
            </el-popover>
            <el-button @click="this.$router.push({name:'package',params:{studioId:studioDetails.id}})" type="primary"
                       style="margin-left: 10px">预约
            </el-button>
            <span style="color:red" @click="this.$router.push({name:'chat',params:{studioId:studioDetails.id}})">有问题？点击咨询客服<el-icon
                size="30px"><ChatDotRound/></el-icon></span>
          </div>
          <div class="rating">
            <h3>【评分】</h3>
            <el-rate v-model="this.studioDetails.rate" disabled show-score text-color="#ff9900"
                     score-template="{value} points"></el-rate>
            (<span>{{ this.studioDetails.rateCount }}</span>人评价)
          </div>
          <div class="rating">
            <h3>【浏览量和收藏】</h3>
            <img :src="require('@/assets/icon/浏览量.svg')" alt="Heart" class="view-icon">
            {{ studioDetails.viewCount }}
            <img :src="require('@/assets/icon/收藏.svg')" alt="Heart" class="view-icon"
                 @click="collect()">{{ studioDetails.collectCount }}
          </div>
          <h3>【剩余可预约：<span>{{ studioDetails.maxReservation }}</span>】</h3>
          <div class="position">
            <h4>
              <el-icon>
                <Position/>
              </el-icon>
              {{ studioDetails.studioLocation }}
            </h4>
          </div>
        </div>
      </div>
    </div>
    <div class="photographer-list">
      <h1>影楼摄影师</h1>
      <div class="photographer" v-for="photographer in studioDetails.photographerVOList" :key="photographer.style">
        <div class="photographer-info">
          <el-avatar :src="photographer.photo" :size="90"></el-avatar>
          <div class="info">
            <p>
              <el-icon>
                <CameraFilled/>
              </el-icon>
              {{ photographer.style }}
            </p>
            <p>性别：
              <el-icon v-if="photographer.sex === '0'">
                <Male/>
              </el-icon>
              <el-icon v-else>
                <Female/>
              </el-icon>
            </p>
            <p>年龄：{{ photographer.age }}</p>
            <p>
              <el-icon>
                <User/>
              </el-icon>
              ：{{ photographer.photographerName }}
            </p>
            <p class="intro">
              <el-icon>
                <ChatDotSquare/>
              </el-icon>
              {{ photographer.description }}
            </p>
          </div>
        </div>
      </div>
    </div>
    <div v-if="studioDetails && studioDetails.images && studioDetails.images.length > 0">
      <h1 style="margin-bottom: 20px">影楼图片展示</h1>
      <el-carousel :interval="3000" type="card" height="400px">
        <el-carousel-item v-for="item in studioDetails.images" :key="item">
          <el-image :src="item" justify="center"></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="comment-box" style="margin-top: 30px">
      <Comment1 v-if="studioDetails.id" :sId="studioDetails.id" :commentType="0"></Comment1>
    </div>
  </div>
</template>
<script lang="js">

import Comment1 from "@/components/Comment.vue";
import {getStudioDetailsById, view, collect} from "@/api/studios";

export default {
  name: 'StudioCard',
  components: {
    Comment1,
  },
  data() {
    return {
      type: 0,
      studioDetails: {
        id: '',
        studioThumbnail: '',
        studioName: '',
        description: '',
        rate: Number,
        studioLocation: '',
        // 营业开始时间
        startTime: '',
        // 营业结束时间
        endTime: '',
        // 剩余最大预约数
        maxReservation: Number,
        // 评价数
        rateCount: Number,
        images: [],
        photographerVOList: [
          {
            photo: '',
            style: '',
            sex: '',
            age: '',
            photographerName: '',
            description: ''
          }
        ],
      },
      param:{
        id:Number,
        type:'0',
        name:''
      },
      comment: '',
    }
  },
  created() {
    this.getStudioDetails();
  },
  methods: {
    getStudioDetails() {
      const id = this.getId();
      console.log('-----------------------查询影楼i详情-------------------------------')
      console.log(id)
      getStudioDetailsById(id).then(res => {
            console.log('------------------------------------')
            console.log(res)
            console.log('------------------------------------')
            this.studioDetails = res;
          }
      ).catch(err => {
        console.error(err);
      })
    },
    getId() {
      return this.$route.params.id
    },
    collect() {
      console.log('-----------------------收藏----------------------')
      this.param.id = this.studioDetails.id
      this.param.name = this.studioDetails.studioName
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
            this.studioDetails.collectCount++;
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
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

/* 新增背景样式 */
.background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: white;
  filter: blur(2px);
  z-index: -1;
  background-size: cover;
}

.studio-card {
  background-color: #F8F8FF;
  max-width: 70vw;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.1);
  padding: 16px;
}

.studioDesc {
  text-align: left;
  word-wrap: break-word;
  color: darksalmon;
  margin: 29px;
  line-height: 40px;
  text-decoration: underline;
}

.comment-box {
  text-align: left;
}

.contact {
  text-align: left;
  margin-left: 20px;
}

.content {
  display: flex;
}

.position {
  font-size: large;
}

.thumbnail {
  border: solid 2px;
  border-radius: 10px;
  flex: 1;
  overflow: hidden;
  margin-right: 20px;
  min-width: 200px;
  max-width: 30vw;
}

.thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.details {
  flex: 1;
  border: #e998cb 3px solid;
}

.name {
  margin-top: 0;
}

.photographer-list {
  margin-top: 40px;
}

.photographer {
  display: flex;
  margin-bottom: 20px;
}

.photographer-info {
  border-radius: 10px;
  background-color: #FFF0F5;
  width: 100vw;
  display: flex;
  align-items: center;
  padding: 0 10px;
  /* 添加这一行 */
}

.info {
  margin-left: 20px;
  text-align: left;
}

.intro {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.view-icon {
  width: 30px; /* 设置图标宽度 */
  height: 30px; /* 设置图标高度 */
  margin-right: 10px;
}

@keyframes heartbeat {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.3);
  }
  100% {
    transform: scale(1);
  }
}

</style>

