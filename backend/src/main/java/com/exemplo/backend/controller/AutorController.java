// src/main/java/com/exemplo/backend/controller/AutorController.java
package com.exemplo.backend.controller;

import java.util.List; // <-- Adicione esta importação
import java.util.Optional;
import java.util.stream.Collectors; // <-- Adicione esta importação

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
import org.springframework.web.bind.annotation.RestController; // Necessário para listarTodos, se converter para DTO

import com.exemplo.backend.dto.AutorDTO;
import com.exemplo.backend.dto.AutorResponseDTO;
import com.exemplo.backend.entity.Autor;
import com.exemplo.backend.service.AutorService;

@RestController
@RequestMapping("/api/v1/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponseDTO> criar(@RequestBody AutorDTO dto) { // <-- Recebe AutorDTO
        Autor autor = new Autor();
        autor.setNome(dto.getNome()); // Usa o getter do DTO

        Autor autorSalvo = autorService.salvar(autor);

        AutorResponseDTO response = new AutorResponseDTO();
        response.setId(autorSalvo.getId());
        response.setNome(autorSalvo.getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> buscarPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id)
                .map(autor -> {
                    AutorResponseDTO response = new AutorResponseDTO();
                    response.setId(autor.getId());
                    response.setNome(autor.getNome());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build()); // Importante para retornar 404
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listarTodos() {
        List<AutorResponseDTO> autoresDTO = autorService.listarTodos().stream()
                .map(autor -> {
                    AutorResponseDTO dto = new AutorResponseDTO();
                    dto.setId(autor.getId());
                    dto.setNome(autor.getNome());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(autoresDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> atualizarAutor(
            @PathVariable Long id,
            @RequestBody AutorDTO autorDTO) {
        Autor autorAtualizado = autorService.atualizar(id, autorDTO);
        if (autorAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        AutorResponseDTO dto = new AutorResponseDTO();
        dto.setId(autorAtualizado.getId());
        dto.setNome(autorAtualizado.getNome());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Void> deletarAutor(@PathVariable Long id) {
    boolean removido = autorService.deletar(id);
    if (removido) {
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}


}
