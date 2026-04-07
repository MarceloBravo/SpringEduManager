package com.SpringEduManager.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringEduManager.web.entities.EstudianteCurso;

import java.util.List;

/**
 * Repositorio para la gestión de entidades EstudianteCurso.
 * Proporciona métodos de consulta personalizados para buscar asignaciones
 * por estudiante o curso específico.
 */
@Repository
public interface EstudianteCursoRepository extends JpaRepository<EstudianteCurso, Long> {
    
    /**
     * Busca todas las asignaciones de un estudiante específico.
     * @param estudianteId ID del estudiante a buscar
     * @return Lista de asignaciones del estudiante
     */
    List<EstudianteCurso> findByEstudianteId(Long estudianteId);

    /**
     * Busca todas las asignaciones de un curso específico.
     * @param cursoId ID del curso a buscar
     * @return Lista de asignaciones del curso
     */
    List<EstudianteCurso> findByCursoId(Long cursoId);
}
