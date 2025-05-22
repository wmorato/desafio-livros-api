<template>
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
import { useGeneroForm } from './GeneroFormScript.js'

// Pega as funções mágicas do Vue
const props = defineProps({
  genero: {
    type: Object,
    required: true
  }
})
const emit = defineEmits(['salvar', 'cancelar'])

const {
  generoLocal,
  onSalvar,
  onCancelar
} = useGeneroForm(props, emit)
</script>
