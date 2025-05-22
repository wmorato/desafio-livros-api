import { ref, watch } from 'vue'

export function useAutorForm(props, emit) {
  const autorLocal = ref({ ...props.autor })

  watch(
    () => props.autor,
    (novoAutor) => {
      autorLocal.value = { ...novoAutor }
    }
  )

  function onSalvar() {
    emit('salvar', { ...autorLocal.value })
  }

  function onCancelar() {
    emit('cancelar')
  }

  return {
    autorLocal,
    onSalvar,
    onCancelar
  }
}
