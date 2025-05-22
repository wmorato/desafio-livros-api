package com.exemplo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.backend.dto.AutorDTO;
import com.exemplo.backend.entity.Autor;
import com.exemplo.backend.repository.AutorRepository;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public boolean deletar(Long id) {
    if (autorRepository.existsById(id)) {
        autorRepository.deleteById(id);
        return true;
    }
    return false;
}


    public Autor atualizar(Long id, AutorDTO autorDTO) {
        Optional<Autor> optionalAutor = autorRepository.findById(id);
        if (optionalAutor.isEmpty()) {
            return null;
        }
        Autor autor = optionalAutor.get();
        autor.setNome(autorDTO.getNome());
        return autorRepository.save(autor);
    }

    

}
