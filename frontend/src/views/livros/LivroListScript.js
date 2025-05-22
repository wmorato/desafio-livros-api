import { ref, computed, onMounted } from 'vue'
import { useLivroStore } from '../../store/livroStore'

export function useLivroList() {
  const livroStore = useLivroStore()
  const filtro = ref('')

  onMounted(() => {
    livroStore.carregarLivros()
  })

  const livrosFiltrados = computed(() =>
    livroStore.livros.filter(livro =>
      livro.titulo.toLowerCase().includes(filtro.value.toLowerCase())
    )
  )

  return {
    livroStore,
    filtro,
    livrosFiltrados
  }
}
