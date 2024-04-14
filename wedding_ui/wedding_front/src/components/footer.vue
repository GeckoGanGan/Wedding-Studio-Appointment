<template>
  <footer class="footer">
    <div class="container">
      <div class="footer-content">
        <div class="footer-section about">
          <h2>About Us</h2>
          <p>我们提供专业的摄影技术和服务，欢迎联系</p>
          <div class="contact">
            <span><i class="fas fa-phone"></i> 166-1926-0584</span>
            <span><i class="fas fa-envelope"></i> zhudake321@163.com</span>
          </div>
          <div class="socials">
            <a href="#"><i class="fab fa-facebook"></i></a>
            <a href="#"><i class="fab fa-instagram"></i></a>
            <a href="#"><i class="fab fa-twitter"></i></a>
            <a href="#"><i class="fab fa-youtube"></i></a>
          </div>
        </div>
        <div class="footer-section links">
          <h2>Quick Links</h2>
          <ul>
            <li><a href="#">Home</a></li>
            <li><a href="#">Services</a></li>
            <li><a href="#">Gallery</a></li>
            <li><a href="#">Contact</a></li>
          </ul>
        </div>
        <div class="footer-section contact-form">
          <h2>联系我们</h2>
          <form action="#">
            <input v-model="contactMessage.email" type="email" name="email" class="text-input contact-input" placeholder="邮件地址">
            <textarea v-model="contactMessage.content" name="message" class="text-input contact-input" placeholder="请输入内容"></textarea>
            <button type="button" class="btn btn-big contact-btn" @click="submitEmail">
              <i class="fas fa-envelope"></i>
              发送
            </button>
          </form>
        </div>
      </div>
    </div>
    <div class="footer-bottom">
      &copy; 2024 Wedding Photography Appointment. All rights reserved.
    </div>
  </footer>
</template>

<script>
import {contact} from "@/api/email";
export default {
  name: 'Footer',
  data(){
    return{
      contactMessage:{
        email:'',
        content:''
      }
    }
  },methods:{
    submitEmail() {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (this.contactMessage.email.trim() !== '' && emailPattern.test(this.contactMessage.email.trim())) {
        if (this.contactMessage.content.trim() !== '') {
          // 调用发送邮件的方法
          contact(this.contactMessage).then(res => {
            console.log('-----------------邮件发送------------')
            console.log(res);
            this.$message.success(res);
          }).catch(err => {
            console.log(err);
          });
        } else {
          this.$message.warning('请输入正确的内容~');
        }
      } else {
        this.$message.warning('请输入正确的邮箱地址~');
      }
    }
  }
}
</script>

<style scoped>
.footer {
  background-color: #F5E3F6;
  color: black;
  padding: 50px 0;
}

.footer-content {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.footer-section {
  flex: 1;
  margin-right: 20px;
}

.footer-section h2 {
  font-size: 1.5rem;
}

.contact {
  margin-top: 20px;
}

.contact span {
  display: block;
  margin-bottom: 10px;
}

.socials a {
  color: #fff;
  font-size: 1.3rem;
  margin-right: 10px;
}

.links ul {
  list-style: none;
  padding: 0;
}

.links li {
  margin-bottom: 10px;
}

.links a {
  font-family: wedding_M;
  color: black;
  text-decoration: none;
}

.contact-form input,
.contact-form textarea {
  width: calc(100% - 20px); /* 减去 padding 的宽度 */
  padding: 10px;
  margin-bottom: 10px;
  box-sizing: border-box; /* 让 padding 不会影响到元素的宽度 */
}

.contact-btn {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
}

.footer-bottom {
  text-align: center;
  margin-top: 20px;
}
</style>

