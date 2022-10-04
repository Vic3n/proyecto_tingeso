package com.example.demo.services;


import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;
    public List<EmpleadoEntity> getAllEmpleados(){
        List<EmpleadoEntity> empleados = empleadoRepository.findAll();
        return empleados;
    }
}
