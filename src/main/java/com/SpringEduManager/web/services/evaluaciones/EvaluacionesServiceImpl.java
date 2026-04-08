package com.SpringEduManager.web.services.evaluaciones;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.EvaluacionDTO;
import com.SpringEduManager.web.entities.EstudianteCurso;
import com.SpringEduManager.web.entities.Evaluacion;
import com.SpringEduManager.web.repositories.EstudianteCursoRepository;
import com.SpringEduManager.web.repositories.EvaluacionRepository;

/**
 * Implementación del servicio de evaluaciones académicas.
 * Proporciona la lógica de negocio para la gestión completa de evaluaciones
 * incluyendo validaciones, transformaciones de datos y operaciones CRUD.
 */
@Service
public class EvaluacionesServiceImpl implements EvaluacionesService  {

    /**
     * Repositorio para el acceso a datos de evaluaciones.
     */
    @Autowired
    private EvaluacionRepository evalRepository;

    /**
     * Repositorio para el acceso a datos de relaciones estudiante-curso.
     */
    @Autowired
    private EstudianteCursoRepository estudianteCursoRepository;

    /**
     * {@inheritDoc}
     * Obtiene todas las evaluaciones y las convierte a DTO para su presentación.
     */
    @Override
    public List<EvaluacionDTO> getAll() {
        List<Evaluacion> evaluaciones = this.evalRepository.findAll();
        return evaluaciones
            .stream()
            .map(eval -> new EvaluacionDTO(
                eval.getId(),
                eval.getNota(),
                eval.getFecha(),
                eval.getEstudianteCurso()
            ))
            .toList();
    }

    /**
     * {@inheritDoc}
     * Busca evaluaciones por estudiante y las convierte a DTO.
     */
    @Override
    public List<EvaluacionDTO> getByEstudianteId(Long estudianteId) {
        List<Evaluacion> evaluaciones = this.evalRepository.findByEstudianteId(estudianteId);
        return evaluaciones
            .stream()
            .map(eval -> new EvaluacionDTO(
                eval.getId(),
                eval.getNota(),
                eval.getFecha(),
                eval.getEstudianteCurso()
            ))
            .toList();
    }

    /**
     * {@inheritDoc}
     * Busca evaluaciones por curso y las convierte a DTO.
     */
    @Override
    public List<EvaluacionDTO> getByCursoId(Long cursoId) {
        List<Evaluacion> evaluaciones = this.evalRepository.findByCursoId(cursoId);
        return evaluaciones
            .stream()
            .map(eval -> new EvaluacionDTO(
                eval.getId(),
                eval.getNota(),
                eval.getFecha(),
                eval.getEstudianteCurso()
            ))
            .toList();
    }

    /**
     * {@inheritDoc}
     * Busca una evaluación específica y la convierte a DTO.
     * Lanza excepción si no se encuentra la evaluación.
     */
    @Override
    public EvaluacionDTO getById(Long id) {
        Evaluacion evaluacion = this.evalRepository.findById(id).orElse(null);
        if (evaluacion == null) {
            throw new RuntimeException("Evaluación no encontrada");
        }
        return new EvaluacionDTO(
            evaluacion.getId(),
            evaluacion.getNota(),
            evaluacion.getFecha(),
            evaluacion.getEstudianteCurso()
        );
    }

    /**
     * {@inheritDoc}
     * Valida los datos y guarda o actualiza una evaluación.
     * Realiza validaciones de negocio antes de persistir los datos.
     */
    @Override
    public Long save(Long id, Double nota, Date fecha, EstudianteCurso estudianteCurso) {

        validaDatos(nota, fecha, estudianteCurso);
        
        Evaluacion eval = new Evaluacion(
            id,
            nota,
            fecha,
            estudianteCurso
        );
        return this.evalRepository.save(eval).getId();
    }

    /**
     * {@inheritDoc}
     * Elimina una evaluación después de verificar su existencia.
     */
    @Override
    public void delete(Long id) {
        Evaluacion evaluacion = this.evalRepository.findById(id).orElse(null);
        if (evaluacion == null) {
            throw new RuntimeException("Evaluación no encontrada");
        }
        this.evalRepository.delete(evaluacion);
    }

    /**
     * Valida los datos de una evaluación antes de guardarla.
     * 
     * @param nota Calificación a validar (debe estar entre 0 y 10)
     * @param fecha Fecha a validar (no puede ser nula)
     * @param estudianteCurso Relación estudiante-curso a validar (debe existir)
     * @throws RuntimeException si algún dato es inválido
     */
    private void validaDatos(Double nota, Date fecha, EstudianteCurso estudianteCurso){        
        if(nota == null || nota < 0 || nota > 10){
            throw new RuntimeException("Nota inválida");
        }
        if(fecha == null){
            throw new RuntimeException("Fecha inválida");
        }
        if(estudianteCurso == null){
            throw new RuntimeException("EstudianteCurso inválido");
        }
        Long id = estudianteCurso.getId();
        EstudianteCurso isExists = this.estudianteCursoRepository.findById(id).orElse(null);
        if(isExists == null){
            throw new RuntimeException("El curso del estudiante no fue encontrado");
        }
    }
    
}
