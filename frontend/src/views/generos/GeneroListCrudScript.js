// Recebe a lista de gêneros como prop do componente pai
const props = defineProps({
  generos: {
    type: Array,
    required: true
  }
})

// Emite eventos para o pai (editar/excluir)
const emit = defineEmits(['editar', 'excluir'])

// Função chamada ao clicar em Editar
function onEditar(genero) {
  emit('editar', genero)
}

// Função chamada ao clicar em Excluir
function onExcluir(id) {
  emit('excluir', id)
}

// Exporta as funções para o template
export {
  onEditar,
  onExcluir
}
