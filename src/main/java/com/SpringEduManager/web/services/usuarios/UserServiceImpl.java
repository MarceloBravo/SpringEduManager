package com.SpringEduManager.web.services.usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.entities.Usuario;
import com.SpringEduManager.web.repositories.UserRepository;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAll(){
        List<Usuario> users = this.repository.findAll();
        return users
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getPassword(), user.getRole()))
                .toList();
    }

    @Override
    public List<UserDTO> getAll(String nombre){
        List<Usuario> users = this.repository.findByNombreContainingIgnoreCase(nombre);
        return users
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getPassword(), user.getRole()))
                .toList();
    }

    @Override
    public UserDTO findById(Long id){
        Usuario user = this.repository.findById(id).orElse(null);
        if(user == null){
            return null;
        }
        return new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getPassword(), user.getRole());
    }

    @Override
    public UserDTO findByEmail(String email){
        Usuario user = this.repository.findByEmail(email).orElse(null);
        if(user == null){
            return null;
        }
        return new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getPassword(), user.getRole());
    }

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
        return this.repository.save(user).getId();
    }

    @Override
    public void delete(Long id){
        Usuario user = this.repository.findById(id).orElse(null);
        if(user == null){
            throw new RuntimeException("El usuario no existe.");
        }
        this.repository.delete(user);
    }
    

    /* **** Funciones de validación de datos **** */

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

    private void validaEmail(UserDTO _user){
        // Regex estándar para validación de email
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(_user.getEmail());
        
        if(!matcher.matches()){
            throw new RuntimeException("El email no es válido.");
        }

        Usuario isEmailExists = this.repository.findByEmail(_user.getEmail()).orElse(null);
        if(isEmailExists != null && isEmailExists.getId() != _user.getId()){
            throw new RuntimeException("El email ya está registrado.");
        }
    }

    private String validaPassword(UserDTO _user){
        String password = null;
        Usuario user = this.repository.findById(_user.getId()).orElse(null);
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
