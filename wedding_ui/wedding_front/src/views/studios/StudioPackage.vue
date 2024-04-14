<template>
  <div class="reservation-container">
    <!-- 影楼信息 -->
    <div class="studio-info">
      <div class="studio-image">
        <img :src="studios.studio.studioThumbnail" alt="Studio Image">
      </div>
      <div class="studio-description">
        <h1>{{studios.studio.studioName}}</h1>
        <p style="margin:10px;text-align: left">{{studios.studio.description}}</p>
        <h2 style="margin:10px;text-align: left;color: aquamarine">营业时间：{{studios.studio.startTime}} ~ {{studios.studio.endTime}}</h2>
        <p style="color: #999999;font-size: 20px"><el-icon>
          <Position />
        </el-icon>{{studios.studio.studioLocation }}</p>
      </div>
    </div>

    <!-- 套餐列表 -->
    <el-card class="package-card" v-for="pkg in studios.studiosPackageList" :key="pkg.id">
      <!-- 套餐选择按钮 -->
      <div class="radio"><el-radio v-model="selectedPackage.selectedPackageInfo" :label="pkg" border class="package-select-button">
        <h1 style="color: coral;text-align: left">{{ pkg.packageName }}</h1>
      </el-radio></div>

      <div class="package-thumbnail">
        <img :src="pkg.packageThumbnail" alt="Package Image">
      </div>
      <div class="package-data">
        <ul>
          <li><h2><span class="package-data-head">【套餐介绍】：</span>{{ pkg.packageDesc }}</h2></li>
          <li><h2><span class="package-data-head">【价格】: </span>{{ pkg.packagePrice }}元</h2></li>
          <li><h2><span class="package-data-head">【拍摄时长】:</span> {{ pkg.packageDuration }}小时</h2></li>
          <li><h2><span class="package-data-head">【服务项目】:</span> {{ pkg.packageIncludes }}</h2></li>
          <li><h1 v-if="pkg.isRecommend === 1" style="color: darksalmon">推荐套餐</h1>
            <h1 v-else style="color: aquamarine">基础套餐</h1>
          </li>
        </ul>
      </div>
    </el-card>

    <!-- 摄影师列表 -->
    <el-card class="photographer-card">
      <h2 slot="header">选择摄影师</h2>
      <el-checkbox-group v-model="selectedPackage.selectedPhotographersInfo">
        <div class="photographer-list">
          <div class="photographer" v-for="photographer in studios.photographers" :key="photographer.style">
            <el-checkbox :label="photographer" style="margin-top: 50px;margin-bottom: 60px" size="large" :key="photographer.id">
              <div class="photographer-info">
                <el-avatar :src="photographer.photo" :size="90"></el-avatar>
                <div class="info">
                  <p> 摄影风格：{{ photographer.style }}</p>
                  <p v-if="photographer.sex==='0'">性别：男</p>
                  <p v-else>性别：女</p>
                  <p>摄影师姓名：{{ photographer.photographerName }}</p>
                  <p class="intro">摄影师介绍： {{ photographer.description }}</p>
                </div>
              </div>
            </el-checkbox>
          </div>
        </div>
      </el-checkbox-group>
    </el-card>
    <!-- 预约按钮 -->
    <el-button class="submit-button" type="primary" @click="openAndColseDialog">立即预约</el-button>
    <!--  弹出对话框-->
    <el-dialog
        v-model="centerDialogVisible"
        title="填写预约信息~"
        width="50vw"
        center
    >
      <div class="package-info">
        <h3>套餐名称：{{ selectedPackage.selectedPackageInfo.packageName }}</h3>
        <el-image class="package-thumbnail" style="max-width: 50vw; max-height: 300px" :src="selectedPackage.selectedPackageInfo.packageThumbnail" fit="cover"></el-image> <!-- 添加套餐缩略图展示 -->
        <h2>套餐价格：</h2><p>{{ selectedPackage.selectedPackageInfo.packagePrice }}元</p>
      </div>

      <div class="photographers-info">
        <h3>选择的摄影师：</h3>
        <div class="photographer" v-for="photographer in selectedPackage.selectedPhotographersInfo" :key="photographer.style">
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
              <el-icon size="20" color="red"><DeleteFilled /></el-icon>
            </span>
          </div>
        </div>
      </div>

      <!-- 选择日期和用户备注信息 -->
      <div class="block">
        <p class="demonstration">
          选择预约时间：
          <el-date-picker
              v-model="selectedPackage.appointmentTime"
              type="datetime"
              placeholder="Select date and time"
              :default-time="defaultTime"
              :picker-options="pickerOptions"
          >
          </el-date-picker>
        </p>
      </div>
      <p>预约备注：<el-input type="textarea" v-model="selectedPackage.appointmentRemark"></el-input></p>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click=openAndColseDialog()>取消</el-button>
          <el-button type="primary" @click=submitReservation()>预约</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {getStudioPackageInfo,addStudioPackageOrder} from "@/api/studiopackage";
export default {
  data() {
    return {
      selectedPackage: {
        studioId:'',
        appointmentTime: Date,
        appointmentRemark: '',
        selectedPackageInfo: {},
        selectedPhotographersInfo: [],
      },
      defaultTime: new Date(2000, 1, 1, 12, 0, 0),
      centerDialogVisible: false,
      studios: {
        studio: '',
        studiosPackageList: [],
        photographers: [],
      },
    };
  },
  mounted() {
    const id = this.$route.params.studioId;
    this.getPackages(id);
  },
  methods: {
    openAndColseDialog() {
      if (this.selectedPackage.selectedPackageInfo.packageName && this.selectedPackage.selectedPhotographersInfo.length > 0) {
        this.centerDialogVisible = !this.centerDialogVisible;
      } else {
        this.$message.warning('请选择一个套餐和至少一位摄影师！')
      }
    },
    getPackages(id) {
      getStudioPackageInfo(id).then(res => {
        this.studios = res;
      }).catch(err => {
        console.log('获取套餐信息失败！')
        console.log(err)
      })

    },
    removePackagePhotographer(photographer) {
      this.selectedPackage.selectedPhotographersInfo.splice(this.selectedPackage.selectedPhotographersInfo.indexOf(photographer), 1)
      this.$message.success('移除摄影师成功！')
    },
    submitReservation() {
      if (this.selectedPackage.selectedPackageInfo.packageName && this.selectedPackage.selectedPhotographersInfo.length > 0) {
        // 检查预约时间是否在营业时间范围内
        const selectedTime = new Date(this.selectedPackage.appointmentTime).getTime();
        const businessStartDateTime = new Date(this.studios.studio.startTime).getTime();
        const businessEndDateTime = new Date(this.studios.studio.endTime).getTime();

        if (selectedTime >= businessStartDateTime && selectedTime <= businessEndDateTime) {
          // 提交预约
          console.log('选中的套餐：', this.selectedPackage);
          this.selectedPackage.studioId = this.$route.params.studioId;
          addStudioPackageOrder(this.selectedPackage).then(res =>{
            console.log('--------------进入预约接口---------------')
            console.log(res)
            this.$message.success(res);
          }).catch(err =>{
            console.log(err)
          })
          this.centerDialogVisible = !this.centerDialogVisible;
        } else {
          this.$message.warning('请选择营业时间范围内的预约时间！');
        }
      } else {
        this.$message.warning('请选择一个套餐和至少一位摄影师！');
      }
    },

  },
};
</script>

<style scoped>
.reservation-container {
  background-color: #e998cb;
  max-width: 70vw;
  margin: 0 auto;
  padding: 20px;
}

.studio-info {
  border: solid 2px pink;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.studio-image img {
  width: 400px;
  height: 300px;
  object-fit: cover;
  border-radius: 5px;
  margin: 10px;
}

.studio-description {
  font-size: 20px;
  flex-grow: 1;
  margin-left: 20px;
  margin-right: 20px;
}

.package-card {
  margin-bottom: 20px;
  border-radius: 25px;
  position: relative;
}
.package-card .package-data{
  text-align: left;
  margin-left: 50px;
}
.package-card .package-data .package-data-head{
  color: #e998cb
}
.package-thumbnail{
  text-align: left;
}
.package-thumbnail img {
  margin-top: 40px;
  width: 50%;
  height:40%;
  object-fit: cover;
  border-radius: 5px;
}

.package-select-button {
  position: absolute;
  left: 10px;
  top: 10px;
}

.photographer-card {
  margin-bottom: 20px;
  border-radius: 25px;
}
.photographer {
  display: flex;
  margin-bottom: 20px;
}
.info {
  margin-left: 20px;
  text-align: left;
}
.photographer-info {
  margin-left: 20px;
  border-radius: 10px;
  background-color: #F5E3F6;
  width: 100vw;
  display: flex;
  align-items: center;
  padding: 0 10px;
  /* 添加这一行 */
}
.el-photographer-info{
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
.submit-button {
  margin-top: 20px;
}
</style>
