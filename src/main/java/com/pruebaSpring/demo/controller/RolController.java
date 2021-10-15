package com.pruebaSpring.demo.controller;


import com.pruebaSpring.demo.models.entity.Rol;
import com.pruebaSpring.demo.services.RolServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rol")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    RolServices rolServices;

    @GetMapping("")
    public List<Rol> list() {return rolServices.listAllRol();}

}
