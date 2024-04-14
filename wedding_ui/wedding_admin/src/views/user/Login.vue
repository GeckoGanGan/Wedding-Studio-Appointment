<template>
  <!-- 后台登陆页面 vue+element-plus -->
  <div class="login-container">
    <el-form :model="form" :rules="rules" ref="form" label-width="80px">
      <h3>朱大可婚纱影楼后台管理系统</h3>
      <el-form-item label="用户名" prop="username">
        <el-input type="text" v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleLogin">登陆</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
import { login } from "@/api/user";
export default {
  name: "login",
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 1, max: 20, message: "长度在 5 到 20 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 1, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" },
        ],
      },
    }
  },
  methods: {
    handleLogin() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          login(this.form).then((res) => {
            // 登录之前先移除token和用户信息
            localStorage.removeItem("token")
            localStorage.removeItem("userInfo")
            console.log(res)
            const data = res;
            localStorage.setItem('token', data.token);
            // 使用 Vuex 存储用户信息
            this.$store.commit('saveUserInfo', data.userInfo);
            localStorage.setItem('userInfo', JSON.stringify(data.userInfo));
            this.$message.success("欢迎" + data.userInfo.nickName + "登录！");
            this.$router.push('/home');
          }).catch((err) => {
            this.$message.error(err);
          });
        } else {
          console.log("error");
        }
      });
    },
  },
}
</script>

<style scoped>
.login-container {
  width: 400px;
  margin: 0 auto;
  padding-top: 100px;
}

h3 {
  text-align: center;
  margin-bottom: 20px;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-button {
  width: 100%;
}

.el-input {
  width: 100%;
}

.el-form-item__error {
  color: red;
  font-size: 12px;
  margin-top: 5px;
}
</style>
