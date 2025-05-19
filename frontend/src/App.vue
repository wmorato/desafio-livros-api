<template>
  <div id="app">
    <Login v-if="!autenticado" @login-sucesso="autenticado = true" />
    <div v-else>
      <LivroForm @livro-cadastrado="buscarLivros" />
      <LivroList :livros="livros" @livroRemovido="buscarLivros" />
    </div>
  </div>
</template>

<script>
import Login from './views/Login.vue';
import LivroForm from './views/LivroForm.vue';
import LivroList from './views/LivroList.vue';
import api from './services/api';

export default {
  name: 'App',
  components: {
    Login,
    LivroForm,
    LivroList
  },
  data() {
    return {
      livros: [],
      autenticado: !!localStorage.getItem('token')
    };
  },
  methods: {
    async buscarLivros() {
      const response = await api.get('/livros');
      this.livros = response.data;
    }
  },
  mounted() {
    if (this.autenticado) {
      this.buscarLivros();
    }
  }
};
</script>
