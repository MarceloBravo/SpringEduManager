package com.SpringEduManager.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringEduManager.web.entities.Usuario;
import com.SpringEduManager.web.enums.RolesEnum;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    //findAll()
    //findById()
    //save()
    //deleteById()
    
    // Métodos personalizados
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    
    List<Usuario> findByApellidoContainingIgnoreCase(String apellido);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRole(RolesEnum role);
}
