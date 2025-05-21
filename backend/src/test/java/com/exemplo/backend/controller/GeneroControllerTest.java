package com.exemplo.backend.controller;

import com.exemplo.backend.dto.GeneroDTO;
import com.exemplo.backend.entity.Genero;
import com.exemplo.backend.service.GeneroService;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeneroController.class)
@Disabled
class GeneroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeneroService generoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Genero genero;

    @BeforeEach
    void setup() {
        genero = new Genero(1L, "Ficção");
    }

    @Test
    @WithMockUser
    void listarTodos_deveRetornarListaDeGeneros() throws Exception {
        Mockito.when(generoService.listarTodos()).thenReturn(Arrays.asList(genero));
        mockMvc.perform(get("/api/v1/generos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(genero.getId()))
                .andExpect(jsonPath("$[0].nome").value(genero.getNome()));
    }

    @Test
    @WithMockUser
    void listarTodos_vazio_deveRetornarListaVazia() throws Exception {
        Mockito.when(generoService.listarTodos()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/generos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser
    void buscarPorId_existente_deveRetornarGenero() throws Exception {
        Mockito.when(generoService.buscarPorId(1L)).thenReturn(Optional.of(genero));
        mockMvc.perform(get("/api/v1/generos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(genero.getId()))
                .andExpect(jsonPath("$.nome").value(genero.getNome()));
    }

    @Test
    @WithMockUser
    void buscarPorId_inexistente_deveRetornarNotFound() throws Exception {
        Mockito.when(generoService.buscarPorId(999L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/generos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void criarGenero_admin_deveRetornarGeneroCriado() throws Exception {
        GeneroDTO generoDTO = new GeneroDTO();
        generoDTO.setNome("Romance");
        Genero salvo = new Genero(2L, "Romance");
        Mockito.when(generoService.salvar(any(Genero.class))).thenReturn(salvo);

        mockMvc.perform(post("/api/v1/generos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(generoDTO))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(salvo.getId()))
                .andExpect(jsonPath("$.nome").value(salvo.getNome()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void atualizarGenero_existente_admin_deveRetornarGeneroAtualizado() throws Exception {
        GeneroDTO generoDTO = new GeneroDTO();
        generoDTO.setNome("Atualizado");

        Mockito.when(generoService.buscarPorId(1L)).thenReturn(Optional.of(genero));
        Genero atualizado = new Genero(1L, "Atualizado");
        Mockito.when(generoService.salvar(any(Genero.class))).thenReturn(atualizado);

        mockMvc.perform(put("/api/v1/generos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(generoDTO))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(atualizado.getId()))
                .andExpect(jsonPath("$.nome").value(atualizado.getNome()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void atualizarGenero_inexistente_admin_deveRetornarNotFound() throws Exception {
        GeneroDTO generoDTO = new GeneroDTO();
        generoDTO.setNome("Novo Nome");

        Mockito.when(generoService.buscarPorId(10L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/generos/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(generoDTO))
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deletarGenero_existente_admin_deveRetornarNoContent() throws Exception {
        Mockito.when(generoService.buscarPorId(1L)).thenReturn(Optional.of(genero));
        Mockito.doNothing().when(generoService).deletar(1L);

        mockMvc.perform(delete("/api/v1/generos/1")
                .with(csrf()))
                .andExpect(status().isNoContent());
        verify(generoService, times(1)).deletar(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deletarGenero_inexistente_admin_deveRetornarNotFound() throws Exception {
        Mockito.when(generoService.buscarPorId(100L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/generos/100")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser // usuário comum, não admin
    void criarGenero_usuarioComum_deveNegarAcesso() throws Exception {
        GeneroDTO generoDTO = new GeneroDTO();
        generoDTO.setNome("Policial");

        mockMvc.perform(post("/api/v1/generos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(generoDTO))
                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser // usuário comum, não admin
    void atualizarGenero_usuarioComum_deveNegarAcesso() throws Exception {
        GeneroDTO generoDTO = new GeneroDTO();
        generoDTO.setNome("Policial");

        mockMvc.perform(put("/api/v1/generos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(generoDTO))
                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser // usuário comum, não admin
    void deletarGenero_usuarioComum_deveNegarAcesso() throws Exception {
        mockMvc.perform(delete("/api/v1/generos/1")
                .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
