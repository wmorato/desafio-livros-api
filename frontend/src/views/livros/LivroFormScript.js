import { ref, watch } from 'vue'

export function useLivroForm(props, emit) {
  const livroLocal = ref({ ...props.livro })

  watch(
    () => props.livro,
    (novoLivro) => {
      livroLocal.value = { ...novoLivro }
    }
  )

  function onSalvar() {
    emit('salvar', { ...livroLocal.value })
  }

  function onCancelar() {
    emit('cancelar')
  }

  return {
    livroLocal,
    onSalvar,
    onCancelar
  }
}
