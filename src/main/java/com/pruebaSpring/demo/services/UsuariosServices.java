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

        //----------------------------servicio para listar todos los usuarios en la Bd
        public List<Usuarios> listAllUsuarios() {
            return iUsuariosDao.findAll();
        }

        //---------------------------servicio para crear o guardar usuarios
        public void saveUsuarios(Usuarios usuarios) {
            iUsuariosDao.save(usuarios);
        }

        //-------------------------servicio para obtener por Id al usuario
        public Usuarios getUsuarios(Long id) {
            return iUsuariosDao.findById(id).get();
        }

        //---------------------------servicio de eliminar por Id al usuario
        public void deleteUsuarios(Long id) {
            iUsuariosDao.deleteById(id);
        }
}
