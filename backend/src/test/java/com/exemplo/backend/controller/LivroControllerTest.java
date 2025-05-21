package com.exemplo.backend.controller;

import com.exemplo.backend.dto.LivroDTO;
import com.exemplo.backend.entity.Livro;
import com.exemplo.backend.service.LivroService;
import com.exemplo.backend.service.GeneroService;
import com.exemplo.backend.service.AutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LivroController.class)
@Disabled
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService livroService;
    @MockBean
    private GeneroService generoService;
    @MockBean
    private AutorService autorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Livro livro;

    @BeforeEach
    void setup() {
        livro = new Livro();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void devePermitirCriarLivroComoAdmin() throws Exception {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.titulo = "Dom Quixote";
        livroDTO.autorId = 1L;
        livroDTO.generoId = 1L;

        Mockito.when(livroService.salvar(any(Livro.class))).thenReturn(livro);

        mockMvc.perform(post("/api/v1/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(livroDTO))
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deveRetornarLivroQuandoExistir() throws Exception {
        Mockito.when(livroService.buscarPorId(1L)).thenReturn(Optional.of(livro));

        mockMvc.perform(get("/api/v1/livros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(livro.getId()))
                .andExpect(jsonPath("$.titulo").value(livro.getTitulo()));
    }

    @Test
    @WithMockUser
    void deveRetornar404QuandoLivroNaoExistir() throws Exception {
        Mockito.when(livroService.buscarPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/livros/2"))
                .andExpect(status().isNotFound());
    }
}
