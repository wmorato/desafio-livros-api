import { onMounted } from 'vue'

export function setupAutorCrud(autorStore) {
  onMounted(() => {
    autorStore.carregarAutores()
  })

  function novoAutor() {
    autorStore.selecionarAutor({ nome: '' })
  }

  function editarAutor(autor) {
    autorStore.selecionarAutor({ ...autor })
  }

  async function salvarAutor() {
    if (!autorStore.autorSelecionado) return
    try {
      if (autorStore.autorSelecionado.id) {
        await autorStore.atualizarAutor(autorStore.autorSelecionado)
      } else {
        await autorStore.adicionarAutor(autorStore.autorSelecionado)
      }
      autorStore.limparSelecao()
    } catch (e) {
      // Erro já está no store
    }
  }

  function cancelarEdicao() {
    autorStore.limparSelecao()
  }

  async function excluirAutor(id) {
    if (confirm('Tem certeza que deseja excluir este autor?')) {
      await autorStore.removerAutor(id)
    }
  }

  return {
    novoAutor,
    editarAutor,
    salvarAutor,
    cancelarEdicao,
    excluirAutor
  }
}
