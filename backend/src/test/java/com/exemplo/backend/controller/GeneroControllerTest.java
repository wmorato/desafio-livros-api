package com.exemplo.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.exemplo.backend.dto.GeneroDTO;
import com.exemplo.backend.service.GeneroService;

@WebMvcTest(controllers = GeneroController.class)
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
public class GeneroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeneroService generoService;

    private GeneroDTO generoDTO;

    @BeforeEach
    void setUp() {
        generoDTO = new GeneroDTO();
        generoDTO.setId(1L);
        generoDTO.setNome("Ficção Científica");
    }

    // ----------------------------- LISTAR TODOS -----------------------------

    @Test
    void deveListarTodosOsGeneros() throws Exception {
        when(generoService.listarTodos()).thenReturn(List.of(generoDTO));

        mockMvc.perform(get("/api/v1/generos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Ficção Científica"));
    }

    // ----------------------------- BUSCAR POR ID -----------------------------

    @Test
    void deveRetornarGeneroQuandoExistir() throws Exception {
        when(generoService.buscarPorId(1L)).thenReturn(Optional.of(generoDTO));

        mockMvc.perform(get("/api/v1/generos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Ficção Científica"));
    }

    @Test
    void deveRetornar404QuandoGeneroNaoExistir() throws Exception {
        when(generoService.buscarPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/generos/999"))
                .andExpect(status().isNotFound());
    }

    // ----------------------------- CRIAR -----------------------------

    @Test
    @WithMockUser(roles = "ADMIN")
    void devePermitirCriarGeneroComoAdmin() throws Exception {
        GeneroDTO novo = new GeneroDTO();
        novo.setNome("Romance");

        GeneroDTO criado = new GeneroDTO();
        criado.setId(2L);
        criado.setNome("Romance");

        when(generoService.criar(any(GeneroDTO.class))).thenReturn(criado);

        mockMvc.perform(post("/api/v1/generos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Romance\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nome").value("Romance"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveNegarCriarGeneroComoUser() throws Exception {
        mockMvc.perform(post("/api/v1/generos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Fantasia\"}"))
                .andExpect(status().isForbidden());
    }

    // ----------------------------- ATUALIZAR -----------------------------

    @Test
    @WithMockUser(roles = "ADMIN")
    void devePermitirAtualizarGeneroComoAdmin() throws Exception {
        GeneroDTO atualizado = new GeneroDTO();
        atualizado.setId(1L);
        atualizado.setNome("Terror");

        when(generoService.atualizar(eq(1L), any(GeneroDTO.class))).thenReturn(Optional.of(atualizado));

        mockMvc.perform(put("/api/v1/generos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Terror\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Terror"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveRetornar404AoAtualizarGeneroInexistenteComoAdmin() throws Exception {
        when(generoService.atualizar(eq(999L), any(GeneroDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/generos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Drama\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveNegarAtualizarGeneroComoUser() throws Exception {
        mockMvc.perform(put("/api/v1/generos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Aventura\"}"))
                .andExpect(status().isForbidden());
    }

    // ----------------------------- DELETAR -----------------------------

    @Test
    @WithMockUser(roles = "ADMIN")
    void devePermitirDeletarGeneroComoAdmin() throws Exception {
        when(generoService.existe(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/generos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveRetornar404AoDeletarGeneroInexistenteComoAdmin() throws Exception {
        when(generoService.existe(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/generos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveNegarDeletarGeneroComoUser() throws Exception {
        mockMvc.perform(delete("/api/v1/generos/1"))
                .andExpect(status().isForbidden());
    }
}