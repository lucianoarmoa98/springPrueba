package com.pruebaSpring.demo.services;

import com.pruebaSpring.demo.models.dao.IUsuariosDao;
import com.pruebaSpring.demo.models.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuariosServices {

        @Autowired
        private IUsuariosDao iUsuariosDao;
        public List<Usuarios> listAllUsuarios() {
            return iUsuariosDao.findAll();
        }

        public void saveUsuarios(Usuarios usuarios) {
            iUsuariosDao.save(usuarios);
        }

        public Usuarios getUsuarios(Long id) {
            return iUsuariosDao.findById(id).get();
        }

        public void deleteUsuarios(Long id) {
            iUsuariosDao.deleteById(id);
        }
}
