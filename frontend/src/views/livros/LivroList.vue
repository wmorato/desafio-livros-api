<template>
  <div class="bg-white rounded-lg shadow p-8 max-w-2xl w-full mx-auto">
    <button
      @click="$router.back()"
      class="mb-6 bg-gray-200 px-4 py-2 rounded hover:bg-gray-300"
    >
      ⬅ Voltar
    </button>
    <h2 class="text-xl font-bold mb-4 flex items-center gap-2">
      <span>📚</span> Lista de Livros
    </h2>
    <div class="mb-4 text-gray-700">
      <b>Total de livros:</b> {{ livrosFiltrados.length }}
    </div>
    <div class="mb-4 flex gap-2">
      <input
        v-model="filtro.titulo"
        @input="onPesquisar"
        minlength="3"
        class="border px-2 py-1 rounded"
        placeholder="Pesquisar por título"
      />
      <input
        v-model="filtro.autor"
        @input="onPesquisar"
        minlength="3"
        class="border px-2 py-1 rounded"
        placeholder="Pesquisar por autor"
      />
      <input
        v-model="filtro.genero"
        @input="onPesquisar"
        minlength="3"
        class="border px-2 py-1 rounded"
        placeholder="Pesquisar por gênero"
      />
    </div>
    <table class="w-full table-auto border">
      <thead>
        <tr class="bg-gray-100">
          <th class="px-2 py-1 text-left">Título</th>
          <th class="px-2 py-1 text-left">Autor</th>
          <th class="px-2 py-1 text-left">Gênero</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="livro in livrosFiltrados"
          :key="livro.id"
          class="hover:bg-gray-50"
        >
          <td class="px-2 py-1">{{ livro.titulo }}</td>
          <td class="px-2 py-1">{{ livro.autor }}</td>
          <td class="px-2 py-1">{{ livro.genero }}</td>
        </tr>
        <tr v-if="livrosFiltrados.length === 0">
          <td colspan="3" class="text-center text-gray-400 py-4">
            Nenhum livro encontrado.
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import api from "@/services/api";

export default {
  data() {
    return {
      livros: [],
      filtro: {
        titulo: "",
        autor: "",
        genero: "",
      },
      livrosFiltrados: [],
    };
  },
  mounted() {
    this.carregarLivros();
  },
  methods: {
    async carregarLivros() {
      const { data } = await api.get("/livros");
      this.livros = data;
      this.livrosFiltrados = data;
    },
    onPesquisar() {
      this.livrosFiltrados = this.livros.filter((livro) => {
        const matchTitulo =
          this.filtro.titulo.length < 3 ||
          livro.titulo.toLowerCase().includes(this.filtro.titulo.toLowerCase());
        const matchAutor =
          this.filtro.autor.length < 3 ||
          livro.autor.toLowerCase().includes(this.filtro.autor.toLowerCase());
        const matchGenero =
          this.filtro.genero.length < 3 ||
          livro.genero.toLowerCase().includes(this.filtro.genero.toLowerCase());
        return matchTitulo && matchAutor && matchGenero;
      });
    },
  },
};
</script>
