<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import SugestaoModal from '../../components/SugestaoModal.vue'

const router = useRouter()
const modalAberto = ref(false)
const user = JSON.parse(localStorage.getItem('user') || '{}');
const isAdmin = computed(() => user?.roles?.includes('ROLE_ADMIN'));



function navegar(path) {
  router.push(path)
}
</script>

<template>
  <div class="p-4">
    <h2 class="text-center text-xl font-bold mb-4">Bem Vindo ao Sistema</h2>

    <div class="grid grid-cols-2 gap-4 max-w-md mx-auto">
      <button v-if="isAdmin" @click="navegar('/livros/crud')" class="border p-4 shadow">Gerenciar Livros<br><small>Admin</small></button>
      <button @click="navegar('/livros/lista')" class="border p-4 shadow">Consultar Livros</button>
      <button v-if="isAdmin" @click="navegar('/usuarios/crud')" class="border p-4 shadow">Gerenciar Usuários<br><small>Admin</small></button>
      <button @click="modalAberto = true" class="border p-4 shadow">Sugestão</button>
    </div>

    <SugestaoModal v-if="modalAberto" @fechar="modalAberto = false" />
  </div>
</template>
