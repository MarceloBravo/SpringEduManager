package com.SpringEduManager.web.controllers.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.common.validation.OnCreate;
import com.SpringEduManager.web.services.usuarios.UserService;
import org.springframework.data.domain.Page;

/**
 * Controlador REST para la gestión de usuarios vía API.
 * Proporciona endpoints CRUD para operaciones con usuarios.
 * Todas las respuestas siguen el formato: {"data": ...} o {"error": ...}
 */
@RestController
public class UsuariosRestController {

    @Autowired
    private UserService userService;

    /**
     * Obtiene todos los usuarios o filtra por nombre.
     * GET /api/users?filtro=nombre
     * @param filtro Parámetro opcional para filtrar por nombre (case insensitive)
     * @return Map con lista de usuarios o error
     */
    @GetMapping("/api/users")
    public Map<String, List<UserDTO>> getAll(
        @RequestParam(name= "filtro", required=false) String filtro,
        @RequestParam(name= "page", required=false, defaultValue = "0") int page,
        @RequestParam(name= "size", required=false, defaultValue = "10") int size,
        @RequestParam(name= "sortBy", required=false, defaultValue = "nombre") String sortBy
    ){
        try{
            Page<UserDTO> users = userService.searchInAllFields(filtro, page, size, sortBy);
            return Map.of("data", users.getContent());
        }catch(Exception e){
            return Map.of("error", List.of());
        }
    }

    // GET /api/users/{id}
    /**
     * Busca un usuario por su ID.
     * GET /api/users/{id}
     * @param id ID del usuario a buscar
     * @return Map con datos del usuario o mensaje de error
     */
    @GetMapping("/api/users/{id}")
    public Map<String, Object> getById(@PathVariable("id") Long id){
        try{
            UserDTO user = this.userService.findById(id);
            if(user != null){
                return Map.of("data", user);
            } else {
                return Map.of("error", "Usuario no encontrado");
            }
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al buscar el usuario: " + e.getMessage());
        }
    }

    // POST /api/users/
    /**
     * Crea un nuevo usuario en el sistema.
     * POST /api/users
     * @param user UserDTO con los datos del nuevo usuario (validado con @Validated)
     * @return Map con mensaje de éxito y usuario creado, o error
     */
    @PostMapping("/api/users")
    public Map<String, Object> save(@Validated(OnCreate.class) @RequestBody UserDTO user){
        try{
            Long id = this.userService.save(user);
            UserDTO newUser = this.userService.findById(id);
            if(newUser != null){
                return Map.of("data", Map.of("message", "El usuario ha sido creado exitosamente", "user", newUser));
            } else {
                return Map.of("error", "No se pudo crear el usuario");
            }
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al crear el usuario: " + e.getMessage());
        }
    }

    // PUT /api/users/{id}
    /**
     * Actualiza un usuario existente.
     * PUT /api/users/{id}
     * @param id ID del usuario a actualizar
     * @param user UserDTO con los datos a actualizar (validado con @Validated)
     * @return Map con mensaje de éxito y usuario actualizado, o error
     */
    @PutMapping("/api/users/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id, @RequestBody UserDTO user){
        try{
            user.setId(id);
            this.userService.save(user);
            UserDTO updatedUser = this.userService.findById(id);            
            if(updatedUser != null){
                return Map.of("data", Map.of("message", "El usuario ha sido actualizado exitosamente", "user", updatedUser));
            } else {
                return Map.of("error", "No se pudo actualizar el usuario");
            }
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al actualizar el usuario: " + e.getMessage());
        }
    }

    // DELETE /api/users/{id}    
    /**
     * Elimina un usuario por su ID.
     * DELETE /api/users/{id}
     * @param id ID del usuario a eliminar
     * @return Map con mensaje de éxito o error
     */
    @DeleteMapping("/api/users/{id}")
    public Map<String, String> delete(@PathVariable("id") Long id){
        try{
            this.userService.delete(id);
            return Map.of("data", "El usuario ha sido eliminado exitosamente");
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al eliminar el usuario");
        }
    }
    
}
