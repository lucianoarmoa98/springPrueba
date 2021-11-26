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

    //------------------------------------------servicio para listar todos los usuarios
    @GetMapping("")
    public List<Usuarios> list() {
        return usuariosServices.listAllUsuarios();
    }



    //-------------------------------------------servicio para seleccionar usuario por su Id
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Usuarios usuarioSeleccionado = null;

        try {
            usuarioSeleccionado = usuariosServices.getUsuarios(id);
            //-------------------------mensaje que se obtuvo al obtener el usuario, mas los Datos del usuario seleccionado
            //-------------------------en caso de querer mostrar el nombre -> colorar: .concat(usuarioSeleccionado.getNombre())
            response.put("Mensaje:", "Se obtuvo correctamente el usuario ".concat(id.toString().concat(" con exito")));
            response.put("Data:", usuarioSeleccionado);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            //------------------------mensajes en caso de no encontrar el usuario
            response.put("Error:", "No se encontro el usuario ".concat(id.toString()));
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    //----------------------------Servicio para guardar usuarios o crear
    @PostMapping("/guardar")
    public ResponseEntity<?> add(@RequestBody Usuarios usuarios) {
        Map<String, Object> response = new HashMap<>();

        try {
            //------------------------------validacion de campos vacios
            if (usuarios.getNombre() == null || usuarios.getApellido() == null){
                response.put("Error:", "Favor completar todos los campos ");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

            }else {
            //------------------------------servicio para guardar los cambios
                response.put("Mensaje:", "Usuario creado con exito");
                usuariosServices.saveUsuarios(usuarios);
            }


        }catch (DataAccessException e) {

            response.put("Mensaje:", "Error al crear usario ");

            //--------------------------------------envia un mensaje del error, del lado del servidor => getMostSpecificCause
            response.put("Error:", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    //------------------------------------------------servicio para editar o actualizar por Id
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Usuarios usuarios, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Usuarios usuarioActual = null;

        try {
            //------------------------------validacion de campos vacios
            if (usuarios.getNombre() == null || usuarios.getApellido() == null){

                response.put("Error:", "Favor completar todos los campos ");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

            }else {
                //-----------------------------se obtiene el Id a modificar
                usuarioActual = usuariosServices.getUsuarios(id);

                //----------------------------luego pasamos el Id a modificar
                usuarios.setId(id);

                //----------------------------servicio para guardar los cambios
                usuariosServices.saveUsuarios(usuarios);

                response.put("Mensaje:", "Se actualizo correctamente el usuario ".concat(id.toString().concat(" con exito")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }

        } catch (Exception e) {

            //--------------------------------------manejo de mensajes en caso de algun error en el back end
            //-------------------------------------se coloca concat en caso de querer concatenar algun mensaje o objeto
            response.put("Error:", "No se encontro el usuario ".concat(id.toString()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

        }
    }

    //---------------------------------------------servicio para eliminar por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        //------------------------------------map para recorrer los mensajes enviados...
        Map<String, Object> response = new HashMap<>();

        try {
            //------------------------------------se obtiene el Id a eliminar
            usuariosServices.deleteUsuarios(id);

            //--------------------------------mensaje personalizado, con concatenacion de Id mas String
            response.put("Mensaje:", "Se elimino correctamente el usuario ".concat(id.toString().concat(" con exito")));

        }catch (DataAccessException e) {
            //-------------------------------mensaje personalizado, con concatenacion de Id
            response.put("Mensaje:", "Ocurrio un error al eliminar el usuario ".concat(id.toString()));

            //-----------------------------envia un mensaje del error, del lado del servidor => getMostSpecificCause
            response.put("Error:", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }
}
