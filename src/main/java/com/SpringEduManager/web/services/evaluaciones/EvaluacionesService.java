package com.SpringEduManager.web.services.evaluaciones;

import java.sql.Date;
import java.util.List;

import com.SpringEduManager.web.dto.EvaluacionDTO;
import com.SpringEduManager.web.entities.EstudianteCurso;

/**
 * Interfaz de servicio para la gestión de evaluaciones académicas.
 * Define los contratos de negocio para operaciones CRUD y consultas especializadas
 * de evaluaciones en el sistema SpringEduManager.
 */
public interface EvaluacionesService {
    
    /**
     * Obtiene todas las evaluaciones registradas en el sistema.
     * 
     * @return Lista de todas las evaluaciones en formato DTO
     */
    List<EvaluacionDTO> getAll();

    /**
     * Busca las evaluaciones de un estudiante específico.
     * 
     * @param estudianteId Identificador único del estudiante
     * @return Lista de evaluaciones del estudiante
     */
    List<EvaluacionDTO> getByEstudianteId(Long estudianteId);

    /**
     * Busca las evaluaciones de un curso específico.
     * 
     * @param cursoId Identificador único del curso
     * @return Lista de evaluaciones del curso
     */
    List<EvaluacionDTO> getByCursoId(Long cursoId);
    
    /**
     * Obtiene una evaluación específica por su identificador.
     * 
     * @param id Identificador único de la evaluación
     * @return Evaluación encontrada en formato DTO
     * @throws RuntimeException si la evaluación no existe
     */
    EvaluacionDTO getById(Long id);
    
    /**
     * Guarda o actualiza una evaluación en el sistema.
     * Si el id es null, crea una nueva evaluación; si tiene valor, actualiza la existente.
     * 
     * @param id Identificador de la evaluación (null para nueva evaluación)
     * @param nota Calificación numérica de la evaluación (0-10)
     * @param fecha Fecha en que se realizó la evaluación
     * @param estudianteCurso Relación estudiante-curso asociada
     * @return Identificador de la evaluación guardada
     * @throws RuntimeException si los datos son inválidos
     */
    Long save(Long id, Double nota, Date fecha, EstudianteCurso estudianteCurso);
    
    /**
     * Elimina una evaluación del sistema por su identificador.
     * 
     * @param id Identificador único de la evaluación a eliminar
     * @throws RuntimeException si la evaluación no existe
     */
    void delete(Long id);
}
