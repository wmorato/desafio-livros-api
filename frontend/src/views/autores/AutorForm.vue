<template>
    <div class="bg-white rounded-lg shadow p-6 mb-6 max-w-md mx-auto">
      <h2 class="text-xl font-bold mb-4">
        {{ autorEdicao.id ? "Editar Autor" : "Novo Autor" }}
      </h2>
      <form @submit.prevent="salvarAutor" class="space-y-3">
        <input
          v-model="autorEdicao.nome"
          placeholder="Nome do Autor"
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
      autorEditar: Object,
    },
    data() {
      return {
        autorEdicao: {
          id: null,
          nome: '',
        },
      };
    },
    watch: {
      autorEditar: {
        immediate: true,
        handler(novo) {
          if (novo) {
            this.autorEdicao = {
              id: novo.id || null,
              nome: novo.nome || '',
            };
          } else {
            this.autorEdicao = {
              id: null,
              nome: '',
            };
          }
        },
      },
    },
    methods: {
      async salvarAutor() {
        if (this.autorEdicao.id) {
          await api.put(`/autores/${this.autorEdicao.id}`, {
            nome: this.autorEdicao.nome,
          });
        } else {
          await api.post('/autores', {
            nome: this.autorEdicao.nome,
          });
        }
        this.$emit('autor-salvo');
      },
    },
  };
  </script>
  