package com.example.demo.controllers;

import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.entities.IngresoEntity;
import com.example.demo.entities.JustificativoEntity;
import com.example.demo.entities.PlanillaEntity;
import com.example.demo.services.EmpleadoService;
import com.example.demo.services.PlanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class PlanillaController {

    @Autowired
    PlanillaService planillaService;

    @Autowired
    EmpleadoService empleadoService;

    @Autowired

    @GetMapping("/calcular/planilla")
    public ModelAndView createView(){
        ModelAndView mav = new ModelAndView("planillaList");
        mav.addObject("planillas",planillaService.getAllPlanillas());
        mav.addObject("mes_planilla", new String());
        return mav;
    }

    @PostMapping("/calculado/planilla")
    public String create(@ModelAttribute(name="mes_planilla") String mes, RedirectAttributes msj){
        List<EmpleadoEntity> empleados = empleadoService.getAllEmpleados();
        planillaService.calculoDePlanilla(mes,empleados);
        msj.addFlashAttribute("mensaje","Planilla cálculada con éxito!!");
        return "redirect:/calcular/planilla";
    }

    @GetMapping("/eliminar/planilla")
    public String delete(){
        planillaService.delete();
        return "redirect:/calcular/planilla";
    }
}
