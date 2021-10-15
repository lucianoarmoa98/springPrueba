package com.pruebaSpring.demo.models.dao;

import com.pruebaSpring.demo.models.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolDao extends JpaRepository<Rol, Integer> {
}
