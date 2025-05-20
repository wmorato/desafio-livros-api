<template>
  <div class="p-8 max-w-xl mx-auto rounded-xl bg-white shadow">
    <button @click="$router.push('/')" class="mb-6 px-4 py-2 rounded bg-gray-100 hover:bg-gray-200">← Voltar</button>
    <h2 class="text-2xl font-bold mb-6 text-center">Gerenciar Autores</h2>

    <table class="min-w-full table-auto border rounded shadow mb-6">
      <thead class="bg-gray-50">
        <tr>
          <th class="px-3 py-2 text-left">Nome</th>
          <th class="px-3 py-2 text-center">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="autor in autores" :key="autor.id" class="border-b">
          <td class="px-3 py-2">{{ autor.nome }}</td>
          <td class="px-3 py-2 flex gap-2 justify-center">
            <button @click="editarAutor(autor)" class="bg-yellow-400 hover:bg-yellow-500 text-black px-3 py-1 rounded">Editar</button>
            <button @click="removerAutor(autor.id)" class="bg-red-400 hover:bg-red-500 text-white px-3 py-1 rounded">Remover</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="flex justify-center">
      <button @click="novoAutor" class="bg-green-600 text-white px-6 py-2 rounded hover:bg-green-700">
        Novo Autor
      </button>
    </div>
  </div>
</template>

<script>
import api from '@/services/api';
export default {
  name: "AutorCrud",
  data() {
    return {
      autores: []
    }
  },
  mounted() {
    this.buscarAutores();
  },
  methods: {
    async buscarAutores() {
      const response = await api.get('/autores');
      this.autores = response.data;
    },
    novoAutor() {
      this.$router.push('/autores/novo');
    },
    editarAutor(autor) {
      this.$router.push(`/autores/editar/${autor.id}`);
    },
    async removerAutor(id) {
      if (confirm('Tem certeza que deseja remover este autor?')) {
        await api.delete(`/autores/${id}`);
        this.buscarAutores();
      }
    }
  }
}
</script>
