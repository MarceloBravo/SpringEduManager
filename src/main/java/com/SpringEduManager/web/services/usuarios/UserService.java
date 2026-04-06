package com.SpringEduManager.web.services.usuarios;

import java.util.List;

import com.SpringEduManager.web.dto.UserDTO;

public interface UserService {

    List<UserDTO> getAll();

    List<UserDTO> getAll(String nombre);

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    Long save(UserDTO user);

    void delete(Long id);
}
