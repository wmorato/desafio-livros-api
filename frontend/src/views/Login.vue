<template>
  <div class="max-w-sm mx-auto mt-20 p-6 bg-white shadow rounded">
    <h2 class="text-2xl font-bold mb-4 text-center">🔐 Login</h2>

    <form @submit.prevent="fazerLogin" class="space-y-4">
      <input
        v-model="username"
        type="text"
        placeholder="Usuário"
        class="w-full px-4 py-2 border rounded"
        required
      />
      <input
        v-model="password"
        type="password"
        placeholder="Senha"
        class="w-full px-4 py-2 border rounded"
        required
      />
      <button
        type="submit"
        class="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
      >
        Entrar
      </button>
    </form>

    <p v-if="erro" class="text-red-500 mt-4 text-sm text-center">{{ erro }}</p>
  </div>
</template>

<script>
import api from '../services/api';

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
        localStorage.setItem('token', token);
        this.$emit('login-sucesso');
      } catch (error) {
        this.erro = 'Usuário ou senha inválidos';
      }
    }
  }
};
</script>
