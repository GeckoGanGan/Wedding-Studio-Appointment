<template>
  <div class="studio-container">
    <div class="search-dataInfo">
      <div class="search-bar">
        <div class="background"></div> <!-- 新增背景元素 -->
        <el-input
            v-model="this.queryParam.queryConditions.studioName"
            size="medium"
            placeholder="请输入影楼名称"
            prefix-icon="Search"
            suffix-icon="Clear"
            style="width: 250px; margin-right: 10px;"
        />
        <el-input
            v-model="this.queryParam.queryConditions.studioLocation"
            size="medium"
            placeholder="请输入影楼地址"
            prefix-icon="Location"
            suffix-icon="Clear"
            style="width: 250px; margin-right: 10px;"
        />
        <el-button type="primary" icon="Search" @click="pageQuery" size="medium" style="width: 40px;"></el-button>
      </div>
    </div>
    <div class="data-hot">
      <div v-for="studio in pageQueryResult.records" :key="studio.id" class="studio-card">
        <div class="kapian" @click="this.$router.push({name:'details',params: {id:studio.id}})">
          <div class="tu">
            <img :src="studio.studioThumbnail">
          </div>
          <div class="wenben">
            <h2>{{studio.studioName}}</h2>
            <div>
              <img :src="require('@/assets/icon/浏览量.svg')" alt="Heart" class="view-icon">{{ studio.viewCount }}
              <img :src="require('@/assets/icon/收藏.svg')" alt="Heart" class="view-icon">{{ studio.collectCount }}
            </div>
            <span class="el-rate"><el-rate v-model="studio.rate" disabled show-score text-color="#ff9900" score-template="{value} points"></el-rate></span>
            <p style="padding-bottom: 20px;">{{studio.studioLocation}}</p>
            <div  class="description"><p>{{studio.description}}</p></div>
          </div>
        </div>
      </div>
      <div>
        <hot-top></hot-top>
      </div>
    </div>
    <div class="page">
      <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :page-sizes="[2,4,50,100]"
      :small="small"
      :disabled="disabled"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageQueryResult.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    </div>
  </div>
</template>

<script>
import { Search } from '@element-plus/icons-vue'
import {getStudioDetailsByPage} from "@/api/studios";
import hotTop from "@/components/HotTop.vue";

export default {
  name: "index",
  components: {Search,hotTop},
  data(){
    return{
      pageQueryResult:{
        total:Number,
        records:[],
      },
      queryParam:{
        queryConditions:{
          studioName:'',
          studioLocation:'',
          description:'',
          delFlag:0
        },
        pageNum: 0,
        pageSize: 10
      }

    }
  },
  mounted() {
    this.pageQuery()
  },
  methods:{
    pageQuery(){
      console.log('-----------测试state-----------')
      console.log(this.$store.state.userInfo.avatar)
      getStudioDetailsByPage(this.queryParam).then(res =>{
        console.log(res)
        this.pageQueryResult = res;
      }).catch(err =>{
        console.log(err)
      })
    },
    handleSizeChange(size){
      console.log('---分页大小改变----')
      console.log(size)
      this.queryParam.pageSize = size;
      this.pageQuery();
      //console.log(`${size} items per page`)
    },
    handleCurrentChange(pageNo){
      this.queryParam.pageNum = pageNo;
      this.pageQuery();
      console.log(`current page: ${pageNo}`)
    }
  }
}
</script>

<style scoped>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body{
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: radial-gradient(rgb(241, 238, 238),black);
}
.page {
  left: 50%;
  background-color: aliceblue;
  position: fixed;
  bottom: 20px; /* 调整分页组件距离底部的距离 */
  /* left: 13%; */
  transform: translateX(-50%);
  z-index: 1000; /* 确保分页组件位于其他内容之上 */

}

.kapian{
  position: relative;
  width: 50vw;
  height: 700px;
  background-color: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 3px 3px 2px rgb(139, 138, 138);
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s;
  border-radius: 20px;
}
.kapian:hover{
  box-shadow: 2px 3px 10px rgb(36, 35, 35);
}
.tu{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 500px;
  overflow: hidden;
}
.tu img{
  width: 100%;
  height: 100%;
  transition: all 0.5s;
}
.kapian:hover .tu img{
  transform: scale(1.5);
  filter: blur(1px);
}
.wenben{
  position: absolute;
  bottom: -200px;
  width: 100%;
  height: 300px;

  background-color: rgb(247, 242, 242);
  transition: 0.5s;
}
.kapian:hover .wenben{
  bottom: 0px;
}
.wenben h2{
  color: rgb(21, 74, 172);
  line-height: 60px;
  text-align: center;

}
.wenben p{
  padding: 0 30px;
  font-family: 'fangsong';
  font-size: 16px;
  font-weight: bold;
  line-height: 20px;
  text-align: center;
}
.studio-card{
  margin-top: 50px;
  margin-bottom: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.search-bar {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 40px;
  margin-bottom: 20px;
}
/* 新增背景样式 */
.background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-image: url('~@/assets/background/b2.jpg');
  filter: blur(1px); /* 添加背景模糊效果 */
  z-index: -1; /* 确保背景位于内容之后 */
  background-size: cover;

}
.view-icon{
  width: 30px; /* 设置图标宽度 */
  height: 30px; /* 设置图标高度 */
  margin-right: 10px;
}
.description{
  justify-content: left;
  text-align: left;
}

</style>
