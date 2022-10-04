package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "categorias",schema = "db")
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;
    @Column(unique = true, nullable = false, length = 1)
    private String id_categoria;
    @Column(nullable = false)
    private Integer sueldo_mensual;
    @Column(nullable = false)
    private Integer monto_hora_extra;

    public CategoriaEntity(String id_categoria, Integer sueldo_mensual, Integer monto_hora_extra) {
        this.id_categoria = id_categoria;
        this.sueldo_mensual = sueldo_mensual;
        this.monto_hora_extra = monto_hora_extra;
    }
}
