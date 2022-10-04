package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "justificativos",schema = "db")
@NoArgsConstructor
@AllArgsConstructor
public class JustificativoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String duracion;

    @Column(nullable = false, length = 10)
    private String ano;

    @Column(nullable = false, length = 10)
    private String dia;

    @Column(nullable = false, length = 10)
    private String mes;

    @Column(nullable = false, length = 200)
    private String razon;
    @ManyToOne
    @JoinColumn(name = "empleado")
    private EmpleadoEntity empleado;


    public JustificativoEntity(String ano, String dia, String mes,String duracion, String razon, EmpleadoEntity empleado) {
        this.duracion = duracion;
        this.ano = ano;
        this.dia = dia;
        this.mes = mes;
        this.razon = razon;
        this.empleado = empleado;
    }
}
