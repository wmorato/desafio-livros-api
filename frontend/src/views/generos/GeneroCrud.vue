<template>
  <!-- 
    Componente de CRUD de Gêneros.
    Toda a lógica está no script externo (GeneroCrudScript.js).
    O CSS global já está aplicado pelo style.css.
  -->
  <div>
    <h2>Gerenciamento de Gêneros</h2>
    <button @click="novoGenero">Novo Gênero</button>

    <!-- Mensagens de carregamento e erro -->
    <div v-if="generoStore.loading">Carregando...</div>
    <div v-if="generoStore.error" class="erro">{{ generoStore.error }}</div>

    <!-- Tabela de gêneros -->
    <table v-if="!generoStore.loading && generoStore.generos.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="genero in generoStore.generos" :key="genero.id">
          <td>{{ genero.id }}</td>
          <td>{{ genero.nome }}</td>
          <td>
            <button @click="editarGenero(genero)">Editar</button>
            <button @click="excluirGenero(genero.id)">Excluir</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-else-if="!generoStore.loading">
      <em>Nenhum gênero cadastrado.</em>
    </div>

    <!-- Formulário de edição/adicionar -->
    <div v-if="generoStore.generoSelecionado">
      <h3>
        {{ generoStore.generoSelecionado.id ? 'Editar Gênero' : 'Novo Gênero' }}
      </h3>
      <form @submit.prevent="salvarGenero">
        <input
          v-model="generoStore.generoSelecionado.nome"
          placeholder="Nome do gênero"
          required
        />
        <button type="submit">Salvar</button>
        <button type="button" @click="cancelarEdicao">Cancelar</button>
      </form>
    </div>
  </div>
</template>

<!--
  Importação do script externo.
  O caminho pode ser relativo, conforme a estrutura do seu projeto.
-->
<script setup>
import {
  generoStore,
  novoGenero,
  editarGenero,
  salvarGenero,
  cancelarEdicao,
  excluirGenero
} from './GeneroCrudScript.js'
</script>

