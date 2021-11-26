package com.pruebaSpring.demo.controller;


import com.pruebaSpring.demo.models.entity.Usuarios;
import com.pruebaSpring.demo.services.UsuariosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    UsuariosServices usuariosServices;

    @GetMapping("")
    public List<Usuarios> list() {
        return usuariosServices.listAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> get(@PathVariable Long id) {
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


    //servicio para editar o actualizar por Id
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Usuarios usuarios, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Usuarios usuarioActual = null;

        try {

            usuarioActual = usuariosServices.getUsuarios(id);

            if(usuarioActual != null){

                usuarios.setId(id);
                usuariosServices.saveUsuarios(usuarios);

                response.put("Mensaje:", "Se actualizo correctamente el usuario ".concat(id.toString().concat(" con exito")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

            }else{
                response.put("Mensaje:", "Ocurrio un error al actualizar el usuario ".concat(id.toString()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }


        } catch (DataAccessException e) {

            //manejo de mensajes en caso de algun error en el back end
            //se coloca concat en caso de querer concatenar algun mensaje o objeto
            response.put("Mensaje:", "No se encontro el usuario ".concat(id.toString()));

            //envia un mensaje del error, del lado del servidor => getMostSpecificCause
            response.put("Error:", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    //servicio para eliminar por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        //map para recorrer los mensajes enviados...
        Map<String, Object> response = new HashMap<>();

        try {

            usuariosServices.deleteUsuarios(id);
            //manejo de mensaje en caso de una buena respuesta
            //se coloca concat en caso de querer concatenar algun mensaje o objeto
            response.put("Mensaje:", "Se elimino correctamente el usuario ".concat(id.toString().concat(" con exito")));

        }catch (DataAccessException e) {

            //manejo de mensajes en caso de algun error en el back end
            //se coloca concat en caso de querer concatenar algun mensaje o objeto
            response.put("Mensaje:", "Ocurrio un error al eliminar el usuario ".concat(id.toString()));

            //envia un mensaje del error, del lado del servidor => getMostSpecificCause
            response.put("Error:", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }
}
