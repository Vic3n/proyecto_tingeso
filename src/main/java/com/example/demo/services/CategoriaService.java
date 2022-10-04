package com.example.demo.services;

import com.example.demo.entities.CategoriaEntity;
import com.example.demo.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public List<CategoriaEntity> getAllCategorias(){
        List<CategoriaEntity> categorias = categoriaRepository.findAll();
        return categorias;
    }


}
