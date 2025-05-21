import { ref, watch, toRefs } from 'vue'

// Props recebidas do componente pai
const props = defineProps({
  genero: {
    type: Object,
    required: true
  }
})

// Emits para comunicar com o pai (salvar/cancelar)
const emit = defineEmits(['salvar', 'cancelar'])

// Usa um ref local para edição sem alterar a prop diretamente (boa prática Vue)
const generoLocal = ref({ ...props.genero })

// Sempre que a prop 'genero' mudar (novo registro, edição diferente), atualiza o local
watch(
  () => props.genero,
  (novoGenero) => {
    generoLocal.value = { ...novoGenero }
  }
)

// Função para emitir o evento de salvar
function onSalvar() {
  emit('salvar', { ...generoLocal.value })
}

// Função para cancelar (emite evento para o pai)
function onCancelar() {
  emit('cancelar')
}

// Exporta as refs/funções usadas no template
export {
  generoLocal,
  onSalvar,
  onCancelar
}
