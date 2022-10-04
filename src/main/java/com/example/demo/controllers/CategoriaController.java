package com.example.demo.controllers;

import com.example.demo.entities.CategoriaEntity;
import com.example.demo.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/listar/categorias")
    public String listar(Model model){
        List<CategoriaEntity> categorias = categoriaService.getAllCategorias();
        model.addAttribute("categorias",categorias);
        return "categoriasList";
    }

}
