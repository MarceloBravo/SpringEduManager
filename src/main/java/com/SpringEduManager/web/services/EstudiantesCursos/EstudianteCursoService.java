package com.SpringEduManager.web.services.EstudiantesCursos;

import java.util.List;

import com.SpringEduManager.web.dto.EstudianteCursoDTO;
import com.SpringEduManager.web.dto.EstudianteDTO;

/**
 * Interfaz de servicio para la gestión de asignaciones estudiante-curso.
 * Define las operaciones CRUD y consultas especializadas para relaciones
 * entre estudiantes y cursos.
 */
public interface EstudianteCursoService {

    /**
     * Obtiene todas las asignaciones estudiante-curso.
     * @return Lista completa de asignaciones
     */
    List<EstudianteCursoDTO> findAll();

    /**
     * Busca todas las asignaciones de un estudiante específico.
     * @param estudianteId ID del estudiante a buscar
     * @return Lista de asignaciones del estudiante
     */
    EstudianteDTO findByEstudianteId(Long estudianteId);

    /**
     * Busca todas las asignaciones de un curso específico.
     * @param cursoId ID del curso a buscar
     * @return Lista de asignaciones del curso
     */
    List<EstudianteCursoDTO> findByCursoId(Long cursoId);

    /**
     * Busca una asignación específica por su ID.
     * @param id ID de la asignación a buscar
     * @return DTO de la asignación encontrada
     * @throws RuntimeException si la asignación no existe
     */
    EstudianteCursoDTO findById(Long id);

    /**
     * Guarda una nueva asignación estudiante-curso.
     * @param estudianteId ID del estudiante a asignar
     * @param cursoId ID del curso al que se asigna
     * @return ID de la asignación creada
     * @throws RuntimeException si el estudiante o curso no existen
     */
    Long save(Long estudianteId, Long cursoId);

    /**
     * Actualiza una asignación existente.
     * @param id ID de la asignación a actualizar
     * @param estudianteId Nuevo ID del estudiante
     * @param cursoId Nuevo ID del curso
     * @return ID de la asignación actualizada
     * @throws RuntimeException si la asignación, estudiante o curso no existen
     */
    Long update(Long id, Long estudianteId, Long cursoId);
    
    /**
     * Elimina una asignación por su ID.
     * @param id ID de la asignación a eliminar
     * @throws RuntimeException si la asignación no existe
     */
    void delete(Long id);

}
