<template>
  <div class="livros-lista-container">
    <h2 class="titulo-pagina">Gerenciamento de Autores</h2>
    <div style="display: flex; gap: 10px; margin-bottom: 24px;">
      <button class="logout-btn" @click="novoAutor">Novo Autor</button>
      <router-link class="logout-btn" to="/livros/crud">Voltar</router-link>
    </div>

    <div v-if="autorStore.loading" class="nenhum-livro">Carregando...</div>
    <div v-if="autorStore.error" class="erro">{{ autorStore.error }}</div>

    <div class="tabela-container">
      <table class="tabela-livros" v-if="!autorStore.loading && autorStore.autores.length">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th style="text-align:center;">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="autor in autorStore.autores" :key="autor.id">
            <td>{{ autor.id }}</td>
            <td>{{ autor.nome }}</td>
            <td style="text-align:center;">
              <button class="logout-btn" @click="editarAutor(autor)">Editar</button>
              <button class="logout-btn" style="background:#f5f5f5;color:#444;" @click="excluirAutor(autor.id)">Excluir</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-else-if="!autorStore.loading">
        <em class="nenhum-livro">Nenhum autor cadastrado.</em>
      </div>
    </div>

    <div class="modal-box" style="margin: 2rem auto; max-width: 400px;" v-if="autorStore.autorSelecionado">
      <h3 class="titulo-pagina" style="font-size:1.1rem;">
        {{ autorStore.autorSelecionado.id ? 'Editar Autor' : 'Novo Autor' }}
      </h3>
      <form @submit.prevent="salvarAutor" style="display:flex;gap:10px;align-items:center;">
        <input
          v-model="autorStore.autorSelecionado.nome"
          class="input-filtro"
          placeholder="Nome do autor"
          required
          style="flex:2"
        />
        <button class="logout-btn" type="submit">Salvar</button>
        <button class="logout-btn" type="button" style="background:#f5f5f5;color:#444;" @click="cancelarEdicao">Cancelar</button>
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
