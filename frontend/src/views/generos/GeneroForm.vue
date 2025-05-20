<template>
    <div class="bg-white rounded-lg shadow p-6 mb-6 max-w-md mx-auto">
      <h2 class="text-xl font-bold mb-4">
        {{ generoEdicao.id ? "Editar genero" : "Novo genero" }}
      </h2>
      <form @submit.prevent="salvargenero" class="space-y-3">
        <input
          v-model="generoEdicao.nome"
          placeholder="Nome do genero"
          class="border px-2 py-1 w-full"
          required
        />
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
      generoEditar: Object,
    },
    data() {
      return {
        generoEdicao: {
          id: null,
          nome: '',
        },
      };
    },
    watch: {
      generoEditar: {
        immediate: true,
        handler(novo) {
          if (novo) {
            this.generoEdicao = {
              id: novo.id || null,
              nome: novo.nome || '',
            };
          } else {
            this.generoEdicao = {
              id: null,
              nome: '',
            };
          }
        },
      },
    },
    methods: {
      async salvargenero() {
        if (this.generoEdicao.id) {
          await api.put(`/generoes/${this.generoEdicao.id}`, {
            nome: this.generoEdicao.nome,
          });
        } else {
          await api.post('/generoes', {
            nome: this.generoEdicao.nome,
          });
        }
        this.$emit('genero-salvo');
      },
    },
  };
  </script>
  