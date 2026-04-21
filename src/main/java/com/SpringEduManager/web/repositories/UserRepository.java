package com.SpringEduManager.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SpringEduManager.web.entities.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de entidades Usuario.
 * Proporciona métodos de consulta personalizados para buscar usuarios
 * por nombre, email, rol y búsquedas combinadas con paginación.
 */
public interface UserRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca usuarios por nombre (case insensitive).
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de usuarios que coinciden con la búsqueda
     */
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca un usuario por su email.
     * @param email Email del usuario a buscar
     * @return Optional con el usuario encontrado o vacío
     */
    Optional<Usuario> findByEmail(String email);
    
            
    /**
     * Busca usuarios en múltiples campos (nombre, apellido, email) con paginación.
     * Utiliza condiciones OR para buscar coincidencias en cualquiera de los campos.
     * @param search Término de búsqueda (case insensitive)
     * @param pageable Configuración de paginación y ordenamiento
     * @return Página de usuarios que coinciden con la búsqueda
     */
    @Query("SELECT u FROM Usuario u WHERE " +
            "NOT EXISTS (" +
            "  SELECT 1 FROM u.roles r1 WHERE r1.authority = 'STUDENT' " +
            "  AND NOT EXISTS (SELECT 1 FROM u.roles r2 WHERE r2.authority != 'STUDENT')" +
            ") AND (" +
                "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
                "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
                "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
                "EXISTS (SELECT 1 FROM u.roles r3 WHERE LOWER(r3.authority) LIKE LOWER(CONCAT('%', :search, '%')))" +
            ")")
    Page<Usuario> searchInMultipleFields(
        @Param("search") String search,
        Pageable pageable
    );

    @Query("SELECT DISTINCT e FROM Usuario e " +
        "WHERE EXISTS (SELECT 1 FROM e.roles r WHERE r.authority != 'STUDENT') " +
        "ORDER BY (" +
        "  SELECT MIN(r2.authority) FROM e.roles r2 WHERE r2.authority != 'STUDENT'" +
        ") ASC, e.nombre ASC")
    Page<Usuario> getAll(Pageable pageable);

    @Query("SELECT u FROM Usuario u " +
            "WHERE NOT EXISTS (" +
            "  SELECT 1 FROM u.roles r1 WHERE r1.authority = 'STUDENT' " +
            "  AND NOT EXISTS (SELECT 1 FROM u.roles r2 WHERE r2.authority != 'STUDENT')" +
            ") " +
            "ORDER BY (" +
            "  SELECT MIN(r.authority) FROM Usuario u2 JOIN u2.roles r WHERE u2.id = u.id AND r.authority != 'STUDENT'" +
            ") ASC, u.nombre ASC")
    Page<Usuario> findAllOrderedByRole(Pageable pageable);

    /**
     * Busca estudiantes por apellido (case insensitive)
     * @param apellido Apellido o parte del apellido a buscar
     * @return Lista de estudiantes que coinciden con la búsqueda
     */
    List<Usuario> findByApellidoContainingIgnoreCase(String apellido);
    
    // ===== MÉTODOS OPTIMIZADOS CON JOIN FETCH =====    
    /**
     * Obtiene un estudiante con sus cursos usando JOIN FETCH.
     * Evita el problema N+1 cargando los cursos en una sola consulta.
     * @param estudianteId ID del estudiante a buscar
     * @return Estudiante con sus cursos cargados
     */
    @Query("SELECT DISTINCT e FROM Usuario e " +
           "LEFT JOIN FETCH e.cursos c " +
           "WHERE e.id = :estudianteId AND " + 
           "EXISTS (SELECT 1 FROM e.roles r WHERE r.authority = 'STUDENT')")
    Usuario findEstudianteWithCursos(@Param("estudianteId") Long estudianteId);
    
    /**
     * Obtiene todos los estudiantes con sus cursos usando JOIN FETCH (paginado).
     * Usar con cuidado - puede generar mucho datos al cargar todas las relaciones.
     * @param pageable Configuración de paginación y ordenamiento
     * @return Página de estudiantes con sus cursos cargados
     */
    @Query("SELECT DISTINCT e FROM Usuario e " +
           "LEFT JOIN FETCH e.cursos c " +
           "WHERE EXISTS (SELECT 1 FROM e.roles r WHERE r.authority = 'STUDENT') " +
           "ORDER BY e.nombre ASC")
    Page<Usuario> findAllEstudiantesWithCursos(Pageable pageable);
    
    /**
     * Busca estudiantes por nombre y carga sus cursos usando JOIN FETCH.
     * Filtra por nombre (case insensitive) y evita el problema N+1.
     * @param nombre Nombre o parte del nombre a buscar
     * @param pageable Configuración de paginación y ordenamiento
     * @return Página de estudiantes que coinciden con sus cursos cargados
     */
    @Query("SELECT DISTINCT e FROM Usuario e " +
           "LEFT JOIN FETCH e.cursos c " +
           "WHERE EXISTS (SELECT 1 FROM e.roles r WHERE r.authority = 'STUDENT') AND " + 
           "LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) " +           
           "ORDER BY e.nombre ASC")
    Page<Usuario> searchEstudiantesWithCursos(@Param("nombre") String nombre, Pageable pageable);

}
