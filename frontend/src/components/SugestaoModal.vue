<template>
    <div class="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
      <div class="bg-white p-6 rounded shadow-md w-96">
        <h2 class="text-lg font-bold mb-4">Enviar Sugestão ou Crítica</h2>
  
        <div class="mb-4">
          <label class="block text-sm mb-1">Tipo:</label>
          <select v-model="tipo" class="w-full border px-2 py-1 rounded">
            <option value="sugestao">Sugestão</option>
            <option value="critica">Crítica</option>
          </select>
        </div>
  
        <div class="mb-4">
          <label class="block text-sm mb-1">Mensagem:</label>
          <textarea v-model="mensagem" class="w-full border px-2 py-1 rounded" rows="3" />
        </div>
  
        <div class="flex justify-end gap-2">
          <button @click="fechar" class="px-4 py-1 border rounded">Cancelar</button>
          <button @click="enviar" class="px-4 py-1 bg-blue-600 text-white rounded">Enviar</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  
  const emit = defineEmits(['fechar'])
  
  const tipo = ref('sugestao')
  const mensagem = ref('')
  
  function fechar() {
    emit('fechar')
  }
  
  function enviar() {
    alert(`Mensagem enviada com sucesso!\n\nTipo: ${tipo.value}\nMensagem: ${mensagem.value}`)
    fechar()
  }
  </script>
  