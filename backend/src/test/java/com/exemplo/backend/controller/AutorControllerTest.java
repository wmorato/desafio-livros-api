// src/test/java/com/exemplo/backend/controller/AutorControllerTest.java
package com.exemplo.backend.controller;

import com.exemplo.backend.dto.AutorDTO; // <-- AGORA SIM, importe o DTO real
import com.exemplo.backend.dto.AutorResponseDTO;
import com.exemplo.backend.entity.Autor;
import com.exemplo.backend.service.AutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AutorController.class)
public class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AutorService autorService;

    @MockBean(name = "jwtFilter")
    private Object jwtFilter;

    @MockBean(name = "jwtUtil")
    private Object jwtUtil;

    private Autor autor;
    private AutorDTO autorDTO; // <-- Usa o DTO real
    private AutorResponseDTO autorResponseDTO;

    @BeforeEach
    void setUp() {
        autor = new Autor();
        autor.setId(1L);
        autor.setNome("Machado de Assis");

        autorDTO = new AutorDTO(); // <-- Instancia o DTO real
        autorDTO.nome = "Carlos Drummond de Andrade";

        autorResponseDTO = new AutorResponseDTO();
        autorResponseDTO.id = 1L;
        autorResponseDTO.nome = "Machado de Assis";
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveRetornarAutorQuandoExistir() throws Exception {
        Long id = 1L;
        Mockito.when(autorService.buscarPorId(id)).thenReturn(Optional.of(autor));

        mockMvc.perform(get("/api/v1/autores/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(autor.getId()))
                .andExpect(jsonPath("$.nome").value(autor.getNome()));

        Mockito.verify(autorService, Mockito.times(1)).buscarPorId(id);
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveRetornar404QuandoAutorNaoExistir() throws Exception {
        Long id = 99L;
        Mockito.when(autorService.buscarPorId(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/autores/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());

        Mockito.verify(autorService, Mockito.times(1)).buscarPorId(id);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void devePermitirCriarAutorComoAdmin() throws Exception {
        Autor autorSalvo = new Autor();
        autorSalvo.setId(2L);
        autorSalvo.setNome(autorDTO.nome); // Usa o DTO real

        Mockito.when(autorService.salvar(any(Autor.class))).thenReturn(autorSalvo);

        mockMvc.perform(post("/api/v1/autores")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(autorDTO))) // Serializa o DTO real
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(autorSalvo.getId()))
                .andExpect(jsonPath("$.nome").value(autorSalvo.getNome()));

        Mockito.verify(autorService, Mockito.times(1)).salvar(any(Autor.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveListarTodosAutores() throws Exception {
        Autor autor2 = new Autor();
        autor2.setId(3L);
        autor2.setNome("Clarice Lispector");

        Mockito.when(autorService.listarTodos()).thenReturn(java.util.Arrays.asList(autor, autor2));

        mockMvc.perform(get("/api/v1/autores"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(autor.getId()))
                .andExpect(jsonPath("$[0].nome").value(autor.getNome()))
                .andExpect(jsonPath("$[1].id").value(autor2.getId()))
                .andExpect(jsonPath("$[1].nome").value(autor2.getNome()));

        Mockito.verify(autorService, Mockito.times(1)).listarTodos();
    }
}