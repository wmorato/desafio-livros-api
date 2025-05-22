import { ref, watch } from 'vue'

export function useGeneroForm(props, emit) {
  const generoLocal = ref({ ...props.genero })

  watch(
    () => props.genero,
    (novoGenero) => {
      generoLocal.value = { ...novoGenero }
    }
  )

  function onSalvar() {
    emit('salvar', { ...generoLocal.value })
  }

  function onCancelar() {
    emit('cancelar')
  }

  return {
    generoLocal,
    onSalvar,
    onCancelar
  }
}
