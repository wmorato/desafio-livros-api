<template>
  <div>
    <h2>Gerenciamento de Usuários</h2>
    <button @click="novoUsuario">Novo Usuário</button>

    <div v-if="usuarioStore.loading">Carregando...</div>
    <div v-if="usuarioStore.error" class="erro">{{ usuarioStore.error }}</div>

    <table v-if="!usuarioStore.loading && usuarioStore.usuarios.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Email</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="usuario in usuarioStore.usuarios" :key="usuario.id">
          <td>{{ usuario.id }}</td>
          <td>{{ usuario.nome }}</td>
          <td>{{ usuario.email }}</td>
          <td>
            <button @click="editarUsuario(usuario)">Editar</button>
            <button @click="excluirUsuario(usuario.id)">Excluir</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-else-if="!usuarioStore.loading">
      <em>Nenhum usuário cadastrado.</em>
    </div>

    <div v-if="usuarioStore.usuarioSelecionado">
      <h3>
        {{ usuarioStore.usuarioSelecionado.id ? 'Editar Usuário' : 'Novo Usuário' }}
      </h3>
      <form @submit.prevent="salvarUsuario">
        <input
          v-model="usuarioStore.usuarioSelecionado.nome"
          placeholder="Nome"
          required
        />
        <input
          v-model="usuarioStore.usuarioSelecionado.email"
          placeholder="Email"
          required
        />
        <input
          v-model="usuarioStore.usuarioSelecionado.senha"
          placeholder="Senha"
          type="password"
          required
        />
        <button type="submit">Salvar</button>
        <button type="button" @click="cancelarEdicao">Cancelar</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { useUsuarioStore } from '../../store/usuarioStore'
import { setupUsuarioCrud } from './UsuarioCrudScript.js'

const usuarioStore = useUsuarioStore()
const {
  novoUsuario,
  editarUsuario,
  salvarUsuario,
  cancelarEdicao,
  excluirUsuario
} = setupUsuarioCrud(usuarioStore)
</script>
