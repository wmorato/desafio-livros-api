<template>
  <div class="p-8 max-w-2xl mx-auto rounded-xl bg-white shadow">
    <button @click="$router.push('/')" class="mb-6 px-4 py-2 rounded bg-gray-100 hover:bg-gray-200">← Voltar</button>
    <h2 class="text-2xl font-bold mb-6 text-center">Gerenciar Livros</h2>
    <div class="flex gap-4 justify-center mb-6">
      <button @click="novoLivro" class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">
        Novo Livro
      </button>
      <button @click="gerenciarAutor" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
        Gerenciar Autor
      </button>
      <button @click="gerenciarGenero" class="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700">
        Gerenciar Gênero
      </button>
    </div>

    <LivroListCrud :livros="livros" @editar="editarLivro" @remover="removerLivro" />
  </div>
</template>

<script>
import LivroListCrud from './LivroListCrud.vue';
import api from '@/services/api';

export default {
  name: 'LivroCrud',
  components: { LivroListCrud },
  data() {
    return {
      livros: []
    }
  },
  mounted() {
    this.buscarLivros();
  },
  methods: {
    async buscarLivros() {
      const response = await api.get('/livros');
      this.livros = response.data;
    },
    novoLivro() {
      this.$router.push('/livros/novo');
    },
    gerenciarAutor() {
      this.$router.push('/autores/crud');
    },
    gerenciarGenero() {
      this.$router.push('/generos/crud');
    },
    editarLivro(livro) {
      this.$router.push(`/livros/editar/${livro.id}`);
    },
    async removerLivro(livroId) {
      if (confirm('Tem certeza que deseja remover este livro?')) {
        await api.delete(`/livros/${livroId}`);
        this.buscarLivros();
      }
    }
  }
}
</script>