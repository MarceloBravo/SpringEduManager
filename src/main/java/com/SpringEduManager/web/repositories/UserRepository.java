package com.SpringEduManager.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SpringEduManager.web.entities.Usuario;
import com.SpringEduManager.web.enums.RolesEnum;

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
     * Busca usuarios por su rol.
     * @param role Rol del usuario a filtrar
     * @return Lista de usuarios con el rol especificado
     */
    List<Usuario> findByRole(RolesEnum role);
        
    /**
     * Busca usuarios en múltiples campos (nombre, apellido, email) con paginación.
     * Utiliza condiciones OR para buscar coincidencias en cualquiera de los campos.
     * @param search Término de búsqueda (case insensitive)
     * @param pageable Configuración de paginación y ordenamiento
     * @return Página de usuarios que coinciden con la búsqueda
     */
    @Query("SELECT u FROM Usuario u WHERE " +
           "(LOWER(u.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "(LOWER(u.apellido) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))))")
    Page<Usuario> searchInMultipleFields(
        @Param("search") String search,
        Pageable pageable
    );
}
