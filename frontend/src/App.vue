<template>
  <div id="app">
    <Login v-if="!autenticado" @login-sucesso="aoLogar" />
    <div v-else>
      <div class="max-w-xl mx-auto mt-4 text-right">
        <button @click="logout" class="text-sm text-gray-500 hover:text-red-600 underline">
          Sair
        </button>
      </div>

      <LivroForm @livro-cadastrado="buscarLivros" />
      <LivroList :livros="livros" @livroRemovido="buscarLivros" />
    </div>

  </div>
</template>

<script>
import Login from './views/auth/Login.vue';
import LivroForm from './views/livros/LivroForm.vue';
import LivroList from './views/livros/LivroList.vue';
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
    },
    aoLogar() {
      this.autenticado = true;
      this.buscarLivros();
    },
    logout() {
      localStorage.removeItem('token');
      this.autenticado = false;
      this.livros = [];
    }
  }
  ,
  mounted() {
    if (this.autenticado) {
      this.buscarLivros();
    }
  }
};
</script>
