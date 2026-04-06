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

import com.SpringEduManager.web.dto.EstudianteDTO;
import com.SpringEduManager.common.validation.OnCreate;
import com.SpringEduManager.web.services.estudiantes.EstudianteService;

/**
 * Controlador REST para la gestión de estudiantes vía API.
 * Proporciona endpoints CRUD para operaciones con estudiantes.
 * Todas las respuestas siguen el formato: {"data": ...} o {"error": ...}
 */
@RestController
public class EstudiantesRestController {

    @Autowired
    private EstudianteService estudianteService;

    /**
     * Obtiene todos los estudiantes o filtra por nombre.
     * GET /api/estudiantes?filtro=nombre
     * @param filtro Parámetro opcional para filtrar por nombre (case insensitive)
     * @return Map con lista de estudiantes o error
     */
    @GetMapping("/api/estudiantes")
    public Map<String, List<EstudianteDTO>> getAll(@RequestParam(name= "filtro", required=false) String filtro){
        try{
            if(filtro != null && !filtro.isEmpty()){
                return Map.of("data", estudianteService.getAll(filtro));
            }
            return Map.of("data", estudianteService.getAll());
        }catch(Exception e){
            return Map.of("error", List.of());
        }
    }

    /**
     * Busca un estudiante por su ID.
     * GET /api/estudiantes/{id}
     * @param id ID del estudiante a buscar
     * @return Map con datos del estudiante o mensaje de error
     */
    @GetMapping("/api/estudiantes/{id}")
    public Map<String, Object> getById(@PathVariable("id") Long id){
        try{
            EstudianteDTO estudiante = this.estudianteService.findById(id);
            if(estudiante != null){
                return Map.of("data", estudiante);
            } else {
                return Map.of("error", "Estudiante no encontrado");
            }
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al buscar el estudiante: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo estudiante en el sistema.
     * POST /api/estudiantes
     * @param estudiante EstudianteDTO con los datos del nuevo estudiante (validado con @Validated)
     * @return Map con mensaje de éxito y estudiante creado, o error
     */
    @PostMapping("/api/estudiantes")
    public Map<String, Object> save(@Validated(OnCreate.class) @RequestBody EstudianteDTO estudiante){
        try{
            Long id = this.estudianteService.save(estudiante);
            EstudianteDTO newEstudiante = this.estudianteService.findById(id);
            if(newEstudiante != null){
                return Map.of("data", Map.of("message", "El estudiante ha sido creado exitosamente", "estudiante", newEstudiante));
            } else {
                return Map.of("error", "No se pudo crear el estudiante");
            }
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al crear el estudiante: " + e.getMessage());
        }
    }

    /**
     * Actualiza un estudiante existente.
     * PUT /api/estudiantes/{id}
     * @param id ID del estudiante a actualizar
     * @param estudiante EstudianteDTO con los datos a actualizar (validado con @Validated)
     * @return Map con mensaje de éxito y estudiante actualizado, o error
     */
    @PutMapping("/api/estudiantes/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id, @RequestBody EstudianteDTO estudiante){
        try{
            estudiante.setId(id);
            this.estudianteService.save(estudiante);
            EstudianteDTO updatedEstudiante = this.estudianteService.findById(id);            
            if(updatedEstudiante != null){
                return Map.of("data", Map.of("message", "El estudiante ha sido actualizado exitosamente", "estudiante", updatedEstudiante));
            } else {
                return Map.of("error", "No se pudo actualizar el estudiante");
            }
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al actualizar el estudiante: " + e.getMessage());
        }
    }

    /**
     * Elimina un estudiante por su ID.
     * DELETE /api/estudiantes/{id}
     * @param id ID del estudiante a eliminar
     * @return Map con mensaje de éxito o error
     */
    @DeleteMapping("/api/estudiantes/{id}")
    public Map<String, String> delete(@PathVariable("id") Long id){
        try{
            this.estudianteService.delete(id);
            return Map.of("data", "El estudiante ha sido eliminado exitosamente");
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al eliminar el estudiante");
        }
    }
    
}
