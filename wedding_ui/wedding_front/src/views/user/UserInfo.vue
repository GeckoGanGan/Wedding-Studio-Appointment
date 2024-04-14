<template>
  <div class="centered-container">
    <div class="container">
      <div v-show="isEdit" class="tcommonBox">
        <header>
          <h1>
            编辑个人资料
          </h1>
        </header>
        <section>
          <ul class="userInfoBox">
            <li class="avatarlist">
              <span class="leftTitle">头像</span>
              <el-upload class="avatar-uploader"
                         name="img" :action="uploadURL"
                         :show-file-list="false"
                         :on-success="handleAvatarSuccess"
                         :before-upload="beforeAvatarUpload"
              >
                <img v-if="userInfoObj.avatar" :src="userInfoObj.avatar ? userInfoObj.avatar : 'static/img/tou.jpg'"
                     :onerror="$store.state.errorImg" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
              <div slot="tip" class="el-upload__tip">点击上传头像，只能上传jpg/png文件，且不超过1mb</div>
            </li>
            <li class="username">
              <span class="leftTitle">昵称</span>
              <el-input v-model="userInfoObj.nickName" placeholder="昵称"></el-input>
              <i
                  class="el-icon-warn fa fa-asterisk"></i>
            </li>
            <li class="username">
              <span class="leftTitle">手机号</span>
              <el-input v-model="userInfoObj.phoneNumber" placeholder="昵称"></el-input>
              <i
                  class="el-icon-warn fa fa-asterisk"></i>
            </li>
            <li>
              <span class="leftTitle">电子邮件</span>
              <el-input v-model="userInfoObj.userEmail" placeholder="电子邮件"></el-input>
            </li>
            <li>
              <span class="leftTitle">性别</span>
              <el-radio-group v-model="userInfoObj.sex">
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </li>
          </ul>
          <div class="saveInfobtn">
            <el-button type="primary" @click="saveInfoFun">保存</el-button>
            <el-button type="primary" @click="isEdit = false">返回</el-button>
          </div>
        </section>
      </div>
      <div v-show="!isEdit" class="tcommonBox">
        <header>
          <h1>
            个人中心
            <span class="gotoEdit" @click="isEdit = true"><i class="el-icon-edit el-icon-wa fa fa-edit"></i>编辑</span>
          </h1>
        </header>
        <section>
          <ul class="userInfoBox">
            <li class="avatarlist">
              <span class="leftTitle">头像</span>
              <div class="avatar-uploader">
                <img :src="userInfoObj.avatar" class="avatar">
              </div>
            </li>
            <li class="username">
              <span class="leftTitle">昵称</span>
              <span>{{ userInfoObj.nickName ? userInfoObj.nickName : '无' }}</span>
            </li>
            <li>
              <span class="leftTitle">手机号</span>
              <span>{{ userInfoObj.phoneNumber ? userInfoObj.phoneNumber : '无' }}</span>
            </li>
            <li>
              <span class="leftTitle">电子邮件</span>
              <span>{{ userInfoObj.userEmail ? userInfoObj.userEmail : '无' }}</span>
            </li>
            <li>
              <span class="leftTitle">性别</span>
              <span>{{ userInfoObj.sex == 0 ? '男' : '女' }}</span>
            </li>
          </ul>
        </section>
      </div>
    </div>
  </div>
</template>

<script>
import {ElUpload, ElInput, ElRadio, ElButton} from 'element-plus';
import {userInfo} from '@/utils/UserUtils.js'
import {getUserInfoById, updateUserInfo} from "@/api/user";


export default {
  name: 'UserInfo',
  created() {
    // const id = JSON.parse(sessionStorage.getItem('userInfo')).id
    const id = this.getUserId();
    this.getUserInfo(id)
  },
  components: {
    ElUpload,
    ElInput,
    ElRadio,
    ElButton,
  },
  data() {
    return {
      isEdit: false,
      userInfoObj: {
        id: Number,
        avatar: '',
        nickName: '',
        userEmail: '',
        sex: 0
      },
      uploadURL: 'http://localhost:9000/upload' // 文件上传路径
    }
  },
  methods: {
    getUserId() {
      return JSON.parse(localStorage.getItem('userInfo')).id
    },
    getUserInfo(id) {
      getUserInfoById(id).then(res => {
        this.userInfoObj = res;
      }).catch(err => {
        console.log(err)
      })
    },
    handleAvatarSuccess(res, file) {
      // 处理上传成功逻辑
      if (res.code == 200) {
        this.userInfoObj.avatar = res.data
        this.userInfoObj.head_start = 1;
      } else {
        this.$message.error("图片上传失败！")
      }
    },
    beforeAvatarUpload(file) {
      // 处理上传前逻辑
      const isJPG = file.type == 'image/png' || file.type === 'image/jpg' || file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 1;
      // console.log(file);
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG/JPEG/PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 1MB!');
      }
      return isJPG && isLt2M;
    },
    saveInfoFun() {
      updateUserInfo(this.userInfoObj).then(res => {
        console.log('-------updateUserInfo------')
        console.log(res)
        this.$message.success(res)
        // 重新设置userInfo
        this.$store.commit('setUserInfo', this.userInfoObj);
        localStorage.setItem('userInfo', JSON.stringify(this.userInfoObj));
      }).catch(err => {
        console.log(err)
      })
      this.isEdit = false;
    }
  }
}
</script>

<style lang="css" scoped>
.centered-container {
  background: linear-gradient(to bottom, #fcecfc 0%, #dacbd5 98%, #dabcd1 99%, #e2cedc 100%, #eec1da 100%, #e998cb 102%);
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}

.container {
  position: relative;
  border: solid 3px;
  border-radius: 20px;
  max-width: 1200px;
  width: 100%; /* 让资料卡占满父容器的宽度 */
  margin: 0 auto;
  padding: 20px; /* 添加内边距以保持内容与边框的间距 */
  box-sizing: border-box; /* 确保内边距不会影响盒子总宽度 */
}

.userInfoBox .avatarlist {
  display: flex;
  align-items: center;
}

.avatar-uploader {
  margin-right: 20px;
}

.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
}

.userInfoBox .leftTitle {
  display: inline-block;
  width: 100px;
  padding: 10px 0;
}

.userInfoBox li {
  display: flex;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #ddd;
}

.userInfoBox li:last-child {
  border-bottom: 1px solid transparent;
}

.userInfoBox .fa-asterisk {
  color: #df2050;
}

.gotoEdit {
  margin-left: auto;
  font-size: 15px;
  cursor: pointer;
  color: #999;
}

.gotoEdit:hover {
  color: #333;
}
</style>
