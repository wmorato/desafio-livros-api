import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/auth/Login.vue'
import Home from '../views/home/Home.vue'
import LivroCrud from '../views/livros/LivroCrud.vue'
import LivroList from '../views/livros/LivroList.vue'
import UsuarioCrud from '../views/usuarios/UsuarioCrud.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
 // { path: '/livros/crud', component: LivroCrud },
 // { path: '/livros/lista', component: LivroList },
  //{ path: '/usuarios/crud', component: UsuarioCrud }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const user = JSON.parse(localStorage.getItem('user'));
  const isAuthenticated = !!localStorage.getItem('token');

  // Rotas protegidas por role
  const adminOnly = ['/livros/crud', '/usuarios/crud'];

  if (adminOnly.includes(to.path)) {
    if (!isAuthenticated || !user?.roles.includes('ROLE_ADMIN')) {
      return next('/');
    }
  }

  if (to.path !== '/login' && !isAuthenticated) {
    return next('/login');
  }

  next();
});


export default router
