package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name ="autorizaciones", schema="db")
@NoArgsConstructor
@AllArgsConstructor
public class AutorizacionesEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String ano;
    @Column(nullable = false, length = 10)
    private String dia;

    @Column(nullable = false, length = 10)
    private String mes;

    @ManyToOne
    @JoinColumn(name = "empleado")
    private EmpleadoEntity empleado;

    @Column(nullable = false)
    private Integer tiempo;

    public AutorizacionesEntity(String ano, String dia, String mes, EmpleadoEntity empleado, Integer tiempo) {
        this.ano = ano;
        this.dia = dia;
        this.mes = mes;
        this.empleado = empleado;
        this.tiempo = tiempo;
    }
}
