<template>
  <div>
    <h2>📘 Cadastrar Livro</h2>
    <form @submit.prevent="cadastrarLivro">
      <input v-model="titulo" placeholder="Título do livro" required />

      <select v-model="autorId" required>
        <option disabled value="">Selecione o autor</option>
        <option v-for="autor in autores" :key="autor.id" :value="autor.id">
          {{ autor.nome }}
        </option>
      </select>

      <select v-model="generoId" required>
        <option disabled value="">Selecione o gênero</option>
        <option v-for="genero in generos" :key="genero.id" :value="genero.id">
          {{ genero.nome }}
        </option>
      </select>

      <button type="submit">Cadastrar</button>
    </form>
  </div>
</template>

<script>
import api from '../services/api';

export default {
  name: 'LivroForm',
  data() {
    return {
      titulo: '',
      autorId: '',
      generoId: '',
      autores: [],
      generos: []
    };
  },
  async mounted() {
    const [autoresRes, generosRes] = await Promise.all([
      api.get('/autores'),
      api.get('/generos')
    ]);
    this.autores = autoresRes.data;
    this.generos = generosRes.data;
  },
  methods: {
    async cadastrarLivro() {
      await api.post('/livros', {
        titulo: this.titulo,
        autorId: this.autorId,
        generoId: this.generoId
      });

      alert('Livro cadastrado com sucesso!');
      // Limpa campos
      this.titulo = '';
      this.autorId = '';
      this.generoId = '';
    }
  }
};
</script>
