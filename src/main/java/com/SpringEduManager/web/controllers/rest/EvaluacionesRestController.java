package com.SpringEduManager.web.controllers.rest;

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

import com.SpringEduManager.common.validation.OnCreate;
import com.SpringEduManager.common.validation.OnUpdate;
import com.SpringEduManager.web.dto.EvaluacionRequestDTO;
import com.SpringEduManager.web.entities.EstudianteCurso;
import com.SpringEduManager.web.services.evaluaciones.EvaluacionesService;

/**
 * Controlador REST para la gestión de evaluaciones académicas.
 * Proporciona endpoints API para operaciones CRUD sobre evaluaciones
 * con formato de respuesta JSON estándar.
 */
@RestController
public class EvaluacionesRestController {

    /**
     * Servicio de negocio para la gestión de evaluaciones.
     */
    @Autowired
    private EvaluacionesService evaluacionesService;

    /**
     * Obtiene todas las evaluaciones o las filtra por curso o estudiante.
     * 
     * @param cursoId ID del curso para filtrar (opcional)
     * @param estudianteId ID del estudiante para filtrar (opcional)
     * @return Mapa con datos de evaluaciones o mensaje de error
     */
    @GetMapping("/api/evaluaciones")
    public Map<String, Object> getAll(
        @RequestParam(name= "curso", required=false) Long cursoId,
        @RequestParam(name= "estudiante", required=false) Long estudianteId
    ) {
        try{
            if(estudianteId != null && cursoId != null){
                throw new IllegalArgumentException("No se puede especificar ambos parámetros curso y estudiante");
            }
            if(cursoId != null){
                return Map.of("data", evaluacionesService.getByCursoId(cursoId));
            }
            if(estudianteId != null){
                return Map.of("data", evaluacionesService.getByEstudianteId(estudianteId));
            }
            return Map.of("data", evaluacionesService.getAll());
        }catch(Exception e){
            return Map.of("error", e.getMessage());
        }
    }
    
    /**
     * Obtiene una evaluación específica por su ID.
     * 
     * @param id Identificador único de la evaluación
     * @return Mapa con datos de la evaluación o mensaje de error
     */
    @GetMapping("/api/evaluaciones/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        try{
            return Map.of("data", evaluacionesService.getById(id));
        }catch(Exception e){
            return Map.of("error", e.getMessage());
        }
    }

    /**
     * Crea una nueva evaluación en el sistema.
     * 
     * @param request DTO con los datos de la evaluación a crear
     * @return Mapa con mensaje de éxito o error
     */
    @PostMapping("/api/evaluaciones")
    public Map<String, Object> create(@Validated(OnCreate.class) @RequestBody EvaluacionRequestDTO request) {
        try{
            EstudianteCurso estudianteCurso = new EstudianteCurso(
                request.getEstudianteCurso().getId(), 
                request.getEstudianteCurso().getEstudiante(), 
                request.getEstudianteCurso().getCurso()
            );
            Long id = evaluacionesService.save(null, request.getNota(), request.getFecha(), estudianteCurso);
            if(id == null){
                return Map.of("error", "No se pudo crear la evaluación");
            }
            return Map.of("data", Map.of("message", "La evaluación ha sido creada exitosamente"));
        }catch(Exception e){
            return Map.of("error", e.getMessage());
        }
    }

    /**
     * Actualiza una evaluación existente en el sistema.
     * 
     * @param id Identificador de la evaluación a actualizar
     * @param request DTO con los datos actualizados de la evaluación
     * @return Mapa con mensaje de éxito o error
     */
    @PutMapping("/api/evaluaciones/{id}")
    public Map<String, Object> update(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody EvaluacionRequestDTO request) {
        try{
            EstudianteCurso estudianteCurso = new EstudianteCurso(
                request.getEstudianteCurso().getId(), 
                request.getEstudianteCurso().getEstudiante(), 
                request.getEstudianteCurso().getCurso()
            );
            Long updatedId = evaluacionesService.save(id, request.getNota(), request.getFecha(), estudianteCurso);
            if(updatedId == null){
                return Map.of("error", "No se pudo actualizar la evaluación");
            }
            return Map.of("data", Map.of("message", "La evaluación ha sido actualizada exitosamente"));
        }catch(Exception e){
            return Map.of("error", e.getMessage());
        }
    }
    
    /**
     * Elimina una evaluación del sistema por su ID.
     * 
     * @param id Identificador único de la evaluación a eliminar
     * @return Mapa con mensaje de éxito o error
     */
    @DeleteMapping("/api/evaluaciones/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        try{
            evaluacionesService.delete(id);
            return Map.of("data", Map.of("message", "La evaluación ha sido eliminada exitosamente"));
        }catch(Exception e){
            return Map.of("error", e.getMessage());
        }
    }
    
}
