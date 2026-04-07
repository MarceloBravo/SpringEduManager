package com.SpringEduManager.web.services.EstudiantesCursos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.EstudianteCursoDTO;
import com.SpringEduManager.web.entities.Curso;
import com.SpringEduManager.web.entities.Estudiante;
import com.SpringEduManager.web.entities.EstudianteCurso;
import com.SpringEduManager.web.repositories.CursoRepository;
import com.SpringEduManager.web.repositories.EstudianteCursoRepository;
import com.SpringEduManager.web.repositories.EstudianteRepository;

/**
 * Implementación del servicio para la gestión de asignaciones estudiante-curso.
 * Proporciona lógica de negocio para CRUD y validaciones de relaciones
 * entre estudiantes y cursos.
 */
@Service
public class EstudianteCursoServiceImpl implements EstudianteCursoService {

    /**
     * Repositorio para gestión de entidades EstudianteCurso.
     */
    @Autowired
    private EstudianteCursoRepository estudianteCursoRepository;

    /**
     * Repositorio para validación de estudiantes.
     */
    @Autowired
    private EstudianteRepository estudianteRepository;

    /**
     * Repositorio para validación de cursos.
     */
    @Autowired
    private CursoRepository cursoRepository;

    /**
     * Obtiene todas las asignaciones estudiante-curso.
     * @return Lista completa de asignaciones convertidas a DTO
     */
    @Override
    public List<EstudianteCursoDTO> findAll() {
        List<EstudianteCurso> estudianteCursos = estudianteCursoRepository.findAll();
        return estudianteCursos
            .stream()
            .map(estudianteCurso -> new EstudianteCursoDTO(
                estudianteCurso.getId(),
                estudianteCurso.getEstudiante(),
                estudianteCurso.getCurso()
            ))
            .toList();
    }

    /**
     * Busca todas las asignaciones de un estudiante específico.
     * @param estudianteId ID del estudiante a buscar
     * @return Lista de asignaciones del estudiante convertidas a DTO
     */
    @Override
    public List<EstudianteCursoDTO> findByEstudianteId(Long estudianteId) {
        List<EstudianteCurso> estudianteCursos = this.estudianteCursoRepository.findByEstudianteId(estudianteId);
        return estudianteCursos
        .stream()
        .map(estudianteCurso -> new EstudianteCursoDTO(
            estudianteCurso.getId(),
            estudianteCurso.getEstudiante(),
            estudianteCurso.getCurso()
            )
        )
        .toList();
    }

    /**
     * Busca todas las asignaciones de un curso específico.
     * @param cursoId ID del curso a buscar
     * @return Lista de asignaciones del curso convertidas a DTO
     */
    @Override
    public List<EstudianteCursoDTO> findByCursoId(Long cursoId) {
        List<EstudianteCurso> estudianteCurso = this.estudianteCursoRepository.findByCursoId(cursoId);
        return estudianteCurso
        .stream().map(ec -> new EstudianteCursoDTO(
                ec.getId(),
                ec.getEstudiante(),
                ec.getCurso()
        ))
        .toList();
    }

    /**
     * Busca una asignación específica por su ID.
     * @param id ID de la asignación a buscar
     * @return DTO de la asignación encontrada
     * @throws RuntimeException si la asignación no existe
     */
    @Override
    public EstudianteCursoDTO findById(Long id) {
        EstudianteCurso estudianteCurso = this.estudianteCursoRepository.findById(id).orElse(null);
        if(estudianteCurso == null){
            throw new RuntimeException("La asignación estudiante-curso no existe");
        }
        return new EstudianteCursoDTO(
            estudianteCurso.getId(),
            estudianteCurso.getEstudiante(),
            estudianteCurso.getCurso()
        );
    }


    /**
     * Guarda una nueva asignación estudiante-curso.
     * Valida que ambos existan antes de crear la relación.
     * @param estudianteId ID del estudiante a asignar
     * @param cursoId ID del curso al que se asigna
     * @return ID de la asignación creada
     * @throws RuntimeException si el estudiante o curso no existen
     */
    @Override
    public Long save(Long estudianteId, Long cursoId) {
        
        EstudianteCurso estudianteCurso = this.validaDatos(estudianteId, cursoId);

        return this.estudianteCursoRepository.save(estudianteCurso).getId();
    }


    /**
     * Actualiza una asignación existente.
     * Valida existencia de todos los elementos antes de actualizar.
     * @param id ID de la asignación a actualizar
     * @param estudianteId Nuevo ID del estudiante
     * @param cursoId Nuevo ID del curso
     * @return ID de la asignación actualizada
     * @throws RuntimeException si la asignación, estudiante o curso no existen
     */
    @Override
    public Long update(Long id, Long estudianteId, Long cursoId) {
     
        EstudianteCurso estudianteCurso = this.validaDatos(estudianteId, cursoId);
        estudianteCurso.setId(id);
        return this.estudianteCursoRepository.save(estudianteCurso).getId();
    }

    /**
     * Elimina una asignación por su ID.
     * @param id ID de la asignación a eliminar
     * @throws RuntimeException si la asignación no existe
     */
    @Override
    public void delete(Long id) {
        EstudianteCurso estudianteCurso = this.estudianteCursoRepository.findById(id).orElse(null);
        if(estudianteCurso == null){
            throw new RuntimeException("EstudianteCurso no encontrado");
        }
        this.estudianteCursoRepository.delete(estudianteCurso);
    }

    /**
     * Valida la existencia de estudiante y curso y crea la entidad EstudianteCurso.
     * @param estudianteId ID del estudiante a validar
     * @param cursoId ID del curso a validar
     * @return Entidad EstudianteCurso con datos válidos
     * @throws RuntimeException si el estudiante o curso no existen
     */
    private EstudianteCurso validaDatos(Long estudianteId, Long cursoId){
        Estudiante estudiante = this.estudianteRepository.findById(estudianteId).orElse(null);
        if(estudiante == null){
            throw new RuntimeException("Estudiante no encontrado");
        }
        Curso curso = this.cursoRepository.findById(cursoId).orElse(null);
        if(curso == null){
            throw new RuntimeException("Curso no encontrado");
        }

        EstudianteCurso ec = new EstudianteCurso();
        ec.setCurso(curso);
        ec.setEstudiante(estudiante);

        return ec;
    }
    
}

