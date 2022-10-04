package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import com.example.demo.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PlanillaTest {
    @Autowired
    IngresoRepository ingresoRepository;
    @Autowired
    JustificativoRepository justificativoRepository;
    @Autowired
    AutorizacionRepository autorizacionRepository;
    @Autowired
    EmpleadoRepository empleadoRepository;
    @Autowired
    PlanillaService planillaService;
    @Autowired
    UploadService ingresoService;
    @Autowired
    AutorizacionService autorizacionService;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    JustificativoUploadService justificativoService;
    EmpleadoEntity empleado = new EmpleadoEntity();
    CategoriaEntity categoria = new CategoriaEntity();

    /***
     * TIEMPO DE SERVICIO
     */
    @Test
    void calcularTiempoServicioDiez() {
        String f_ingreso = "2012/12/23";
        Integer anosServicio = planillaService.calcularTiempoServicio(f_ingreso);
        assertEquals(10, anosServicio, 0.0);
    }

    @Test
    void calcularTiempoServicioCero() {
        String f_ingreso = "2022/12/23";
        Integer anosServicio = planillaService.calcularTiempoServicio(f_ingreso);
        assertEquals(0, anosServicio, 0.0);
    }

    @Test
    void calcularTiempoServicioMuyInferior() {
        String f_ingreso = "1922/12/23";
        Integer anosServicio = planillaService.calcularTiempoServicio(f_ingreso);
        assertEquals(100, anosServicio, 0.0);
    }

    /***
     * BONIFICACION POR AÑOS DE SERVICIO
     */
    @Test
    void calcularBonificaciontiempoServicioCero() {
        Integer tiempo_servicio = 0;
        Integer sueldo_fijo_mensual = 1700000;
        double bonificacion = planillaService.calcularBonificacionTiempoServicio(tiempo_servicio, sueldo_fijo_mensual);
        assertEquals(0, bonificacion, 0.0);
    }

    @Test
    void calcularBonificaciontiempoServicioCinco() {
        Integer tiempo_servicio = 5;
        Integer sueldo_fijo_mensual = 1700000;
        double bonificacion = planillaService.calcularBonificacionTiempoServicio(tiempo_servicio, sueldo_fijo_mensual);
        assertEquals(85000, bonificacion, 0.0);
    }

    @Test
    void calcularBonificaciontiempoServicioDiez() {
        Integer tiempo_servicio = 10;
        Integer sueldo_fijo_mensual = 1700000;
        double bonificacion = planillaService.calcularBonificacionTiempoServicio(tiempo_servicio, sueldo_fijo_mensual);
        assertEquals(136000, bonificacion, 0.0);
    }

    @Test
    void calcularBonificaciontiempoServicioQuince() {
        Integer tiempo_servicio = 15;
        Integer sueldo_fijo_mensual = 1700000;
        double bonificacion = planillaService.calcularBonificacionTiempoServicio(tiempo_servicio, sueldo_fijo_mensual);
        assertEquals(187000, bonificacion, 0.0);
    }

    @Test
    void calcularBonificaciontiempoServicioVeinte() {
        Integer tiempo_servicio = 20;
        Integer sueldo_fijo_mensual = 1700000;
        double bonificacion = planillaService.calcularBonificacionTiempoServicio(tiempo_servicio, sueldo_fijo_mensual);
        assertEquals(238000.00000000003, bonificacion, 0.0);
    }

    @Test
    void calcularBonificaciontiempoServicioVienteCinco() {
        Integer tiempo_servicio = 25;
        Integer sueldo_fijo_mensual = 1700000;
        double bonificacion = planillaService.calcularBonificacionTiempoServicio(tiempo_servicio, sueldo_fijo_mensual);
        assertEquals(289000, bonificacion, 0.0);
    }

    /***
     * CALCULO BONIFACION POR HORAS EXTRAS.
     */
    @Test
    void calcularBonificacionHorasExtrasCeroHoras() {
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        double bonificacion = planillaService.calcularBonifacionHorasExtra(empleado,"09");
        assertEquals(0, bonificacion, 0.0);

    }

    @Test
    void calcularBonificacionHorasExtrasMenosDeUnaHoras() {
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        AutorizacionesEntity autorizacion = new AutorizacionesEntity("2022","01","01",empleado,30);
        autorizacionRepository.saveAndFlush(autorizacion);
        double bonificacion = planillaService.calcularBonifacionHorasExtra(empleado,"01");

        assertEquals(12500, bonificacion, 0.0);

    }

    @Test
    void calcularBonificacionHorasExtrasTresHorasVenteMinutos() {
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        AutorizacionesEntity autorizacion = new AutorizacionesEntity("2022","01","01",empleado,20);
        autorizacionRepository.saveAndFlush(autorizacion);
        AutorizacionesEntity autorizacion1 = new AutorizacionesEntity("2022","01","01",empleado,120);
        autorizacionRepository.saveAndFlush(autorizacion1);
        AutorizacionesEntity autorizacion2 = new AutorizacionesEntity("2022","01","01",empleado,60);
        autorizacionRepository.saveAndFlush(autorizacion2);
        double bonificacion = planillaService.calcularBonifacionHorasExtra(empleado,"01");
        assertEquals(83333.33333333334, bonificacion, 0.0);
    }

    @Test
    void calcularBonificacionHorasExtrasVeinteHoras() {
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        AutorizacionesEntity autorizacion = new AutorizacionesEntity("2022","01","01",empleado,60);
        autorizacionRepository.saveAndFlush(autorizacion);
        AutorizacionesEntity autorizacion1 = new AutorizacionesEntity("2022","04","01",empleado,120);
        autorizacionRepository.saveAndFlush(autorizacion1);
        AutorizacionesEntity autorizacion2 = new AutorizacionesEntity("2022","05","01",empleado,60);
        autorizacionRepository.saveAndFlush(autorizacion2);
        AutorizacionesEntity autorizacion3 = new AutorizacionesEntity("2022","12","01",empleado,60);
        autorizacionRepository.saveAndFlush(autorizacion3);
        AutorizacionesEntity autorizacion4 = new AutorizacionesEntity("2022","15","01",empleado,120);
        autorizacionRepository.saveAndFlush(autorizacion4);
        AutorizacionesEntity autorizacion5 = new AutorizacionesEntity("2022","23","01",empleado,60);
        autorizacionRepository.saveAndFlush(autorizacion5);
        AutorizacionesEntity autorizacion6 = new AutorizacionesEntity("2022","28","01",empleado,60);
        autorizacionRepository.saveAndFlush(autorizacion6);
        AutorizacionesEntity autorizacion7 = new AutorizacionesEntity("2022","28","01",empleado,60);
        autorizacionRepository.saveAndFlush(autorizacion7);
        AutorizacionesEntity autorizacion8 = new AutorizacionesEntity("2022","28","01",empleado,600);
        autorizacionRepository.saveAndFlush(autorizacion8);
        double bonificacion = planillaService.calcularBonifacionHorasExtra(empleado,"01");
        assertEquals(500000, bonificacion, 0.0);
    }

    /***
     * CALCULO DEL MONTO DE DESCUENTO POR INASISTENCIA
     */
    @Test
    void calcularMontoDescuentoLlegoTarde15() {
        //Solo se trabajó 1 día en el mes.
        String[] servicio = {
                "Si", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No"
        };

        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        IngresoEntity ingreso = new IngresoEntity("2022","01","01","08:15",empleado);
        ingresoRepository.saveAndFlush(ingreso);

        double monto = planillaService.calcularMontoDescuento(empleado, servicio, "01");
        assertEquals(17000, monto, 0.0);
    }

    @Test
    void calcularMontoDescuentoLlegoTarde30() {
        //Solo se trabajó 1 día en el mes.
        String[] servicio = {
                "Si", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No"
        };

        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        IngresoEntity ingreso = new IngresoEntity("2022","01","01","08:30",empleado);
        ingresoRepository.saveAndFlush(ingreso);

        double monto = planillaService.calcularMontoDescuento(empleado, servicio, "01");
        assertEquals(51000, monto, 0.0);
    }

    @Test
    void calcularMontoDescuentoLlegoTarde50() {
        //Solo se trabajó 1 día en el mes.
        String[] servicio = {
                "Si", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No"
        };
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        IngresoEntity ingreso = new IngresoEntity("2022","01","01","08:50",empleado);
        ingresoRepository.saveAndFlush(ingreso);

        double monto = planillaService.calcularMontoDescuento(empleado, servicio, "01");
        assertEquals(102000, monto, 0.0);
    }

    @Test
    void calcularMontoDescuentoLlegoTarde71SinJustificativo() {
        //Solo se trabajó 1 día en el mes.
        String[] servicio = {
                "Si", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No"
        };

        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        IngresoEntity ingreso = new IngresoEntity("2022","01","01","09:11",empleado);
        ingresoRepository.saveAndFlush(ingreso);

        double monto = planillaService.calcularMontoDescuento(empleado, servicio, "01");
        assertEquals(255000, monto, 0.0);
    }

    @Test
    void calcularMontoDescuentoLlegoTarde70ConJustificativo() {
        //Solo se trabajó 1 día en el mes.
        String[] servicio = {
                "Si", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No"
        };

        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        IngresoEntity ingreso = new IngresoEntity("2022","01","01","09:11",empleado);
        ingresoRepository.saveAndFlush(ingreso);

        JustificativoEntity justificativoEntity = new JustificativoEntity("2022", "01", "01", "1", "JUSTIFICATIVO 1", empleado);
        justificativoRepository.saveAndFlush(justificativoEntity);

        double monto = planillaService.calcularMontoDescuento(empleado, servicio, "01");
        assertEquals(0, monto, 0.0);
    }

    @Test
    void calcularMontoDescuentoNoAsisteConJustificativo() {
        //Solo se trabajó 1 día en el mes.
        String[] servicio = {
                "Si", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No"
        };

        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");


        JustificativoEntity justificativoEntity = new JustificativoEntity("2022", "01", "01", "1", "JUSTIFICATIVO 1", empleado);
        justificativoRepository.saveAndFlush(justificativoEntity);

        double monto = planillaService.calcularMontoDescuento(empleado, servicio, "01");
        assertEquals(0, monto, 0.0);
    }

    @Test
    void calcularMontoDescuentoNoAsisteSinJustificativo() {
        //Solo se trabajó 1 día en el mes.
        String[] servicio = {
                "Si", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No"
        };

        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        double monto = planillaService.calcularMontoDescuento(empleado, servicio, "01");
        assertEquals(255000, monto, 0.0);
    }

    /***
     * REPOSITORIOS
     */
    // EMPLEADOS
    @Test
    void encontrarEmpleadoPorRut() {
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");


        empleadoRepository.save(empleado);

        // when
        EmpleadoEntity empleado2 = empleadoRepository.findByRut(empleado.getRut());

        // then
        assertThat( empleado2.getRut())
                .isEqualTo(empleado.getRut());
    }

    //  AUTORIZACIONES
    @Test
    void encontrarAutorizacionPorMes(){
        empleado.setId(1);
        empleado.setApellidos("Vargas");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.100-1");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        AutorizacionesEntity autorizacion = new AutorizacionesEntity("2022","01","01",empleado,15);
        autorizacionRepository.saveAndFlush(autorizacion);
        //when
        ArrayList<AutorizacionesEntity> autorizacion2 = autorizacionRepository.findByMes(autorizacion.getMes());
        //then
        assertThat(autorizacion2.get(0).getEmpleado()).isEqualTo(autorizacion.getEmpleado());
    }

    @Test
    void encontrarAutorizacionTodas(){
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas Morales");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.293-1");
        empleado.setFecha_ingreso("2022/09/09");
        empleado.setFecha_nacimiento("1999/09/03");

        AutorizacionesEntity autorizacion = new AutorizacionesEntity("2022","01","01",empleado,15);
        autorizacionRepository.saveAndFlush(autorizacion);
        //when
        ArrayList<AutorizacionesEntity> autorizacion2 = autorizacionRepository.findAll();
        //then
        assertThat(autorizacion2.get(0).getEmpleado()).isEqualTo(autorizacion.getEmpleado());
    }

    // JUSTIFICATIVO
    @Test
    void encontrarJustificativoMesDiaEmpleado(){
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas Morales");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.293-1");
        empleado.setFecha_ingreso("2022/09/09");
        empleado.setFecha_nacimiento("1999/09/03");

        JustificativoEntity justificativo = new JustificativoEntity("2022","01","02","5","ASD",empleado);
        justificativoRepository.saveAndFlush(justificativo);

        List<JustificativoEntity> justificativo2 = justificativoRepository.findByMesDiaEmpleado("02","01",empleado);

        assertThat(justificativo2.get(0)).isNotNull();
    }

    // INGRESOS
    @Test
    void encontrarIngresoPorMes(){
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas Morales");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.293-1");
        empleado.setFecha_ingreso("2022/09/09");
        empleado.setFecha_nacimiento("1999/09/03");

        IngresoEntity ingreso = new IngresoEntity("2022","03","03","08:00",empleado);
        ingresoRepository.saveAndFlush(ingreso);

        List<IngresoEntity> ingreso2 = ingresoRepository.findByMes("03");

        assertThat(ingreso2.get(0).getMes()).isEqualTo("03");
    }

    @Test
    void encontrarIngresoMesDiaEmpleado() {
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas Morales");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.293-1");
        empleado.setFecha_ingreso("2022/09/09");
        empleado.setFecha_nacimiento("1999/09/03");

        IngresoEntity ingreso = new IngresoEntity("2022","05","03","08:00",empleado);
        ingresoRepository.saveAndFlush(ingreso);

        List<IngresoEntity> ingreso2 = ingresoRepository.findByMesDiaEmpleado("05","3",empleado);

        assertThat(ingreso2).isNotNull();
    }

    /***
     * SERVICIOS
     */
    // AUTORIZACIONES
    @Test
    void getAllAutorizaciones(){
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas Morales");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.293-1");
        empleado.setFecha_ingreso("2022/09/09");
        empleado.setFecha_nacimiento("1999/09/03");

        AutorizacionesEntity autorizacion = new AutorizacionesEntity("2022","01","01",empleado,15);
        autorizacionRepository.saveAndFlush(autorizacion);
        //when
        List<AutorizacionesEntity> autorizaciones = autorizacionService.getAllAutorizaciones();
        //then
        assertThat(autorizaciones.get(0).getEmpleado()).isEqualTo(autorizacion.getEmpleado());
    }

    @Test
    void addAutorizaciones(){
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Vargas Morales");
        empleado.setNombres("Vicente");
        empleado.setCategoria(categoria);
        empleado.setRut("20.144.293-1");
        empleado.setFecha_ingreso("2022/09/09");
        empleado.setFecha_nacimiento("1999/09/03");

        AutorizacionesEntity autorizacion = new AutorizacionesEntity("2022","04","05",empleado,15);
        autorizacionService.add(autorizacion);

        ArrayList<AutorizacionesEntity> autorizacion2 = autorizacionRepository.findByMes("05");

        assertThat(autorizacion2).isNotNull();
    }

    // CATEGORIAS
    @Test
    void getAllCategorias(){
        categoria.setId(123);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("Z");

        categoriaRepository.saveAndFlush(categoria);
        //when
        List<CategoriaEntity> categoria2 = categoriaService.getAllCategorias();
        //then
        boolean solution = false;
        for(int i = 0 ; i < categoria2.size() ; i++ ){
            if (categoria2.get(i).getId_categoria().equals("Z")){
                solution = true;
            }
        }
        assertThat(solution).isEqualTo(true);
    }

    // EMPLEADOS
    @Test
    void getAllEmpleados(){
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Romero Peres");
        empleado.setNombres("Juan");
        empleado.setCategoria(categoria);
        empleado.setRut("20.200.200-2");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        empleadoRepository.saveAndFlush(empleado);
        //when
        List<EmpleadoEntity> empleado2 = empleadoService.getAllEmpleados();
        //then
        boolean solution = false;
        for(int i = 0 ; i < empleado2.size() ; i++ ){
            if (empleado2.get(i).getRut().equals(empleado.getRut())) {
                solution = true;
            }
        }
        assertThat(solution).isEqualTo(true);
    }

    //JUSTIFICATIVOS
    @Test
    void getAllJustificativos(){
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Romero Peres");
        empleado.setNombres("Juan");
        empleado.setCategoria(categoria);
        empleado.setRut("20.200.200-2");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        JustificativoEntity justificativo = new JustificativoEntity("2022","02","02","5","ASD",empleado);
        //when
        justificativoRepository.saveAndFlush(justificativo);
        List<JustificativoEntity> justificativo2 = justificativoService.getAllJustificativos();
        //then
        boolean solution = false;
        if(justificativo2.contains(justificativo)){
            solution = true;
        }
        assertThat(solution).isEqualTo(true);
    }

    @Test
    void agregarJustificativo(){
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Romero Peres");
        empleado.setNombres("Juan");
        empleado.setCategoria(categoria);
        empleado.setRut("20.200.200-2");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        JustificativoEntity justificativo = new JustificativoEntity("2022","02","02","5","ASD",empleado);
        //when
        justificativoService.add(justificativo);
        List<JustificativoEntity> justificativo2 = justificativoService.getAllJustificativos();
        boolean solution = false;
        if(justificativo2.contains(justificativo)){
            solution = true;
        }
        assertThat(solution).isEqualTo(true);
    }

    // PLANILLA
    @Test
    void calculoDePlanilla(){
        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Romero Peres");
        empleado.setNombres("Juan");
        empleado.setCategoria(categoria);
        empleado.setRut("20.200.200-2");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        empleadoRepository.save(empleado);
        List<EmpleadoEntity> empleados = empleadoRepository.findAll();
        String result = planillaService.calculoDePlanilla("03",empleados);

        assertThat(result).isEqualTo("Calculo hecho de forma correcta.");
    }

    @Test
    void diasDeServicio() {
        String[] servicio = new String[31];
        String[] servicioResult = {
                "Si", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No", "No", "No", "No", "No",
                "No", "No", "No"
        };

        categoria.setId(1);
        categoria.setSueldo_mensual(1700000);
        categoria.setMonto_hora_extra(25000);
        categoria.setId_categoria("A");

        empleado.setId(1);
        empleado.setApellidos("Romero Peres");
        empleado.setNombres("Juan");
        empleado.setCategoria(categoria);
        empleado.setRut("20.200.200-2");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        IngresoEntity ingreso = new IngresoEntity("2022","07","01","08:00",empleado);
        ingresoRepository.save(ingreso);

        List<IngresoEntity> listIngreso = ingresoRepository.findByMes("07");

        servicio = planillaService.diasDeServicio(servicio,listIngreso);

        boolean solution = true;
        for(int i = 0 ; i < 31 ; i++){
            if(servicio[i] != servicioResult[i]){
                solution = false;
            }
        }
        assertThat(solution).isEqualTo(true);

    }

    // INGRESOS
    @Test
    void getAllIngresos(){
        CategoriaEntity categoria = new CategoriaEntity("Z",123456789,999);

        empleado.setId(1);
        empleado.setApellidos("Romero Peres");
        empleado.setNombres("Juan");
        empleado.setCategoria(categoria);
        empleado.setRut("20.200.200-2");
        empleado.setFecha_ingreso("2022/03/09");
        empleado.setFecha_nacimiento("2022/03/09");

        IngresoEntity ingreso = new IngresoEntity("2022","07","01","08:00",empleado);
        ingresoRepository.save(ingreso);

        List<IngresoEntity> ingreso2 = ingresoService.getAllIngresos();

        boolean solution = false;
        if(ingreso2.contains(ingreso)){ solution = true;}
        assertThat(solution).isEqualTo(true);
    }

}

