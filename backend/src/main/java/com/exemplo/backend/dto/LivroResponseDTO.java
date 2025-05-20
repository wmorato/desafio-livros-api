package com.exemplo.backend.dto;

public class LivroResponseDTO {
    public Long id;
    public String titulo;
    public String autor;
    public String genero;
    public Long autorId;    // <--- Adicione isso
    public Long generoId;   // <--- E isso
}
