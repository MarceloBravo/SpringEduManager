package com.SpringEduManager.web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.SpringEduManager.web.entities.Rol;
import com.SpringEduManager.web.enums.RolesEnum;
 
/**
 * Repositorio para la gestión de entidades Rol.
 * Proporciona métodos para buscar roles por su autoridad.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
 
    /**
     * Busca un rol por su autoridad.
     * @param authority Autoridad del rol a buscar
     * @return Optional con el rol encontrado o vacío
     */
    Optional<Rol> findByAuthority(RolesEnum authority);
}