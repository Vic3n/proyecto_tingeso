package com.example.demo.repositories;

import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.entities.JustificativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JustificativoRepository extends JpaRepository<JustificativoEntity,Integer> {

    @Query("select j from JustificativoEntity j where j.mes = :mes and j.dia = :dia and j.empleado = :empleado")
    List<JustificativoEntity> findByMesDiaEmpleado(@Param("mes") String mes,@Param("dia") String dia, @Param("empleado") EmpleadoEntity empleado);

}
