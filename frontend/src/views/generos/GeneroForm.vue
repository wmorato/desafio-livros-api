<template>
  <div class="p-8 max-w-xl mx-auto rounded-xl bg-white shadow">
    <button @click="voltar" class="mb-6 px-4 py-2 rounded bg-gray-100 hover:bg-gray-200">← Voltar</button>
    <h2 class="text-2xl font-bold mb-6 text-center">{{ isEdicao ? "Editar Gênero" : "Novo Gênero" }}</h2>
    <form @submit.prevent="salvarGenero" class="space-y-4">
      <input v-model="genero.nome" placeholder="Nome do gênero" class="w-full px-4 py-2 border rounded" required />
      <div class="flex gap-4 justify-center">
        <button type="submit" class="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700">
          {{ isEdicao ? "Salvar Alterações" : "Cadastrar" }}
        </button>
        <button type="button" @click="voltar" class="bg-gray-400 text-white px-6 py-2 rounded hover:bg-gray-500">
          Cancelar
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import api from '@/services/api';
export default {
  name: "GeneroForm",
  props: ["id"],
  data() {
    return {
      genero: {
        nome: ""
      },
      isEdicao: false
    }
  },
  async mounted() {
    if (this.$route.params.id) {
      // Editando: buscar gênero pelo id
      const response = await api.get(`/generos/${this.$route.params.id}`);
      this.genero = {
        nome: response.data.nome
      };
      this.isEdicao = true;
    }
  },
  methods: {
    async salvarGenero() {
      if (this.isEdicao) {
        await api.put(`/generos/${this.$route.params.id}`, this.genero);
      } else {
        await api.post("/generos", this.genero);
      }
      this.voltar();
    },
    voltar() {
      this.$router.push('/generos/crud');
    }
  }
}
</script>
