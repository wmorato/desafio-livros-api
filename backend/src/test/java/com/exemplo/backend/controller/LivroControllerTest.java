package com.exemplo.backend.controller;

import com.exemplo.backend.dto.GeneroDTO;
import com.exemplo.backend.dto.LivroDTO;
import com.exemplo.backend.dto.LivroResponseDTO;
import com.exemplo.backend.entity.Autor;
import com.exemplo.backend.entity.Genero;
import com.exemplo.backend.entity.Livro;
import com.exemplo.backend.service.AutorService;
import com.exemplo.backend.service.GeneroService;
import com.exemplo.backend.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


// Especifica o controlador a ser testado.
// WebMvcTest carrega apenas o contexto MVC, ideal para testar controladores.
// MockBean injeta mocks para os serviços que o controlador depende.
@WebMvcTest(LivroController.class)
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc; // Usado para fazer requisições HTTP simuladas

    @Autowired
    private ObjectMapper objectMapper; // Para converter objetos Java em JSON e vice-versa

    @MockBean
    private LivroService livroService;

    @MockBean
    private AutorService autorService;

    @MockBean
    private GeneroController generoService;

    // Embora o JwtFilter e JwtUtil sejam mocks (já que WebMvcTest não carrega a cadeia de filtros completa),
    // é necessário declará-los como @MockBean se eles são injetados em algum lugar do contexto da aplicação
    // que WebMvcTest tenta carregar (ex: em uma classe de configuração de segurança que o WebMvcTest indiretamente importa).
    // Isso evita erros de "bean not found".
    @MockBean(name = "jwtFilter") // Especifica o nome do bean se houver ambiguidade
    private Object jwtFilter;

    @MockBean(name = "jwtUtil") // Especifica o nome do bean se houver ambiguidade
    private Object jwtUtil;

    // Objetos comuns para serem usados nos testes
    private Livro livro;
    private Autor autor;
    private Genero genero;
    private LivroDTO livroDTO;

    @BeforeEach
    void setUp() {
        // Inicializa Autor e Genero com IDs e nomes para uso nos mocks
        autor = new Autor();
        autor.setId(1L);
        autor.setNome("Autor Teste");

        genero = new Genero();
        genero.setId(2L);
        genero.setNome("Genero Teste");

        // Inicializa Livro completo com Autor e Genero para simular um livro no banco
        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Livro Teste");
        livro.setAutor(autor);
        livro.setGenero(genero);

        // Inicializa LivroDTO para simular dados de entrada de requisições POST/PUT
        livroDTO = new LivroDTO();
        livroDTO.titulo = "Novo Livro";
        livroDTO.autorId = 1L;
        livroDTO.generoId = 2L;
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Simula um usuário autenticado com a role ADMIN
    void deveRetornarLivroQuandoExistir() throws Exception {
        Long id = 1L;

        // Configura o mock do livroService para retornar um Optional contendo o 'livro'
        // quando buscarPorId(id) é chamado.
        Mockito.when(livroService.buscarPorId(id))
                .thenReturn(Optional.of(livro));

        mockMvc.perform(get("/api/v1/livros/{id}", id)) // Realiza uma requisição GET
                .andDo(print()) // Imprime a requisição e a resposta no console para depuração
                .andExpect(status().isOk()) // Espera um status HTTP 200 OK
                // Verifica os campos do JSON retornado
                .andExpect(jsonPath("$.id").value(livro.getId()))
                .andExpect(jsonPath("$.titulo").value(livro.getTitulo()))
                .andExpect(jsonPath("$.autor").value(livro.getAutor().getNome()))
                .andExpect(jsonPath("$.genero").value(livro.getGenero().getNome()))
                .andExpect(jsonPath("$.autorId").value(livro.getAutor().getId()))
                .andExpect(jsonPath("$.generoId").value(livro.getGenero().getId()));

        // Verifica se o método buscarPorId do livroService foi chamado exatamente uma vez
        Mockito.verify(livroService, Mockito.times(1)).buscarPorId(id);
    }

    @Test
    @WithMockUser(roles = "USER") // Simula um usuário autenticado (role USER é suficiente para buscar)
    void deveRetornar404QuandoLivroNaoExistir() throws Exception {
        Long id = 99L; // Um ID que simulamos que não existe

        // Configura o mock do livroService para retornar um Optional vazio
        // quando buscarPorId(id) é chamado para simular que o livro não foi encontrado.
        Mockito.when(livroService.buscarPorId(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/livros/{id}", id)) // Realiza a requisição GET
                .andDo(print()) // Imprime para depuração
                .andExpect(status().isNotFound()); // Espera um status HTTP 404 Not Found

        // Verifica se o método buscarPorId do livroService foi chamado
        Mockito.verify(livroService, Mockito.times(1)).buscarPorId(id);
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Simula um usuário autenticado com a role ADMIN (necessário para criar)
    void devePermitirCriarLivroComoAdmin() throws Exception {
        // Mocks dos serviços de dependência para que o controlador possa validar e construir o Livro
        GeneroDTO generoDTO = new GeneroDTO(livroDTO.generoId, "Nome do gênero");
        Mockito.when(generoService.buscarPorId(livroDTO.generoId)).thenReturn(ResponseEntity.of(Optional.of(generoDTO)));


        // Cria uma instância de Livro que representa o que o serviço de salvar retornaria
        // É CRUCIAL que este objeto tenha um ID, pois o controlador espera que o livro salvo
        // tenha um ID para poder retornar no LivroResponseDTO.
        Livro livroSalvoComId = new Livro();
        livroSalvoComId.setId(10L); // Um ID simulado para o livro recém-criado
        livroSalvoComId.setTitulo(livroDTO.titulo);
        livroSalvoComId.setAutor(autor);
        livroSalvoComId.setGenero(genero);

        // Configura o mock do livroService para retornar o livro salvo com ID
        Mockito.when(livroService.salvar(any(Livro.class))).thenReturn(livroSalvoComId);

        mockMvc.perform(post("/api/v1/livros") // Realiza uma requisição POST
                .with(csrf()) // Adiciona token CSRF, importante para POST/PUT/DELETE com Spring Security
                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo como JSON
                .content(objectMapper.writeValueAsString(livroDTO))) // Converte o DTO para JSON e envia no corpo
                .andDo(print()) // Imprime para depuração
                .andExpect(status().isCreated()) // Espera status HTTP 201 Created
                // Verifica os campos do JSON de resposta (do livro criado)
                .andExpect(jsonPath("$.id").value(livroSalvoComId.getId()))
                .andExpect(jsonPath("$.titulo").value(livroSalvoComId.getTitulo()))
                .andExpect(jsonPath("$.autor").value(livroSalvoComId.getAutor().getNome()))
                .andExpect(jsonPath("$.genero").value(livroSalvoComId.getGenero().getNome()))
                .andExpect(jsonPath("$.autorId").value(livroSalvoComId.getAutor().getId()))
                .andExpect(jsonPath("$.generoId").value(livroSalvoComId.getGenero().getId()));

        // Verifica as chamadas aos serviços
        Mockito.verify(autorService, Mockito.times(1)).buscarPorId(livroDTO.autorId);
        Mockito.verify(generoService, Mockito.times(1)).buscarPorId(livroDTO.generoId);
        Mockito.verify(livroService, Mockito.times(1)).salvar(any(Livro.class));
    }
}
