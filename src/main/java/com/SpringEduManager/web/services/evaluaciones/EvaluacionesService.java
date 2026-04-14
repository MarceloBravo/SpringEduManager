package com.SpringEduManager.web.services.evaluaciones;

import java.util.List;

import com.SpringEduManager.web.dto.CursoNotasDTO;
import com.SpringEduManager.web.dto.EvaluacionRequestDTO;

public interface EvaluacionesService {
    
    List<CursoNotasDTO[]> getEstudianteNotas(Long estudianteId);
    
    Long save(EvaluacionRequestDTO request);

    void deleteById(Long id);
}
