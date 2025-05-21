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

import com.exemplo.backend.dto.GeneroDTO;
import com.exemplo.backend.entity.Genero;
import com.exemplo.backend.repository.GeneroRepository;

class GeneroServiceTest {

    @Mock
    private GeneroRepository generoRepository;

    @InjectMocks
    private GeneroService generoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_encontrado() {
        Long id = 1L;
        Genero genero = new Genero();
        genero.setId(id);
        genero.setNome("Genero Teste");

        when(generoRepository.findById(id)).thenReturn(Optional.of(genero));

        Optional<GeneroDTO> resultado = generoService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals("Genero Teste", resultado.get().getNome());
    }

    @Test
    void testBuscarPorId_naoEncontrado() {
        Long id = 2L;

        when(generoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<GeneroDTO> resultado = generoService.buscarPorId(id);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testListarTodos() {
        Genero genero1 = new Genero(); genero1.setId(1L); genero1.setNome("Genero 1");
        Genero genero2 = new Genero(); genero2.setId(2L); genero2.setNome("Genero 2");

        when(generoRepository.findAll()).thenReturn(Arrays.asList(genero1, genero2));

        List<GeneroDTO> resultado = generoService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Genero 1", resultado.get(0).getNome());
    }
}
