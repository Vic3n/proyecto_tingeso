package com.example.demo.services;

import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.entities.JustificativoEntity;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.JustificativoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class JustificativoUploadService {

    @Autowired
    JustificativoRepository justificativoRepository;
    @Autowired
    EmpleadoRepository empleadoRepository;

    public List<JustificativoEntity> getAllJustificativos(){
        List<JustificativoEntity> justificativos = justificativoRepository.findAll();
        return justificativos;
    }

    private final Logger logg = LoggerFactory.getLogger(UploadService.class);

    public void delete(){
        justificativoRepository.deleteAll();
    }

    @Transactional
    public String save(MultipartFile file){
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
                    String duracion = dataSplitSystem[1].strip();
                    String rut = dataSplitSystem[2].strip();
                    String razon = dataSplitSystem[3];
                    EmpleadoEntity empleado = empleadoRepository.findByRut(rut);
                    if(empleado != null) {
                        JustificativoEntity justificativo = new JustificativoEntity(ano,dia,mes,duracion,razon,empleado);
                        add(justificativo);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "Archivo guardado Correctamente";
    }


    public JustificativoEntity add(JustificativoEntity jE){
        Integer duracionTotal = Integer.valueOf(jE.getDuracion());
        Integer duracion = duracionTotal - 1;
        Integer dia = Integer.valueOf(jE.getDia());
        String s_mes = jE.getMes();
        Integer mes = Integer.valueOf(s_mes);
        String s_dia= "";
        String s_ano = jE.getAno();
        Integer ano = Integer.valueOf(s_ano);
        for (int i = 1; i<duracionTotal;i++){
            if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){
                if(dia >= 31) {
                    dia = 1;
                    mes++;
                    if (mes > 12) {
                        ano++;
                        mes = 1;
                    }
                }else{
                    dia++;
                }
            }else if(mes == 4 || mes == 6 || mes == 9 || mes == 11){
                if(dia >= 30) {
                    dia = 1;
                    mes++;
                    if (mes > 12) {
                        ano++;
                        mes = 1;
                    }
                }else{
                    dia++;
                }
            }else if(mes == 2){
                if(dia >= 28) {
                    dia = 1;
                    mes++;
                    if (mes > 12) {
                        ano++;
                        mes = 1;
                    }
                }else{
                    dia++;
                }
            }
            if(dia < 10){
                s_dia = "0"+String.valueOf(dia);
            }else{
                s_dia = String.valueOf(dia);
            }
            if(mes < 10){
                s_mes= "0"+String.valueOf(mes);
            }else{
                s_mes = String.valueOf(mes);
            }
            s_ano = String.valueOf(ano);
            JustificativoEntity auxiliar = new JustificativoEntity(s_ano,s_dia, s_mes, String.valueOf(duracion), jE.getRazon(), jE.getEmpleado());
            justificativoRepository.save(auxiliar);
            duracion--;
        }
        if(Integer.valueOf(jE.getDia()) < 10){
            s_dia = "0"+jE.getDia();
            jE.setDia(s_dia);
        }
        return  justificativoRepository.save(jE);
    }

}
