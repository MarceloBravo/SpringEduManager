package com.SpringEduManager.web.services.roles;

import com.SpringEduManager.web.dto.RolDTO;
import com.SpringEduManager.web.entities.Rol;
import com.SpringEduManager.web.enums.RolesEnum;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RolService {
    // Rol save(Rol rol);
    // void deleteById(Long id);
    Page<RolDTO> searchInAllFields(String searchTerm, int page, int size, String sortBy);

    List<RolDTO> findAll();

    Optional<Rol> findByAuthority(RolesEnum authority);

    RolDTO findById(Long id);

    RolDTO save(RolDTO rol);

    void deleteById(Long id);

    /**
     * Obtiene los nombres de los roles del enum que no existen en la base de datos.
     * @return Lista de nombres de roles que faltan en la base de datos
     */
    List<String> getMissingRoles();
}
