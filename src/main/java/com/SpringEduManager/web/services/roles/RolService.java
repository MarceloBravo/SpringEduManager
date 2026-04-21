package com.SpringEduManager.web.services.roles;

import com.SpringEduManager.web.dto.RolDTO;
import com.SpringEduManager.web.entities.Rol;
import com.SpringEduManager.web.enums.RolesEnum;

import java.util.List;
import java.util.Optional;

public interface RolService {
    // Rol save(Rol rol);
    // void deleteById(Long id);
    List<RolDTO> findAll();

    Optional<Rol> findByAuthority(RolesEnum authority);
}
