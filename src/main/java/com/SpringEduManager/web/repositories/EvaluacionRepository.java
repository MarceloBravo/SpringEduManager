package com.SpringEduManager.web.repositories;

import org.springframework.stereotype.Repository;

import com.SpringEduManager.web.entities.Evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio Spring Data JPA para la entidad Evaluacion.
 * Proporciona métodos de acceso a datos para gestionar las evaluaciones en la base de datos.
 */
@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    
    /**
     * Busca todas las evaluaciones asociadas a un estudiante específico.
     * 
     * @param estudianteId Identificador único del estudiante
     * @return Lista de evaluaciones del estudiante ordenadas por fecha
     */
    List<Evaluacion> findByEstudianteId(Long estudianteId);

    /**
     * Busca todas las evaluaciones asociadas a un curso específico.
     * 
     * @param cursoId Identificador único del curso
     * @return Lista de evaluaciones del curso ordenadas por fecha
     */
    List<Evaluacion> findByCursoId(Long cursoId);
}
