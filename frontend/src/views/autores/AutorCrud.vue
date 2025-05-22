<template>
  <div>
    <h2>Gerenciamento de Autores</h2>
    <button @click="novoAutor">Novo Autor</button>

    <div v-if="autorStore.loading">Carregando...</div>
    <div v-if="autorStore.error" class="erro">{{ autorStore.error }}</div>

    <table v-if="!autorStore.loading && autorStore.autores.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="autor in autorStore.autores" :key="autor.id">
          <td>{{ autor.id }}</td>
          <td>{{ autor.nome }}</td>
          <td>
            <button @click="editarAutor(autor)">Editar</button>
            <button @click="excluirAutor(autor.id)">Excluir</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-else-if="!autorStore.loading">
      <em>Nenhum autor cadastrado.</em>
    </div>

    <div v-if="autorStore.autorSelecionado">
      <h3>
        {{ autorStore.autorSelecionado.id ? 'Editar Autor' : 'Novo Autor' }}
      </h3>
      <form @submit.prevent="salvarAutor">
        <input
          v-model="autorStore.autorSelecionado.nome"
          placeholder="Nome do autor"
          required
        />
        <button type="submit">Salvar</button>
        <button type="button" @click="cancelarEdicao">Cancelar</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { useAutorStore } from '../../store/autorStore'
import { setupAutorCrud } from './AutorCrudScript.js'

const autorStore = useAutorStore()
const {
  novoAutor,
  editarAutor,
  salvarAutor,
  cancelarEdicao,
  excluirAutor
} = setupAutorCrud(autorStore)
</script>
