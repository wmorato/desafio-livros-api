import { ref, watch } from 'vue'

export function useGeneroForm(props, emit) {
  // Usa um ref local para edição sem alterar a prop diretamente
  const generoLocal = ref({ ...props.genero })

  // Sempre que a prop 'genero' mudar, atualiza o local
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

  // Função para cancelar
  function onCancelar() {
    emit('cancelar')
  }

  return {
    generoLocal,
    onSalvar,
    onCancelar
  }
}
