<template>
  <form @submit.prevent="onSalvar">
    <label for="tituloLivro">Título:</label>
    <input
      id="tituloLivro"
      v-model="livroLocal.titulo"
      placeholder="Título do livro"
      required
      autofocus
    />
    <!-- Adicione campos de autor/gênero se quiser -->
    <button type="submit">Salvar</button>
    <button type="button" @click="onCancelar">Cancelar</button>
  </form>
</template>

<script setup>
import { useLivroForm } from './LivroFormScript.js'

const props = defineProps({
  livro: {
    type: Object,
    required: true
  }
})
const emit = defineEmits(['salvar', 'cancelar'])

const { livroLocal, onSalvar, onCancelar } = useLivroForm(props, emit)
</script>
