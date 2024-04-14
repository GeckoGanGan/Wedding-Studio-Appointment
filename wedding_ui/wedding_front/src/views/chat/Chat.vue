<template>
  <div class="box">
    <div class="common-layout">
      <el-container>
        <el-aside class="aside" width="200px">在线客服列表
          <div>
            <ul v-for="(user,index) in onlineCustomers" :key="index">
              <li class="customer" @click="chooseCustomer(user)">
                {{user.username}}
                <span v-if="unRead">
                 <el-icon style="color: red"><ChatDotRound /></el-icon>
                </span>
              </li>
            </ul>
          </div>
        </el-aside>
        <el-container style="border:solid paleturquoise; border-radius: 20px;">
          <el-header class="header"  v-if="currentCustomer"><div><el-avatar :src="currentCustomer.avatar"></el-avatar>{{currentCustomer.username}}在线为您服务~</div></el-header>
          <el-main class="main" v-if="currentCustomer">
            <div v-for="(message, index) in historyMessage" :key="index" class="chat-box">
            <!--如果消息发送者是自己-->
              <div v-if="checkMessageSender(message)" class="message-box">
                <div class="message-avatar">
                  <div>
                    <span>{{ message.createdTime }}</span>|
                    <span class="chat-name-self">{{message.senderName}}</span>
                    <el-avatar :src="message.senderAvatar"></el-avatar>
                  </div>
                  <div class="chat-content">
                    <div class="message-content">{{ message.messageText }}</div>
                  </div>
                </div>
              </div>
              <!--消息发送者是别人 -->
              <div v-else class="message-box-other">
                <div>
                  <el-avatar :src="this.currentCustomer.avatar"></el-avatar>
                  <span class="chat-name-other">{{message.senderName}}</span>|
                  <span>{{ message.createdTime }}</span>
                </div>
                <div class="message-avatar">
                  <div class="chat-content-other">
                    <div class="message-content-other">
                      {{ message.messageText }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </el-main>
          <el-footer>
            <div class="textarea">
              <el-input style="width: 90%;height: 100%; margin-top:10px" v-model="inputMessage" type="text"
                        placeholder="请输入消息，按下回车发送消息" @keyup.enter="sendMessage()"></el-input>
              <el-button style="margin-top:10px"type="primary" @click="sendMessage()">发送</el-button>
            </div>
          </el-footer>
        </el-container>
      </el-container>
    </div>
  </div>
</template>


<script>
import {userInfo} from "@/utils/UserUtils";
import {getOnlineUser} from "@/api/user";
import {getChatHistory} from "@/api/ChatMessage";

export default {
  name: "chat",
  data() {
    return {
      unRead:true,
      tips:1,
      currentCustomer:{},
      onlineCustomers: [
      ], // 添加在线客服列表属性
      socket: null,
      historyMessage:[],
      message:{
        senderId:Number,
        receiverId:Number,
        message:'',
        toUserName:'',
        fromUserName:'',
        createdTime:Date
      },
      historyParam:{
        senderId:Number,
        receiverId:Number,
        studioId:Number,
      },
      inputMessage: ""
    }
  },
  created() {
    this.createWebsocket()
    this.getMessage()
  },
  beforeRouteLeave(to, from, next) {
    // 在用户离开页面之前执行一些操作，例如向后端发送通知
    // 发送离开聊天室的请求或关闭 WebSocket 连接
    // 您可以根据需要执行相应的操作

    // 示例：关闭 WebSocket 连接
    this.closeWebSocket();
    // 继续路由导航
    next();
  },
  methods: {
    createWebsocket(){
      const studioId = this.$route.params.studioId;
      console.log('---------studioId---------')
      console.log(studioId);
      const username = userInfo().username;
      const websocketUrl = `${this.$store.state.webSocketURL}/${username}?studioId=${studioId}`;
      this.socket = new WebSocket(websocketUrl);
      // 并将socket赋值给socket
     // this.socket = socket;
      console.log(this.socket)
      // 统计在线客服列表
      this.socket.onopen = () => {
        this.getOnlineUsers();
       console.log("WebSocket连接已打开")
      }
    },
    getMessage() {
      this.socket.onmessage = (event) => {
        console.log('---------------onMessage----------------')
        const data = JSON.parse(event.data);
        console.log(data.messageText)
        console.log('---------------onMessage----------------')
        if (data.messageText ==undefined){
          return
        }
        // 将消息发送者得头像和和消息接收者得头像
        this.historyMessage.push(data)
        console.log(data)
      }
    },
    closeWebSocket() {
      try {
        if (this.socket) {
          this.socket.close();
          console.log('WebSocket连接已关闭');
        }
      }finally {
        // 重新获取在线客服列表
        this.getOnlineUsers()
      }
    },
    getOnlineUsers(){
      // 清空在线用户列表
      this.onlineCustomers =[]
      const studioId = this.$route.params.studioId;
      getOnlineUser(studioId).then(res =>{
        console.log('------------获取在线用户列表------')
        this.onlineCustomers = res;
      }).catch(err =>{
        console.log(err)
      })
    },
    sendMessage() {
      console.log('------进入发送消息方法---------')
      console.log(this.socket)
      console.log(this.currentCustomer)

      if (this.currentCustomer.username==''||this.currentCustomer.id==null){
        this.$message.warning('请选择一个客服哟~')
        return
      }
      if (this.inputMessage.trim() !== "") {
        console.log('---------------')
        console.log(this.inputMessage);
        const now = new Date();
        // 实践格式化：yyyy-MM-dd HH:mm:ss
        const time = `${now.getFullYear()}-${now.getMonth()}-${now.getDate()} ${now.getHours()}:${now.getMinutes()}:${now.getSeconds()}`;

        // 构建消息对象，包括消息文本、发送者 ID 等信息
        const message = {
          messageText: this.inputMessage,
          senderId: userInfo().id, // 当前用户的 ID
          senderName: userInfo().username, // 当前用户的姓名
          senderAvatar: userInfo().avatar, // 当前用户的头像，根据您的具体实现
          receiverId: this.currentCustomer.id, // 接收者的 ID，根据您的具体实现
          receiverName: this.currentCustomer.username, // 接收者的姓名，根据您的具体实现
          receiverAvatar: this.currentCustomer.avatar, // 接收者的头像，根据您的具体实现
          studioId: this.$route.params.studioId, // 工作室 ID，根据您的具体实现
          createdTime: time, // 获取当前时间，可以使用您的方法
        };

        // 发送消息
        this.socket.send(JSON.stringify(message))
        this.historyMessage.push(message)
        // 在实际应用中，此处应该发送消息给服务器或其他客户端
        // 然后接收来自服务器或其他客户端的消息并将其添加到 messages 数组中
        this.inputMessage = ""; // 清空输入框
      }
    },
    chooseCustomer(user){
      this.currentCustomer = user;
      this.historyParam.senderId = userInfo().id;
      this.historyParam.receiverId = this.currentCustomer.id;
      this.historyParam.studioId = this.$route.params.studioId;
      this.getHistory(this.historyParam)
    },
    getHistory(param){
      getChatHistory(param).then(res =>{
        console.log('------------获取聊天记录------')
        this.historyMessage = res;
        console.log(res)
      }).catch(err =>{
        console.log(err)
      })
    },
    // 判断该条消息发送者是否是自己
    checkMessageSender(message){
      const currentUserId = userInfo().id;
      console.log('------------判断该条消息发送者是否是自己------')
      // 判断消息的发送者是否是当前用户
      if (message.senderId === currentUserId) {
        // 如果发送者是当前用户，则返回 true，表示消息显示在右边
        return true;
      } else {
        // 如果发送者不是当前用户，则返回 false，表示消息显示在左边
        return false;
      }
    }
  }
}
</script>

<style scoped>
.box {
  background-color: paleturquoise;
  border:solid 2px;
  font-family: Arial, sans-serif;
  justify-content: center;
  align-items: center;
  text-align: center;
  display: flex;
  height: 100vh;
  width: 100%;
  border-radius: 10px;
}

.common-layout {
  height: 100vh;
  border-radius: 10px;
  display: flex;
  width: 70vw;
  /* 设置宽度为70vw */
}
/*消息数量*/
.tips {
  border-radius: 50%;
  background-color: red;
}
.aside {
  border-radius: 10px;
  width: 20vw;
  border: solid hotpink;
  height: 100%;
}
.customer{
  margin-top: 20px;
  width: 100%;
}
.header {
  font-size: 30px;
  border: solid blue;
  margin-top: 10px;
  margin-bottom: 10px;
  align-items: center;
  justify-content: center;
  text-align: left;
}

.main{
  border:solid;
  background-image: url("~@/assets/background/chat_bg_cat.jpg");
  background-size: cover;
  border-radius: 10px;
  background-color: rgb(255 228 225);
}
.message-box {
  text-align: right;
  width: 100%;
}

.chat-content {
  text-align: left;
  display: inline-block;
  height: auto;
  width: auto;
  border: #363d4c solid;
  background-color: pink;
  border-radius: 10px;
  justify-content: left;
  margin-right: 50px;
}

.message-content {
  text-align: left;
  word-break: break-word; /* 长文本换行 */
}

.chat-name-self {
  color: #df2050;
}
.chat-name-other {
  color: #42b983;
}


.message-box-other {
  text-align: left;
  width: 100%;
}

.chat-content-other {
  display: inline-block;
  height: auto;
  width: auto;
  border: #dabcd1 solid;
  background-color: pink;
  border-radius: 10px;
  justify-content: left;
  margin-left: 50px;
}

.message-content-other {
  text-align: left;
  word-break: break-word; /* 长文本换行 */
}

</style>
