// src/main/java/com/exemplo/backend/service/GeneroService.java
package com.exemplo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.backend.dto.GeneroDTO;
import com.exemplo.backend.entity.Genero;
import com.exemplo.backend.repository.GeneroRepository;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public List<GeneroDTO> listarTodos() {
        List<Genero> generos = generoRepository.findAll();
        return generos.stream()
                .map(genero -> new GeneroDTO(genero.getId(), genero.getNome()))
                .collect(java.util.stream.Collectors.toList());
    }

    public Optional<GeneroDTO> buscarPorId(Long id) {
        return generoRepository.findById(id)
                .map(genero -> new GeneroDTO(genero.getId(), genero.getNome()));
    }

    public Genero salvar(Genero genero) {
        return generoRepository.save(genero);
    }

    public void deletar(Long id) {
        generoRepository.deleteById(id);
    }

    public GeneroDTO criar(GeneroDTO dto) {
        Genero genero = new Genero();
        genero.setNome(dto.getNome());
        Genero salvo = generoRepository.save(genero); // <-- só aqui!
        return new GeneroDTO(salvo.getId(), salvo.getNome());
    }

    public Optional<GeneroDTO> atualizar(Long id, GeneroDTO dto) {
        return generoRepository.findById(id)
                .map(genero -> {
                    genero.setNome(dto.getNome());
                    Genero atualizado = generoRepository.save(genero);
                    return new GeneroDTO(atualizado.getId(), atualizado.getNome());
                });
    }

    public boolean existe(Long id) {
        return generoRepository.existsById(id);
    }

    public Optional<Genero> buscarEntidadePorId(Long id) {
        return generoRepository.findById(id);
    }

}
