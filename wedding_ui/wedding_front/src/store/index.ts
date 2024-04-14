import { createStore } from 'vuex'

export default createStore({
  state: {
    baseURL: 'http://localhost:9000/',
    webSocketURL: 'ws://localhost:9000/wedding/chat',
    userInfo: {}
  },
  getters: {
  },
  mutations: {
    // 定义 mutation 来更新 userInfo
    saveUserInfo(state, newUserInfo) {
      state.userInfo = { ...state.userInfo, ...newUserInfo };
    },
    // 定义 mutation 来更新 userInfo
    updateUserInfo(state, newUserInfo) {
      state.userInfo = { ...state.userInfo, ...newUserInfo };
    }
  },
  actions: {
  },
  modules: {
  }
})
