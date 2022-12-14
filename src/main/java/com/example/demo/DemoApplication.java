package com.example.demo;

import com.example.demo.entities.CategoriaEntity;
import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.repositories.CategoriaRepository;
import com.example.demo.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
