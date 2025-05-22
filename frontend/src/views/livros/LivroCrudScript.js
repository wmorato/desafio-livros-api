import { onMounted } from 'vue'

export function setupLivroCrud(livroStore) {
  onMounted(() => {
    livroStore.carregarLivros()
  })

  function novoLivro() {
    livroStore.selecionarLivro({ titulo: '', autorId: null, generoId: null })
  }

  function editarLivro(livro) {
    livroStore.selecionarLivro({ ...livro })
  }

  async function salvarLivro() {
    if (!livroStore.livroSelecionado) return
    try {
      if (livroStore.livroSelecionado.id) {
        await livroStore.atualizarLivro(livroStore.livroSelecionado)
      } else {
        await livroStore.adicionarLivro(livroStore.livroSelecionado)
      }
      livroStore.limparSelecao()
    } catch (e) {
      // Erro já está na store
    }
  }

  function cancelarEdicao() {
    livroStore.limparSelecao()
  }

  async function excluirLivro(id) {
    if (confirm('Tem certeza que deseja excluir este livro?')) {
      await livroStore.removerLivro(id)
    }
  }

  return {
    novoLivro,
    editarLivro,
    salvarLivro,
    cancelarEdicao,
    excluirLivro
  }
}
