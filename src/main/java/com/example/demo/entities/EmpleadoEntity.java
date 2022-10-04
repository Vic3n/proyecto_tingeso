package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "empleados",schema = "db")
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;
    @Column(unique = true, nullable = false, length = 25)
    private String rut;
    @Column(nullable = false, length = 40)
    private String apellidos;
    @Column(nullable = false, length = 40)
    private String nombres;
    @Column(nullable = false, length = 25)
    private String fecha_nacimiento;
    @ManyToOne
    @JoinColumn(name = "categoria")
    private CategoriaEntity categoria;
    @Column(nullable = false, length = 25)
    private String fecha_ingreso;

    public EmpleadoEntity(String rut, String apellidos, String nombres, String fecha_nacimiento,
                          CategoriaEntity categoria, String fecha_ingreso) {
        this.rut = rut;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.fecha_nacimiento = fecha_nacimiento;
        this.categoria = categoria;
        this.fecha_ingreso = fecha_ingreso;
    }
}
