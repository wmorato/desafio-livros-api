// src/main/java/com/exemplo/backend/repository/GeneroRepository.java
package com.exemplo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemplo.backend.entity.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
}