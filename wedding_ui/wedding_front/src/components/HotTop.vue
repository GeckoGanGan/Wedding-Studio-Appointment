<template>
  <div class="hot-ranking">
    <div class="ranking-header">
      <h2 class="ranking-title">热榜排行</h2>
      <el-radio-group v-model="rankingType" size="small">
        <el-radio-button label="0" @click="getHotRanking()">热度排行</el-radio-button>
        <el-radio-button label="1" @click="getHotRanking()">评分排行</el-radio-button>
      </el-radio-group>
    </div>
    <div class="ranking-list">
      <ul v-if="rankingType === '0'" class="hot-list">
        <li v-for="(item, index) in ranking" :key="item.id" class="list-item">
          <span class="rank">{{ index + 1 }}</span>
          <span class="name" @click="this.$router.push({name:'details',params:{id:item.id}})">{{ item.studioName }}</span>
          <span class="info">
            <img :src="require('../assets/icon/浏览量.svg')" class="hot-icon">
            热度：{{ item.viewCount }}
          </span>
        </li>
      </ul>
      <ul v-else-if="rankingType === '1'" class="rating-list">
        <li v-for="(item, index) in ranking" :key="item.id" class="list-item">
          <span class="rank">{{ index + 1 }}</span>
          <span class="name" @click="this.$router.push({name:'details',params:{id:item.id}})">{{ item.studioName }}</span>
          <span class="info">
            <img :src="require('../assets/icon/评分等级Rating (7).svg')" class="hot-icon">
            评分：{{ item.rate }}
          </span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import {getHotStudio} from "@/api/studios";
export default {
  data() {
    return {
      rankingType: '1', // 默认显示热度排行
      ranking: [], // 热度排行列表
    };
  },
  methods:{
    getHotRanking(){
      getHotStudio(this.rankingType).then(res =>{
        console.log('-----------查询影楼排名----------')
        console.log(res)
        this.ranking = res;
      }).catch(err =>{
        console.log(err)
      })
    }
  },
  mounted() {
    this.getHotRanking();
  },
};
</script>

<style scoped>
.hot-ranking {
  position: fixed;
  top: 70px; /* 调整位置 */
  bottom: 50px;
  right:20px; /* 调整位置 */
  width: 20%; /* 调整宽度 */
  background-color: rgba(245, 245, 245, 1); /* 修改为透明色值 */
  border: 1px solid #ddd;
  border-radius: 5px;
  max-height: 90vh; /* 设置最大高度 */
  overflow-y: auto; /* 显示滚动条 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 10px;
}

.ranking-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

.ranking-title {
  font-size: 18px;
  margin: 0;
}

.ranking-list {
  overflow: hidden; /* 隐藏溢出内容 */
}

.hot-list,
.rating-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.list-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}

.rank {
  font-weight: bold;
  font-size: 16px;
}

.name {
  flex: 1;
  margin-left: 10px;
}

.info {
  color: #888;
}
.hot-icon{
  width: 20px;
  height: 20px;
}
</style>
