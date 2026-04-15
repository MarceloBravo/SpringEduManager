package com.SpringEduManager.web.controllers.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.SpringEduManager.web.dto.CursoNotasDTO;
import com.SpringEduManager.web.services.evaluaciones.EvaluacionesService;
/**
 * Controlador REST para la gestión de evaluaciones académicas.
 * Proporciona endpoints API para operaciones CRUD sobre evaluaciones
 * con formato de respuesta JSON estándar.
 */
@RestController
public class EvaluacionesRestController {

    /**
     * Servicio para la gestión de evaluaciones y notas.
     */
    @Autowired
    private EvaluacionesService evaluacionesService;

    /**
     * Obtiene todas las evaluaciones de un estudiante específico.
     * GET /api/evaluaciones/{id}
     * @param id ID del estudiante para filtrar
     * @return Mapa con datos de evaluaciones o mensaje de error
     */
    @GetMapping("/api/evaluaciones/{id}")
    public Map<String, Object> getAll(
        @PathVariable("id") Long id
    ) {
        try{
            List<CursoNotasDTO[]> cursoNotas = evaluacionesService.getEstudianteNotas(id);
            return Map.of("data", cursoNotas.size() > 0 ? cursoNotas : new CursoNotasDTO[0]);
       }catch(Exception e){
            return Map.of("error", "Ocurrió un error al obtener los cursos y sus evaluaciones");
       }
    }

    /**
     * Elimina una evaluación existente por su ID.
     * DELETE /api/evaluaciones/{id}
     * @param id ID de la evaluación a eliminar
     * @return Mapa con mensaje de éxito o error
     */
    @DeleteMapping("/api/evaluaciones/{id}")
    public Map<String, Object> delete(
        @PathVariable("id") Long id
    ) {
        try{
            evaluacionesService.deleteById(id);
            return Map.of("success", "Evaluación eliminada correctamente");
       }catch(Exception e){
            return Map.of("error", "Ocurrió un error al eliminar la evaluación");
       }
    }
    
}
