<template>
  <div>
    <table v-if="livros.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Título</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="livro in livros" :key="livro.id">
          <td>{{ livro.id }}</td>
          <td>{{ livro.titulo }}</td>
          <td>
            <button @click="onEditar(livro)">Editar</button>
            <button @click="onExcluir(livro.id)">Excluir</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-else>
      <em>Nenhum livro cadastrado.</em>
    </div>
  </div>
</template>

<script setup>
import { useLivroListCrud } from './LivroListCrudScript.js'

const props = defineProps({
  livros: {
    type: Array,
    required: true
  }
})
const emit = defineEmits(['editar', 'excluir'])

const { onEditar, onExcluir } = useLivroListCrud(emit)
</script>
