<template>
  <div class="bg-white rounded-lg shadow p-6 max-w-md mx-auto">
    <h2 class="text-xl font-bold mb-4 flex items-center gap-2">
      <span>📚</span> Lista de Livros
    </h2>
    <div
      v-for="livro in livros"
      :key="livro.id"
      class="mb-3 flex items-center gap-3 border-b pb-2"
    >
      <span class="font-bold">{{ livro.titulo }}</span>
      – <span>{{ livro.autor }}</span> (<span>{{ livro.genero }}</span>)
      <button
        class="bg-yellow-400 text-black rounded px-2 py-1"
        @click="$emit('editar', livro)"
      >
        Editar
      </button>
      <button
        class="bg-red-400 text-white rounded px-2 py-1"
        @click="$emit('remover', livro.id)"
      >
        Remover
      </button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    livros: Array,
  },
};
</script>
