<template>
  <div class="min-h-screen flex flex-col items-center justify-center bg-gray-50 py-8">
    <button
      @click="$router.back()"
      class="mb-6 bg-gray-200 px-4 py-2 rounded hover:bg-gray-300"
    >
      ⬅ Voltar
    </button>
    <div class="bg-white rounded-lg shadow p-8 max-w-md w-full">
      <h2 class="text-xl font-bold mb-6 flex items-center gap-2">
        <span>📚</span> Lista de Livros
      </h2>
      <div v-if="carregando" class="text-center text-gray-500">Carregando...</div>
      <div v-else-if="livros.length === 0" class="text-center text-gray-400">Nenhum livro encontrado.</div>
      <div v-else>
        <div
          v-for="livro in livros"
          :key="livro.id"
          class="mb-4 pb-2 border-b last:border-b-0"
        >
          <div class="font-bold">{{ livro.titulo }}</div>
          <div>
            <span class="text-gray-700">Autor:</span>
            <span>{{ livro.autor }}</span>
          </div>
          <div>
            <span class="text-gray-700">Gênero:</span>
            <span>{{ livro.genero }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '@/services/api';

export default {
  data() {
    return {
      livros: [],
      carregando: true,
    };
  },
  mounted() {
    this.carregarLivros();
  },
  methods: {
    async carregarLivros() {
      try {
        const { data } = await api.get('/livros');
        this.livros = data;
      } catch (e) {
        this.livros = [];
      } finally {
        this.carregando = false;
      }
    },
  },
};
</script>
