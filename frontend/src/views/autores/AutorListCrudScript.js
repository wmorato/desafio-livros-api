export function useAutorListCrud(emit) {
  function onEditar(autor) {
    emit('editar', autor)
  }
  function onExcluir(id) {
    emit('excluir', id)
  }
  return { onEditar, onExcluir }
}
