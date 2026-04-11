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

public interface UserRepository extends JpaRepository<Usuario, Long> {
    // Métodos básicos (sin paginación)
    //findAll() - retorna todos los registros
    //findById() - retorna un registro por ID
    //save() - guarda/actualiza
    //deleteById() - elimina por ID
    
    // Métodos personalizados (sin paginación)
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByApellidoContainingIgnoreCase(String apellido);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByRole(RolesEnum role);
    
    // ===== MÉTODOS CON PAGINACIÓN =====
    
    // Paginación básica - todos los usuarios
    Page<Usuario> findAll(Pageable pageable);
    
    // Paginación con filtro por nombre
    Page<Usuario> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    
    // Paginación con filtro por apellido  
    Page<Usuario> findByApellidoContainingIgnoreCase(String apellido, Pageable pageable);
    
    // Paginación con filtro por email
    Page<Usuario> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    
    // Paginación con filtro por rol
    Page<Usuario> findByRole(RolesEnum role, Pageable pageable);
    
    // Paginación con múltiples filtros (JPQL) - Versión mejorada
    @Query("SELECT u FROM Usuario u WHERE " +
           "(:nombre IS NULL OR :nombre = '' OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:apellido IS NULL OR :apellido = '' OR LOWER(u.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))) AND " +
           "(:email IS NULL OR :email = '' OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:role IS NULL OR u.role = :role)")
    Page<Usuario> findWithMultipleFilters(
        @Param("nombre") String nombre,
        @Param("apellido") String apellido, 
        @Param("email") String email,
        @Param("role") RolesEnum role,
        Pageable pageable
    );
    
    // Búsqueda exacta por múltiples campos
    @Query("SELECT u FROM Usuario u WHERE " +
           "(:nombre IS NULL OR u.nombre = :nombre) AND " +
           "(:apellido IS NULL OR u.apellido = :apellido) AND " +
           "(:email IS NULL OR u.email = :email) AND " +
           "(:role IS NULL OR u.role = :role)")
    Page<Usuario> findByExactFields(
        @Param("nombre") String nombre,
        @Param("apellido") String apellido, 
        @Param("email") String email,
        @Param("role") RolesEnum role,
        Pageable pageable
    );
    
    // Búsqueda con rango de fechas (si tuviera campo fecha)
    // @Query("SELECT u FROM Usuario u WHERE " +
    //        "u.createdAt BETWEEN :startDate AND :endDate")
    // Page<Usuario> findByDateRange(
    //     @Param("startDate") LocalDateTime startDate,
    //     @Param("endDate") LocalDateTime endDate,
    //     Pageable pageable
    // );
    
    // Búsqueda con condiciones OR
    @Query("SELECT u FROM Usuario u WHERE " +
           "(LOWER(u.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Usuario> searchInMultipleFields(
        @Param("search") String search,
        Pageable pageable
    );
}
