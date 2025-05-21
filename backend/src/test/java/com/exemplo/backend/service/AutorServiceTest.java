package com.exemplo.backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.exemplo.backend.entity.Autor;
import com.exemplo.backend.repository.AutorRepository;

class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_encontrado() {
        Long id = 1L;
        Autor autor = new Autor();
        autor.setId(id);
        autor.setNome("Autor Teste");

        when(autorRepository.findById(id)).thenReturn(Optional.of(autor));

        Optional<Autor> resultado = autorService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals("Autor Teste", resultado.get().getNome());
    }

    @Test
    void testBuscarPorId_naoEncontrado() {
        Long id = 2L;

        when(autorRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Autor> resultado = autorService.buscarPorId(id);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testListarTodos() {
        Autor autor1 = new Autor(); autor1.setId(1L); autor1.setNome("Autor 1");
        Autor autor2 = new Autor(); autor2.setId(2L); autor2.setNome("Autor 2");

        when(autorRepository.findAll()).thenReturn(Arrays.asList(autor1, autor2));

        List<Autor> resultado = autorService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Autor 1", resultado.get(0).getNome());
    }
}
