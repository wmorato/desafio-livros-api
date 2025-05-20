<template>
    <table class="min-w-full table-auto border rounded shadow">
      <thead class="bg-gray-50">
        <tr>
          <th class="px-3 py-2 text-left">Título</th>
          <th class="px-3 py-2 text-left">Autor</th>
          <th class="px-3 py-2 text-left">Gênero</th>
          <th class="px-3 py-2"></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="livro in livros" :key="livro.id" class="border-b">
          <td class="px-3 py-2">{{ livro.titulo }}</td>
          <td class="px-3 py-2">{{ livro.autor }}</td>
          <td class="px-3 py-2">{{ livro.genero }}</td>
          <td class="px-3 py-2 flex gap-2">
            <button @click="$emit('editar', livro)" class="bg-yellow-400 hover:bg-yellow-500 text-black px-3 py-1 rounded">Editar</button>
            <button @click="$emit('remover', livro.id)" class="bg-red-400 hover:bg-red-500 text-white px-3 py-1 rounded">Remover</button>
          </td>
        </tr>
      </tbody>
    </table>
  </template>
  
  <script>
  export default {
    name: "LivroListCrud",
    props: {
      livros: Array
    }
  }
  </script>
  