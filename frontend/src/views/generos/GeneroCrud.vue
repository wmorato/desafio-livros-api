<template>
    <div class="container mx-auto p-8">
      <button @click="$router.back()" class="mb-6 bg-gray-200 px-4 py-2 rounded hover:bg-gray-300">
        ⬅ Voltar
      </button>
      <h1 class="text-2xl font-bold mb-4">Gerenciar generoes</h1>
      <generoForm
        v-if="showForm"
        :genero-editar="generoEditar"
        @cancelar="cancelarEdicao"
        @genero-salvo="atualizarLista"
      />
      <generoListCrud
        :generoes="generoes"
        @editar="editargenero"
        @remover="removergenero"
      />
      <button
        class="mt-4 bg-green-600 text-white px-4 py-2 rounded"
        v-if="!showForm"
        @click="novogenero"
      >
        Novo genero
      </button>
    </div>
  </template>
  
  <script>
  import generoForm from './generoForm.vue';
  import generoListCrud from './generoListCrud.vue';
  import api from '@/services/api';
  
  export default {
    components: { generoForm, generoListCrud },
    data() {
      return {
        generoes: [],
        showForm: false,
        generoEditar: null,
      };
    },
    mounted() {
      this.atualizarLista();
    },
    methods: {
      async atualizarLista() {
        const { data } = await api.get('/generoes');
        this.generoes = data;
        this.showForm = false;
        this.generoEditar = null;
      },
      editargenero(genero) {
        this.generoEditar = { ...genero };
        this.showForm = true;
      },
      novogenero() {
        this.generoEditar = null;
        this.showForm = true;
      },
      cancelarEdicao() {
        this.showForm = false;
        this.generoEditar = null;
      },
      async removergenero(id) {
        if (confirm('Confirma a remoção do genero?')) {
          await api.delete(`/generoes/${id}`);
          this.atualizarLista();
        }
      },
    },
  };
  </script>
  