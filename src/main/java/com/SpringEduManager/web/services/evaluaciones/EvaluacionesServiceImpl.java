package com.SpringEduManager.web.services.evaluaciones;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringEduManager.web.dto.CursoDTO;
import com.SpringEduManager.web.dto.CursoNotasDTO;
import com.SpringEduManager.web.dto.EstudianteDTO;
import com.SpringEduManager.web.dto.EvaluacionRequestDTO;
import com.SpringEduManager.web.dto.NotaConIdDTO;
import com.SpringEduManager.web.entities.Curso;
import com.SpringEduManager.web.entities.Estudiante;
import com.SpringEduManager.web.entities.EstudianteCurso;
import com.SpringEduManager.web.entities.Evaluacion;
import com.SpringEduManager.web.repositories.CursoRepository;
import com.SpringEduManager.web.repositories.EstudianteRepository;
import com.SpringEduManager.web.repositories.EvaluacionRepository;
import com.SpringEduManager.web.repositories.EstudianteCursoRepository;

@Service
public class EvaluacionesServiceImpl implements EvaluacionesService {
    
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EstudianteCursoRepository estudianteCursoRepository;

    @Override
    public List<CursoNotasDTO[]> getEstudianteNotas(Long estudianteId){
        List<Object[]> resultados = evaluacionRepository.findEstudianteNotas(estudianteId);
        List<CursoNotasDTO[]> cursosNotas = new ArrayList<>();
        for (Object[] row : resultados) {
            // Datos del estudiante y curso se encuentran en las primeras 7 columnas de cada fila (índices 0-6)
            Long id = (Long) row[0];
            Long estId = (Long) row[1];
            String estNombre = (String) row[2];
            String estApellido = (String) row[3];
            String estEmail = (String) row[4];
            Long cursoId = (Long) row[5];
            String cursoNombre = (String) row[6];
            String cursoDescripcion = (String) row[7];

            // Crear DTOs de estudiante y curso
            EstudianteDTO estudiante = new EstudianteDTO(estId, estNombre, estApellido, estEmail);
            CursoDTO curso = new CursoDTO(cursoId, cursoNombre, cursoDescripcion);
            NotaConIdDTO[] notas = notas(row);            
            // Crear CursoNotasDTO
            CursoNotasDTO cursoNotasDTO = new CursoNotasDTO(
                id,
                estudiante, curso, 
                notas[0], notas[1], notas[2], notas[3], notas[4], 
                notas[5], notas[6], notas[7], notas[8], notas[9]
            );
            cursosNotas.add( new CursoNotasDTO[]{cursoNotasDTO});
        }
        return cursosNotas;
    }

    /**
     * Procesa las 10 evaluaciones de un estudiante
     * @param row fila de la consulta
     * @return array de 10 objetos NotaConIdDTO
     */
    private NotaConIdDTO[] notas(Object[] row){
        // Las evaluaciones se encuentran en las columnas 8-37 de cada fila (índices 8-37)
        // Cada evaluación ocupa 3 columnas: ev_id, ev_nota, ev_fecha (id, nota, fecha)
        NotaConIdDTO[] notas = new NotaConIdDTO[10];
        for (int i = 0; i < 10; i++) {
            Long evId = (Long) row[8 + i * 3];  // ev_id
            if (evId != null) {
                Double evNota = (Double) row[9 + i * 3];  // ev_nota
                LocalDate localDate = (LocalDate) row[10 + i * 3];  // ev_fecha
                Date evFecha;
                evFecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                notas[i] = new NotaConIdDTO(evId, evNota, evFecha);
            } else {
                notas[i] = null;
            }
        }
        return notas;
    }

    @Override
    public Long save(EvaluacionRequestDTO request) {
        Double nota = request.getNota();
        java.sql.Date fecha = request.getFecha() != null ? request.getFecha() : new java.sql.Date(System.currentTimeMillis());
        Estudiante estudiante = getEstudiante(request.getEstudianteId());
        Curso curso = getCurso(request.getCursoId());
        EstudianteCurso estCurso = getEstudianteCurso(estudiante, curso);
        Long id = request.getId();
        Evaluacion eval;
        if(id != null){
            eval = new Evaluacion(id, nota, fecha, estCurso);
        }else{
            eval = new Evaluacion(nota, fecha, estCurso);
        }

        Long savedId = evaluacionRepository.save(eval).getId();
        if(savedId == null){
            throw new RuntimeException("Error al guardar la evaluación");
        }
        return savedId;
    }

    private Estudiante getEstudiante(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id).orElse(null);
        if(estudiante == null){
            throw new RuntimeException("Estudiante no encontrado");
        }
        return estudiante;
    }

    private Curso getCurso(Long id){
        Curso curso = cursoRepository.findById(id).orElse(null);
        if(curso == null){
            throw new RuntimeException("Curso no encontrado");
        }
        return curso;
    }

    private EstudianteCurso getEstudianteCurso(Estudiante estudiante, Curso curso) {
        List<EstudianteCurso> estudianteCursos = estudianteCursoRepository.findByEstudianteId(estudiante.getId());
        EstudianteCurso estCurso = estudianteCursos.stream()
            .filter(ec -> ec.getCurso().getId().equals(curso.getId()))
            .findFirst()
            .orElse(null);
        
        if (estCurso == null) {
            estCurso = new EstudianteCurso(null, estudiante, curso);
            estCurso = estudianteCursoRepository.save(estCurso);
        }

        return estCurso;
    }

    @Override
    public void deleteById(Long id) {
        Evaluacion evaluacion = evaluacionRepository.findById(id).orElse(null);
        if(evaluacion == null){
            throw new RuntimeException("Evaluación no encontrada");
        }
        evaluacionRepository.deleteById(id);
    }
}
