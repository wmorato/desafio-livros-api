package com.exemplo.backend.dto;

public class GeneroDTO {
    private Long id;
    private String nome;

    public GeneroDTO() {}

    public GeneroDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

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

    @Override
    public String toString() {
        return "GeneroDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    
}
