import { defineStore } from 'pinia'
import api from '../services/api' 

export const useAutorStore = defineStore('autor', {
  state: () => ({
    autores: [],
    loading: false,
    error: null,
    autorSelecionado: null
  }),

  actions: {
    async carregarAutores() {
      this.loading = true
      this.error = null
      try {
        const res = await api.get('/api/v1/autores')
        this.autores = res.data
      } catch (e) {
        this.error = e.response?.data?.message || e.message
      } finally {
        this.loading = false
      }
    },
    async adicionarAutor(autor) {
      this.loading = true
      this.error = null
      try {
        const res = await api.post('/api/v1/autores', autor)
        this.autores.push(res.data)
      } catch (e) {
        this.error = e.response?.data?.message || e.message
        throw e
      } finally {
        this.loading = false
      }
    },
    async atualizarAutor(autor) {
      this.loading = true
      this.error = null
      try {
        const res = await api.put(`/api/v1/autores/${autor.id}`, autor)
        const idx = this.autores.findIndex(a => a.id === autor.id)
        if (idx !== -1) this.autores[idx] = res.data
      } catch (e) {
        this.error = e.response?.data?.message || e.message
        throw e
      } finally {
        this.loading = false
      }
    },
    async removerAutor(id) {
      this.loading = true
      this.error = null
      try {
        await api.delete(`/api/v1/autores/${id}`)
        this.autores = this.autores.filter(a => a.id !== id)
      } catch (e) {
        this.error = e.response?.data?.message || e.message
        throw e
      } finally {
        this.loading = false
      }
    },
    selecionarAutor(autor) {
      this.autorSelecionado = autor
    },
    limparSelecao() {
      this.autorSelecionado = null
    }
  }
})
