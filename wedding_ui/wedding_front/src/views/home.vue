<template>
  <div class="home">
    <div class="background"></div> <!-- 将背景图片添加到这个元素中 -->
    <div class="content">
      <h1 ref="title" class="typing">Welcome to Our Wedding Photography Appointment Website~~<span class="cursor"></span></h1>
      <p class="p_des">我们致力于提供优质的婚纱影楼摄影预约服务。在这里，您可以方便快捷地预约您心仪的摄影服务，留下美好的回忆。</p>
    </div>
  </div>
  <div class="footer">
    <Footer></Footer>
  </div>
</template>

<script>
import '../assets/styles/WeddingFont.css'
import Footer from '@/components/footer.vue'
export default {
  name: 'Home',
  components:{
    Footer
  },
  mounted() {
    this.animateTitle();
  },
  methods: {
    async animateTitle() {
      const title = this.$refs.title;
      const text = title.innerText;
      title.innerText = '';
      for (const char of text) {
        title.innerHTML += char === ' ' ? '&nbsp;' : char;
        await this.sleep(100); // 等待100毫秒
      }
      title.innerHTML += '<span class="cursor"></span>'; // 添加光标
    },
    sleep(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    }
  }
};

</script>

<style scoped>
.home {
  position: relative;
  height: 100vh; /* 设置高度为视口高度 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('~@/assets/background/kissing_bk.jpg'); /* 引用本地图片，路径以@/开头 */
  background-size: cover;
  background-position: center;
  filter: blur(1px); /* 添加模糊效果，数值越大模糊程度越高 */
  z-index: -1; /* 使背景图片位于内容之后 */
}

.content {
  text-align: center;
  color: #FFC0CB; /* 文字颜色为白色 */
  position: relative; /* 保证内容位于背景图片之上 */
  z-index: 1; /* 使内容位于背景图片之上 */
}

.content h1 {
  font-family: wedding;
  font-size: 8em;
  margin-bottom: 20px;
  display: inline-block;
  word-wrap: break-word; /* 单词过长时自动换行 */
}
.p_des{
  font-size: 2.5em;
  color: #FFC0CB;
}

.cursor {
  display: inline-block;
  width: 2px; /* 修改光标宽度为2px */
  height: 1.2em; /* 光标高度 */
  background-color: #fff; /* 光标颜色 */
  animation: blink 1s infinite; /* 光标闪烁动画 */
}


@keyframes blink {
  0% {
    opacity: 1;
  }
  50% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
</style>
