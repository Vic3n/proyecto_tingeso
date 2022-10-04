package com.example.demo.repositories;

import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.entities.IngresoEntity;
import com.example.demo.entities.JustificativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngresoRepository extends JpaRepository<IngresoEntity,Integer > {

    List<IngresoEntity> findByMes(String mes);

    @Query("select i from IngresoEntity i where i.mes = :mes and i.dia = :dia and i.empleado = :empleado")
    List<IngresoEntity> findByMesDiaEmpleado(@Param("mes") String mes, @Param("dia") String dia, @Param("empleado") EmpleadoEntity empleado);

}
