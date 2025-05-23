// src/store/authStore.js
import { defineStore } from "pinia";
import api from "../services/api"; // ajuste o caminho conforme seu projeto

export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: null,
    token: null,
    error: null,
    loading: false,
  }),
  actions: {
    async login(username, password) {
      this.loading = true;
      this.error = null;
      try {
        const res = await api.post("/auth/login", { username, password });
        this.token = res.data.token;
        this.user = res.data.user;
        api.defaults.headers.common["Authorization"] = `Bearer ${this.token}`;
        return true;
      } catch (e) {
        this.error = "Usuário ou senha inválidos";
        this.token = null;
        this.user = null;
        return false;
      } finally {
        this.loading = false;
      }
    },
    logout() {
      this.user = null;
      this.token = null;
      delete api.defaults.headers.common["Authorization"];
    },
  },
  persist: true, // <-- Garante persistência via Pinia
});
