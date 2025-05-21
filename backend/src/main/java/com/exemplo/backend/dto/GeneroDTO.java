package com.exemplo.backend.dto;

public class GeneroDTO {
    private Long id;
    private String nome;

    // Construtor padrão
    public GeneroDTO() {}

    // Construtor completo
    public GeneroDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
