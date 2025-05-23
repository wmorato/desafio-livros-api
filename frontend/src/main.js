import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate' // <-- Adicione isto
import App from './App.vue'
import router from './router'
import './assets/css/style.css'
import { setAuthInterceptor } from './services/api'
setAuthInterceptor()

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate) // <-- Ative o plugin

const app = createApp(App)
app.use(pinia)
app.use(router)
app.mount('#app')
