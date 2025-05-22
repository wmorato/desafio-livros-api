import { setupGeneroCrud } from './GeneroCrudScript.js';

describe('setupGeneroCrud', () => {
  let generoStore;

  beforeEach(() => {
    generoStore = {
      carregarGeneros: jest.fn(),
      selecionarGenero: jest.fn(),
      limparSelecao: jest.fn(),
      adicionarGenero: jest.fn().mockResolvedValue(),
      atualizarGenero: jest.fn().mockResolvedValue(),
      removerGenero: jest.fn().mockResolvedValue(),
      generoSelecionado: null,
      error: null
    };
  });

  it('novoGenero seleciona gênero vazio', () => {
    const { novoGenero } = setupGeneroCrud(generoStore);
    novoGenero();
    expect(generoStore.selecionarGenero).toHaveBeenCalledWith({ nome: '' });
  });

  it('editarGenero seleciona o gênero copiado', () => {
    const { editarGenero } = setupGeneroCrud(generoStore);
    const genero = { id: 7, nome: 'Aventura' };
    editarGenero(genero);
    expect(generoStore.selecionarGenero).toHaveBeenCalledWith({ id: 7, nome: 'Aventura' });
  });

  it('salvarGenero chama adicionarGenero para novo', async () => {
    const { salvarGenero } = setupGeneroCrud(generoStore);
    generoStore.generoSelecionado = { nome: 'Terror' };
    await salvarGenero();
    expect(generoStore.adicionarGenero).toHaveBeenCalledWith({ nome: 'Terror' });
    expect(generoStore.limparSelecao).toHaveBeenCalled();
  });

  it('salvarGenero chama atualizarGenero para existente', async () => {
    const { salvarGenero } = setupGeneroCrud(generoStore);
    generoStore.generoSelecionado = { id: 4, nome: 'Ficção' };
    await salvarGenero();
    expect(generoStore.atualizarGenero).toHaveBeenCalledWith({ id: 4, nome: 'Ficção' });
    expect(generoStore.limparSelecao).toHaveBeenCalled();
  });

  it('salvarGenero não faz nada se nenhum gênero selecionado', async () => {
    const { salvarGenero } = setupGeneroCrud(generoStore);
    generoStore.generoSelecionado = null;
    await salvarGenero();
    expect(generoStore.adicionarGenero).not.toHaveBeenCalled();
    expect(generoStore.atualizarGenero).not.toHaveBeenCalled();
    expect(generoStore.limparSelecao).not.toHaveBeenCalled();
  });

  it('cancelarEdicao limpa seleção', () => {
    const { cancelarEdicao } = setupGeneroCrud(generoStore);
    cancelarEdicao();
    expect(generoStore.limparSelecao).toHaveBeenCalled();
  });

  it('excluirGenero chama removerGenero se confirmado', async () => {
    global.confirm = jest.fn(() => true);
    const { excluirGenero } = setupGeneroCrud(generoStore);
    await excluirGenero(8);
    expect(generoStore.removerGenero).toHaveBeenCalledWith(8);
  });

  it('excluirGenero não chama removerGenero se não confirmado', async () => {
    global.confirm = jest.fn(() => false);
    const { excluirGenero } = setupGeneroCrud(generoStore);
    await excluirGenero(8);
    expect(generoStore.removerGenero).not.toHaveBeenCalled();
  });
});
