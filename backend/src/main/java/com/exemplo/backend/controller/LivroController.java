package com.exemplo.backend.controller;

import com.exemplo.backend.dto.LivroDTO;
import com.exemplo.backend.dto.LivroResponseDTO;
import com.exemplo.backend.entity.Autor;
import com.exemplo.backend.entity.Genero;
import com.exemplo.backend.entity.Livro;
import com.exemplo.backend.service.AutorService;
import com.exemplo.backend.service.GeneroService;
import com.exemplo.backend.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
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

    @PostMapping
    public Livro criar(@RequestBody LivroDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.titulo);

        Optional<Autor> autor = autorService.buscarPorId(dto.autorId);
        Optional<Genero> genero = generoService.buscarPorId(dto.generoId);

        autor.ifPresent(livro::setAutor);
        genero.ifPresent(livro::setGenero);

        return livroService.salvar(livro);
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

}
