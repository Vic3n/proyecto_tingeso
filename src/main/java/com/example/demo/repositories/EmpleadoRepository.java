package com.example.demo.repositories;

import com.example.demo.entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity,Integer>{

    EmpleadoEntity findByRut(String rut);


}
