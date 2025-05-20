<template>
    <div class="container mx-auto p-8">
      <button @click="$router.back()" class="mb-6 bg-gray-200 px-4 py-2 rounded hover:bg-gray-300">
        ⬅ Voltar
      </button>
      <h1 class="text-2xl font-bold mb-4">Gerenciar Livros</h1>
      <LivroForm
        v-if="showForm"
        :livro-editar="livroEditar"
        @cancelar="cancelarEdicao"
        @livro-salvo="atualizarLista"
      />
      <LivroListCrud
        :livros="livros"
        @editar="editarLivro"
        @remover="removerLivro"
      />
      <button
        class="mt-4 bg-green-600 text-white px-4 py-2 rounded"
        v-if="!showForm"
        @click="novoLivro"
      >
        Novo Livro
      </button>
    </div>
  </template>
  
  <script>
  import LivroForm from './LivroForm.vue';
  import LivroListCrud from './LivroListCrud.vue'; // Novo componente!
  
  import api from '@/services/api';
  
  export default {
    components: { LivroForm, LivroListCrud },
    data() {
      return {
        livros: [],
        showForm: false,
        livroEditar: null,
      };
    },
    mounted() {
      this.atualizarLista();
    },
    methods: {
      async atualizarLista() {
        const { data } = await api.get('/livros');
        this.livros = data;
        this.showForm = false;
        this.livroEditar = null;
      },
      editarLivro(livro) {
        this.livroEditar = { ...livro };
        this.showForm = true;
      },
      novoLivro() {
        this.livroEditar = null;
        this.showForm = true;
      },
      cancelarEdicao() {
        this.showForm = false;
        this.livroEditar = null;
      },
      async removerLivro(id) {
        if (confirm('Confirma a remoção do livro?')) {
          await api.delete(`/livros/${id}`);
          this.atualizarLista();
        }
      },
    },
  };
  </script>
  