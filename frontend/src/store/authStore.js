// src/store/authStore.js
import { defineStore } from "pinia";
import api from "../services/api"; // ajuste o caminho conforme seu projeto, pode ser './api' se estiver na mesma pasta

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
        // Salva o token
        localStorage.setItem("token", this.token);
        // Salva o usuário
        localStorage.setItem("user", JSON.stringify(this.user));
        api.defaults.headers.common["Authorization"] = `Bearer ${this.token}`;
        return true;
      } catch (e) {
        this.error = "Usuário ou senha inválidos";
        this.token = null;
        this.user = null;
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        return false;
      } finally {
        this.loading = false;
      }
    },
    logout() {
      this.user = null;
      this.token = null;
      localStorage.removeItem("token");
      delete api.defaults.headers.common["Authorization"];
    },
  },
});
