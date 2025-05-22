<template>
  <div class="livros-lista-container">
    <!-- Barra de navegação -->
    <div style="display: flex; gap: 10px; margin-bottom: 24px;">
      <router-link class="logout-btn" to="/livros/crud">Livros</router-link>
      <router-link class="logout-btn" to="/autores/crud">Autores</router-link>
      <router-link class="logout-btn" to="/generos/crud">Gêneros</router-link>
      <button class="logout-btn" @click="voltar">Voltar</button>
    </div>

    <h2 class="titulo-pagina">Gerenciamento de Livros</h2>

    <!-- Filtros e inclusão -->
    <div style="display: flex; gap: 12px; align-items: center; margin-bottom: 16px; flex-wrap: wrap;">
      <input v-model="filtroTitulo" placeholder="Título do livro" class="input-filtro" style="flex: 2;" />
      <select v-model="filtroAutor" class="input-filtro" style="flex: 1;">
        <option value="">Selecione o Autor</option>
        <option v-for="autor in autores" :key="autor.id" :value="autor.nome">{{ autor.nome }}</option>
      </select>
      <select v-model="filtroGenero" class="input-filtro" style="flex: 1;">
        <option value="">Selecione o Gênero</option>
        <option v-for="genero in generos" :key="genero.id" :value="genero.nome">{{ genero.nome }}</option>
      </select>
      <button class="logout-btn" @click="abrirCadastroLivro">Incluir</button>
    </div>

    <!-- Tabela -->
    <div class="tabela-container">
      <table class="tabela-livros">
        <thead>
          <tr>
            <th>ID</th>
            <th>Título</th>
            <th>Autor</th>
            <th>Gênero</th>
            <th style="text-align:center;">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="livro in livrosFiltrados" :key="livro.id">
            <td>{{ livro.id }}</td>
            <td>{{ livro.titulo }}</td>
            <td>{{ livro.autor }}</td>
            <td>{{ livro.genero }}</td>
            <td style="text-align:center;">
              <button class="logout-btn" @click="editarLivro(livro)">Editar</button>
              <button class="logout-btn" style="background:#f5f5f5;color:#444;"
                @click="excluirLivro(livro.id)">Excluir</button>
            </td>
          </tr>
          <tr v-if="livrosFiltrados && livrosFiltrados.length === 0">
            <td colspan="5" class="nenhum-livro">Nenhum livro encontrado.</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal de cadastro/edição de livro -->
    <div v-if="showModalLivro" class="modal-overlay">
      <div class="modal-box">
        <h2 style="font-size:1.2rem;margin-bottom:1rem;">{{ editandoLivro ? 'Editar Livro' : 'Novo Livro' }}</h2>
        <input v-model="livroForm.titulo" placeholder="Título" class="input-filtro" style="margin-bottom:10px;" />
        <select v-model="livroForm.autorId" @change="logAutorId">
          <option value="">Selecione o Autor</option>
          <option v-for="autor in autores" :key="autor.id" :value="autor.id">{{ autor.nome }}</option>
        </select>
        <select v-model="livroForm.generoId" class="input-filtro" style="margin-bottom:10px;">
          <option value="">Selecione o Gênero</option>
          <option v-for="genero in generos" :key="genero.id" :value="genero.id">{{ genero.nome }}</option>
        </select>

        <div style="display:flex; justify-content: flex-end; gap:10px; margin-top:12px;">
          <button class="logout-btn" @click="salvarLivro">{{ editandoLivro ? 'Salvar' : 'Incluir' }}</button>
          <button class="logout-btn" style="background:#f5f5f5;color:#444;" @click="fecharModalLivro">Cancelar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/services/api';

const router = useRouter();

const livros = ref([]);
const autores = ref([]);
const generos = ref([]);

const filtroTitulo = ref('');
const filtroAutor = ref('');
const filtroGenero = ref('');

const showModalLivro = ref(false);
const editandoLivro = ref(null);
const livroForm = ref({
  id: null,
  titulo: '',
  autorId: '',
  generoId: ''
});

function logAutorId() {
  console.log('Selecionado autorId:', livroForm.value.autorId);
}



// Navegação
function voltar() {
  router.push('/');
}

// Modal
function abrirCadastroLivro() {
  editandoLivro.value = null;
  livroForm.value = { id: null, titulo: '', autor: '', genero: '' };
  showModalLivro.value = true;
}
function editarLivro(livro) {
  editandoLivro.value = livro;
  livroForm.value = { ...livro };
  showModalLivro.value = true;
}
function fecharModalLivro() {
  showModalLivro.value = false;
}

// Salvar/Incluir Livro
async function salvarLivro() {
  console.log('======= DEBUG FRONTEND =======');
  console.log('livroForm:', livroForm);
  console.log('autorId:', livroForm.value.autorId);
  console.log('generoId:', livroForm.value.generoId);

  const payload = {
    id: livroForm.value.id,
    titulo: livroForm.value.titulo,
    autorId: livroForm.value.autorId,
    generoId: livroForm.value.generoId,
  };
  console.log('Payload enviado para API:', payload);

  if (editandoLivro.value) {
    await api.put(`/api/v1/livros/${livroForm.value.id}`, payload);
  } else {
    await api.post('/api/v1/livros', payload);
  }
  showModalLivro.value = false;
  await carregarLivros();
}



// Excluir
async function excluirLivro(id) {
  if (confirm("Tem certeza que deseja excluir?")) {
    await api.delete(`/api/v1/livros/${id}`);
    await carregarLivros();
  }
}

// Carregamento e utilitários
async function carregarLivros() {
  const res = await api.get('/api/v1/livros');
  livros.value = Array.isArray(res.data) ? res.data : [];
}
async function carregarAutores() {
  const res = await api.get('/api/v1/autores');
  autores.value = Array.isArray(res.data) ? res.data : [];
}
async function carregarGeneros() {
  const res = await api.get('/api/v1/generos');
  generos.value = Array.isArray(res.data) ? res.data : [];
}

onMounted(() => {
  carregarLivros();
  carregarAutores();
  carregarGeneros();
});

// Filtro seguro (usando nome, já que o backend retorna o nome em string)
const livrosFiltrados = computed(() => {
  return livros.value.filter(livro => {
    const titulo = filtroTitulo.value.trim().toLowerCase();
    const filtroAutorNome = filtroAutor.value.trim().toLowerCase();
    const filtroGeneroNome = filtroGenero.value.trim().toLowerCase();

    const autorLivro = (livro.autor || '').trim().toLowerCase();
    const generoLivro = (livro.genero || '').trim().toLowerCase();

    return (
      (titulo === "" || livro.titulo.toLowerCase().includes(titulo)) &&
      (filtroAutorNome === "" || autorLivro === filtroAutorNome) &&
      (filtroGeneroNome === "" || generoLivro === filtroGeneroNome)
    );
  });
});
</script>
