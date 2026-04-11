package com.SpringEduManager.web.services.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.entities.Usuario;
import com.SpringEduManager.web.enums.RolesEnum;
import com.SpringEduManager.web.repositories.UserRepository;
import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.dto.UserSearchCriteria;

/**
 * Ejemplo de servicio con paginación para usuarios
 */
@Service
public class UserServiceWithPagination {

    @Autowired
    private UserRepository userRepository;

    /**
     * Obtiene todos los usuarios con paginación
     * @param page Número de página (0-based)
     * @param size Tamaño de página
     * @param sortBy Campo para ordenar
     * @param sortDir Dirección de ordenación (ASC/DESC)
     * @return Page<UserDTO> con los usuarios paginados
     */
    public Page<UserDTO> getAllUsers(int page, int size, String sortBy, String sortDir) {
        // Crear objeto Pageable
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? 
            Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        // Obtener página desde el repository
        Page<Usuario> userPage = userRepository.findAll(pageable);
        
        // Convertir a DTO
        return userPage.map(this::convertToDTO);
    }

    /**
     * Busca usuarios por nombre con paginación
     */
    public Page<UserDTO> searchByName(String nombre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Usuario> userPage = userRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        return userPage.map(this::convertToDTO);
    }

    /**
     * Busca usuarios con múltiples filtros (versión mejorada)
     * @param nombre Filtro por nombre (opcional)
     * @param apellido Filtro por apellido (opcional)
     * @param email Filtro por email (opcional)
     * @param role Filtro por rol (opcional)
     * @param page Número de página
     * @param size Tamaño de página
     * @return Page<UserDTO> con los usuarios filtrados y paginados
     */
    public Page<UserDTO> searchWithMultipleFilters(String nombre, String apellido, String email, 
                                                  RolesEnum role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Usuario> userPage = userRepository.findWithMultipleFilters(
            (nombre == null || nombre.trim().isEmpty()) ? null : nombre.trim(),
            (apellido == null || apellido.trim().isEmpty()) ? null : apellido.trim(),
            (email == null || email.trim().isEmpty()) ? null : email.trim(),
            role,
            pageable
        );
        return userPage.map(this::convertToDTO);
    }

    /**
     * Búsqueda exacta por múltiples campos
     */
    public Page<UserDTO> findByExactFields(String nombre, String apellido, String email, 
                                          RolesEnum role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Usuario> userPage = userRepository.findByExactFields(nombre, apellido, email, role, pageable);
        return userPage.map(this::convertToDTO);
    }

    /**
     * Búsqueda en múltiples campos con condición OR
     * Busca el término en nombre, apellido o email
     */
    public Page<UserDTO> searchInAllFields(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Usuario> userPage = userRepository.searchInMultipleFields(searchTerm, pageable);
        return userPage.map(this::convertToDTO);
    }

    /**
     * Búsqueda avanzada con diferentes tipos de filtros
     */
    public Page<UserDTO> advancedSearch(UserSearchCriteria criteria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by(criteria.getSortField()).ascending());
        
        // Usar el método apropiado según los criterios
        if (criteria.isExactMatch()) {
            return findByExactFields(
                criteria.getNombre(), criteria.getApellido(), 
                criteria.getEmail(), criteria.getRole(), page, size);
        } else {
            return searchWithMultipleFilters(
                criteria.getNombre(), criteria.getApellido(), 
                criteria.getEmail(), criteria.getRole(), page, size);
        }
    }

    /**
     * Ejemplo de uso básico sin parámetros
     */
    public Page<UserDTO> getFirstPage() {
        // Página 0, 10 elementos, ordenados por nombre
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nombre").ascending());
        Page<Usuario> userPage = userRepository.findAll(pageable);
        return userPage.map(this::convertToDTO);
    }

    /**
     * Convierte Usuario a UserDTO
     */
    private UserDTO convertToDTO(Usuario usuario) {
        UserDTO dto = new UserDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setRole(usuario.getRole());
        // No incluir password en el DTO para seguridad
        return dto;
    }

    /**
     * Obtiene información de paginación
     */
    public void printPaginationInfo(Page<?> page) {
        System.out.println("=== Información de Paginación ===");
        System.out.println("Página actual: " + page.getNumber());
        System.out.println("Total páginas: " + page.getTotalPages());
        System.out.println("Total elementos: " + page.getTotalElements());
        System.out.println("Elementos en página actual: " + page.getNumberOfElements());
        System.out.println("Tamaño página: " + page.getSize());
        System.out.println("¿Es primera página?: " + page.isFirst());
        System.out.println("¿Es última página?: " + page.isLast());
        System.out.println("¿Hay siguiente página?: " + page.hasNext());
        System.out.println("¿Hay página anterior?: " + page.hasPrevious());
        System.out.println("===============================");
    }
}
