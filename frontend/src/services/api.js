import axios from 'axios';




const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
});

api.interceptors.request.use((config) => {
  // Logs de debug, só para testes:
  console.log('baseURL ', config.baseURL);
  console.log('VITE_API_BASE_URL ', import.meta.env.VITE_API_BASE_URL);

  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default api;
