package com.SpringEduManager.web.services.evaluaciones;

import java.util.List;

import com.SpringEduManager.web.dto.CursoNotasDTO;
import com.SpringEduManager.web.dto.EvaluacionRequestDTO;

/**
 * Interfaz de servicio para la gestión de evaluaciones académicas.
 * Define las operaciones CRUD y consultas especializadas para
 * la administración de notas y evaluaciones de estudiantes.
 */
public interface EvaluacionesService {
    
    /**
     * Obtiene las evaluaciones y notas de un estudiante por su email.
     * Busca al estudiante por email y retorna sus cursos con evaluaciones.
     * @param userEmail Email del estudiante a buscar
     * @return Lista de arrays con datos de cursos y evaluaciones pivotizados
     */
    List<CursoNotasDTO[]> getEstudianteNotasByUserEmail(String userEmail);
    
    /**
     * Obtiene las evaluaciones y notas de un estudiante por su ID.
     * Retorna los cursos del estudiante con sus evaluaciones en formato pivotizado.
     * @param estudianteId ID del estudiante a buscar
     * @return Lista de arrays con datos de cursos y evaluaciones pivotizados
     */
    List<CursoNotasDTO[]> getEstudianteNotas(Long estudianteId);
    
    /**
     * Guarda una nueva evaluación para un estudiante en un curso.
     * Crea o actualiza la relación estudiante-curso si es necesario.
     * @param request DTO con los datos de la evaluación a registrar
     * @return ID de la evaluación guardada
     */
    Long save(EvaluacionRequestDTO request);

    /**
     * Elimina una evaluación por su ID.
     * @param id ID de la evaluación a eliminar
     */
    void deleteById(Long id);
    
}
