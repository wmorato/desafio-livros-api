import { fileURLToPath, URL } from 'node:url';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      // Proxy para APIs REST (livros, autores, generos)
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
        // NÃO precisa de rewrite, porque o backend já tem /api/v1
      },
      // Proxy para autenticação (login, leitor)
      '/auth': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
});
