<template xmlns="http://www.w3.org/1999/html">
  <div v-if="hasData">
    <div class="appointment-page">
      <div class="header">
        <p>收藏信息</p>
        <div class="collect-info">
          <div class="appoint">
            <p>共{{ total }}个收藏</p>
            <p>
              类型选择：
              <el-cascader :options="options" placeholder="请选择类型" @change="handleTypeChange" />
            </p>
          </div>
          <div class="appoint-search">
            <el-input style="width: 400px;" v-model="queryParams.queryConditions.name"
                      placeholder="请输入名称进行搜索"
                      clearable>
            </el-input>
            <el-button style="margin-left: 50px;" type='primary' @click="getCollects()">search</el-button>
          </div>
        </div>
      </div>
      <div class="collect-list">
       <div v-if="type=='0'">
         <el-card v-for="studio in studios" :key="studio.id" class="collect-card" @click="this.$router.push({name:'details',params:{id:studio.id}})">
           <div class="studio-info">
             <h1>{{ studio.studioName }}</h1>
             <p><el-icon>
               <Position />
             </el-icon>{{ studio.studioLocation }}</p>
           </div>
           <div class="package-info">
             <h3>收藏时间：{{ studio.createdTime }}</h3>
             <div class="studio-thumbnail">
               <el-image style="max-width: 70vw; max-height: 500px;"
                         :src="studio.studioThumbnail" fit="cover">
               </el-image>
             </div>
             <h3>{{studio.description}}</h3>
           </div>
           <div slot="footer" class="card-footer">
             <el-button type="danger" size="mini" @click="cancelCollect(studio)">取消收藏</el-button>
           </div>
         </el-card>
       </div>
        <div v-else>
          <el-card v-for="photographer in photographers" :key="photographer.id" class="collect-card"  @click="this.$router.push({name:'phtographerIngfo',params: {photographerId:photographer.id}})">
            <div class="package-info">
              <el-avatar :src="photographer.photo" size="large"></el-avatar><h3>{{ photographer.photographerName }}</h3>
              <h3>收藏时间：{{ photographer.createdTime }}</h3>
              <h3>摄影风格：{{ photographer.style }}</h3>
              <h3>邮箱：{{ photographer.photographerEmail }}</h3>
              <h3>{{photographer.description}}</h3>
              <div class="photographer-carousel">
                <el-carousel :interval="5000" arrow="always">
                  <el-carousel-item v-for="(photo, index) in photographer.portfolio" :key="index">
                    <el-image class="photographer-portfolio"
                              style="max-width: 50vw; max-height: 300px"
                              :src="photo.imageUrl" fit="cover">
                    </el-image>
                  </el-carousel-item>
                </el-carousel>
              </div>
            </div>
            <div slot="footer" class="card-footer">
              <el-button type="danger" size="mini" @click="cancelCollect(photographer)">取消收藏</el-button>
            </div>
          </el-card>
        </div>

      </div>
      <div class="page">
        <el-pagination
            v-model:current-page="queryParams.pageNum"
            v-model:page-size="queryParams.pageSize"
            :page-sizes="[2,5, 10, 15, 20]"
            :small="small"
            :disabled="disabled"
            :background="background"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
  <!-- 如果数据为空 -->
  <template v-else>
    <el-empty image-size="400" description="客官，这里空空如也~"/>
  </template>
</template>

<script>
import {getCollect,cancelCollect} from "@/api/studios";

export default {
  data() {
    return {
      hasData: false,
      type:'0',
      options: [
        {
          label: '影楼',
          value: '0'
        },
        {
          label: '摄影师',
          value: '1'
        }
      ],
      studios:[],
      photographers:[],
      total: Number,
      searchKeyword: '',
      totalCount:Number,
      queryParams:{
        queryConditions: {
          name: '',
          type:'0'
        },
        pageNum: 0,
        pageSize: 5
      },
    };
  },

  created() {
    this.getCollects();
  },
  methods: {
    getCollects(){
      getCollect(this.queryParams).then(res =>{
        console.log(res)
        this.total = res.total;
        this.totalCount = res.total;
        this.hasData =res.hasData;

        if (this.type =='0'){
          this.studios = res.records;
        }else {
          this.photographers = res.records;
        }
      }).catch(err =>{
        console.log(err)
      })
    },
    cancelCollect(collect){
      const collectId = collect.id;
      cancelCollect(collectId).then(res =>{
        this.$message.success(res)
       if (this.type=='0'){
         this.studios.splice(this.studios.indexOf(collect),1)
       } else {
         this.photographers.splice(this.photographers.indexOf(collect),1)
       }
        this.total--;
      }).catch(err =>{
        console.log(err)
      })
    },
    handleTypeChange(value){
      // value 是一个数组，包含所有级联选择器中被选中的值
      // 我们只需获取最后一个元素即可，因为这里只有一个级别
      const selectedType = value[value.length - 1];
      this.queryParams.queryConditions.type = selectedType;
      this.type = selectedType; // 可以选择更新类型值，以便在模板中动态显示不同的内容
      this.getCollects(); // 调用查询收藏的方法
    },
    handleSizeChange(size) {
      console.log('---分页大小改变----')
      console.log(size)
      this.queryParams.pageSize = size;
      this.getCollects();

    },
    handleCurrentChange(pageNo) {
      this.queryParams.pageNum = pageNo;
      this.getCollects();
    }
  },
};
</script>
<style scoped>
.page {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: aliceblue;
  position: fixed;
  bottom: 20px;
  /* 调整分页组件距离底部的距离 */
  left: 50%;
  transform: translateX(-50%);
  z-index: 1000;
  /* 确保分页组件位于其他内容之上 */
}

.header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.collect-info {
  display: flex;
  margin-left: 20px;
  text-align: left;
  width: 100%;
}


.appoint-search {
  align-items: center;
  margin: 50px;
}

.collect-list {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.collect-card {
  text-align: center;
  width: 50vw;
  min-height: 500px; /* 使用 min-height 而不是 height */
  margin-bottom: 20px;
}


.card-content img {
  width: 400px;
  height: 300px;
}


.card-footer {
  position: relative;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-bottom: 0%;
}

.card-footer el-button {
  padding-bottom: 0px;
}


.package-info {
  text-align: left;
}
.photographer-portfolio{
  text-align: center;
}
.studio-thumbnail{
  text-align: center;
}
.photographer-carousel {
  text-align: center; /* 让走马灯中的内容居中 */
}

.photographer-portfolio {
  display: inline-block; /* 将图片设为行内块级元素 */
}

</style>

