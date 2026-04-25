package com.SpringEduManager.web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
 
import com.SpringEduManager.web.entities.Rol;
import com.SpringEduManager.web.enums.RolesEnum;
 
/**
 * Repositorio para la gestión de entidades Rol.
 * Proporciona métodos CRUD personalizados y consultas especializadas
 * para la búsqueda y gestión de roles en el sistema.
 * 
 * @author SpringEduManager
 * @version 1.0
 * @since 2025
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
 
    /**
     * Busca un rol por su autoridad (authority).
     * Método derivado de Spring Data JPA para búsqueda específica.
     * @param authority Autoridad del rol a buscar (enum RolesEnum)
     * @return Optional con el rol encontrado o vacío si no existe
     */
    Optional<Rol> findByAuthority(RolesEnum authority);
    
    /**
     * Busca roles que contengan el término de búsqueda en el campo authority.
     * Utiliza consulta JPQL personalizada para búsqueda case-insensitive.
     * @param searchTerm Término de búsqueda a buscar en el authority
     * @param pageable Configuración de paginación y ordenamiento
     * @return Page con los roles que coinciden con la búsqueda
     */
    @Query("SELECT r FROM Rol r WHERE LOWER(r.authority) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Rol> searchInMultipleFields(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Obtiene todos los roles existentes en la base de datos.
     * @return Lista de nombres de roles que están persistidos
     */
    @Query(value = "SELECT DISTINCT authority FROM roles", nativeQuery = true)
    List<String> findAllRoles();
}