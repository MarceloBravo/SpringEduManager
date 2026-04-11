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


    // Búsqueda con condiciones OR y paginación - Busca en nombre y descripcion
    @Query("SELECT c FROM Curso c WHERE " +
           "(LOWER(c.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Curso> searchInMultipleFields(
        @Param("search") String search,
        Pageable pageable
    );
}
