<template>
  <div style="min-height: 100vh; background: linear-gradient(120deg, #f1f3f8 60%, #3399ff 100%); display: flex; align-items: center; justify-content: center;">
    <div class="home-container" style="max-width:370px; min-width:310px; box-shadow:0 4px 32px #2222; border-radius: 22px; padding: 2.7rem 2rem 2.2rem 2rem;">
      <div style="display: flex; align-items: center; flex-direction: column; gap: 10px;">
        <div style="background:#3399ff; color:#fff; border-radius:50%; width:66px; height:66px; display:flex; align-items:center; justify-content:center; font-size:2.1rem; box-shadow:0 4px 18px #3399ff33;">
          <span>🏢</span>
        </div>
        <h1 style="font-weight:900; font-size:1.6rem; letter-spacing:-1.5px; color:#253447;margin:0;">CorpSys</h1>
        <div style="font-size:1.11rem; color:#314364; font-weight:600; margin-top:.5rem;">Acesso ao Painel Corporativo</div>
        <div style="font-size:1rem; color:#314364b8; margin-bottom:1.2rem;">Digite seu usuário institucional</div>
      </div>
      <form @submit.prevent="onLogin" style="display: flex; flex-direction: column; gap: 13px; margin-bottom:10px;">
        <input
          v-model="username"
          class="input-filtro"
          type="text"
          placeholder="Usuário"
          autocomplete="username"
          required
          autofocus
        />
        <input
          v-model="password"
          class="input-filtro"
          type="password"
          placeholder="Senha"
          autocomplete="current-password"
          required
        />
        <button class="logout-btn" type="submit" style="margin-top:6px;font-size:1.1rem;">Entrar</button>
        <button class="logout-btn" type="button" style="background:#eef7fd;color:#3399ff;" @click="loginAsLeitor">
          Entrar como Leitor (Demo)
        </button>
        <div v-if="authStore.error" class="erro" style="margin:0 0 2px 0; text-align:center;">{{ authStore.error }}</div>
      </form>
      <div style="text-align:center;color:#677690a6;font-size:.98rem; margin-top:2.1rem; border-top:1px solid #ecf2f7; padding-top:10px;">
        © 2024 CorpSys Solutions
      </div>
    </div>
  </div>
</template>

<script setup>
import { useLogin } from './LoginScript.js'
const { username, password, authStore, onLogin, loginAsLeitor } = useLogin()
</script>
