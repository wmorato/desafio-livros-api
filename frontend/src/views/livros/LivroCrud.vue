<template>
  <div>
    <!-- Barra de Navegação -->
    <div class="navbar">
      <button @click="tela = 'livros'">Livros</button>
      <button @click="tela = 'autores'">Autores</button>
      <button @click="tela = 'generos'">Gêneros</button>
      <button @click="voltar">Voltar</button>
    </div>

    <!-- Livros -->
    <div v-if="tela === 'livros'">
      <h2>Gerenciamento de Livros</h2>
      <form @submit.prevent="adicionarLivro">
        <input v-model="novoLivro.titulo" placeholder="Título do livro" required />
        <select v-model="novoLivro.autorId" required>
          <option disabled value="">Selecione o Autor</option>
          <option v-for="autor in autores" :key="autor.id" :value="autor.id">
            {{ autor.nome }}
          </option>
        </select>
        <select v-model="novoLivro.generoId" required>
          <option disabled value="">Selecione o Gênero</option>
          <option v-for="genero in generos" :key="genero.id" :value="genero.id">
            {{ genero.nome }}
          </option>
        </select>
        <button type="submit">Incluir</button>
        <button type="button" @click="limparLivro">Cancelar</button>
      </form>
      <table>
        <thead>
          <tr>
            <th>ID</th><th>Título</th><th>Autor</th><th>Gênero</th><th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="livro in livros" :key="livro.id">
            <td>{{ livro.id }}</td>
            <td v-if="editandoLivro?.id !== livro.id">{{ livro.titulo }}</td>
            <td v-else><input v-model="editandoLivro.titulo" /></td>
            <td v-if="editandoLivro?.id !== livro.id">{{ nomeAutor(livro.autorId) }}</td>
            <td v-else>
              <select v-model="editandoLivro.autorId">
                <option v-for="autor in autores" :key="autor.id" :value="autor.id">
                  {{ autor.nome }}
                </option>
              </select>
            </td>
            <td v-if="editandoLivro?.id !== livro.id">{{ nomeGenero(livro.generoId) }}</td>
            <td v-else>
              <select v-model="editandoLivro.generoId">
                <option v-for="genero in generos" :key="genero.id" :value="genero.id">
                  {{ genero.nome }}
                </option>
              </select>
            </td>
            <td>
              <template v-if="editandoLivro?.id === livro.id">
                <button @click="salvarLivro(livro.id)">Salvar</button>
                <button @click="cancelarEdicaoLivro">Cancelar</button>
              </template>
              <template v-else>
                <button @click="editarLivro(livro)">Editar</button>
                <button @click="excluirLivro(livro.id)">Excluir</button>
              </template>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Autores -->
    <div v-if="tela === 'autores'">
      <h2>Gerenciamento de Autores</h2>
      <form @submit.prevent="adicionarAutor">
        <input v-model="novoAutor.nome" placeholder="Nome do autor" required />
        <button type="submit">Incluir</button>
        <button type="button" @click="limparAutor">Cancelar</button>
      </form>
      <table>
        <thead>
          <tr><th>ID</th><th>Nome</th><th>Ações</th></tr>
        </thead>
        <tbody>
          <tr v-for="autor in autores" :key="autor.id">
            <td>{{ autor.id }}</td>
            <td v-if="editandoAutor?.id !== autor.id">{{ autor.nome }}</td>
            <td v-else><input v-model="editandoAutor.nome" /></td>
            <td>
              <template v-if="editandoAutor?.id === autor.id">
                <button @click="salvarAutor(autor.id)">Salvar</button>
                <button @click="cancelarEdicaoAutor">Cancelar</button>
              </template>
              <template v-else>
                <button @click="editarAutor(autor)">Editar</button>
                <button @click="excluirAutor(autor.id)">Excluir</button>
              </template>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Gêneros -->
    <div v-if="tela === 'generos'">
      <h2>Gerenciamento de Gêneros</h2>
      <form @submit.prevent="adicionarGenero">
        <input v-model="novoGenero.nome" placeholder="Nome do gênero" required />
        <button type="submit">Incluir</button>
        <button type="button" @click="limparGenero">Cancelar</button>
      </form>
      <table>
        <thead>
          <tr><th>ID</th><th>Nome</th><th>Ações</th></tr>
        </thead>
        <tbody>
          <tr v-for="genero in generos" :key="genero.id">
            <td>{{ genero.id }}</td>
            <td v-if="editandoGenero?.id !== genero.id">{{ genero.nome }}</td>
            <td v-else><input v-model="editandoGenero.nome" /></td>
            <td>
              <template v-if="editandoGenero?.id === genero.id">
                <button @click="salvarGenero(genero.id)">Salvar</button>
                <button @click="cancelarEdicaoGenero">Cancelar</button>
              </template>
              <template v-else>
                <button @click="editarGenero(genero)">Editar</button>
                <button @click="excluirGenero(genero.id)">Excluir</button>
              </template>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/services/api'

const router = useRouter()
const tela = ref('livros')

// --- Livros ---
const livros = ref([])
const novoLivro = reactive({ titulo: '', autorId: '', generoId: '' })
const editandoLivro = ref(null)

// --- Autores ---
const autores = ref([])
const novoAutor = reactive({ nome: '' })
const editandoAutor = ref(null)

// --- Gêneros ---
const generos = ref([])
const novoGenero = reactive({ nome: '' })
const editandoGenero = ref(null)

// ---- FUNÇÕES GERAIS ----
function voltar() {
  router.push('/')
}

// ---- LIVRO ----
async function carregarLivros() {
  livros.value = (await api.get('/api/v1/livros')).data
}
async function adicionarLivro() {
  await api.post('/api/v1/livros', { ...novoLivro })
  limparLivro()
  carregarLivros()
}
function editarLivro(livro) {
  editandoLivro.value = { ...livro }
}
async function salvarLivro(id) {
  await api.put(`/api/v1/livros/${id}`, { ...editandoLivro.value })
  editandoLivro.value = null
  carregarLivros()
}
function cancelarEdicaoLivro() {
  editandoLivro.value = null
}
async function excluirLivro(id) {
  await api.delete(`/api/v1/livros/${id}`)
  carregarLivros()
}
function limparLivro() {
  novoLivro.titulo = ''
  novoLivro.autorId = ''
  novoLivro.generoId = ''
}
function nomeAutor(id) {
  return autores.value.find(a => a.id === id)?.nome || ''
}
function nomeGenero(id) {
  return generos.value.find(g => g.id === id)?.nome || ''
}

// ---- AUTOR ----
async function carregarAutores() {
  autores.value = (await api.get('/api/v1/autores')).data
}
async function adicionarAutor() {
  await api.post('/api/v1/autores', { ...novoAutor })
  limparAutor()
  carregarAutores()
}
function editarAutor(autor) {
  editandoAutor.value = { ...autor }
}
async function salvarAutor(id) {
  await api.put(`/api/v1/autores/${id}`, { ...editandoAutor.value })
  editandoAutor.value = null
  carregarAutores()
}
function cancelarEdicaoAutor() {
  editandoAutor.value = null
}
async function excluirAutor(id) {
  await api.delete(`/api/v1/autores/${id}`)
  carregarAutores()
}
function limparAutor() {
  novoAutor.nome = ''
}

// ---- GÊNERO ----
async function carregarGeneros() {
  generos.value = (await api.get('/api/v1/generos')).data
}
async function adicionarGenero() {
  await api.post('/api/v1/generos', { ...novoGenero })
  limparGenero()
  carregarGeneros()
}
function editarGenero(genero) {
  editandoGenero.value = { ...genero }
}
async function salvarGenero(id) {
  await api.put(`/api/v1/generos/${id}`, { ...editandoGenero.value })
  editandoGenero.value = null
  carregarGeneros()
}
function cancelarEdicaoGenero() {
  editandoGenero.value = null
}
async function excluirGenero(id) {
  await api.delete(`/api/v1/generos/${id}`)
  carregarGeneros()
}
function limparGenero() {
  novoGenero.nome = ''
}

// ---- CARREGAMENTO INICIAL ----
onMounted(() => {
  carregarLivros()
  carregarAutores()
  carregarGeneros()
})
</script>
