package com.SpringEduManager.web.services.usuarios;

import java.util.List;

import org.springframework.data.domain.Page;

import com.SpringEduManager.web.dto.UserDTO;

/**
 * Interfaz de servicio para la gestión de usuarios del sistema.
 * Define las operaciones CRUD y consultas especializadas para
 * la administración de usuarios y autenticación.
 */
public interface UserService {

    /**
     * Obtiene todos los usuarios o filtra por nombre.
     * @param nombre Nombre o parte del nombre a buscar (case insensitive)
     * @return Lista de UserDTO que coinciden con la búsqueda
     */
    List<UserDTO> getAll(String nombre);

    /**
     * Busca usuarios en múltiples campos con paginación.
     * @param searchTerm Término de búsqueda para nombre, apellido y email
     * @param page Número de página
     * @param size Tamaño de página
     * @param sortBy Campo de ordenamiento
     * @return Página de usuarios que coinciden con la búsqueda
     */
    Page<UserDTO> searchInAllFields(String searchTerm, int page, int size, String sortBy);

    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario a buscar
     * @return UserDTO del usuario encontrado o null si no existe
     */
    UserDTO findById(Long id);

    /**
     * Busca un usuario por su email.
     * @param email Email del usuario a buscar
     * @return UserDTO del usuario encontrado o null si no existe
     */
    UserDTO findByEmail(String email);

    /**
     * Guarda un nuevo usuario o actualiza uno existente.
     * @param user UserDTO con los datos del usuario
     * @return ID del usuario guardado
     */
    Long save(UserDTO user);
    
    /**
     * Registra un nuevo usuario con validaciones adicionales.
     * @param userDTO UserDTO con los datos del nuevo usuario
     */
    void register(UserDTO userDTO);

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar
     */
    void delete(Long id);
}
