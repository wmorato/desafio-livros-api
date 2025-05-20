<template>
  <div class="p-8 max-w-xl mx-auto rounded-xl bg-white shadow">
    <button @click="$router.push('/livros/crud')" class="mb-6 px-4 py-2 rounded bg-gray-100 hover:bg-gray-200">← Voltar</button>
    <h2 class="text-2xl font-bold mb-6 text-center">{{ isEdicao ? "Editar Livro" : "Novo Livro" }}</h2>
    <form @submit.prevent="salvarLivro" class="space-y-4">
      <input v-model="livro.titulo" placeholder="Título" class="w-full px-4 py-2 border rounded" required />

      <select v-model="livro.autorId" class="w-full px-4 py-2 border rounded" required>
        <option value="">Selecione o autor</option>
        <option v-for="autor in autores" :value="autor.id" :key="autor.id">{{ autor.nome }}</option>
      </select>

      <select v-model="livro.generoId" class="w-full px-4 py-2 border rounded" required>
        <option value="">Selecione o gênero</option>
        <option v-for="genero in generos" :value="genero.id" :key="genero.id">{{ genero.nome }}</option>
      </select>

      <button type="submit" class="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700">
        {{ isEdicao ? "Salvar Alterações" : "Cadastrar" }}
      </button>
    </form>
  </div>
</template>

<script>
import api from '@/services/api';
export default {
  name: "LivroForm",
  props: ["id"],
  data() {
    return {
      livro: {
        titulo: "",
        autorId: "",
        generoId: ""
      },
      isEdicao: false,
      autores: [],
      generos: []
    }
  },
  async mounted() {
    // Buscar autores e gêneros para os selects
    const [autoresResp, generosResp] = await Promise.all([
      api.get('/autores'),
      api.get('/generos')
    ]);
    this.autores = autoresResp.data;
    this.generos = generosResp.data;

    // Se edição, buscar dados do livro
    if (this.$route.params.id) {
      const response = await api.get(`/livros/${this.$route.params.id}`);
      this.livro = {
        titulo: response.data.titulo,
        autorId: response.data.autorId,
        generoId: response.data.generoId
      };
      this.isEdicao = true;
    }
  },
  methods: {
    async salvarLivro() {
      if (!this.livro.autorId || !this.livro.generoId) {
        alert('Selecione o autor e o gênero!');
        return;
      }
      if (this.isEdicao) {
        await api.put(`/livros/${this.$route.params.id}`, this.livro);
      } else {
        await api.post("/livros", this.livro);
      }
      this.$router.push('/livros/crud');
    }
  }
}
</script>
