<template>
  <div class="home-container">
    <div class="home-header">
      <h1>Bem-vindo(a), {{ userNome }}</h1>
      <button class="logout-btn" @click="logout">Sair</button>
    </div>
    <div class="cards-grid">
      <router-link to="/livros/crud" class="card">
        <span>📚 <b>Gerenciar Livros</b></span>
        <small>Apenas Admin</small>
      </router-link>
      <router-link to="/livros/lista" class="card">
        <span>📖 <b>Consultar Livros</b></span>
      </router-link>
      <router-link to="/usuarios/crud" class="card">
        <span>👥 <b>Gerenciar Usuários</b></span>
        <small>Apenas Admin</small>
      </router-link>
      <router-link to="/sugestao" class="card">
        <span>💡 <b>Sugestão</b></span>
      </router-link>
    </div>
  </div>
</template>

<script>
import SugestaoModal from '../../components/SugestaoModal.vue';
export default {
  name: 'Home',
  components: { SugestaoModal },
  data() {
    return {
      user: null,
      modalAberto: false
    };
  },
  computed: {
    isAdmin() {
      return this.user?.roles?.includes('ROLE_ADMIN');
    }
  },
  created() {
    const userData = localStorage.getItem('user');
    this.user = userData ? JSON.parse(userData) : { username: 'Visitante', roles: [] };
  },
  methods: {
    irPara(path) {
      this.$router.push(path);
    },
    abrirSugestao() {
      this.modalAberto = true;
    },
    logout() {
      localStorage.clear();
      this.$router.push('/login');
    }
  }
};
</script>
