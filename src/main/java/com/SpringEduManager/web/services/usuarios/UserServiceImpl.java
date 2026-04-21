package com.SpringEduManager.web.services.usuarios;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.entities.Rol;
import com.SpringEduManager.web.entities.Usuario;
import com.SpringEduManager.web.enums.RolesEnum;
import com.SpringEduManager.web.repositories.RolRepository;
import com.SpringEduManager.web.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

/**
 * Implementación del servicio para la gestión de usuarios del sistema.
 * Proporciona lógica de negocio para CRUD, autenticación y
 * gestión de roles con conversión a DTOs.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Repositorio para gestión de entidades Usuario.
     */
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private RolRepository rolRepository;
    
    /**
     * Codificador de contraseñas para seguridad.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios o filtra por nombre.
     * @param nombre Nombre o parte del nombre a buscar (case insensitive)
     * @return Lista de UserDTO que coinciden con la búsqueda
     */
    @Override
    public List<UserDTO> getAll(String nombre){
        List<Usuario> usuarios;
        if(nombre == null || nombre.isEmpty()){
            usuarios = this.repository.findAll();
        }else{
            usuarios = this.repository.findByNombreContainingIgnoreCase(nombre);
        }
        return usuarios
                .stream()
                .map(usuario -> new UserDTO(
                    usuario.getId(), 
                    usuario.getNombre(), 
                    usuario.getApellido(),
                    usuario.getEmail(),
                    usuario.getPassword(),
                    usuario.getRoles()
                        .stream()
                        .map(Rol::getAuthority)
                        .collect(Collectors.toSet())
                ))
                .toList();
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     * @return Lista de UserDTO con todos los usuarios
     */
    @Override
    public Page<UserDTO> searchInAllFields(String searchTerm, int page, int size, String sortBy){
        sortBy = (sortBy == null || sortBy.isEmpty()) ? "nombre" : sortBy;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Usuario> userPage;
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            userPage = repository.getAll(pageable);
        } else {
            userPage = repository.searchInMultipleFields(searchTerm, pageable);
        }
        
        return userPage.map(this::convertToDTO);
    }

    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario a buscar
     * @return UserDTO del usuario encontrado o null si no existe
     */
    @Override
    public UserDTO findById(Long id){
        Usuario user = this.repository.findById(id).orElse(null);
        if(user == null){
            return null;
        }
        Set<RolesEnum> roles = user.getRoles().stream().map(Rol::getAuthority).collect(Collectors.toSet());
        return new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getPassword(), roles);
    }

    /**
     * Busca un usuario por su email.
     * @param email Email del usuario a buscar
     * @return UserDTO del usuario encontrado o null si no existe
     */
    @Override
    public UserDTO findByEmail(String email){
        Usuario user = this.repository.findByEmail(email).orElse(null);
        if(user == null){
            return null;
        }
        Set<RolesEnum> roles = user.getRoles().stream().map(Rol::getAuthority).collect(Collectors.toSet());
        return new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getPassword(), roles);
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente.
     * Para nuevos usuarios: el password es obligatorio.
     * Para actualizaciones: el password es opcional (si no se envía, mantiene el actual).
     * @param _user UserDTO con los datos del usuario
     * @return ID del usuario guardado
     * @throws RuntimeException si hay errores de validación
     */
    @Override
    public Long save(UserDTO _user){
        this.validaDatosObligatorios(_user);
        this.validaEmail(_user);
        String pwd = this.validaPassword(_user);
        Usuario user = new Usuario();
        if(_user.getId() != null){
            user.setId(_user.getId());
        }
        user.setNombre(_user.getNombre());
        user.setApellido(_user.getApellido());
        user.setEmail(_user.getEmail());
        user.setPassword(pwd);
        // Buscar o crear roles existentes
        Set<Rol> roles = _user.getRoles().stream()
                .map((RolesEnum roleEnum) -> {
                    // Buscar si el rol ya existe
                    return rolRepository.findByAuthority(roleEnum)
                            .orElseGet(() -> rolRepository.save(new Rol(roleEnum)));
                })
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return this.repository.save(user).getId();
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar
     * @throws RuntimeException si el usuario no existe
     */
    @Override
    public void delete(Long id){
        Usuario user = this.repository.findById(id).orElse(null);
        if(user == null){
            throw new RuntimeException("El usuario no existe.");
        }
        this.repository.delete(user);
    }
    

    /* **** Funciones de validación de datos **** */

    /**
     * Valida que los campos obligatorios del usuario no estén vacíos.
     * @param user UserDTO a validar
     * @throws RuntimeException si hay campos obligatorios vacíos
     */
    private void validaDatosObligatorios(UserDTO user){
        boolean isOk = true;
        if(user.getNombre() == null || user.getNombre().trim().isEmpty()){
            isOk = false;
        }
        if(user.getApellido() == null || user.getApellido().trim().isEmpty()){
            isOk = false;
        }
        if(user.getEmail() == null || user.getEmail().trim().isEmpty()){
            isOk = false;
        }
        if(user.getId() == null && (user.getPassword() == null || user.getPassword().trim().isEmpty())){
            throw new RuntimeException("El password no puede estar vacio");
        }
        if(!isOk){
            throw new RuntimeException("Datos incompletos o no válidos");
        }
    }

    /**
     * Valida que el email no esté duplicado.
     * @param _user UserDTO con el email a validar
     * @throws RuntimeException si el email ya está registrado por otro usuario
     */
    private void validaEmail(UserDTO _user){
        Usuario isEmailExists = this.repository.findByEmail(_user.getEmail()).orElse(null);
        if(isEmailExists != null && !Objects.equals(isEmailExists.getId(), _user.getId())){
            throw new RuntimeException("El email ya está registrado.");
        }
    }

    /**
     * Valida y procesa el password del usuario.
     * Para actualizaciones: si no se envía password, usa el existente.
     * Para nuevos usuarios: el password es obligatorio.
     * @param _user UserDTO con los datos del usuario
     * @return Password codificado o password existente
     * @throws RuntimeException si el password es obligatorio y no se proporcionó
     */
    private String validaPassword(UserDTO _user){
        Usuario user = null;
        String password = null;
        if(_user.getId() == null && _user.getPassword() != null && !_user.getPassword().trim().isEmpty()){
            return passwordEncoder.encode(_user.getPassword());
        }
        if(_user.getId() != null){
            user = this.repository.findById(_user.getId()).orElse(null);
        }
        if(
            user != null && 
            user.getPassword() != null && 
            !user.getPassword().trim().isEmpty() &&
           (_user.getPassword() == null || _user.getPassword().trim().isEmpty()) 
        ){
            password =  user.getPassword();
        }else{
            password = passwordEncoder.encode(_user.getPassword());
        }
        if(password == null){
            throw new RuntimeException("El password no puede estar vacio");
        }
        return password;
    }


    /**
     * Obtiene todos los usuarios ordenados por rol.
     * @param page Número de página
     * @param size Tamaño de página
     * @return Página de usuarios ordenados por rol
     */
    @Override
    public Page<UserDTO> findAllOrderedByRole(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> userPage = this.repository.findAllOrderedByRole(pageable);
        return userPage.map(this::convertToDTO);
    }

    /**
     * Convierte Usuario a UserDTO sin password
     * @param usuario Usuario a convertir
     * @return UserDTO sin password para ser devuelto en la API
     */
    private UserDTO convertToDTO(Usuario usuario) {
        UserDTO dto = new UserDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        Set<RolesEnum> roles = usuario.getRoles().stream().map((Rol e) -> RolesEnum.valueOf(e.getAuthority().name())).collect(Collectors.toSet());
        dto.setRoles(roles);
        // No incluir password en el DTO para seguridad
        return dto;
    }
}
