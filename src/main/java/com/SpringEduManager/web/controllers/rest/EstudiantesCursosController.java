package com.SpringEduManager.web.controllers.rest;

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

import com.SpringEduManager.web.dto.EstudianteCursoRequestDTO;
import com.SpringEduManager.web.services.EstudiantesCursos.EstudianteCursoService;

/**
 * Controlador REST para la gestión de asignaciones de estudiantes a cursos.
 * Proporciona endpoints para listar, crear, actualizar y eliminar relaciones estudiante-curso.
 * Permite filtrar por estudiante o curso específico.
 */
@RestController
public class EstudiantesCursosController {

    /**
     * Servicio para la gestión de asignaciones estudiante-curso.
     */
    @Autowired
    private EstudianteCursoService estudianteCursoService;

    /**
     * Obtiene todas las asignaciones estudiante-curso con filtros opcionales.
     * GET /api/estudiantes-cursos
     * @param cursoId ID del curso para filtrar (opcional)
     * @param estudianteId ID del estudiante para filtrar (opcional)
     * @return Map con lista de asignaciones o mensaje de error
     * @throws IllegalArgumentException si se especifican ambos parámetros
     */
    @GetMapping("/api/estudiantes-cursos")
    public Map<String, Object> getAll(
        @RequestParam(name= "curso", required=false) Long cursoId,
        @RequestParam(name= "estudiante", required=false) Long estudianteId
    ){
        try{
            if(cursoId != null && estudianteId != null){
                throw new IllegalArgumentException("No se puede especificar ambos parámetros curso y estudiante");
            }
            if(cursoId != null){
                return Map.of("data", estudianteCursoService.findByCursoId(cursoId));
            }
            if(estudianteId != null){
                return Map.of("data", estudianteCursoService.findByEstudianteId(estudianteId));
            }
            return Map.of("data", estudianteCursoService.findAll());
        }catch(Exception e){
            return Map.of("error", e.getMessage());
        }
    }

    /**
     * Obtiene una asignación específica por su ID.
     * GET /api/estudiantes-cursos/{id}
     * @param id ID de la asignación a buscar
     * @return Map con la asignación encontrada o mensaje de error
     */
    @GetMapping("/api/estudiantes-cursos/{id}")
    public Map<String, Object> getById(@PathVariable("id") Long id){
        try{
            return Map.of("data", estudianteCursoService.findById(id));
        }catch(Exception e){
            return Map.of("error", e.getMessage());
        }
    }

    /**
     * Crea una nueva asignación de estudiante a curso.
     * POST /api/estudiantes-cursos
     * @param request DTO con IDs de estudiante y curso
     * @return Map con mensaje de éxito o error
     */
    @PostMapping("/api/estudiantes-cursos")
    public Map<String, Object> create(@RequestBody EstudianteCursoRequestDTO request){
        try{
            System.out.println("Estudiante ID: " + request.estudianteId());
            System.out.println("Curso ID: " + request.cursoId());
            Long id = estudianteCursoService.save(request.estudianteId(), request.cursoId());
            if(id == null){
                return Map.of("error", "No se pudo asignar el estudiante al curso");
            }
            return Map.of("data", Map.of("message", "El estudiante ha sido asignado al curso exitosamente"));
        }catch(Exception e){
            return Map.of("error", e.getMessage());
        }
    }

    /**
     * Actualiza una asignación existente.
     * PUT /api/estudiantes-cursos/{id}
     * @param id ID de la asignación a actualizar
     * @param request DTO con nuevos IDs de estudiante y curso
     * @return Map con mensaje de éxito o error
     */
    @PutMapping("/api/estudiantes-cursos/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id, @RequestBody EstudianteCursoRequestDTO request){
        try{
            Long savedId = estudianteCursoService.update(id, request.estudianteId(), request.cursoId());
            if(savedId == null){
                return Map.of("error", "No se pudo actualizar la asignación");
            }
            return Map.of("data", Map.of("message", "La asignación ha sido actualizada exitosamente"));
        }catch(Exception e){
            return Map.of("error", e.getMessage());
        }
    }

    /**
     * Elimina una asignación existente.
     * DELETE /api/estudiantes-cursos/{id}
     * @param id ID de la asignación a eliminar
     * @return Map con mensaje de éxito o error
     */
    @DeleteMapping("/api/estudiantes-cursos/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id){
        try{
            estudianteCursoService.delete(id);
            return Map.of("data", Map.of("message", "La asignación ha sido eliminada exitosamente"));
        }catch(Exception e){
            return Map.of("error", e.getMessage());
        }
    }
    
}