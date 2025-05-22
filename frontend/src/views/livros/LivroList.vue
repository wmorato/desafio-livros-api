<template>
  <div class="livros-lista-container">
    <div style="display: flex; justify-content: space-between; align-items: center;">
      <h2 class="titulo-pagina">
        <span style="font-size:2rem;vertical-align:middle;">📚</span>
        Lista de Livros
      </h2>
      <router-link class="logout-btn" to="/">Voltar</router-link>
    </div>
    <div style="display: flex; justify-content: flex-end; margin-bottom: 20px;">
      <input
        v-model="filtro"
        class="input-filtro"
        placeholder="Pesquisar por título..."
        style="min-width: 240px;"
      />
    </div>
    <div class="tabela-container">
      <table class="tabela-livros">
        <thead>
          <tr>
            <th>ID</th>
            <th>Título</th>
            <th>Autor</th>
            <th>Gênero</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="livro in livrosFiltrados" :key="livro.id">
            <td>{{ livro.id }}</td>
            <td>{{ livro.titulo }}</td>
            <td>{{ livro.autor }}</td>
            <td>{{ livro.genero }}</td>
          </tr>
          <tr v-if="livrosFiltrados.length === 0">
            <td colspan="4" class="nenhum-livro">Nenhum livro encontrado.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { useLivroList } from './LivroListScript.js'

const { livroStore, filtro, livrosFiltrados } = useLivroList()
</script>
