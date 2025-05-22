<template>
  <div class="livros-lista-container">
    <h2 class="titulo-pagina">📚 Lista de Livros</h2>
    <div class="filtro-container">
      <input v-model="filtro" placeholder="Pesquisar por título..." class="input-filtro" />
    </div>

    <div class="tabela-container">
      <table v-if="livrosFiltrados.length" class="tabela-livros">
        <thead>
          <tr>
            <th>ID</th>
            <th>Título</th>
            <!-- Adicione mais colunas se quiser -->
          </tr>
        </thead>
        <tbody>
          <tr v-for="livro in livrosFiltrados" :key="livro.id">
            <td>{{ livro.id }}</td>
            <td>{{ livro.titulo }}</td>
          </tr>
        </tbody>
      </table>
      <div v-else class="nenhum-livro">
        <em>Nenhum livro encontrado.</em>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useLivroList } from './LivroListScript.js'
const { filtro, livrosFiltrados } = useLivroList()
</script>
