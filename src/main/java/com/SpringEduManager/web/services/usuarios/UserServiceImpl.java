package com.SpringEduManager.web.services.usuarios;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.entities.Usuario;
import com.SpringEduManager.web.enums.RolesEnum;
import com.SpringEduManager.web.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios de la base de datos.
     * @return Lista de UserDTO con todos los usuarios
     */
    @Override
    public List<UserDTO> getAll(){
        List<Usuario> users = this.repository.findAll();
        return users
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getPassword(), user.getRole()))
                .toList();
    }

    /**
     * Busca usuarios por nombre (case insensitive).
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de UserDTO que coinciden con la búsqueda
     */
    @Override
    public List<UserDTO> getAll(String nombre){
        List<Usuario> users = this.repository.findByNombreContainingIgnoreCase(nombre);
        return users
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getPassword(), user.getRole()))
                .toList();
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
        return new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getPassword(), user.getRole());
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
        return new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getPassword(), user.getRole());
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
        user.setRole(_user.getRole());
        System.out.println("Usuario a guardar: " + user.getId() + " - " + user.getNombre() + " - " + user.getApellido() + " - " + user.getEmail() + " - " + user.getPassword() + " - " + user.getRole());
        System.out.println("Valor del role (enum): " + _user.getRole());
        System.out.println("Valor del role (numérico): " + (_user.getRole() != null ? _user.getRole().getRole() : "NULL"));
        return this.repository.save(user).getId();
    }

    /**
     * Registra un nuevo usuario con rol por defecto USER.
     * Asigna automáticamente el rol USER antes de guardar el usuario.
     * @param userDTO DTO con los datos del nuevo usuario a registrar
     */
    @Override
    public void register(UserDTO userDTO) {
        userDTO.setRole(RolesEnum.USER);
        this.save(userDTO);
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
}
