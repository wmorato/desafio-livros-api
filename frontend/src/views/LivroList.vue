<template>
  <div class="max-w-xl mx-auto mt-10 bg-white shadow-md rounded-lg p-6">
    <h1 class="text-3xl font-bold mb-4 flex items-center gap-2">
      📚 Lista de Livros
    </h1>

    <ul v-if="livros.length" class="space-y-2">
      <li
        v-for="livro in livros"
        :key="livro.id"
        class="border-b pb-2 text-gray-800 flex justify-between items-center"
      >
        <div>
          <strong>{{ livro.titulo }}</strong> – {{ livro.autor }} ({{ livro.genero }})
        </div>
        <button
          @click="removerLivro(livro.id)"
          class="text-red-500 hover:text-red-700 text-sm"
        >
          Remover
        </button>
      </li>
    </ul>

    <p v-else class="text-gray-500">Nenhum livro encontrado.</p>
  </div>
</template>

<script>
import api from '../services/api';

export default {
  name: 'LivroList',
  props: {
    livros: Array
  },
  emits: ['livroRemovido'],
  methods: {
    async removerLivro(id) {
      if (confirm('Tem certeza que deseja remover este livro?')) {
        await api.delete(`/livros/${id}`);
        this.$emit('livroRemovido');
      }
    }
  }
};
</script>
