package com.exemplo.backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import com.exemplo.backend.entity.Genero;
import com.exemplo.backend.repository.GeneroRepository;

class GeneroServiceTest {

    private GeneroRepository generoRepository;
    private GeneroService generoService;

    @BeforeEach
    void setUp() {
        generoRepository = Mockito.mock(GeneroRepository.class);
        generoService = new GeneroService(generoRepository);
    }

    @Test
    void testBuscarPorId_encontrado() {
        Genero genero = new Genero(1L, "Ficção");
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        Optional<Genero> result = generoService.buscarPorId(1L);
        assertTrue(result.isPresent());
        assertEquals("Ficção", result.get().getNome());
    }

    @Test
    void testBuscarPorId_naoEncontrado() {
        when(generoRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Genero> result = generoService.buscarPorId(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testListarTodos() {
        Genero genero1 = new Genero(1L, "Aventura");
        Genero genero2 = new Genero(2L, "Terror");
        when(generoRepository.findAll()).thenReturn(Arrays.asList(genero1, genero2));
        List<Genero> result = generoService.listarTodos();
        assertEquals(2, result.size());
        assertEquals("Aventura", result.get(0).getNome());
    }
}
