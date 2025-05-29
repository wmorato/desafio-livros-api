import { nextTick, reactive } from 'vue'
import { useAutorForm } from './AutorFormScript'

// frontend/src/views/autores/AutorFormScript.test.js

describe('useAutorForm', () => {
  let emit
  let props

  beforeEach(() => {
    emit = jest.fn()
    props = { autor: { id: 1, nome: 'Machado' } }
  })

  it('inicializa autorLocal com props.autor', () => {
    const { autorLocal } = useAutorForm(props, emit)
    expect(autorLocal.value).toEqual({ id: 1, nome: 'Machado' })
  })

  it('atualiza autorLocal quando props.autor muda', async () => {
    // Torna props reativo
    const reactiveProps = reactive({ autor: { id: 1, nome: 'Machado' } })
    const { autorLocal } = useAutorForm(reactiveProps, emit)
    reactiveProps.autor = { id: 2, nome: 'Clarice' }
    await nextTick()
    expect(autorLocal.value).toEqual({ id: 2, nome: 'Clarice' })
  })

  it('onSalvar emite evento salvar com autorLocal', () => {
    const { autorLocal, onSalvar } = useAutorForm(props, emit)
    autorLocal.value.nome = 'Drummond'
    onSalvar()
    expect(emit).toHaveBeenCalledWith('salvar', { id: 1, nome: 'Drummond' })
  })

  it('onCancelar emite evento cancelar', () => {
    const { onCancelar } = useAutorForm(props, emit)
    onCancelar()
    expect(emit).toHaveBeenCalledWith('cancelar')
  })
})