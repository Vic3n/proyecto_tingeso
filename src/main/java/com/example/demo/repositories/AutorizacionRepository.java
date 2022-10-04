package com.example.demo.repositories;

import com.example.demo.entities.AutorizacionesEntity;
import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.entities.IngresoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AutorizacionRepository extends JpaRepository<AutorizacionesEntity,Integer> {

    ArrayList<AutorizacionesEntity> findByMes(String mes);

    ArrayList<AutorizacionesEntity> findAll();


}
