package com.pruebaSpring.demo.models.dao;

import com.pruebaSpring.demo.models.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuariosDao extends JpaRepository<Usuarios, Integer> {
}
