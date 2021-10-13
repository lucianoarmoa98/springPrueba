package com.pruebaSpring.demo.controller;


import com.pruebaSpring.demo.models.entity.Usuarios;
import com.pruebaSpring.demo.services.UsuariosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuariosServices usuariosServices;

    @GetMapping("")
    public List<Usuarios> list() {
        return usuariosServices.listAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> get(@PathVariable Integer id) {
        try {
            Usuarios usuarios = usuariosServices.getUsuarios(id);
            return new ResponseEntity<Usuarios>(usuarios, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return  new ResponseEntity<Usuarios>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/guardar")
    public void add(@RequestBody Usuarios usuarios) {
        usuariosServices.saveUsuarios(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Usuarios usuarios, @PathVariable Integer id) {
        try {
            Usuarios existUsuarios = usuariosServices.getUsuarios(id);
            usuarios.setId(id);
            usuariosServices.saveUsuarios(usuarios);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        usuariosServices.deleteUsuarios(id);
    }
}
