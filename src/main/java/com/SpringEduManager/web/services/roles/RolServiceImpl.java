package com.SpringEduManager.web.services.roles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.RolDTO;
import com.SpringEduManager.web.entities.Rol;
import com.SpringEduManager.web.enums.RolesEnum;
import com.SpringEduManager.web.repositories.RolRepository;

/**
 * Implementación del servicio para la gestión de roles del sistema.
 * Proporciona lógica de negocio para operaciones CRUD y consultas
 * especializadas de roles con conversión a DTOs.
 * 
 * @author SpringEduManager
 * @version 1.0
 * @since 2025
 */
@Service
public class RolServiceImpl implements RolService {

    /**
     * Repositorio para gestión de entidades Rol.
     */
    @Autowired
    private RolRepository rolRepository;

    /**
     * Busca roles en múltiples campos con paginación y ordenamiento.
     * Permite búsqueda por authority con paginación y ordenamiento configurable.
     * @param searchTerm Término de búsqueda (case insensitive)
     * @param page Número de página
     * @param size Tamaño de página
     * @param sortBy Campo de ordenamiento
     * @return Página de roles que coinciden con la búsqueda
     */
    @Override
    public Page<RolDTO> searchInAllFields(String searchTerm, int page, int size, String sortBy) {
        sortBy = (sortBy != null && sortBy.isEmpty()) ? "authority" : sortBy;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Rol> rolPage;
        if(searchTerm == null || searchTerm.trim().isEmpty()) {
            rolPage = rolRepository.findAll(pageable);
        } else {
            rolPage = rolRepository.searchInMultipleFields(searchTerm, pageable);
        }

        return rolPage.map(rol -> new RolDTO(rol.getId(), rol.getAuthority().name()));
    }

    /**
     * Obtiene todos los roles de la base de datos.
     * @return Lista de RolDTO con todos los roles
     */
    @Override
    public List<RolDTO> findAll() {
        return rolRepository.findAll().stream().map(rol -> new RolDTO(rol.getId(), rol.getAuthority().name())).toList();
    }

    /**
     * Busca un rol por su authority.
     * @param authority Authority del rol a buscar
     * @return Optional con el rol encontrado o vacío si no existe
     */
    @Override
    public Optional<Rol> findByAuthority(RolesEnum authority) {
        return rolRepository.findByAuthority(authority);
    }

    /**
     * Busca un rol por su ID.
     * @param id ID del rol a buscar
     * @return RolDTO del rol encontrado o null si no existe
     */
    @Override
    public RolDTO findById(Long id){
        Rol rol = rolRepository.findById(id).orElse(null);
        if(rol == null){
            return null;
        }
        return new RolDTO(rol.getId(), rol.getAuthority().name());
    }
    
    /**
     * Guarda un nuevo rol o actualiza uno existente.
     * Convierte el DTO a entidad y persiste en la base de datos.
     * @param rol RolDTO con los datos del rol
     * @return RolDTO del rol guardado con ID asignado
     * @throws RuntimeException si hay errores de validación
     */
    @Override
    public RolDTO save(RolDTO rol){
        Rol rolExists = rol.getId() != null ? rolRepository.findById(rol.getId()).orElse(null) : new Rol();
        rolExists.setAuthority(RolesEnum.valueOf(rol.getNombre()));
        rolRepository.save(rolExists);
        return new RolDTO(rolExists.getId(), rolExists.getAuthority().name());
    }

    /**
     * Elimina un rol por su ID.
     * Verifica que el rol exista antes de eliminarlo.
     * @param id ID del rol a eliminar
     * @throws RuntimeException si el rol no existe
     */
    @Override
    public void deleteById(Long id){
        Rol rol = rolRepository.findById(id).orElse(null);
        if(rol == null){
            throw new RuntimeException("El usuario no existe.");
        }
        rolRepository.delete(rol);
    }

    /**
     * Obtiene los nombres de los roles del enum que no existen en la base de datos.
     * Compara los valores de RolesEnum contra los roles persistidos.
     * @return Lista de nombres de roles (como String) que faltan en la base de datos
     */
    public List<String> getMissingRoles() {
        // Obtener todos los roles existentes en la base de datos
        List<String> existingRoles = rolRepository.findAllRoles();
        
        // Obtener todos los roles definidos en el enum
        List<String> allEnumRoles = RolesEnum.getAllRoleNames();
        
        // Filtrar los roles del enum que no existen en la base de datos
        return allEnumRoles.stream()
                .filter(roleName -> !existingRoles.contains(roleName))
                .collect(Collectors.toList());
    }
}
