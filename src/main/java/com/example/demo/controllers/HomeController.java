package com.example.demo.controllers;

import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.entities.IngresoEntity;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/listar/ingresos")
    public String listar(Model model){
        List<IngresoEntity> ingresos = uploadService.getAllIngresos();
        model.addAttribute("ingresos",ingresos);
        return "ingresosList";
    }

    @GetMapping("/upload")
    public String upload(){
        return "uploadView";
    }


    @PostMapping("/cargar")
    public String carga(@RequestParam("archivos") MultipartFile file, RedirectAttributes msj){

        List<EmpleadoEntity> empleados =empleadoRepository.findAll();
        uploadService.add(file);
        msj.addFlashAttribute("mensaje","RELOJ cargado con éxito!!!");
        return "redirect:/upload";
    }
}
