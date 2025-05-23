import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/auth/Login.vue'
import Home from '../views/home/Home.vue'

import LivroCrud from '../views/livros/LivroCrud.vue'
import LivroForm from '../views/livros/LivroForm.vue';
import LivroList from '../views/livros/LivroList.vue'

import UsuarioCrud from '../views/usuarios/UsuarioCrud.vue'
import AutorCrud from '../views/autores/AutorCrud.vue'
import AutorForm from '../views/autores/AutorForm.vue'
import GeneroCrud from '../views/generos/GeneroCrud.vue'
import GeneroForm from '../views/generos/GeneroForm.vue'
import SugestaoModal from "@/components/SugestaoModal.vue"

import { useAuthStore } from '@/store/authStore'

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/livros/crud', component: LivroCrud },
  { path: '/livros/lista', component: LivroList },
  { path: '/livros/novo', component: LivroForm },
  { path: '/livros/editar/:id', component: LivroForm, props: true },
  { path: '/usuarios/crud', component: UsuarioCrud },
  { path: '/autores/crud', component: AutorCrud },
  { path: '/autores/novo', component: AutorForm },
  { path: '/autores/editar/:id', component: AutorForm, props: true },
  { path: '/generos/crud', component: GeneroCrud },
  { path: '/generos/novo', component: GeneroForm },
  { path: '/generos/editar/:id', component: GeneroForm, props: true },
  { path: '/sugestao', name: 'Sugestao', component: SugestaoModal }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  const user = authStore.user
  const isAuthenticated = !!authStore.token

  const adminOnly = ['/livros/crud', '/usuarios/crud']

  if (adminOnly.includes(to.path)) {
    if (!isAuthenticated || !user?.roles?.includes('ROLE_ADMIN')) {
      return next('/')
    }
  }

  if (to.path !== '/login' && !isAuthenticated) {
    return next('/login')
  }

  next()
})

export default router
