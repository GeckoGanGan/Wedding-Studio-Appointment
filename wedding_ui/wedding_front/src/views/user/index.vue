<template xmlns="http://www.w3.org/1999/html">
  <div class="container">
    <div class="background"></div> <!-- 将背景图片添加到这个元素中 -->
    <div class="card">
      <div class="left-panel">
        <div class="website-info">
          <h2>Welcome to Our Website</h2>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua.</p>
        </div>
      </div>
      <div class="right-panel">
        <div class="form-wrapper" v-if="isLoginFormVisible">
          <h2>账号登录</h2>
          <el-form @submit.prevent="login">
            <input type="text" placeholder="Username" v-model="loginData.username">
            <input type="password" placeholder="Password" v-model="loginData.password">
            <button type="submit" style="margin-top: 10px">账号登录</button>
            <button type="submit" style="margin-top: 10px" @click="chaneLoginToPhone"><el-icon><Iphone /></el-icon>手机号登录</button>
            <p style="color: black">Don't have an account? <span @click="toggleForm">Register</span></p>
          </el-form>
        </div>
        <div class="form-wrapper" v-if="phoneLoginFormVisible">
          <h2>手机号登录</h2>
          <el-form @submit.prevent="phoneLogin">
            <input type="text" placeholder="手机号" v-model="phoneLoginData.phone">
            <div class="group">
              <input type="text" placeholder="图片验证码" v-model="phoneLoginData.imageCode">
              <img style="height: 100%;margin-top: 5px;margin-bottom: 5px" :src="this.imageCodeUrl" @click="refreshImageCode">
            </div>
            <div class="code">
              <input style="width: 55%;margin-top: 16px" type="text" placeholder="验证码" v-model="phoneLoginData.code">
              <button style="width: 45%; height: 100%;float: right" type="button" @click="sendSms">发送验证码</button>
            </div>
            <button type="submit" style="margin-top: 10px" @click="changeLoginToName">账号登录</button>
            <button type="submit" style="margin-top: 10px" @click="chaneLoginToPhone"><el-icon><Iphone /></el-icon>手机号登录</button>
          </el-form>
        </div>
        <div class="form-wrapper" v-if="registerFormVisible">
          <h2>Register</h2>
          <el-form @submit.prevent="register">
            <input type="text" placeholder="Username" v-model="registerData.username">
            <input type="email" placeholder="Email" v-model="registerData.userEmail">
            <input type="password" placeholder="Password" v-model="registerData.password">
            <input type="text" placeholder="phoneNumber" v-model="registerData.phoneNumber">
            <li>
              <el-radio-group v-model="registerData.sex" style="color: black;">
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </li>
            <button type="submit">Register</button>
            <p style="color: black">Already have an account? <span @click="changeLoginToName">Login</span></p>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {login, register} from "@/api/user";
import {sendCode, smsLogin} from "@/api/sms";
import {Notification} from 'element-plus';


export default {
  created() {
    this.timeStamp = new Date().getTime(); // 在组件创建时初始化时间戳
    this.imageCodeUrl = `http://localhost:9000/image/imageCode?key=${this.timeStamp}`; // 设置图片验证码URL
  },
  data() {
    return {
      loginData: {
        username: '',
        password: ''
      },
      registerData: {
        username: '',
        userEmail: '',
        password: '',
        phoneNumber: '',
        sex: ''
      },
      phoneLoginData: {
        // 传给后端的作为redis获取图片验证码
        key:'',
        // 手机号
        phone:'',
        // 图形验证码
        imageCode:'',
        // 短信验证码
        code:''
      },
      isLoginFormVisible: true,
      phoneLoginFormVisible: false,
      registerFormVisible: false,
      // 用时间作为key
      timeStamp:new Date().getTime(),
      imageCodeUrl:'http://localhost:9000/image/imageCode?key='+this.timeStamp,
      smsRes:{
        resCode:Number,
        resMsg:'',
      }
    };
  },
  methods: {
    chaneLoginToPhone() {
      this.phoneLoginFormVisible = true;
      this.isLoginFormVisible = false;
      this.registerFormVisible = false;
    },
    changeLoginToName(){
      this.isLoginFormVisible = true;
      this.phoneLoginFormVisible = false;
      this.registerFormVisible = false;

    },
    toggleForm() {
      this.isLoginFormVisible = false;
      this.phoneLoginFormVisible = false;
      this.registerFormVisible = true;
    },
    sendSms(){

      // 手机号不能为空或
      if (this.phoneLoginData.phone==''||this.phoneLoginData.phone==null){
        this.$message.warning('手机号不能为空！')
        return
      }
      // 手机号必须合法
      if (!(/^1[3456789]\d{9}$/).test(this.phoneLoginData.phone)){
        this.$message.warning('手机号格式不正确！')
        return
      }
      this.phoneLoginData.key = this.timeStamp;
      sendCode(this.phoneLoginData).then(res =>{
        console.log('---------发送验证码--------')
        console.log(res)
        if (res.resCode == 200){
          this.$message.success(res.resMsg)
        }
      }).catch(err =>{
        console.log(err)
      })
    },
    phoneLogin(){
      if(this.phoneLoginData.phone==''||this.phoneLoginData.code==null){
        this.$message.error("手机号和验证码不能为空！")
        return
      }
      smsLogin(this.phoneLoginData).then(res =>{
        console.log('---------手机号登录--------')
        console.log(res)
        // 登录之前先移除token和用户信息
        localStorage.removeItem("token")
        localStorage.removeItem("userInfo")
        console.log(res)
        const data = res;
        localStorage.setItem('token', data.token);
        // 使用 Vuex 存储用户信息
        this.$store.commit('saveUserInfo',data.userInfo);
        localStorage.setItem('userInfo', JSON.stringify(data.userInfo));
        this.$message.success("欢迎" + data.userInfo.nickName + "登录！");
        this.$router.push('/');

      })

    },
    // 刷新图片验证码
    refreshImageCode() {
      this.timeStamp = new Date().getTime();
      this.imageCodeUrl = 'http://localhost:9000/image/imageCode?key=' + new Date().getTime();
    },
    login() {
      if (this.loginData.username === '' || this.loginData.username == null) {
        this.$message.error("用户名不能为空！");
        return;
      }
      if (this.loginData.password === '' || this.loginData.password == null) {
        this.$message.error("密码不能为空！");
        return;
      }
      login(this.loginData).then(res => {
        // 登录之前先移除token和用户信息
        localStorage.removeItem("token")
        localStorage.removeItem("userInfo")
        console.log(res)
        const data = res;
        localStorage.setItem('token', data.token);
        // 使用 Vuex 存储用户信息
        this.$store.commit('saveUserInfo',data.userInfo);
        localStorage.setItem('userInfo', JSON.stringify(data.userInfo));
        this.$message.success("欢迎" + data.userInfo.nickName + "登录！");
        this.$router.push('/');
      }).catch(err => {
        console.error('登录失败:', err);
      })
    },
    register() {
      // 注册逻辑
      console.log('Register:', this.registerData);
      if (this.registerData.username === '' || this.registerData.username == null) {
        this.$message.error("用户名不能为空！");
        return;
      }
      if (this.registerData.password === '' || this.registerData.password == null) {
        this.$message.error("密码不能为空！");
        return;
      }
      register(this.registerData).then(res => {
        console.log("------------注册用户----------")
        console.log(res);
        this.$message.success(res);
      }).catch(err => {
        console.log(err)
      })
    },
  },
}
</script>

<style scoped>
.container {
  position: relative;
  height: 100vh;
}

.background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('~@/assets/background/weddingLogin_back.jpg');
  background-size: cover;
  background-position: center;
}

.card {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 80%;
  max-width: 600px;
  display: flex;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

.left-panel {
  flex: 1;
  background-color: rgba(0, 255, 255, 0.2);
  color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  /*  border-right: 1px solid #ccc;*/
}

.right-panel {
  background-color: rgba(0, 255, 255, 0.2);
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.website-info {
  text-align: center;
}

h2 {
  margin-bottom: 20px;
}

.form-wrapper {
  padding: 20px;
  text-align: center;
}
.form-wrapper .group input{
  margin-left: 0;
  height: 100%;
  width: 60%;
}
.form-wrapper .group img{
  width: 40%;
  height: 100%;
  background-color: white;
}
.form-wrapper .group{
  display: flex;
  justify-content: left;
  max-height: 40px;
}
input {
  width: calc(100% - 20px);
  /* 设置宽度为卡片宽度减去内边距的值 */
  margin-bottom: 15px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.imageCode input{
  display: flex;
  width: 50%;
  margin-left: 0;
}.imageCode img{
   display: flex;
   width: 50%;
   margin-right: 0;
 }
button {
  width: calc(100% - 20px);
  /* 设置宽度为卡片宽度减去内边距的值 */
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

p {
  margin-top: 20px;
}

span {
  color: #007bff;
  cursor: pointer;
}

span:hover {
  text-decoration: underline;
}
.code{
  display: flex;
  align-items: center;
}
</style>
