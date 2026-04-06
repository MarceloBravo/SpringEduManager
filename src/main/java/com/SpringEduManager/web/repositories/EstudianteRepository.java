package com.SpringEduManager.web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringEduManager.web.entities.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
    /**
     * Busca estudiantes por nombre (case insensitive)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de estudiantes que coinciden con la búsqueda
     */
    List<Estudiante> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Busca estudiantes por apellido (case insensitive)
     * @param apellido Apellido o parte del apellido a buscar
     * @return Lista de estudiantes que coinciden con la búsqueda
     */
    List<Estudiante> findByApellidoContainingIgnoreCase(String apellido);
    
    /**
     * Busca un estudiante por su email
     * @param email Email del estudiante a buscar
     * @return Optional con el estudiante encontrado o vacío
     */
    Optional<Estudiante> findByEmail(String email);
}
