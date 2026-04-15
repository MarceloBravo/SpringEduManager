package com.SpringEduManager.web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.SpringEduManager.web.entities.Curso;

/**
 * Repositorio para la gestión de entidades Curso.
 * Proporciona métodos de consulta personalizados para buscar cursos
 * por nombre, descripción y búsquedas combinadas con paginación.
 * Incluye métodos optimizados con JOIN FETCH para evitar problemas N+1.
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    /**
     * Busca cursos por nombre (case insensitive)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de cursos que coinciden con la búsqueda
     */
    List<Curso> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Busca un curso por su nombre exacto
     * @param nombre Nombre exacto del curso a buscar
     * @return Optional con el curso encontrado o vacío
     */
    Optional<Curso> findByNombre(String nombre);


    /**
     * Busca cursos en múltiples campos (nombre, descripción) con paginación.
     * Utiliza condiciones OR para buscar coincidencias en cualquiera de los campos.
     * @param search Término de búsqueda (case insensitive)
     * @param pageable Configuración de paginación y ordenamiento
     * @return Página de cursos que coinciden con la búsqueda
     */
    @Query("SELECT c FROM Curso c WHERE " +
           "(LOWER(c.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "(LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :search, '%'))))")
    Page<Curso> searchInMultipleFields(
        @Param("search") String search,
        Pageable pageable
    );

    // ===== MÉTODOS OPTIMIZADOS CON JOIN FETCH =====
    
    /**
     * Obtiene un curso con sus estudiantes usando JOIN FETCH.
     * Evita el problema N+1 cargando los estudiantes en una sola consulta.
     * @param cursoId ID del curso a buscar
     * @return Curso con sus estudiantes cargados
     */
    @Query("SELECT DISTINCT c FROM Curso c " +
           "LEFT JOIN FETCH c.estudiantes e " +
           "WHERE c.id = :cursoId")
    Curso findCursoWithEstudiantes(@Param("cursoId") Long cursoId);
    
    /**
     * Obtiene todos los cursos con sus estudiantes usando JOIN FETCH (paginado).
     * Usar con cuidado - puede generar mucho datos al cargar todas las relaciones.
     * @param pageable Configuración de paginación y ordenamiento
     * @return Página de cursos con sus estudiantes cargados
     */
    @Query("SELECT DISTINCT c FROM Curso c " +
           "LEFT JOIN FETCH c.estudiantes e " +
           "ORDER BY c.nombre ASC")
    Page<Curso> findAllCursosWithEstudiantes(Pageable pageable);
    
    /**
     * Busca cursos por nombre y carga sus estudiantes usando JOIN FETCH.
     * Filtra por nombre (case insensitive) y evita el problema N+1.
     * @param nombre Nombre o parte del nombre a buscar
     * @param pageable Configuración de paginación y ordenamiento
     * @return Página de cursos que coinciden con sus estudiantes cargados
     */
    @Query("SELECT DISTINCT c FROM Curso c " +
           "LEFT JOIN FETCH c.estudiantes e " +
           "WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) " +
           "ORDER BY c.nombre ASC")
    Page<Curso> searchCursosWithEstudiantes(@Param("nombre") String nombre, Pageable pageable);
}
