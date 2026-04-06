package com.SpringEduManager.web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringEduManager.web.entities.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    /**
     * Busca cursos por nombre (case insensitive)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de cursos que coinciden con la búsqueda
     */
    List<Curso> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Busca un curso por su nombre exacto
     * @param nombre Nombre exacto del curso a buscar
     * @return Optional con el curso encontrado o vacío
     */
    Optional<Curso> findByNombre(String nombre);
}
