package com.exemplo.backend.dto;

public class LivroDTO {
    public String titulo;
    public Long autorId;
    public Long generoId;

    // Add getters
    public String getTitulo() { return titulo; }
    public Long getAutorId() { return autorId; }
    public Long getGeneroId() { return generoId; }
}