export function useLivroListCrud(emit) {
  function onEditar(livro) {
    emit('editar', livro)
  }
  function onExcluir(id) {
    emit('excluir', id)
  }
  return { onEditar, onExcluir }
}
