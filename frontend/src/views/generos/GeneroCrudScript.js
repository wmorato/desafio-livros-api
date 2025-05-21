// Importação dos hooks do Vue
import { onMounted } from 'vue'

// Importação da store Pinia centralizada para Gêneros
import { useGeneroStore } from '@/store/generoStore'

// Instancia a store, tornando disponível para o template
const generoStore = useGeneroStore()

// Carrega os gêneros ao montar o componente
onMounted(() => {
  generoStore.carregarGeneros()
})

// Inicia criação de um novo gênero (limpa seleção anterior)
function novoGenero() {
  generoStore.selecionarGenero({ nome: '' }) // Cria objeto vazio para novo
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
      // Se já existe id, atualiza
      await generoStore.atualizarGenero(generoStore.generoSelecionado)
    } else {
      // Se não existe, adiciona
      await generoStore.adicionarGenero(generoStore.generoSelecionado)
    }
    // Limpa seleção ao final
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

// Exporta tudo para ficar acessível no <template>
export {
  generoStore,
  novoGenero,
  editarGenero,
  salvarGenero,
  cancelarEdicao,
  excluirGenero
}
