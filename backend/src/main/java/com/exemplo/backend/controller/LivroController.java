package com.exemplo.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.backend.dto.LivroDTO;
import com.exemplo.backend.dto.LivroResponseDTO;
import com.exemplo.backend.entity.Autor;
import com.exemplo.backend.entity.Genero;
import com.exemplo.backend.entity.Livro;
import com.exemplo.backend.service.AutorService;
import com.exemplo.backend.service.GeneroService;
import com.exemplo.backend.service.LivroService;

@RestController
@RequestMapping("/api/v1/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public List<LivroResponseDTO> listar() {
        return livroService.listarTodos().stream().map(livro -> {
            LivroResponseDTO dto = new LivroResponseDTO();
            dto.id = livro.getId();
            dto.titulo = livro.getTitulo();
            dto.autor = livro.getAutor().getNome();
            dto.genero = livro.getGenero().getNome();
            return dto;
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        livroService.deletar(id);
    }

    @PutMapping("/{id}")
    public LivroResponseDTO atualizar(@PathVariable Long id, @RequestBody LivroDTO dto) {
        Livro livro = livroService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        livro.setTitulo(dto.titulo);

        Autor autor = autorService.buscarPorId(dto.autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

       Genero genero = generoService.buscarPorId(dto.generoId)
            .orElseThrow(() -> new RuntimeException("Gênero não encontrado"));



        livro.setAutor(autor);
        livro.setGenero(genero);

        Livro atualizado = livroService.salvar(livro);

        LivroResponseDTO response = new LivroResponseDTO();
        response.id = atualizado.getId();
        response.titulo = atualizado.getTitulo();
        response.autor = atualizado.getAutor().getNome();
        response.genero = atualizado.getGenero().getNome();
        response.autorId = atualizado.getAutor().getId();
        response.generoId = atualizado.getGenero().getId();
        return response;
    }
// GET por ID - retorna DTO (ou 404)

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(livro -> {
                    LivroResponseDTO response = new LivroResponseDTO();
                    response.id = livro.getId();
                    response.titulo = livro.getTitulo();
                    response.autor = livro.getAutor() != null ? livro.getAutor().getNome() : null;
                    response.genero = livro.getGenero() != null ? livro.getGenero().getNome() : null;
                    response.autorId = livro.getAutor() != null ? livro.getAutor().getId() : null;
                    response.generoId = livro.getGenero() != null ? livro.getGenero().getId() : null;
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> criar(@RequestBody LivroDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.titulo);

        Optional<Autor> autor = autorService.buscarPorId(dto.autorId);
        Optional<Genero> genero = generoService.buscarPorId(dto.generoId);

        autor.ifPresent(livro::setAutor);
        genero.ifPresent(livro::setGenero);

        Livro livroSalvo = livroService.salvar(livro);

        LivroResponseDTO response = new LivroResponseDTO();
        response.id = livroSalvo.getId();
        response.titulo = livroSalvo.getTitulo();
        response.autor = livroSalvo.getAutor() != null ? livroSalvo.getAutor().getNome() : null;
        response.genero = livroSalvo.getGenero() != null ? livroSalvo.getGenero().getNome() : null;
        response.autorId = livroSalvo.getAutor() != null ? livroSalvo.getAutor().getId() : null;
        response.generoId = livroSalvo.getGenero() != null ? livroSalvo.getGenero().getId() : null;

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
