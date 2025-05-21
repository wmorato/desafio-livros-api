package com.exemplo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemplo.backend.entity.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
    // Você pode adicionar métodos de consulta customizados aqui, se necessário
    boolean existsByNome(String nome);
}
