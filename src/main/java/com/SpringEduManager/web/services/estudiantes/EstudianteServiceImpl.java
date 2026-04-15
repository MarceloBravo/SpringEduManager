package com.SpringEduManager.web.services.estudiantes;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.EstudianteDTO;
import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.entities.Estudiante;
import com.SpringEduManager.web.enums.RolesEnum;
import com.SpringEduManager.web.repositories.EstudianteRepository;
import com.SpringEduManager.web.services.usuarios.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    /**
     * Servicio de usuarios para gestión de cuentas asociadas.
     */
    @Autowired
    private UserService userService;

    /**
     * Obtiene todos los estudiantes de la base de datos.
     * @return Lista de EstudianteDTO con todos los estudiantes
     */
    @Override
    public List<EstudianteDTO> getAll(){
        List<Estudiante> estudiantes = this.repository.findAll();
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
        Page<Estudiante> estudiantePage;

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            estudiantePage = repository.findAll(pageable);
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
        List<Estudiante> estudiantes = this.repository.findByNombreContainingIgnoreCase(nombre);
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
        Estudiante estudiante = this.repository.findById(id).orElse(null);
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
        Estudiante estudiante = this.repository.findByEmail(email).orElse(null);
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
        this.validaDatosObligatorios(_estudiante);
        this.validaEmail(_estudiante);

        Estudiante estudiante = new Estudiante();
        if(_estudiante.getId() != null){
            estudiante.setId(_estudiante.getId());
        }
        estudiante.setNombre(_estudiante.getNombre());
        estudiante.setApellido(_estudiante.getApellido());
        estudiante.setEmail(_estudiante.getEmail());
        Long id = this.repository.save(estudiante).getId();

        if(id != null){
            crearUsuario(_estudiante);
        }else{
            String accion = estudiante.getId() != null ? "actualizar" : "registrar";
            throw new RuntimeException("Error al " + accion + " el estudiante.");
        }
        return id;
    }

    /**
     * Elimina un estudiante por su ID.
     * @param id ID del estudiante a eliminar
     * @throws RuntimeException si el estudiante no existe
     */
    @Override
    public void delete(Long id){
        Estudiante estudiante = this.repository.findById(id).orElse(null);
        if(estudiante == null){
            throw new RuntimeException("El estudiante no existe.");
        }
        this.repository.delete(estudiante);
    }
    

    /**
     * Valida que los campos obligatorios del estudiante no estén vacíos.
     * @param estudiante EstudianteDTO a validar
     * @throws RuntimeException si hay campos obligatorios vacíos
     */
    private void validaDatosObligatorios(EstudianteDTO estudiante){
        if(estudiante.getId() == null && (estudiante.getPassword() == null || estudiante.getPassword().trim().isEmpty())){
            throw new RuntimeException("La contraseña es obligatoria.");
        }

        boolean isOk = true;
        if(estudiante.getNombre() == null || estudiante.getNombre().trim().isEmpty()){
            isOk = false;
        }
        if(estudiante.getApellido() == null || estudiante.getApellido().trim().isEmpty()){
            isOk = false;
        }
        if(estudiante.getEmail() == null || estudiante.getEmail().trim().isEmpty()){
            isOk = false;
        }
        if(!isOk){
            throw new RuntimeException("Datos incompletos o no válidos");
        }
    }

    /**
     * Valida que el email no esté duplicado y tenga formato válido.
     * @param _estudiante EstudianteDTO con el email a validar
     * @throws RuntimeException si el email ya está registrado por otro estudiante o no es válido
     */
    private void validaEmail(EstudianteDTO _estudiante){
        Estudiante isEmailExists = this.repository.findByEmail(_estudiante.getEmail()).orElse(null);
        if(isEmailExists != null && !Objects.equals(isEmailExists.getId(), _estudiante.getId())){
            throw new RuntimeException("El email ya está registrado.");
        }
    }

    private void crearUsuario(EstudianteDTO estudiante){
        UserDTO userExists = this.userService.findByEmail(estudiante.getEmail());
        if(userExists != null){
            return;
        }
        UserDTO user = new UserDTO();
        user.setNombre(estudiante.getNombre());
        user.setApellido(estudiante.getApellido());
        user.setEmail(estudiante.getEmail());
        user.setRole(RolesEnum.STUDENT);
        if(estudiante.getPassword() != null && !estudiante.getPassword().trim().isEmpty()){
            user.setPassword(estudiante.getPassword());
        }
        Long userId = this.userService.save(user);
        if(userId == null){
            throw new RuntimeException("Error al crear el usuario para el estudiante.");
        }
    }
}
