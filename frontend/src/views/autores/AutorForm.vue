<template>
  <form @submit.prevent="onSalvar">
    <label for="nomeAutor">Nome do autor:</label>
    <input
      id="nomeAutor"
      v-model="autorLocal.nome"
      placeholder="Nome do autor"
      required
      autofocus
    />
    <button type="submit">Salvar</button>
    <button type="button" @click="onCancelar">Cancelar</button>
  </form>
</template>

<script setup>
import { useAutorForm } from './AutorFormScript.js'

const props = defineProps({
  autor: {
    type: Object,
    required: true
  }
})
const emit = defineEmits(['salvar', 'cancelar'])

const { autorLocal, onSalvar, onCancelar } = useAutorForm(props, emit)
</script>
