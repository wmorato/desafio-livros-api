<template>
  <div class="home-header" style="margin-bottom: 2rem;">
    <div></div>
    <button class="logout-btn" @click="logout">Sair</button>
  </div>

  <div class="home-container">
    <h2 class="titulo-pagina">Bem-vindo ao Sistema de Gerenciamento de Livros</h2>
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
      <!-- Card Sugestão -->
      <div class="card" style="cursor:pointer;" @click="abrirSugestao">
        <span>💡 <b>Sugestão</b></span>
        <small>Clique para enviar sugestão</small>
      </div>
    </div>
    <!-- Aqui fica o modal -->
    <SugestaoModal v-if="modalSugestao" @close="modalSugestao = false" />
  </div>
</template>

<script>
import SugestaoModal from "@/components/SugestaoModal.vue";
import { useAuthStore } from '@/store/authStore'


export default {
  abrirSugestao() {
    this.modalSugestao = true;
  },
  logout() {
    // Ajuste conforme sua lógica de logout:
    const authStore = useAuthStore()
    authStore.logout()
    this.$router.push("/login"); // ou a rota de login do seu sistema
  },
  components: { SugestaoModal },
  data() {
    return {
      modalSugestao: false
    }
  },
  methods: {    
    abrirSugestao() {
      this.modalSugestao = true;
    },
    logout() {
      const authStore = useAuthStore()
      authStore.logout()
      this.$router.push('/login');
    }
  }
}
</script>
