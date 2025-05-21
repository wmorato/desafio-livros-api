package com.exemplo.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.backend.dto.GeneroDTO;
import com.exemplo.backend.dto.GeneroResponseDTO;
import com.exemplo.backend.entity.Genero;
import com.exemplo.backend.service.GeneroService;

@RestController
@RequestMapping("/api/v1/generos")
public class GeneroController {

    private final GeneroService generoService;

    @Autowired
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    // Listar todos - Liberado para usuários autenticados
    @GetMapping
    public List<GeneroResponseDTO> listarTodos() {
        List<Genero> generos = generoService.listarTodos();
        return generos.stream()
                .map(g -> new GeneroResponseDTO(g.getId(), g.getNome()))
                .collect(Collectors.toList());
    }

    // Buscar por ID - Liberado para usuários autenticados
    @GetMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<Genero> generoOpt = generoService.buscarPorId(id);
        return generoOpt
                .map(g -> ResponseEntity.ok(new GeneroResponseDTO(g.getId(), g.getNome())))
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo gênero - Apenas ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GeneroResponseDTO> criar(@RequestBody GeneroDTO generoDTO) {
        Genero genero = new Genero(generoDTO.getNome());
        Genero salvo = generoService.salvar(genero);
        return ResponseEntity.ok(new GeneroResponseDTO(salvo.getId(), salvo.getNome()));
    }

    // Atualizar gênero - Apenas ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> atualizar(@PathVariable Long id, @RequestBody GeneroDTO generoDTO) {
        Optional<Genero> generoOpt = generoService.buscarPorId(id);
        if (generoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Genero genero = generoOpt.get();
        genero.setNome(generoDTO.getNome());
        Genero atualizado = generoService.salvar(genero);
        return ResponseEntity.ok(new GeneroResponseDTO(atualizado.getId(), atualizado.getNome()));
    }

    // Deletar gênero - Apenas ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Genero> generoOpt = generoService.buscarPorId(id);
        if (generoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        generoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
