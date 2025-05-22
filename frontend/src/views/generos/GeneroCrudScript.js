import { onMounted } from 'vue'

function setupGeneroCrud(generoStore) {
  // Carrega os gêneros ao montar o componente
  onMounted(() => {
    generoStore.carregarGeneros()
  })

  // Inicia criação de um novo gênero (limpa seleção anterior)
  function novoGenero() {
    generoStore.selecionarGenero({ nome: '' })
  }

  // Prepara edição (faz uma cópia para não alterar diretamente o da lista)
  function editarGenero(genero) {
    generoStore.selecionarGenero({ ...genero })
  }

  // Salva gênero, seja novo ou edição
  async function salvarGenero() {
    if (!generoStore.generoSelecionado) return
    try {
      if (generoStore.generoSelecionado.id) {
        await generoStore.atualizarGenero(generoStore.generoSelecionado)
      } else {
        await generoStore.adicionarGenero(generoStore.generoSelecionado)
      }
      generoStore.limparSelecao()
    } catch (e) {
      // O erro já será exibido pelo store (generoStore.error)
    }
  }

  // Cancela edição/criação e limpa seleção
  function cancelarEdicao() {
    generoStore.limparSelecao()
  }

  // Remove gênero (confirmação antes)
  async function excluirGenero(id) {
    if (confirm('Tem certeza que deseja excluir este gênero?')) {
      await generoStore.removerGenero(id)
    }
  }

  // Retorna todas as funções para uso no componente
  return {
    novoGenero,
    editarGenero,
    salvarGenero,
    cancelarEdicao,
    excluirGenero
  }
}

export { setupGeneroCrud }
