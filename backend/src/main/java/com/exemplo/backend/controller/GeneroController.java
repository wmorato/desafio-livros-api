package com.exemplo.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.exemplo.backend.service.GeneroService;

@RestController
@RequestMapping("/api/v1/generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public ResponseEntity<List<GeneroDTO>> listarTodos() {
        List<GeneroDTO> generos = generoService.listarTodos();
        return ResponseEntity.ok(generos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> buscarPorId(@PathVariable Long id) {
        Optional<GeneroDTO> genero = generoService.buscarPorId(id);
        return genero.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GeneroDTO> criar(@RequestBody GeneroDTO dto) {
        GeneroDTO criado = generoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GeneroDTO> atualizar(@PathVariable Long id, @RequestBody GeneroDTO dto) {
        Optional<GeneroDTO> atualizado = generoService.atualizar(id, dto);
        return atualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!generoService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        generoService.deletar(id);
        return ResponseEntity.noContent().build(); // <--- Corrija aqui!
    }
}
