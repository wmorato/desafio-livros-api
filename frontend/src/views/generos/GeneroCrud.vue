<template>
    <div class="p-8 max-w-xl mx-auto rounded-xl bg-white shadow">
      <button @click="$router.push('/')" class="mb-6 px-4 py-2 rounded bg-gray-100 hover:bg-gray-200">← Voltar</button>
      <h2 class="text-2xl font-bold mb-6 text-center">Gerenciar Gêneros</h2>
  
      <table class="min-w-full table-auto border rounded shadow mb-6">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-3 py-2 text-left">Nome</th>
            <th class="px-3 py-2 text-center">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="genero in generos" :key="genero.id" class="border-b">
            <td class="px-3 py-2">{{ genero.nome }}</td>
            <td class="px-3 py-2 flex gap-2 justify-center">
              <button @click="editarGenero(genero)" class="bg-yellow-400 hover:bg-yellow-500 text-black px-3 py-1 rounded">Editar</button>
              <button @click="removerGenero(genero.id)" class="bg-red-400 hover:bg-red-500 text-white px-3 py-1 rounded">Remover</button>
            </td>
          </tr>
        </tbody>
      </table>
  
      <div class="flex justify-center">
        <button @click="novoGenero" class="bg-green-600 text-white px-6 py-2 rounded hover:bg-green-700">
          Novo Gênero
        </button>
      </div>
    </div>
  </template>
  
  <script>
  import api from '@/services/api';
  export default {
    name: "GeneroCrud",
    data() {
      return {
        generos: []
      }
    },
    mounted() {
      this.buscarGeneros();
    },
    methods: {
      async buscarGeneros() {
        const response = await api.get('/generos');
        this.generos = response.data;
      },
      novoGenero() {
        this.$router.push('/generos/novo');
      },
      editarGenero(genero) {
        this.$router.push(`/generos/editar/${genero.id}`);
      },
      async removerGenero(id) {
        if (confirm('Tem certeza que deseja remover este gênero?')) {
          await api.delete(`/generos/${id}`);
          this.buscarGeneros();
        }
      }
    }
  }
  </script>
  