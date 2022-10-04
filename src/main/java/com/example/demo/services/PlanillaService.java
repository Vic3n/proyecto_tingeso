package com.example.demo.services;

import com.example.demo.entities.*;
import com.example.demo.repositories.AutorizacionRepository;
import com.example.demo.repositories.IngresoRepository;
import com.example.demo.repositories.JustificativoRepository;
import com.example.demo.repositories.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanillaService {
    @Autowired
    PlanillaRepository planillaRepository;
    @Autowired
    AutorizacionRepository autorizacionRepository;
    @Autowired
    IngresoRepository ingresoRepository;

    @Autowired
    JustificativoRepository justificativoRepository;

    final String YEAR_WORKING_ON = "2022";
    final double PORCENTAJE_COTIZACION_PREVISIONAL = 0.1;
    final double PORCENTAJE_COTIZACION_PLAN_SALUD = 0.08;
    final Integer HORA_INGRESO = 8;

    public List<PlanillaEntity> getAllPlanillas(){
        List<PlanillaEntity> planillas = planillaRepository.findAll();
        return planillas;
    }

    public void delete(){
        planillaRepository.deleteAll();
    }


    public String calculoDePlanilla(String mes, List<EmpleadoEntity> empleados){
        int cantEmpleados = empleados.size();
        System.out.println("||"+cantEmpleados+"||");
        String[] diasTrabajados = new String[31];
        List<IngresoEntity> listIngresosMes = ingresoRepository.findByMes(mes);
        diasTrabajados = diasDeServicio(diasTrabajados,listIngresosMes);
        for(int i = 0; i<cantEmpleados; i++){
            //RUT
            EmpleadoEntity empleado =empleados.get(i);
            String rut_empleado = empleado.getRut();
            //NOMBRE
            String nombre_empleado = empleado.getApellidos() + " " +empleado.getNombres();
            //CATEGORIA
            String categoria = empleado.getCategoria().getId_categoria();
            String categoria_empleado= categoria;
            //TIEMPO EN EL SERVICIO
            Integer tiempo_servicio = calcularTiempoServicio(empleados.get(i).getFecha_ingreso());
            //SUELDO FIJO MENSUAL
            Integer sueldo_fijo_mensual = empleado.getCategoria().getSueldo_mensual();
            //MONTO BONIFICACION AÑOS DE SERVICIO
            double bonificacion_anos_servicio = calcularBonificacionTiempoServicio(tiempo_servicio,sueldo_fijo_mensual);
            //MONTO PAGO POR HORAS EXTRA {falta}
            ArrayList<AutorizacionesEntity> listAutorizacionesMes = autorizacionRepository.findByMes(mes);
            double pago_por_horas_extra = calcularBonifacionHorasExtra(empleado,mes);
            //MONTO DESCUeNTOS
            double monto_descuento = calcularMontoDescuento(empleado,diasTrabajados,mes);
            //SUELDO BRUTO
            double sueldo_bruto = empleado.getCategoria().getSueldo_mensual() + bonificacion_anos_servicio +
                    pago_por_horas_extra - monto_descuento;
            //COTIZACION PREVISIONAL
            double cotizacion_previsional = sueldo_bruto * PORCENTAJE_COTIZACION_PREVISIONAL;
            //COTIZACION DE SALUD
            double cotizacion_plan_salud = sueldo_bruto * PORCENTAJE_COTIZACION_PLAN_SALUD;
            //MONTO SUELDO FINAL
            double monto_sueldo_final = sueldo_bruto - (cotizacion_plan_salud + cotizacion_previsional);
            PlanillaEntity planillaEmpleado = new PlanillaEntity(rut_empleado, nombre_empleado,
                    categoria_empleado,tiempo_servicio,sueldo_fijo_mensual,bonificacion_anos_servicio,
                    pago_por_horas_extra,monto_descuento,sueldo_bruto,cotizacion_previsional,
                    cotizacion_plan_salud,monto_sueldo_final);
            planillaRepository.saveAndFlush(planillaEmpleado);
        }
        return "Calculo hecho de forma correcta.";
    }

    /***
     * Función para marcar los días que se asistio a trabajar en un mes según los ingresos en el array "servicio".
     * @param servicio: lista que corresponde a la maxima cantidad de dias por mes, en ella se marcará si se trabajó o no.
     * @param listIngresosMes: Todos los ingresos de un mes.
     * @return servicios marcado.
     */
    public String[] diasDeServicio(String[] servicio,List<IngresoEntity> listIngresosMes){
        Integer cantAutorizacionesMes = listIngresosMes.size();
        Integer ite = 0;
        Integer value = 1;
        for(int i = 0; i< cantAutorizacionesMes ; i++) {
            if (Integer.valueOf(listIngresosMes.get(i).getDia()).equals(value)) { // por ejemplo:
                servicio[ite] = "Si";                value++;                ite++;
            } else if (Integer.valueOf(listIngresosMes.get(i).getDia()) > value) {
                servicio[ite] = "No";
                value++;                ite++;                i--;
            }
        }
        while(ite < 31){
            servicio[ite] = "No";  ite++;
        }
        return servicio;
    }

    /***
     * Función para calcular el tiempo que lleva el empleado trabajando.
     * @param f_ingreso: Fecha ingreso del empleado a la empresa
     * @return Tiempo de servicio
     */
    public Integer calcularTiempoServicio(String f_ingreso){
        String[] f_ingresoSplit = f_ingreso.split("/");
        String s_f_ingreso_ano = f_ingresoSplit[0];
        Integer i_f_ingreso_ano = Integer.parseInt(s_f_ingreso_ano);
        Integer i_year_working_on = Integer.parseInt(YEAR_WORKING_ON);
        Integer result = i_year_working_on - i_f_ingreso_ano;
        return result;
    }

    /***
     * Calculo del monto de bonificación por años de un empleado.
     * @param tiempo_servicio: Años de trabajo del empleado
     * @param sueldo_fijo_mensual: Monto sueldo fijo del empleado
     * @return Monto de bonificación
     */
    public double calcularBonificacionTiempoServicio(Integer tiempo_servicio, Integer sueldo_fijo_mensual) {
        double monto = 0;
        if(tiempo_servicio < 5){ // < 5 años son 0%
            monto = 0;
        }else if(tiempo_servicio >= 5 && tiempo_servicio < 10){ // >= 5 años son 5%
            monto = sueldo_fijo_mensual*0.05;
        }else if(tiempo_servicio >= 10 && tiempo_servicio < 15){ // >= 10 años son 8%
            monto = sueldo_fijo_mensual*0.08;
        }else if(tiempo_servicio >= 15 && tiempo_servicio < 20){ // >= 15 años son 11%
            monto = sueldo_fijo_mensual*0.11;
        }else if(tiempo_servicio >= 20 && tiempo_servicio < 25){ // >= 20 años son 14%
            monto = sueldo_fijo_mensual*0.14;
        }else if(tiempo_servicio >= 25){                         // >= 25 años son 17%
            monto = sueldo_fijo_mensual*0.17;
        }
        return monto;
    }


    /***
     * Calculo del monto a beneficio del empleado por la cantidad de horas extras trabajadas autorizadas.
     * @param empleado: Empleado a trabajar.
     * @return monto correspondiente al pago por trabajar horas extras.
     */
    public double calcularBonifacionHorasExtra(EmpleadoEntity empleado,String mes){
        double monto = 0;
        double minutos_acumulados = 0;
        double horas_acumuladas=0;
        ArrayList<AutorizacionesEntity> listAutorizacionesMes = autorizacionRepository.findByMes(mes);
        //acumular horas extra según el mes por empleado:
        Integer totalAutorizaciones = listAutorizacionesMes.size();
        for(int i = 0; i<totalAutorizaciones;i++){
            if(listAutorizacionesMes.get(i).getEmpleado().equals(empleado)){
                minutos_acumulados += listAutorizacionesMes.get(i).getTiempo();
            }
        }
        //calculo cantidad de horas y monto según categoría
        horas_acumuladas = minutos_acumulados / 60;
        monto = horas_acumuladas * empleado.getCategoria().getMonto_hora_extra();
        return monto;
    }

    /***
     * Calculo del descuento para un empleado que no asistio ni justificó su falla.
     * @param empleado: Empleado en cuestión
     * @param servicio: Lista de días trabajados.
     * @param mes: Mes en el cual se quiere obtener datos.
     * @return Monto que se debe descontar al trabajador por no asistir y no justificar.
     */
    public double calcularMontoDescuento(EmpleadoEntity empleado, String[] servicio, String mes){
        double monto=0;
        String dia = "";
        for(int i = 0 ; i < 31 ; i++){  // 31 máxima cant de días en un mes.
            if(servicio[i].equals("Si")){ //es decir, se trabajó {i = 0}
                double descto = 0;
                boolean llegada = true;
                //buscar ingreso.
                if(i+1 < 10){
                    dia = "0"+String.valueOf(i+1);
                }else{
                    dia = String.valueOf(i+1);
                }
                List<JustificativoEntity> listJustificativoMDE = justificativoRepository.findByMesDiaEmpleado(mes,dia,empleado);
                List<IngresoEntity> ingresosMDE =  ingresoRepository.findByMesDiaEmpleado(mes,dia,empleado);
                if(ingresosMDE.isEmpty()){ // no fue a trabajar
                    if(listJustificativoMDE.isEmpty()) { // no tiene justificativo
                        descto = empleado.getCategoria().getSueldo_mensual() * 0.15;
                        monto = monto + descto;
                    }
                }
                else{ // si asistio a trabajar
                    for(IngresoEntity ingreso : ingresosMDE){
                        String[] horaSplit = ingreso.getHora().split(":");
                        Integer hora = Integer.valueOf(horaSplit[0]);
                        Integer minutos = Integer.valueOf(horaSplit[1]);
                        if(hora < HORA_INGRESO && llegada == true){
                        }else if(hora == HORA_INGRESO && llegada == true){
                            if(minutos <= 10){
                            }else if(minutos > 10 && minutos <=25 && llegada == true){
                                descto = empleado.getCategoria().getSueldo_mensual() * 0.01;
                                monto = descto + monto;

                                llegada = false;
                            }else if(minutos > 25 && minutos <= 45 && llegada == true){
                                descto = empleado.getCategoria().getSueldo_mensual() * 0.03;
                                monto = descto + monto;
                                llegada = false;
                            }else if(minutos > 45 && minutos < 60 && llegada == true){
                                descto = empleado.getCategoria().getSueldo_mensual() * 0.06;
                                monto = descto + monto;
                                llegada = false;
                            }
                        }else if(hora == HORA_INGRESO+1 && minutos <= 10 && llegada == true){
                            descto = empleado.getCategoria().getSueldo_mensual() * 0.06;
                            monto = descto + monto;
                            llegada = false;
                        }else if(hora >= HORA_INGRESO+1 && minutos > 10 && llegada == true){// debe presentar justificativo
                            if(listJustificativoMDE.isEmpty()){ // no tiene justificativo
                                descto = empleado.getCategoria().getSueldo_mensual() * 0.15;
                                monto = descto + monto;
                                llegada = false;
                            }
                        }
                    }
                }
            }
        }
        return monto;
    }

}
