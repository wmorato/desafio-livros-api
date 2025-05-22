<template>
  <div>
    <div class="crud-nav">
      <button @click="$router.push('/livros/crud')">Livros</button>
      <button @click="$router.push('/autores/crud')" class="ativo">Autores</button>
      <button @click="$router.push('/generos/crud')">Gêneros</button>
      <button @click="$router.push('/')" class="btn-voltar">Voltar</button>
    </div>
    <h2>Gerenciamento de Autores</h2>
    <form @submit.prevent="salvar">
      <input v-model="autor.nome" placeholder="Nome do autor" required />
      <button type="submit">{{ autor.id ? 'Salvar' : 'Incluir' }}</button>
      <button type="button" @click="cancelar" v-if="autor.id">Cancelar</button>
    </form>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in autores" :key="item.id">
          <td>{{ item.id }}</td>
          <td>{{ item.nome }}</td>
          <td>
            <button @click="editar(item)">Editar</button>
            <button @click="excluir(item.id)">Excluir</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../../services/api'

const autores = ref([])
const autor = ref({ id: null, nome: '' })

async function carregar() {
  const res = await api.get('/api/v1/autores')
  autores.value = res.data
}
onMounted(carregar)

function editar(a) {
  autor.value = { ...a }
}
function cancelar() {
  autor.value = { id: null, nome: '' }
}
async function salvar() {
  if (autor.value.id) {
    await api.put(`/api/v1/autores/${autor.value.id}`, autor.value)
  } else {
    await api.post('/api/v1/autores', autor.value)
  }
  cancelar()
  carregar()
}
async function excluir(id) {
  await api.delete(`/api/v1/autores/${id}`)
  carregar()
}
</script>
