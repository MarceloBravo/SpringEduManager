package com.SpringEduManager.web.services.cursos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.CursoDTO;
import com.SpringEduManager.web.entities.Curso;
import com.SpringEduManager.web.repositories.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository repository;

    /**
     * Obtiene todos los cursos de la base de datos.
     * @return Lista de CursoDTO con todos los cursos
     */
    @Override
    public List<CursoDTO> getAll(){
        List<Curso> cursos = this.repository.findAll();
        return cursos
                .stream()
                .map(curso -> new CursoDTO(
                    curso.getId(), 
                    curso.getNombre(), 
                    curso.getDescripcion()
                ))
                .toList();
    }


    public Page<CursoDTO> searchInAllFields(String searchTerm, int page, int size, String sortBy){
        sortBy = (sortBy == null || sortBy.isEmpty()) ? "nombre" : sortBy;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Curso> userPage;
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            userPage = repository.findAll(pageable);
        } else {
            userPage = repository.searchInMultipleFields(searchTerm, pageable);
        }
        
        return userPage.map(this::convertToDTO);
    }



    /**
     * Busca cursos por nombre (case insensitive).
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de CursoDTO que coinciden con la búsqueda
     */
    @Override
    public List<CursoDTO> getAll(String nombre){
        List<Curso> cursos = this.repository.findByNombreContainingIgnoreCase(nombre);
        return cursos
                .stream()
                .map(curso -> new CursoDTO(
                    curso.getId(), 
                    curso.getNombre(), 
                    curso.getDescripcion()
                ))
                .toList();
    }

    /**
     * Busca un curso por su ID.
     * @param id ID del curso a buscar
     * @return CursoDTO del curso encontrado o null si no existe
     */
    @Override
    public CursoDTO findById(Long id){
        Curso curso = this.repository.findById(id).orElse(null);
        if(curso == null){
            return null;
        }
        return new CursoDTO(
            curso.getId(), 
            curso.getNombre(), 
            curso.getDescripcion()
        );
    }

    /**
     * Busca un curso por su nombre exacto.
     * @param nombre Nombre exacto del curso a buscar
     * @return CursoDTO del curso encontrado o null si no existe
     */
    @Override
    public CursoDTO findByNombre(String nombre){
        Curso curso = this.repository.findByNombre(nombre).orElse(null);
        if(curso == null){
            return null;
        }
        return new CursoDTO(
            curso.getId(), 
            curso.getNombre(), 
            curso.getDescripcion()
        );
    }

    /**
     * Guarda un nuevo curso o actualiza uno existente.
     * @param _curso CursoDTO con los datos del curso
     * @return ID del curso guardado
     * @throws RuntimeException si hay errores de validación
     */
    @Override
    public Long save(CursoDTO _curso){
        this.validaDatosObligatorios(_curso);
        this.validaNombreDuplicado(_curso);

        Curso curso = new Curso();
        if(_curso.getId() != null){
            curso.setId(_curso.getId());
        }
        curso.setNombre(_curso.getNombre());
        curso.setDescripcion(_curso.getDescripcion());
        return this.repository.save(curso).getId();
    }

    /**
     * Elimina un curso por su ID.
     * @param id ID del curso a eliminar
     * @throws RuntimeException si el curso no existe
     */
    @Override
    public void delete(Long id){
        Curso curso = this.repository.findById(id).orElse(null);
        if(curso == null){
            throw new RuntimeException("El curso no existe.");
        }
        this.repository.delete(curso);
    }
    

    /**
     * Valida que los campos obligatorios del curso no estén vacíos.
     * @param curso CursoDTO a validar
     * @throws RuntimeException si hay campos obligatorios vacíos
     */
    private void validaDatosObligatorios(CursoDTO curso){
        boolean isOk = true;
        if(curso.getNombre() == null || curso.getNombre().trim().isEmpty()){
            isOk = false;
        }
        if(!isOk){
            throw new RuntimeException("Datos incompletos o no válidos");
        }
    }

    /**
     * Valida que el nombre del curso no esté duplicado.
     * @param _curso CursoDTO con el nombre a validar
     * @throws RuntimeException si el nombre ya está registrado por otro curso
     */
    private void validaNombreDuplicado(CursoDTO _curso){
        Curso isNombreExists = this.repository.findByNombre(_curso.getNombre()).orElse(null);
        if(isNombreExists != null && isNombreExists.getId() != _curso.getId()){
            throw new RuntimeException("El nombre del curso ya está registrado.");
        }
    }

    /**
     * Convierte Usuario a UserDTO
     */
    private CursoDTO convertToDTO(Curso curso) {
        CursoDTO dto = new CursoDTO();
        dto.setId(curso.getId());
        dto.setNombre(curso.getNombre());
        dto.setDescripcion(curso.getDescripcion());
        return dto;
    }
}
