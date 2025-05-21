<template>
  <!--
    Lista de Gêneros para visualização e ações.
    Recebe lista de gêneros via prop.
    Emite eventos para editar/excluir para o componente pai.
  -->
  <div>
    <table v-if="generos.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="genero in generos" :key="genero.id">
          <td>{{ genero.id }}</td>
          <td>{{ genero.nome }}</td>
          <td>
            <button @click="onEditar(genero)">Editar</button>
            <button @click="onExcluir(genero.id)">Excluir</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-else>
      <em>Nenhum gênero cadastrado.</em>
    </div>
  </div>
</template>

<script setup src="./GeneroListCrudScript.js"></script>

<script setup>
import {
  onEditar,
  onExcluir
} from './GeneroListCrudScript.js'
</script>
