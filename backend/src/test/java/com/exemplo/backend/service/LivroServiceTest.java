package com.exemplo.backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.exemplo.backend.entity.Livro;
import com.exemplo.backend.repository.LivroRepository;

class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarTodos() {
        Livro livro1 = new Livro();
        livro1.setId(1L);
        livro1.setTitulo("Livro 1");

        Livro livro2 = new Livro();
        livro2.setId(2L);
        livro2.setTitulo("Livro 2");

        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro1, livro2));

        List<Livro> resultado = livroService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Livro 1", resultado.get(0).getTitulo());
    }

    @Test
    void testBuscarPorId_encontrado() {
        Long id = 1L;
        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo("Livro Teste");

        when(livroRepository.findById(id)).thenReturn(Optional.of(livro));

        Optional<Livro> resultado = livroService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals("Livro Teste", resultado.get().getTitulo());
    }

    @Test
    void testBuscarPorId_naoEncontrado() {
        Long id = 99L;

        when(livroRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Livro> resultado = livroService.buscarPorId(id);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testSalvar() {
        Livro livro = new Livro();
        livro.setTitulo("Livro Novo");

        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        Livro salvo = livroService.salvar(livro);

        assertNotNull(salvo);
        assertEquals("Livro Novo", salvo.getTitulo());
    }

    @Test
    void testDeletar() {
        Long id = 1L;
        doNothing().when(livroRepository).deleteById(id);

        livroService.deletar(id);

        verify(livroRepository, times(1)).deleteById(id);
    }
}
