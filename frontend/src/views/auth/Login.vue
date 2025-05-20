<template>
  <div class="flex items-center justify-center h-screen bg-gray-50">
    <div class="bg-white p-8 rounded shadow-md w-96">
      <h2 class="text-2xl font-bold mb-6 text-center flex items-center justify-center gap-2">
        <span class="text-yellow-500">🔓</span> Login
      </h2>
      <input v-model="username" type="text" placeholder="Usuário"
        class="w-full mb-4 p-2 border border-gray-300 rounded" />
      <input v-model="password" type="password" placeholder="Senha"
        class="w-full mb-4 p-2 border border-gray-300 rounded" />
      <button @click="fazerLogin" class="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700">
        Entrar
      </button>

      <button @click="loginComoLeitor" class="mt-4 mx-auto block text-sm text-gray-600 underline">
        Entrar como Leitor
      </button>

      <p v-if="erro" class="text-red-500 mt-2 text-center">{{ erro }}</p>
    </div>
  </div>
</template>

<script>
import api from '@/services/api';
import { nextTick } from 'vue';

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      erro: ''
    };
  },
  methods: {
    async fazerLogin() {
      try {
        const response = await api.post('/auth/login', {
          username: this.username,
          password: this.password
        });

        const token = response.data.token;
        const user = response.data.user;

        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify(user));

        // Redireciona para a página Home
        //this.$router.push('/');
        window.location.href = '/';
      } catch (error) {
        this.erro = 'Usuário ou senha inválidos';
      }
    }
    ,
    async loginComoLeitor() {
      try {
        const response = await api.post('/auth/leitor');
        const token = response.data.token;
        const user = response.data.user; // garanta que o backend retorne esse campo

        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify(user));

        // Recomendo manter o padrão, forçando reload para o guard funcionar igual
        window.location.href = '/';

      } catch (error) {
        this.erro = 'Falha ao entrar como leitor.';
      }
    }

  }
};
</script>

<style scoped>
/* Adicione estilos extras aqui se desejar */
</style>
