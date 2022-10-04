package com.example.demo.controllers;

import com.example.demo.entities.AutorizacionesEntity;
import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.services.AutorizacionService;
import com.example.demo.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class AutorizacionController {

    @Autowired
    AutorizacionService autorizacionService;
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/listar/autorizaciones")
    public String listar(Model model){
        List<AutorizacionesEntity> autorizaciones = autorizacionService.getAllAutorizaciones();
        model.addAttribute("autorizaciones",autorizaciones);
        return "autorizacionList";
    }

    @GetMapping("/crear/autorizacion")
    public ModelAndView createView(){
        ModelAndView mav = new ModelAndView("autorizacionCreate");
        List<EmpleadoEntity> listEmpleados = empleadoService.getAllEmpleados();
        mav.addObject("autorizacion", new AutorizacionesEntity());
        mav.addObject("empleados",listEmpleados);
        return mav;
    }

    @PostMapping("/creado/autorizacion")
    public String create(@ModelAttribute(name="autorizacion") AutorizacionesEntity aE, RedirectAttributes msj){
        autorizacionService.add(aE);
        msj.addFlashAttribute("mensaje","Autorizacion subido con éxito!!!");
        return "redirect:/crear/autorizacion";
    }
}
