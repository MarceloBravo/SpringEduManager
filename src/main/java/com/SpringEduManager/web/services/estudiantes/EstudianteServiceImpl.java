package com.SpringEduManager.web.services.estudiantes;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.EstudianteDTO;
import com.SpringEduManager.web.entities.Rol;
import com.SpringEduManager.web.entities.Usuario;
import com.SpringEduManager.web.enums.RolesEnum;
import com.SpringEduManager.web.repositories.EstudianteRepository;
import com.SpringEduManager.web.repositories.RolRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.PageRequest;

/**
 * Implementación del servicio para la gestión de estudiantes.
 * Proporciona lógica de negocio para CRUD y consultas especializadas
 * de estudiantes académicos con conversión a DTOs.
 */
@Service
public class EstudianteServiceImpl implements EstudianteService {

    /**
     * Repositorio para gestión de entidades Estudiante.
     */
    @Autowired
    private EstudianteRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    /**
     * Obtiene todos los estudiantes de la base de datos.
     * @return Lista de EstudianteDTO con todos los estudiantes
     */
    @Override
    public List<EstudianteDTO> getAll(){
        List<Usuario> estudiantes = this.repository.findAll();
        return estudiantes
                .stream()
                .map(estudiante -> new EstudianteDTO(
                    estudiante.getId(), 
                    estudiante.getNombre(), 
                    estudiante.getApellido(), 
                    estudiante.getEmail(),
                    null
                ))
                .toList();
    }

    /**
     * Busca estudiantes en múltiples campos con paginación.
     * @param searchTerm Término de búsqueda para nombre, apellido y email
     * @param page Número de página
     * @param size Tamaño de página
     * @param sortBy Campo de ordenamiento
     * @return Página de estudiantes que coinciden con la búsqueda
     */
    @Override
    public Page<EstudianteDTO> searchInAllFields(String searchTerm, int page, int size, String sortBy){
        sortBy = (sortBy == null || sortBy.isEmpty()) ? "nombre" : sortBy;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Usuario> estudiantePage;

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            estudiantePage = repository.getAll(pageable);
        } else {
            estudiantePage = repository.searchInMultipleFields(searchTerm, pageable);
        }

        return estudiantePage.map(estudiante -> new EstudianteDTO(
            estudiante.getId(),
            estudiante.getNombre(),
            estudiante.getApellido(),
            estudiante.getEmail(),
            null
        ));
    }

    /**
     * Busca estudiantes por nombre (case insensitive).
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de EstudianteDTO que coinciden con la búsqueda
     */
    @Override
    public List<EstudianteDTO> getAll(String nombre){
        List<Usuario> estudiantes = this.repository.findByNombreContainingIgnoreCase(nombre);
        return estudiantes
                .stream()
                .map(estudiante -> new EstudianteDTO(
                    estudiante.getId(), 
                    estudiante.getNombre(), 
                    estudiante.getApellido(), 
                    estudiante.getEmail(),
                    null
                ))
                .toList();
    }

    /**
     * Busca un estudiante por su ID.
     * @param id ID del estudiante a buscar
     * @return EstudianteDTO del estudiante encontrado o null si no existe
     */
    @Override
    public EstudianteDTO findById(Long id){
        Usuario estudiante = this.repository.findById(id).orElse(null);
        if(estudiante == null){
            return null;
        }
        return new EstudianteDTO(
            estudiante.getId(), 
            estudiante.getNombre(), 
            estudiante.getApellido(), 
            estudiante.getEmail(),
            null
        );
    }

    /**
     * Busca un estudiante por su email.
     * @param email Email del estudiante a buscar
     * @return EstudianteDTO del estudiante encontrado o null si no existe
     */
    @Override
    public EstudianteDTO findByEmail(String email){
        Usuario estudiante = this.repository.findByEmail(email).orElse(null);
        if(estudiante == null){
            return null;
        }
        return new EstudianteDTO(
            estudiante.getId(), 
            estudiante.getNombre(), 
            estudiante.getApellido(), 
            estudiante.getEmail(),
            null
        );
    }

    /**
     * Guarda un nuevo estudiante o actualiza uno existente.
     * @param _estudiante EstudianteDTO con los datos del estudiante
     * @return ID del estudiante guardado
     * @throws RuntimeException si hay errores de validación
     */
    @Override
    public Long save(EstudianteDTO _estudiante){
        String password = validaPassword(_estudiante);
        validaEmail(_estudiante);
        Usuario estudiante = new Usuario();
        if(_estudiante.getId() != null){
            Usuario existing = this.repository.findById(_estudiante.getId()).orElse(null);            
            estudiante.setId(existing.getId());
        }
        estudiante.setNombre(_estudiante.getNombre());
        estudiante.setApellido(_estudiante.getApellido());
        estudiante.setEmail(_estudiante.getEmail());
        estudiante.setPassword(password);        
        // Buscar o crear el rol STUDENT existente
        Rol studentRol = rolRepository.findByAuthority(RolesEnum.STUDENT)
                .orElseGet(() -> rolRepository.save(new Rol(RolesEnum.STUDENT)));
        estudiante.setRoles(Set.of(studentRol));
        Usuario saved = this.repository.save(estudiante);
        
        if(saved == null){
            String accion = _estudiante.getId() != null ? "actualizar" : "registrar";
            throw new RuntimeException("Error al " + accion + " el estudiante.");
        }
        return saved.getId();
    }

    /**
     * Elimina un estudiante por su ID.
     * @param id ID del estudiante a eliminar
     * @throws RuntimeException si el estudiante no existe
     */
    @Override
    public void delete(Long id){
        Usuario usuario = this.repository.findById(id).orElse(null);
        if(usuario == null){
            throw new RuntimeException("Estudiante no encontrado");
        }
        this.repository.delete(usuario);
    }


    /**
     * Valida que el email no esté duplicado.
     * @param _user UserDTO con el email a validar
     * @throws RuntimeException si el email ya está registrado por otro usuario
     */
    private void validaEmail(EstudianteDTO _user){
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
    private String validaPassword(EstudianteDTO _user){
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
