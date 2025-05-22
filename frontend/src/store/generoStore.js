// src/store/generoStore.js
import { defineStore } from 'pinia'
import api from '../services/api' 

export const useGeneroStore = defineStore('genero', {
  state: () => ({
    generos: [],
    loading: false,
    error: null,
    generoSelecionado: null // para edição/visualização
  }),

  actions: {
    async carregarGeneros() {
      this.loading = true
      try {
        const resposta = await api.get('/api/v1/generos')
        this.generos = resposta.data
      } catch (e) {
        this.error = e
      } finally {
        this.loading = false
      }
    },

    async adicionarGenero(genero) {
      this.loading = true
      try {
        const resposta = await api.post('/api/v1/generos', genero)
        this.generos.push(resposta.data)
      } catch (e) {
        this.error = e
        throw e
      } finally {
        this.loading = false
      }
    },

    async atualizarGenero(genero) {
      this.loading = true
      try {
        const resposta = await api.put(`/api/v1/generos/${genero.id}`, genero)
        // Atualiza o gênero na lista
        const idx = this.generos.findIndex(g => g.id === genero.id)
        if (idx !== -1) this.generos[idx] = resposta.data
      } catch (e) {
        this.error = e
        throw e
      } finally {
        this.loading = false
      }
    },

    async removerGenero(id) {
      this.loading = true
      try {
        await api.delete(`/api/v1/generos/${id}`)
        this.generos = this.generos.filter(g => g.id !== id)
      } catch (e) {
        this.error = e
        throw e
      } finally {
        this.loading = false
      }
    },

    selecionarGenero(genero) {
      this.generoSelecionado = genero
    },

    limparSelecao() {
      this.generoSelecionado = null
    }
  }
})
