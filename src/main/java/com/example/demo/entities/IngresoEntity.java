package com.example.demo.entities;

import com.example.demo.repositories.EmpleadoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ingresos",schema = "db")
@NoArgsConstructor
@AllArgsConstructor
public class IngresoEntity {

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
    @Column(nullable = false, length = 5)
    private String hora;
    @ManyToOne
    @JoinColumn(name = "empleado")
    private EmpleadoEntity empleado;

    public IngresoEntity(String ano, String mes, String dia, String hora, EmpleadoEntity empleado) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.empleado = empleado;
    }
}
