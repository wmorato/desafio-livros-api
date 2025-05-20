<template>
  <div class="bg-white rounded-lg shadow p-6 mb-6 max-w-md mx-auto">
    <h2 class="text-xl font-bold mb-4">
      {{ livroEdicao.id ? "Editar Livro" : "Novo Livro" }}
    </h2>
    <form @submit.prevent="salvarLivro" class="space-y-3">
      <input
        v-model="livroEdicao.titulo"
        placeholder="Título do Livro"
        class="border px-2 py-1 w-full"
        required
      />
      <select
        v-model="livroEdicao.autorId"
        class="border px-2 py-1 w-full"
        required
      >
        <option value="">Selecione o Autor</option>
        <option v-for="autor in autores" :key="autor.id" :value="autor.id">
          {{ autor.nome }}
        </option>
      </select>
      <select
        v-model="livroEdicao.generoId"
        class="border px-2 py-1 w-full"
        required
      >
        <option value="">Selecione o Gênero</option>
        <option v-for="genero in generos" :key="genero.id" :value="genero.id">
          {{ genero.nome }}
        </option>
      </select>
      <div class="flex gap-3">
        <button type="submit" class="bg-green-600 text-white px-4 py-1 rounded">
          Salvar
        </button>
        <button
          type="button"
          @click="$emit('cancelar')"
          class="bg-gray-300 px-4 py-1 rounded"
        >
          Cancelar
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import api from '@/services/api';

export default {
  props: {
    livroEditar: Object,
  },
  data() {
    return {
      livroEdicao: {
        id: null,
        titulo: '',
        autorId: '',
        generoId: '',
      },
      autores: [],
      generos: [],
    };
  },
  watch: {
    livroEditar: {
      immediate: true,
      handler(novo) {
        if (novo) {
          this.livroEdicao = {
            id: novo.id || null,
            titulo: novo.titulo || '',
            autorId: novo.autorId || '',
            generoId: novo.generoId || '',
          };
        } else {
          this.livroEdicao = {
            id: null,
            titulo: '',
            autorId: '',
            generoId: '',
          };
        }
      },
    },
  },
  mounted() {
    this.carregarAutores();
    this.carregarGeneros();
  },
  methods: {
    async carregarAutores() {
      const { data } = await api.get('/autores');
      this.autores = data;
    },
    async carregarGeneros() {
      const { data } = await api.get('/generos');
      this.generos = data;
    },
    async salvarLivro() {
      if (this.livroEdicao.id) {
        // Atualização
        await api.put(`/livros/${this.livroEdicao.id}`, {
          titulo: this.livroEdicao.titulo,
          autorId: this.livroEdicao.autorId,
          generoId: this.livroEdicao.generoId,
        });
      } else {
        // Criação
        await api.post('/livros', {
          titulo: this.livroEdicao.titulo,
          autorId: this.livroEdicao.autorId,
          generoId: this.livroEdicao.generoId,
        });
      }
      this.$emit('livro-salvo');
    },
  },
};
</script>
