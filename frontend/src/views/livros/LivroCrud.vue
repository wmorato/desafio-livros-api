<template>
    <div class="p-6">
      <h2 class="text-2xl font-bold mb-4">📚 Gerenciar Livros</h2>
      <p class="text-sm text-gray-500 mb-4">Apenas para administradores</p>
      <button @click="$router.push('/')" class="text-sm underline text-blue-600">← Voltar</button>
    </div>
  </template>
  
  <script>
  export default {
    name: 'LivroCrud'
  }
  </script>
  