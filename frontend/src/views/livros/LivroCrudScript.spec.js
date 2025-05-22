import { setupLivroCrud } from './LivroCrudScript.js';

describe('setupLivroCrud', () => {
  let livroStore;

  beforeEach(() => {
    livroStore = {
      carregarLivros: jest.fn(),
      selecionarLivro: jest.fn(),
      limparSelecao: jest.fn(),
      adicionarLivro: jest.fn().mockResolvedValue(),
      atualizarLivro: jest.fn().mockResolvedValue(),
      removerLivro: jest.fn().mockResolvedValue(),
      livroSelecionado: null,
      error: null
    };
  });


  it('editarLivro seleciona o livro copiado', () => {
    const { editarLivro } = setupLivroCrud(livroStore);
    const livro = { id: 2, titulo: '1984', autorId: 5, generoId: 3 };
    editarLivro(livro);
    expect(livroStore.selecionarLivro).toHaveBeenCalledWith({ id: 2, titulo: '1984', autorId: 5, generoId: 3 });
  });

  it('salvarLivro chama adicionarLivro para novo', async () => {
    const { salvarLivro } = setupLivroCrud(livroStore);
    livroStore.livroSelecionado = { titulo: 'Novo Livro', autorId: 1, generoId: 2 };
    await salvarLivro();
    expect(livroStore.adicionarLivro).toHaveBeenCalledWith({ titulo: 'Novo Livro', autorId: 1, generoId: 2 });
    expect(livroStore.limparSelecao).toHaveBeenCalled();
  });

  it('salvarLivro chama atualizarLivro para existente', async () => {
    const { salvarLivro } = setupLivroCrud(livroStore);
    livroStore.livroSelecionado = { id: 3, titulo: 'Update', autorId: 2, generoId: 7 };
    await salvarLivro();
    expect(livroStore.atualizarLivro).toHaveBeenCalledWith({ id: 3, titulo: 'Update', autorId: 2, generoId: 7 });
    expect(livroStore.limparSelecao).toHaveBeenCalled();
  });

  it('salvarLivro não faz nada se nenhum livro selecionado', async () => {
    const { salvarLivro } = setupLivroCrud(livroStore);
    livroStore.livroSelecionado = null;
    await salvarLivro();
    expect(livroStore.adicionarLivro).not.toHaveBeenCalled();
    expect(livroStore.atualizarLivro).not.toHaveBeenCalled();
    expect(livroStore.limparSelecao).not.toHaveBeenCalled();
  });



  it('excluirLivro chama removerLivro se confirmado', async () => {
    global.confirm = jest.fn(() => true);
    const { excluirLivro } = setupLivroCrud(livroStore);
    await excluirLivro(8);
    expect(livroStore.removerLivro).toHaveBeenCalledWith(8);
  });

  it('excluirLivro não chama removerLivro se não confirmado', async () => {
    global.confirm = jest.fn(() => false);
    const { excluirLivro } = setupLivroCrud(livroStore);
    await excluirLivro(8);
    expect(livroStore.removerLivro).not.toHaveBeenCalled();
  });
});
