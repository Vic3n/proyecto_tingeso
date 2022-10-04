package com.example.demo.services;

import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.entities.IngresoEntity;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.IngresoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class UploadService {
    @Autowired
    IngresoRepository ingresoRepository;
    @Autowired
    EmpleadoRepository empleadoRepository;

    private final Logger logg = LoggerFactory.getLogger(UploadService.class);

    public List<IngresoEntity> getAllIngresos(){
        List<IngresoEntity> ingresos = ingresoRepository.findAll();
        return ingresos;
    }

    public String add(MultipartFile file){
        if(!file.isEmpty()){
            byte[] bytes = new byte[0];
            try {
                bytes = file.getBytes();
                String text = new String(bytes);
                String[] dataSplitLn = text.split("\n");
                for (String actualDataSplitLn : dataSplitLn){
                    //El sistema requiere el formato: fecha;hora;rut_empleado
                    String[] dataSplitSystem = actualDataSplitLn.split(";");
                    String[] fecha = dataSplitSystem[0].split("/");
                    String ano = fecha[0].strip();
                    String mes = fecha[1].strip();
                    String dia = fecha[2].strip();
                    String hora = dataSplitSystem[1].strip();
                    String rut = dataSplitSystem[2].strip();
                    EmpleadoEntity empleado = empleadoRepository.findByRut(rut);
                    if(empleado != null) {
                        IngresoEntity ingreso = new IngresoEntity(ano,mes,dia,hora,empleado);
                        ingresoRepository.save(ingreso);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "Archivo guardado Correctamente";
    }
}
