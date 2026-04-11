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
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByRole(RolesEnum role);
        
    // Búsqueda con condiciones OR y paginación- Busca en nombre, apellido y email
    @Query("SELECT u FROM Usuario u WHERE " +
           "(LOWER(u.nombre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Usuario> searchInMultipleFields(
        @Param("search") String search,
        Pageable pageable
    );
}
