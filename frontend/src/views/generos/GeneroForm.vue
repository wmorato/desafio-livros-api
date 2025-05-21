<template>
  <!-- 
    Formulário de criação/edição de Gênero.
    Recebe o objeto 'genero' via prop (para edição ou novo).
    Emite eventos para o componente pai (salvar/cancelar).
  -->
  <form @submit.prevent="onSalvar">
    <label for="nomeGenero">Nome do gênero:</label>
    <input
      id="nomeGenero"
      v-model="generoLocal.nome"
      placeholder="Nome do gênero"
      required
      autofocus
    />
    <button type="submit">Salvar</button>
    <button type="button" @click="onCancelar">Cancelar</button>
  </form>
</template>

<script setup>
import {
  generoLocal,
  onSalvar,
  onCancelar
} from './GeneroFormScript.js'
</script>
