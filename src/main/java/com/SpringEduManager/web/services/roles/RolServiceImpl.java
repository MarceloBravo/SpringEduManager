package com.SpringEduManager.web.services.roles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.RolDTO;
import com.SpringEduManager.web.entities.Rol;
import com.SpringEduManager.web.enums.RolesEnum;
import com.SpringEduManager.web.repositories.RolRepository;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<RolDTO> findAll() {
        return rolRepository.findAll().stream().map(rol -> new RolDTO(rol.getId(), rol.getAuthority().name())).toList();
    }

    @Override
    public Optional<Rol> findByAuthority(RolesEnum authority) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
    
}
