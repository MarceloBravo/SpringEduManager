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
import org.springframework.data.domain.Page;

import com.SpringEduManager.web.dto.CursoDTO;
import com.SpringEduManager.common.validation.OnCreate;
import com.SpringEduManager.web.services.cursos.CursoService;

/**
 * Controlador REST para la gestión de cursos vía API.
 * Proporciona endpoints CRUD para operaciones con cursos.
 * Todas las respuestas siguen el formato: {"data": ...} o {"error": ...}
 */
@RestController
public class CursosRestController {

    @Autowired
    private CursoService cursoService;


    @GetMapping("/api/cursos/all")
    public Map<String, List<CursoDTO>> getAll(){
        try{
            return Map.of("data", cursoService.getAll());
        }catch(Exception e){
            return Map.of("error", List.of());
        }
    }

    /**
     * Obtiene todos los cursos o filtra por nombre.
     * GET /api/cursos?filtro=nombre
     * @param filtro Parámetro opcional para filtrar por nombre (case insensitive)
     * @return Map con lista de cursos o error
     */
    @GetMapping("/api/cursos")
    public Map<String, List<CursoDTO>> getPage(
        @RequestParam(name= "filtro", required=false) String filtro,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "sortBy", defaultValue = "") String sortBy
    ){
        try{
            Page<CursoDTO> cursos = cursoService.searchInAllFields(filtro, page, size, sortBy);
            return Map.of("data", cursos.getContent());
        }catch(Exception e){
            return Map.of("error", List.of());
        }
    }

    /**
     * Busca un curso por su ID.
     * GET /api/cursos/{id}
     * @param id ID del curso a buscar
     * @return Map con datos del curso o mensaje de error
     */
    @GetMapping("/api/cursos/{id}")
    public Map<String, Object> getById(@PathVariable("id") Long id){
        try{
            CursoDTO curso = this.cursoService.findById(id);
            if(curso != null){
                return Map.of("data", curso);
            } else {
                return Map.of("message", "Curso no encontrado");
            }
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al buscar el curso: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo curso en el sistema.
     * POST /api/cursos
     * @param curso CursoDTO con los datos del nuevo curso (validado con @Validated)
     * @return Map con mensaje de éxito y curso creado, o error
     */
    @PostMapping("/api/cursos")
    public Map<String, Object> save(@Validated(OnCreate.class) @RequestBody CursoDTO curso){
        try{
            Long id = this.cursoService.save(curso);
            CursoDTO newCurso = this.cursoService.findById(id);
            if(newCurso != null){
                return Map.of("data", Map.of("message", "El curso ha sido creado exitosamente", "curso", newCurso));
            } else {
                return Map.of("message", "No se pudo crear el curso");
            }
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al crear el curso: " + e.getMessage());
        }
    }

    /**
     * Actualiza un curso existente.
     * PUT /api/cursos/{id}
     * @param id ID del curso a actualizar
     * @param curso CursoDTO con los datos a actualizar (validado con @Validated)
     * @return Map con mensaje de éxito y curso actualizado, o error
     */
    @PutMapping("/api/cursos/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id, @RequestBody CursoDTO curso){
        try{
            curso.setId(id);
            this.cursoService.save(curso);
            CursoDTO updatedCurso = this.cursoService.findById(id);            
            if(updatedCurso != null){
                return Map.of("data", Map.of("message", "El curso ha sido actualizado exitosamente", "curso", updatedCurso));
            } else {
                return Map.of("message", "No se pudo actualizar el curso");
            }
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al actualizar el curso: " + e.getMessage());
        }
    }

    /**
     * Elimina un curso por su ID.
     * DELETE /api/cursos/{id}
     * @param id ID del curso a eliminar
     * @return Map con mensaje de éxito o error
     */
    @DeleteMapping("/api/cursos/{id}")
    public Map<String, String> delete(@PathVariable("id") Long id){
        try{
            this.cursoService.delete(id);
            return Map.of("data", "El curso ha sido eliminado exitosamente");
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al eliminar el curso");
        }
    }
    
}
