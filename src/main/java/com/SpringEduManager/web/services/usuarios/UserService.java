package com.SpringEduManager.web.services.usuarios;

import java.util.List;

import org.springframework.data.domain.Page;

import com.SpringEduManager.web.dto.UserDTO;

public interface UserService {

    //List<UserDTO> getAll();

    List<UserDTO> getAll(String nombre);

    Page<UserDTO> searchInAllFields(String searchTerm, int page, int size, String sortBy);

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    Long save(UserDTO user);
    
    void register(UserDTO userDTO);

    void delete(Long id);
}
