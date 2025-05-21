// src/main/java/com/exemplo/backend/dto/AutorDTO.java
package com.exemplo.backend.dto;

// Importe as anotações de validação se você as estiver usando (ex: @NotBlank)
// import jakarta.validation.constraints.NotBlank;
// Importe Lombok se estiver usando (ex: @Getter, @Setter)
// import lombok.Getter;
// import lombok.Setter;

// Se estiver usando Lombok
// @Getter
// @Setter
public class AutorDTO {
    // @NotBlank(message = "O nome do autor é obrigatório") // Exemplo de validação
    public String nome;

    // Construtor padrão (necessário para Jackson)
    public AutorDTO() {}

    // Construtor para facilitar testes (opcional)
    public AutorDTO(String nome) {
        this.nome = nome;
    }

    // Getters e Setters (se não estiver usando Lombok)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}