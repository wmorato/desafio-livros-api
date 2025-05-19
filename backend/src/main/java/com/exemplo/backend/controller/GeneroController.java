package com.exemplo.backend.controller;

import com.exemplo.backend.entity.Genero;
import com.exemplo.backend.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public List<Genero> listar() {
        return generoService.listarTodos();
    }

    @PostMapping
    public Genero criar(@RequestBody Genero genero) {
        return generoService.salvar(genero);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        generoService.deletar(id);
    }
}
