import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../store/authStore'

export function useLogin() {
  const authStore = useAuthStore()
  const router = useRouter()
  const username = ref('')
  const password = ref('')

  async function onLogin() {
    const success = await authStore.login(username.value, password.value)
    if (success) {
      router.push('/') // ou página de dashboard
    }
    // Se falhar, erro já estará em authStore.error
  }

  async function loginAsLeitor() {
    username.value = 'leitor'
    password.value = 'leitor'
    await onLogin()
  }

  return {
    username,
    password,
    authStore,
    onLogin,
    loginAsLeitor,
  }
}
