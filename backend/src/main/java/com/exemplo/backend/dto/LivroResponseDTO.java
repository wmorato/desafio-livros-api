package com.exemplo.backend.dto;

public class LivroResponseDTO {
    public Long id;
    public String titulo;
    public String autor;
    public String genero;
    public Long autorId;
    public Long generoId;

    // Add getters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getGenero() { return genero; }
    public Long getAutorId() { return autorId; }
    public Long getGeneroId() { return generoId; }
}