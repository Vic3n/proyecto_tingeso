package com.example.demo.services;

import com.example.demo.entities.AutorizacionesEntity;
import com.example.demo.entities.CategoriaEntity;
import com.example.demo.entities.JustificativoEntity;
import com.example.demo.repositories.AutorizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorizacionService {

    @Autowired
    AutorizacionRepository autorizacionRepository;

    public List<AutorizacionesEntity> getAllAutorizaciones(){
        List<AutorizacionesEntity> autorizaciones = autorizacionRepository.findAll();
        return autorizaciones;
    }

    public AutorizacionesEntity add(AutorizacionesEntity aE) {
        return  autorizacionRepository.save(aE);
    }
}
