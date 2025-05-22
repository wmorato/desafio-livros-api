import { onMounted } from 'vue'

export function setupUsuarioCrud(usuarioStore) {
  onMounted(() => {
    usuarioStore.carregarUsuarios()
  })

  function novoUsuario() {
    usuarioStore.selecionarUsuario({ nome: '', email: '', senha: '' })
  }

  function editarUsuario(usuario) {
    usuarioStore.selecionarUsuario({ ...usuario })
  }

  async function salvarUsuario() {
    if (!usuarioStore.usuarioSelecionado) return
    try {
      if (usuarioStore.usuarioSelecionado.id) {
        await usuarioStore.atualizarUsuario(usuarioStore.usuarioSelecionado)
      } else {
        await usuarioStore.adicionarUsuario(usuarioStore.usuarioSelecionado)
      }
      usuarioStore.limparSelecao()
    } catch (e) {
      // Erro já tratado no store
    }
  }

  function cancelarEdicao() {
    usuarioStore.limparSelecao()
  }

  async function excluirUsuario(id) {
    if (confirm('Tem certeza que deseja excluir este usuário?')) {
      await usuarioStore.removerUsuario(id)
    }
  }

  return {
    novoUsuario,
    editarUsuario,
    salvarUsuario,
    cancelarEdicao,
    excluirUsuario
  }
}
