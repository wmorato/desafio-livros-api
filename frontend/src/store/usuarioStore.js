import { defineStore } from 'pinia'
import api from '../services/api' 

export const useUsuarioStore = defineStore('usuario', {
  state: () => ({
    usuarios: [],
    loading: false,
    error: null,
    usuarioSelecionado: null
  }),

  actions: {
    async carregarUsuarios() {
      this.loading = true
      this.error = null
      try {
        const res = await api.get('/api/v1/usuarios')
        this.usuarios = res.data
      } catch (e) {
        this.error = e.response?.data?.message || e.message
      } finally {
        this.loading = false
      }
    },
    async adicionarUsuario(usuario) {
      this.loading = true
      this.error = null
      try {
        const res = await api.post('/api/v1/usuarios', usuario)
        this.usuarios.push(res.data)
      } catch (e) {
        this.error = e.response?.data?.message || e.message
        throw e
      } finally {
        this.loading = false
      }
    },
    async atualizarUsuario(usuario) {
      this.loading = true
      this.error = null
      try {
        const res = await api.put(`/api/v1/usuarios/${usuario.id}`, usuario)
        const idx = this.usuarios.findIndex(u => u.id === usuario.id)
        if (idx !== -1) this.usuarios[idx] = res.data
      } catch (e) {
        this.error = e.response?.data?.message || e.message
        throw e
      } finally {
        this.loading = false
      }
    },
    async removerUsuario(id) {
      this.loading = true
      this.error = null
      try {
        await api.delete(`/api/v1/usuarios/${id}`)
        this.usuarios = this.usuarios.filter(u => u.id !== id)
      } catch (e) {
        this.error = e.response?.data?.message || e.message
        throw e
      } finally {
        this.loading = false
      }
    },
    selecionarUsuario(usuario) {
      this.usuarioSelecionado = usuario
    },
    limparSelecao() {
      this.usuarioSelecionado = null
    }
  }
})
