<template>
  <div class="p-6">
    <div class="flex justify-between items-center mb-6">
      <h2 class="text-xl font-bold">Bem-vindo(a), {{ user?.username }}</h2>
      <button @click="logout" class="text-sm underline text-red-600">Sair</button>
    </div>

    <div class="grid grid-cols-2 gap-6 max-w-md mx-auto">
      <!-- Apenas Admin -->
      <div
        v-if="isAdmin"
        class="border p-4 shadow-md hover:bg-gray-100 cursor-pointer"
        @click="irPara('/livros/crud')"
      >
        <h3 class="font-bold mb-1">📚 Gerenciar Livros</h3>
        <p class="text-xs text-gray-500">Apenas Admin</p>
      </div>

      <!-- Acesso comum -->
      <div
        class="border p-4 shadow-md hover:bg-gray-100 cursor-pointer"
        @click="irPara('/livros/lista')"
      >
        <h3 class="font-bold">📖 Consultar Livros</h3>
      </div>

      <!-- Apenas Admin -->
      <div
        v-if="isAdmin"
        class="border p-4 shadow-md hover:bg-gray-100 cursor-pointer"
        @click="irPara('/usuarios/crud')"
      >
        <h3 class="font-bold mb-1">👥 Gerenciar Usuários</h3>
        <p class="text-xs text-gray-500">Apenas Admin</p>
      </div>

      <!-- Modal -->
      <div
        class="border p-4 shadow-md hover:bg-gray-100 cursor-pointer"
        @click="abrirSugestao"
      >
        <h3 class="font-bold">💡 Sugestão</h3>
      </div>
    </div>

    <!-- Modal Sugestão -->
    <SugestaoModal v-if="modalAberto" @fechar="modalAberto = false" />
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
