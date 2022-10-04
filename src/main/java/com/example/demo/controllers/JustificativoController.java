package com.example.demo.controllers;

import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.entities.JustificativoEntity;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.services.EmpleadoService;
import com.example.demo.services.JustificativoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class JustificativoController {

    @Autowired
    private JustificativoUploadService justificativoUploadService;
    @Autowired
    public EmpleadoService empleadoService;

    @GetMapping("/listar/justificativos")
    public String listar(Model model) {
        List<JustificativoEntity> justificativos = justificativoUploadService.getAllJustificativos();
        model.addAttribute("justificativos", justificativos);
        return "justificativosList";
    }

    @GetMapping("/upload/justificativo")
    private String home() {
        return "justificativoUploadView";
    }

    @PostMapping("/cargar/justificativo")
    public String carga(@RequestParam("justificativos") MultipartFile file, RedirectAttributes msj) {
        justificativoUploadService.save(file);
        msj.addFlashAttribute("mensaje", "Justificativo cargado con éxito!!!");
        return "redirect:/upload/justificativo";
    }

    @GetMapping("/crear/justificativo")
    public ModelAndView createView(){
        ModelAndView mav = new ModelAndView("justificativoCreate");
        List<EmpleadoEntity> listEmpleados = empleadoService.getAllEmpleados();
        mav.addObject("justificativo", new JustificativoEntity());
        mav.addObject("empleados",listEmpleados);
        return mav;
    }

    @PostMapping("/creado/justificativo")
    public String create(@ModelAttribute(name="justificativo") JustificativoEntity jE, RedirectAttributes msj){
        justificativoUploadService.add(jE);
        msj.addFlashAttribute("mensaje","Justificativo subido con éxito!!!");
        return "redirect:/crear/justificativo";
    }


    @GetMapping("/eliminar/justificativos")
    public String delete(){
        justificativoUploadService.delete();
        return "redirect:/listar/justificativos";
    }
}
