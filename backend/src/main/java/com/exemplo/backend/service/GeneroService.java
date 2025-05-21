package com.exemplo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.backend.entity.Genero;
import com.exemplo.backend.repository.GeneroRepository;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    @Autowired
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }

    public Optional<Genero> buscarPorId(Long id) {
        return generoRepository.findById(id);
    }

    public Genero salvar(Genero genero) {
        return generoRepository.save(genero);
    }

    public void deletar(Long id) {
        generoRepository.deleteById(id);
    }

    public boolean existePorNome(String nome) {
        return generoRepository.existsByNome(nome);
    }
}
