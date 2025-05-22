<template>
  <div class="login-container">
    <h1>
      <span style="color: #F7B500;">🔒</span> Login
    </h1>
    <input v-model="username" placeholder="Usuário" />
    <input v-model="password" placeholder="Senha" type="password" />
    <button @click="onLogin" :disabled="authStore.loading">Entrar</button>
    <button @click="loginAsLeitor" :disabled="authStore.loading">Entrar como Leitor</button>
    <div v-if="authStore.error" class="erro">{{ authStore.error }}</div>
  </div>
</template>

<script setup>
import { useLogin } from './LoginScript.js'
const { username, password, authStore, onLogin, loginAsLeitor } = useLogin()
</script>
