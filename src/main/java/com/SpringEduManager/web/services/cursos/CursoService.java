package com.SpringEduManager.web.services.cursos;

import java.util.List;

import com.SpringEduManager.web.dto.CursoDTO;

public interface CursoService {
    
    /**
     * Obtiene todos los cursos
     * @return Lista de CursoDTO con todos los cursos
     */
    List<CursoDTO> getAll();
    
    /**
     * Busca cursos por nombre (case insensitive)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de CursoDTO que coinciden con la búsqueda
     */
    List<CursoDTO> getAll(String nombre);
    
    /**
     * Busca un curso por su ID
     * @param id ID del curso a buscar
     * @return CursoDTO del curso encontrado o null si no existe
     */
    CursoDTO findById(Long id);
    
    /**
     * Busca un curso por su nombre exacto
     * @param nombre Nombre exacto del curso a buscar
     * @return CursoDTO del curso encontrado o null si no existe
     */
    CursoDTO findByNombre(String nombre);
    
    /**
     * Guarda un nuevo curso o actualiza uno existente
     * @param curso CursoDTO con los datos del curso
     * @return ID del curso guardado
     */
    Long save(CursoDTO curso);
    
    /**
     * Elimina un curso por su ID
     * @param id ID del curso a eliminar
     */
    void delete(Long id);
}
