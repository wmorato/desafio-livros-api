<template>
    <div class="container mx-auto p-8">
      <button @click="$router.back()" class="mb-6 bg-gray-200 px-4 py-2 rounded hover:bg-gray-300">
        ⬅ Voltar
      </button>
      <h1 class="text-2xl font-bold mb-4">Gerenciar Autores</h1>
      <AutorForm
        v-if="showForm"
        :autor-editar="autorEditar"
        @cancelar="cancelarEdicao"
        @autor-salvo="atualizarLista"
      />
      <AutorListCrud
        :autores="autores"
        @editar="editarAutor"
        @remover="removerAutor"
      />
      <button
        class="mt-4 bg-green-600 text-white px-4 py-2 rounded"
        v-if="!showForm"
        @click="novoAutor"
      >
        Novo Autor
      </button>
    </div>
  </template>
  
  <script>
  import AutorForm from './AutorForm.vue';
  import AutorListCrud from './AutorListCrud.vue';
  import api from '@/services/api';
  
  export default {
    components: { AutorForm, AutorListCrud },
    data() {
      return {
        autores: [],
        showForm: false,
        autorEditar: null,
      };
    },
    mounted() {
      this.atualizarLista();
    },
    methods: {
      async atualizarLista() {
        const { data } = await api.get('/autores');
        this.autores = data;
        this.showForm = false;
        this.autorEditar = null;
      },
      editarAutor(autor) {
        this.autorEditar = { ...autor };
        this.showForm = true;
      },
      novoAutor() {
        this.autorEditar = null;
        this.showForm = true;
      },
      cancelarEdicao() {
        this.showForm = false;
        this.autorEditar = null;
      },
      async removerAutor(id) {
        if (confirm('Confirma a remoção do autor?')) {
          await api.delete(`/autores/${id}`);
          this.atualizarLista();
        }
      },
    },
  };
  </script>
  