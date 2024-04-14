import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store' // 引入 Vuex store

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ELIcons from '@element-plus/icons-vue'




let app = createApp(App)
for (let iconName in ELIcons) {
    // @ts-ignore
    app.component(iconName, ELIcons[iconName])
}

app.use(store) // 不需要再使用 app.use(Vuex)
    .use(router)
    .use(ElementPlus)
    .mount('#app')
