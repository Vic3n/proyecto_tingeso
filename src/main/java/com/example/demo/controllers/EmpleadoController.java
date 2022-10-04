package com.example.demo.controllers;

import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/listar/empleados")
    public String listar(Model model){
        List<EmpleadoEntity> empleados = empleadoService.getAllEmpleados();
        model.addAttribute("empleados",empleados);
        return "empleadosList";
    }
}
