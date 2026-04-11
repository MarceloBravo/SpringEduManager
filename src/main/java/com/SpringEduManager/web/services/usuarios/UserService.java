package com.SpringEduManager.web.services.usuarios;

import org.springframework.data.domain.Page;

import com.SpringEduManager.web.dto.UserDTO;

public interface UserService {

    Page<UserDTO> searchInAllFields(String searchTerm, int page, int size, String sortBy);

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    Long save(UserDTO user);
    
    void register(UserDTO userDTO);

    void delete(Long id);
}
