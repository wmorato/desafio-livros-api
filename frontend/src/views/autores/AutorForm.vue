<template>
  <div class="p-8 max-w-xl mx-auto rounded-xl bg-white shadow">
    <button @click="voltar" class="mb-6 px-4 py-2 rounded bg-gray-100 hover:bg-gray-200">← Voltar</button>
    <h2 class="text-2xl font-bold mb-6 text-center">{{ isEdicao ? "Editar Autor" : "Novo Autor" }}</h2>
    <form @submit.prevent="salvarAutor" class="space-y-4">
      <input v-model="autor.nome" placeholder="Nome do autor" class="w-full px-4 py-2 border rounded" required />
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
  name: "AutorForm",
  props: ["id"],
  data() {
    return {
      autor: {
        nome: ""
      },
      isEdicao: false
    }
  },
  async mounted() {
    if (this.$route.params.id) {
      // Editando: buscar autor pelo id
      const response = await api.get(`/autores/${this.$route.params.id}`);
      this.autor = {
        nome: response.data.nome
      };
      this.isEdicao = true;
    }
  },
  methods: {
    async salvarAutor() {
      if (this.isEdicao) {
        await api.put(`/autores/${this.$route.params.id}`, this.autor);
      } else {
        await api.post("/autores", this.autor);
      }
      this.voltar();
    },
    voltar() {
      this.$router.push('/autores/crud');
    }
  }
}
</script>
