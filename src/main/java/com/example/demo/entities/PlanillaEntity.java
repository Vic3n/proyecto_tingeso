package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "planilla",schema="db")
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;
    @Column(nullable = false, length = 25)
    private String rut_empleado;
    @Column(nullable = false, length = 80)
    private String nombre_empleado;
    @Column(nullable = false, length = 1)
    private String categoria_empleado;
    @Column(nullable = false)
    private Integer t_servicio_empleado;
    @Column(nullable = false)
    private Integer sueldo_fijo_mensual;
    @Column(nullable = false)
    private double monto_bonificacion_anos_servicio;
    @Column(nullable = false)
    private double monto_pago_horas_extra;
    @Column(nullable = false)
    private double monto_descuentos;
    @Column(nullable = false)
    private double sueldo_bruto;
    @Column(nullable = false)
    private double cotizacion_previsional;
    @Column(nullable = false)
    private double cotizacion_salud;
    @Column(nullable = false)
    private double monto_sueldo_final;


    public PlanillaEntity(String rut_empleado, String nombre_empleado, String categoria_empleado,
                          Integer t_servicio_empleado, Integer sueldo_fijo_mensual, double monto_bonificacion_anos_servicio,
                          double monto_pago_horas_extra, double monto_descuentos, double sueldo_bruto,
                          double cotizacion_previsional, double cotizacion_salud, double monto_sueldo_final) {
        this.rut_empleado = rut_empleado;
        this.nombre_empleado = nombre_empleado;
        this.categoria_empleado = categoria_empleado;
        this.t_servicio_empleado = t_servicio_empleado;
        this.sueldo_fijo_mensual = sueldo_fijo_mensual;
        this.monto_bonificacion_anos_servicio = monto_bonificacion_anos_servicio;
        this.monto_pago_horas_extra = monto_pago_horas_extra;
        this.monto_descuentos = monto_descuentos;
        this.sueldo_bruto = sueldo_bruto;
        this.cotizacion_previsional = cotizacion_previsional;
        this.cotizacion_salud = cotizacion_salud;
        this.monto_sueldo_final = monto_sueldo_final;
    }
}
