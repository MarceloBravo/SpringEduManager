package com.SpringEduManager.web.controllers.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.services.usuarios.UserService;

@RestController
public class UsuariosRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/users")
    public Map<String, List<UserDTO>> getAll(@RequestParam(name= "filtro", required=false) String filtro){
        try{
            if(filtro != null && !filtro.isEmpty()){
                return Map.of("data", userService.getAll(filtro));
            }
            return Map.of("data", userService.getAll());
        }catch(Exception e){
            return Map.of("error", List.of());
        }
    }

    // GET /api/users/{id}
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
    @PostMapping("/api/users")
    public Map<String, Object> save(@RequestBody UserDTO user){
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
