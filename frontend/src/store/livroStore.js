import { defineStore } from 'pinia'
import api from '../services/api' 

export const useLivroStore = defineStore('livro', {
  state: () => ({
    livros: [],
    loading: false,
    error: null,
    livroSelecionado: null
  }),

  actions: {
    async carregarLivros() {
      this.loading = true
      this.error = null
      try {
        const res = await api.get('/api/v1/livros')
        this.livros = res.data
      } catch (e) {
        this.error = e.response?.data?.message || e.message
      } finally {
        this.loading = false
      }
    },
    async adicionarLivro(livro) {
      this.loading = true
      this.error = null
      try {
        const res = await api.post('/api/v1/livros', livro)
        this.livros.push(res.data)
      } catch (e) {
        this.error = e.response?.data?.message || e.message
        throw e
      } finally {
        this.loading = false
      }
    },
    async atualizarLivro(livro) {
      this.loading = true
      this.error = null
      try {
        const res = await api.put(`/api/v1/livros/${livro.id}`, livro)
        const idx = this.livros.findIndex(l => l.id === livro.id)
        if (idx !== -1) this.livros[idx] = res.data
      } catch (e) {
        this.error = e.response?.data?.message || e.message
        throw e
      } finally {
        this.loading = false
      }
    },
    async removerLivro(id) {
      this.loading = true
      this.error = null
      try {
        await api.delete(`/api/v1/livros/${id}`)
        this.livros = this.livros.filter(l => l.id !== id)
      } catch (e) {
        this.error = e.response?.data?.message || e.message
        throw e
      } finally {
        this.loading = false
      }
    },
    selecionarLivro(livro) {
      this.livroSelecionado = livro
    },
    limparSelecao() {
      this.livroSelecionado = null
    }
  }
})
