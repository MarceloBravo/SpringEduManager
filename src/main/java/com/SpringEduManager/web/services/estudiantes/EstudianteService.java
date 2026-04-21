package com.SpringEduManager.web.services.estudiantes;

import java.util.List;

import org.springframework.data.domain.Page;

import com.SpringEduManager.web.dto.EstudianteDTO;
import com.SpringEduManager.web.dto.UserDTO;

/**
 * Interfaz de servicio para la gestión de estudiantes.
 * Define las operaciones CRUD y consultas especializadas para
 * la administración de estudiantes académicos.
 */
public interface EstudianteService {
    
    /**
     * Obtiene todos los estudiantes sin filtros.
     * @return Lista de EstudianteDTO con todos los estudiantes
     */
    List<EstudianteDTO> getAll();

    /**
     * Busca estudiantes en múltiples campos con paginación.
     * @param searchTerm Término de búsqueda para nombre, apellido y email
     * @param page Número de página
     * @param size Tamaño de página
     * @param sortBy Campo de ordenamiento
     * @return Página de estudiantes que coinciden con la búsqueda
     */
    Page<EstudianteDTO> searchInAllFields(String searchTerm, int page, int size, String sortBy);
    
    /**
     * Busca estudiantes por nombre (case insensitive)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de EstudianteDTO que coinciden con la búsqueda
     */
    List<EstudianteDTO> getAll(String nombre);
    
    /**
     * Busca un estudiante por su ID
     * @param id ID del estudiante a buscar
     * @return EstudianteDTO del estudiante encontrado o null si no existe
     */
    EstudianteDTO findById(Long id);
    
    /**
     * Busca un estudiante por su email
     * @param email Email del estudiante a buscar
     * @return EstudianteDTO del estudiante encontrado o null si no existe
     */
    EstudianteDTO findByEmail(String email);
    
    /**
     * Guarda un nuevo estudiante o actualiza uno existente
     * @param estudiante EstudianteDTO con los datos del estudiante
     * @return ID del estudiante guardado
     */
    Long save(EstudianteDTO estudiante);
    
    /**
     * Elimina un estudiante por su ID
     * @param id ID del estudiante a eliminar
     */
    void delete(Long id);
}
