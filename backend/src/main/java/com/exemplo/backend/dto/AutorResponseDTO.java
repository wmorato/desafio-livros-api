// src/main/java/com/exemplo/backend/dto/AutorResponseDTO.java
package com.exemplo.backend.dto;

// Importe Lombok se estiver usando (ex: @Getter, @Setter)
// import lombok.Getter;
// import lombok.Setter;

// Se estiver usando Lombok
// @Getter
// @Setter
public class AutorResponseDTO {
    public Long id;
    public String nome;

    // Construtor padrão
    public AutorResponseDTO() {}

    // Construtor para facilitar testes (opcional)
    public AutorResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters (se não estiver usando Lombok)
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