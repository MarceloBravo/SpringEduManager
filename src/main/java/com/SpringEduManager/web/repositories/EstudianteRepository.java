package com.SpringEduManager.web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SpringEduManager.web.entities.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
    /**
     * Busca estudiantes por nombre (case insensitive)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de estudiantes que coinciden con la búsqueda
     */
    List<Estudiante> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Busca estudiantes por apellido (case insensitive)
     * @param apellido Apellido o parte del apellido a buscar
     * @return Lista de estudiantes que coinciden con la búsqueda
     */
    List<Estudiante> findByApellidoContainingIgnoreCase(String apellido);
    
    /**
     * Busca un estudiante por su email
     * @param email Email del estudiante a buscar
     * @return Optional con el estudiante encontrado o vacío
     */
    Optional<Estudiante> findByEmail(String email);

    // Búsqueda con condiciones OR y paginación - Busca en nombre, apellido y email
    @Query("SELECT e FROM Estudiante e WHERE " +
           "(LOWER(e.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.apellido) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Estudiante> searchInMultipleFields(
        @Param("search") String search,
        Pageable pageable
    );
}
