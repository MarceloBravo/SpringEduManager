package com.SpringEduManager.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SpringEduManager.web.entities.Evaluacion;

/**
 * Repositorio para la entidad Evaluacion.
 * Proporciona métodos de acceso a datos para operaciones CRUD
 * y consultas personalizadas sobre evaluaciones académicas.
 */
@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    
    /**
     * Busca todas las evaluaciones asociadas a un estudiante específico.
     * 
     * @param estudianteId Identificador único del estudiante
     * @return Lista de evaluaciones del estudiante ordenadas por fecha
     */
    List<Evaluacion> findByEstudianteCurso_EstudianteId(Long estudianteId);

    /**
     * Busca todas las evaluaciones asociadas a un curso específico.
     * 
     * @param cursoId Identificador único del curso
     * @return Lista de evaluaciones del curso ordenadas por fecha
     */
    List<Evaluacion> findByEstudianteCurso_CursoId(Long cursoId);

    @Query(value = "SELECT " +
              "id, " +
              "sub.est_id, sub.est_nombre, sub.est_apellido, sub.est_email, " +
              "sub.curso_id, sub.curso_nombre, sub.curso_descripcion, " +
              "MAX(CASE WHEN fila_nota = 1 THEN sub.ev_id END) as ev_id_1, " +
              "MAX(CASE WHEN fila_nota = 1 THEN sub.ev_nota END) as ev_nota_1, " +
              "MAX(CASE WHEN fila_nota = 1 THEN sub.ev_fecha END) as ev_fecha_1, " +
              "MAX(CASE WHEN fila_nota = 2 THEN sub.ev_id END) as ev_id_2, " +
              "MAX(CASE WHEN fila_nota = 2 THEN sub.ev_nota END) as ev_nota_2, " +
              "MAX(CASE WHEN fila_nota = 2 THEN sub.ev_fecha END) as ev_fecha_2, " +
              "MAX(CASE WHEN fila_nota = 3 THEN sub.ev_id END) as ev_id_3, " +
              "MAX(CASE WHEN fila_nota = 3 THEN sub.ev_nota END) as ev_nota_3, " +
              "MAX(CASE WHEN fila_nota = 3 THEN sub.ev_fecha END) as ev_fecha_3, " +
              "MAX(CASE WHEN fila_nota = 4 THEN sub.ev_id END) as ev_id_4, " +
              "MAX(CASE WHEN fila_nota = 4 THEN sub.ev_nota END) as ev_nota_4, " +
              "MAX(CASE WHEN fila_nota = 4 THEN sub.ev_fecha END) as ev_fecha_4, " +
              "MAX(CASE WHEN fila_nota = 5 THEN sub.ev_id END) as ev_id_5, " +
              "MAX(CASE WHEN fila_nota = 5 THEN sub.ev_nota END) as ev_nota_5, " +
              "MAX(CASE WHEN fila_nota = 5 THEN sub.ev_fecha END) as ev_fecha_5, " +
              "MAX(CASE WHEN fila_nota = 6 THEN sub.ev_id END) as ev_id_6, " +
              "MAX(CASE WHEN fila_nota = 6 THEN sub.ev_nota END) as ev_nota_6, " +
              "MAX(CASE WHEN fila_nota = 6 THEN sub.ev_fecha END) as ev_fecha_6, " +
              "MAX(CASE WHEN fila_nota = 7 THEN sub.ev_id END) as ev_id_7, " +
              "MAX(CASE WHEN fila_nota = 7 THEN sub.ev_nota END) as ev_nota_7, " +
              "MAX(CASE WHEN fila_nota = 7 THEN sub.ev_fecha END) as ev_fecha_7, " +
              "MAX(CASE WHEN fila_nota = 8 THEN sub.ev_id END) as ev_id_8, " +
              "MAX(CASE WHEN fila_nota = 8 THEN sub.ev_nota END) as ev_nota_8, " +
              "MAX(CASE WHEN fila_nota = 8 THEN sub.ev_fecha END) as ev_fecha_8, " +
              "MAX(CASE WHEN fila_nota = 9 THEN sub.ev_id END) as ev_id_9, " +
              "MAX(CASE WHEN fila_nota = 9 THEN sub.ev_nota END) as ev_nota_9, " +
              "MAX(CASE WHEN fila_nota = 9 THEN sub.ev_fecha END) as ev_fecha_9, " +
              "MAX(CASE WHEN fila_nota = 10 THEN sub.ev_id END) as ev_id_10, " +
              "MAX(CASE WHEN fila_nota = 10 THEN sub.ev_nota END) as ev_nota_10, " +
              "MAX(CASE WHEN fila_nota = 10 THEN sub.ev_fecha END) as ev_fecha_10 " +
              "FROM (" +
              "SELECT " +
                    "ec.id as id, " +
                    "e.id as est_id, e.nombre as est_nombre, e.apellido as est_apellido, e.email as est_email, " +
                    "c.id as curso_id, c.nombre as curso_nombre, c.descripcion as curso_descripcion, " +
                    "ev.id as ev_id, ev.nota as ev_nota, ev.fecha as ev_fecha, " +
                    "ROW_NUMBER() OVER (PARTITION BY ec.id ORDER BY ev.id) AS fila_nota " +
              "FROM estudiantes e " +
              "LEFT JOIN estudiante_cursos ec ON e.id = ec.estudiante_id " +
              "LEFT JOIN cursos c ON ec.curso_id = c.id " +
              "LEFT JOIN evaluaciones ev ON ec.id = ev.estudiante_curso_id " +
              "WHERE e.id = :estudianteId " +
              ") sub " +
              "GROUP BY sub.id, sub.est_id, sub.curso_id, sub.est_nombre, sub.est_apellido, sub.est_email, sub.curso_nombre, sub.curso_descripcion", nativeQuery = true)
    
    /**
     * Obtiene las notas de un estudiante en formato pivotizado.
     * Utiliza una consulta SQL nativa compleja con ROW_NUMBER() y MAX(CASE WHEN)
     * para transformar evaluaciones en columnas (ev_nota_1, ev_nota_2, etc.).
     * Retorna datos como Object[] para ser procesados por el servicio.
     * @param estudianteId ID del estudiante a consultar
     * @return Lista de Object[] con datos pivotizados del estudiante
     */
    List<Object[]> findEstudianteNotas(@Param("estudianteId") Long estudianteId);
}
