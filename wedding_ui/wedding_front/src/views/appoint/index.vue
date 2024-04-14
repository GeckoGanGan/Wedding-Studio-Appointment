<template xmlns="http://www.w3.org/1999/html">
  <div v-if="hasData">
    <div class="appointment-page">
      <div class="header">
        <div class="appoint-info">
          <div class="appoint">
            <h3>共{{ total }}个预约</h3>
            <h3>总金额: ${{ totalMoney }}</h3>
          </div>
          <div class="appoint-search">
            <el-input style="width: 400px;" v-model="queryParam.queryConditions.studioName"
                      placeholder="请输入影楼名称进行搜索"
                      @change="handleSearch"
                      clearable>
            </el-input>
            <el-button style="margin-left: 50px;" type='primary' @click="pageQuery()">search</el-button>
          </div>
        </div>
      </div>
      <div class="appointment-list">
        <el-card v-for="appointment in appointments" :key="appointment.id" class="appointment-card">
          <div class="studio-info">
            <h1>{{ appointment.studio.studioName }}</h1>
            <p><el-icon>
              <Position />
            </el-icon>{{ appointment.studio.studioLocation }}</p>

          </div>
          <div class="package-info">
            <h3>预约时间：{{ appointment.createdTime }}</h3>
            <h3 v-if="appointment.status=='0'">状态：预约成功</h3>
            <h3 v-else>状态：预约中</h3>
            <h3>套餐名称：{{ appointment.studiosPackage.packageName }}</h3>
            <el-image class="package-thumbnail" style="max-width: 50vw; max-height: 300px"
                      :src="appointment.studiosPackage.packageThumbnail" fit="cover">
            </el-image>
            <h2>套餐价格：{{ appointment.studiosPackage.packagePrice }}元</h2>
          </div>
          <div class="photographers-info">
            <div class="photographer" v-for="photographer in appointment.photographers" :key="photographer.style">
              <div class="el-photographer-info">
                <el-avatar :src="photographer.photo" :size="90"></el-avatar>
                <div class="info">
                  <p> 摄影风格：{{ photographer.style }}</p>
                  <p v-if="photographer.sex==='0'">性别：男</p>
                  <p v-else>性别：女</p>
                  <p>摄影师姓名：{{ photographer.photographerName }}</p>
                  <p class="intro">摄影师介绍： {{ photographer.description }}</p>
                </div>
                <span class="delete-icon" @click="removePackagePhotographer(photographer)">
            </span>
              </div>
            </div>
          </div>
          <div slot="footer" class="card-footer">
            <el-button type="danger" size="mini" @click="deleteAppointment(appointment)">取消预约</el-button>
          </div>
        </el-card>
      </div>
      <div class="page">
        <el-pagination
            v-model:current-page="queryParam.pageNum"
            v-model:page-size="queryParam.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            :small="small"
            :disabled="disabled"
            :background="background"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total" @size-change="handleSizeChange"
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
import {getAppointList,cancelAppoint} from '@/api/appoint'

export default {
  data() {
    return {
      hasData: false,
      queryParam: {
        queryConditions: {
          userId: Number,
          studioName: ''
        },
        pageNum: 0,
        pageSize: 5
      },
      total: Number,
      appointments: [],
      searchKeyword: '',
      totalMoney: Number,


    };
  },

  created() {
    this.pageQuery()
  },
  computed: {
    filteredAppointments() {
      return this.appointments.filter(appointment =>
          appointment.name.toLowerCase().includes(this.searchKeyword.toLowerCase())
      );
    },
    latestAppointmentDate() {
      // Find the latest appointment date
      const sortedAppointments = this.appointments.slice().sort((a, b) => new Date(b.date) - new Date(a.date));
      return sortedAppointments.length > 0 ? sortedAppointments[0].date : '无';
    },
  },
  methods: {
    pageQuery() {
      const userInfo = localStorage.getItem('userInfo')
      this.queryParam.queryConditions.userId = JSON.parse(userInfo).id
      getAppointList(this.queryParam).then(res => {
        if (res.total > 0) {
          this.hasData = true;
          this.total = res.total;
          this.appointments = res.records;
          this.totalMoney = res.totalMoney;
          // 计算总价格
          this.totalAmount()
        }
        console.log('---------预约信息分页查询结果-----------')
        console.log(res)
      }).catch(err => {
        console.log(err)
      })

    },
    // 取消预约
    deleteAppointment(appointment) {
      cancelAppoint(appointment.id).then(res =>{
        this.$message({
          message:res.data,
          type:"success"
        })
        this.appointments.splice(this.appointments.indexOf(appointment), 1)
      }).catch(err =>{
        console.log(err)
      })
    },
    handleSearch() {
      // Logic to handle search
      console.log('Search keyword:', this.searchKeyword);
    },
    handleSizeChange(size) {
      console.log('---分页大小改变----')
      console.log(size)
      this.queryParam.pageSize = size;
      this.pageQuery();
      //console.log(`${size} items per page`)
    },
    handleCurrentChange(pageNo) {
      this.queryParam.pageNum = pageNo;
      this.pageQuery();
      console.log(`current page: ${pageNo}`)
    }
  },
};
</script>
<style scoped>
.appointment-page {
  border: solid 2px;
  padding: 20px;
}

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

.appoint-info {
  display: flex;
  margin-left: 20px;
  text-align: left;
  width: 100%;
}

.appoint {
  width: 35%;
}

.appoint-search {
  align-items: center;
  margin: 50px;
}

.appointment-list {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.appointment-card {
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

.photographer {
  display: flex;
  margin-bottom: 20px;
}

.package-info {
  text-align: left;
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

.el-photographer-info {
  margin-left: 20px;
  border-radius: 10px;
  width: 100vw;
  display: flex;
  align-items: center;
  padding: 0 10px;
}

.delete-icon {
  position: absolute;
  right: 10px;
}

</style>

