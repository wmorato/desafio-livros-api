<template>
  <div>
    <h1>📚 Lista de Livros</h1>
    <ul v-if="livros.length">
      <li v-for="livro in livros" :key="livro.id">
        <strong>{{ livro.titulo }}</strong> - {{ livro.autor }} ({{ livro.genero }})
      </li>
    </ul>
    <p v-else>Carregando livros...</p>
  </div>
</template>

<script>
import api from '../services/api';

export default {
  name: 'LivroList',
  data() {
    return {
      livros: []
    };
  },
  async mounted() {
    const response = await api.get('/livros');
    this.livros = response.data;
  }
};
</script>
