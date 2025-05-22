<template>
  <div class="livros-lista-container">
    <h2 class="titulo-pagina">Gerenciamento de Gêneros</h2>
    <div style="display: flex; gap: 10px; margin-bottom: 24px;">
      <button class="logout-btn" @click="novoGenero">Novo Gênero</button>
      <router-link class="logout-btn" to="/livros/crud">Voltar</router-link>
    </div>

    <div v-if="generoStore.loading" class="nenhum-livro">Carregando...</div>
    <div v-if="generoStore.error" class="erro">{{ generoStore.error }}</div>

    <div class="tabela-container">
      <table class="tabela-livros" v-if="!generoStore.loading && generoStore.generos.length">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th style="text-align:center;">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="genero in generoStore.generos" :key="genero.id">
            <td>{{ genero.id }}</td>
            <td>{{ genero.nome }}</td>
            <td style="text-align:center;">
              <button class="logout-btn" @click="editarGenero(genero)">Editar</button>
              <button class="logout-btn" style="background:#f5f5f5;color:#444;" @click="excluirGenero(genero.id)">Excluir</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-else-if="!generoStore.loading">
        <em class="nenhum-livro">Nenhum gênero cadastrado.</em>
      </div>
    </div>

    <div class="modal-box" style="margin: 2rem auto; max-width: 400px;" v-if="generoStore.generoSelecionado">
      <h3 class="titulo-pagina" style="font-size:1.1rem;">
        {{ generoStore.generoSelecionado.id ? 'Editar Gênero' : 'Novo Gênero' }}
      </h3>
      <form @submit.prevent="salvarGenero" style="display:flex;gap:10px;align-items:center;">
        <input
          v-model="generoStore.generoSelecionado.nome"
          class="input-filtro"
          placeholder="Nome do gênero"
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
import { useGeneroStore } from '../../store/generoStore'
import { setupGeneroCrud } from './GeneroCrudScript.js'

const generoStore = useGeneroStore()
const {
  novoGenero,
  editarGenero,
  salvarGenero,
  cancelarEdicao,
  excluirGenero
} = setupGeneroCrud(generoStore)
</script>
