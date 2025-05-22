
import { setupAutorCrud } from './AutorCrudScript.js';



describe('setupAutorCrud', () => {
  let autorStore;

  beforeEach(() => {
    autorStore = {
      carregarAutores: jest.fn(),
      selecionarAutor: jest.fn(),
      limparSelecao: jest.fn(),
      adicionarAutor: jest.fn().mockResolvedValue(),
      atualizarAutor: jest.fn().mockResolvedValue(),
      removerAutor: jest.fn().mockResolvedValue(),
      autorSelecionado: null
    };
  });


  it('novoAutor seleciona autor vazio', () => {
    const { novoAutor } = setupAutorCrud(autorStore);
    novoAutor();
    expect(autorStore.selecionarAutor).toHaveBeenCalledWith({ nome: '' });
  });

  it('editarAutor seleciona o autor copiado', () => {
    const { editarAutor } = setupAutorCrud(autorStore);
    const autor = { id: 2, nome: 'Machado' };
    editarAutor(autor);
    expect(autorStore.selecionarAutor).toHaveBeenCalledWith({ id: 2, nome: 'Machado' });
  });

  it('salvarAutor chama adicionarAutor para novo', async () => {
    const { salvarAutor } = setupAutorCrud(autorStore);
    autorStore.autorSelecionado = { nome: 'Novo' };
    await salvarAutor();
    expect(autorStore.adicionarAutor).toHaveBeenCalledWith({ nome: 'Novo' });
    expect(autorStore.limparSelecao).toHaveBeenCalled();
  });

  it('salvarAutor chama atualizarAutor para existente', async () => {
    const { salvarAutor } = setupAutorCrud(autorStore);
    autorStore.autorSelecionado = { id: 3, nome: 'Update' };
    await salvarAutor();
    expect(autorStore.atualizarAutor).toHaveBeenCalledWith({ id: 3, nome: 'Update' });
    expect(autorStore.limparSelecao).toHaveBeenCalled();
  });

  it('cancelarEdicao limpa seleção', () => {
    const { cancelarEdicao } = setupAutorCrud(autorStore);
    cancelarEdicao();
    expect(autorStore.limparSelecao).toHaveBeenCalled();
  });

  it('excluirAutor chama removerAutor se confirmado', async () => {
    global.confirm = jest.fn(() => true);
    const { excluirAutor } = setupAutorCrud(autorStore);
    await excluirAutor(7);
    expect(autorStore.removerAutor).toHaveBeenCalledWith(7);
  });

  it('excluirAutor não chama removerAutor se não confirmado', async () => {
    global.confirm = jest.fn(() => false);
    const { excluirAutor } = setupAutorCrud(autorStore);
    await excluirAutor(7);
    expect(autorStore.removerAutor).not.toHaveBeenCalled();
  });
});
