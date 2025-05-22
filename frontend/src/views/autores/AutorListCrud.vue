<template>
  <div>
    <table v-if="autores.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="autor in autores" :key="autor.id">
          <td>{{ autor.id }}</td>
          <td>{{ autor.nome }}</td>
          <td>
            <button @click="onEditar(autor)">Editar</button>
            <button @click="onExcluir(autor.id)">Excluir</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-else>
      <em>Nenhum autor cadastrado.</em>
    </div>
  </div>
</template>

<script setup>
import { useAutorListCrud } from './AutorListCrudScript.js'

const props = defineProps({
  autores: {
    type: Array,
    required: true
  }
})
const emit = defineEmits(['editar', 'excluir'])

const { onEditar, onExcluir } = useAutorListCrud(emit)
</script>
