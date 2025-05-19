<template>
  <div class="max-w-xl mx-auto mt-10 bg-white shadow-md rounded-lg p-6">
    <h2 class="text-2xl font-bold mb-4 flex items-center gap-2">
      📘 Cadastrar Livro
    </h2>

    <form @submit.prevent="cadastrarLivro" class="space-y-4">
      <input
        v-model="titulo"
        type="text"
        placeholder="Título do livro"
        required
        class="w-full px-4 py-2 border rounded-md"
      />

      <select
        v-model="autorId"
        required
        class="w-full px-4 py-2 border rounded-md"
      >
        <option disabled value="">Selecione o autor</option>
        <option v-for="autor in autores" :key="autor.id" :value="autor.id">
          {{ autor.nome }}
        </option>
      </select>

      <select
        v-model="generoId"
        required
        class="w-full px-4 py-2 border rounded-md"
      >
        <option disabled value="">Selecione o gênero</option>
        <option v-for="genero in generos" :key="genero.id" :value="genero.id">
          {{ genero.nome }}
        </option>
      </select>

      <button
        type="submit"
        class="bg-blue-600 text-white px-6 py-2 rounded-md hover:bg-blue-700"
      >
        Cadastrar
      </button>
    </form>
  </div>
</template>

<script>
import api from "../services/api";

export default {
  name: "LivroForm",
  data() {
    return {
      titulo: "",
      autorId: "",
      generoId: "",
      autores: [],
      generos: [],
    };
  },
  async mounted() {
    const [autoresRes, generosRes] = await Promise.all([
      api.get("/autores"),
      api.get("/generos"),
    ]);
    this.autores = autoresRes.data;
    this.generos = generosRes.data;
  },
  methods: {
    async cadastrarLivro() {
      await api.post("/livros", {
        titulo: this.titulo,
        autorId: this.autorId,
        generoId: this.generoId,
      });

      alert("Livro cadastrado com sucesso!");
      this.titulo = "";
      this.autorId = "";
      this.generoId = "";

      this.$emit("livro-cadastrado");
    },
  },
};
</script>
