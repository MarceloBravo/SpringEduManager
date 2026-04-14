package com.SpringEduManager.web.controllers.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SpringEduManager.web.dto.CursoNotasDTO;
import com.SpringEduManager.web.dto.EvaluacionRequestDTO;
import com.SpringEduManager.web.services.evaluaciones.EvaluacionesService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/cursos-evaluaciones")
public class EvaluacionesWebController {

    @Autowired
    private EvaluacionesService evaluacionesService;

    @GetMapping("/alumno/{id}")
    public String getEvaluaciones(
        @PathVariable(name = "id", required = true) Long id, 
        Model model
    ) {
       try{
            List<CursoNotasDTO[]> cursoNotas = evaluacionesService.getEstudianteNotas(id);
            model.addAttribute("data", cursoNotas.size() > 0 ? cursoNotas : null);
        }catch(Exception e){
            model.addAttribute("message", "Ocurrió un error al buscar el registro");
        }
        return "evaluaciones/list";  
    }


    @GetMapping("/notas-alumno")
    public String getListadoEvaluaciones( 
        Model model,
        RedirectAttributes redirectAttributes
    ) {
       try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<CursoNotasDTO[]> cursoNotas = evaluacionesService.getEstudianteNotasByUserEmail(auth.getName());
            model.addAttribute("data", cursoNotas.size() > 0 ? cursoNotas : null);
            model.addAttribute("menu","notas");
            return "evaluaciones/evaluaciones-list";  
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("code", 500);  // Error interno del servidor
            return "redirect:/home";  
        }
    }


    @PostMapping("/grabar")
    @ResponseBody   
    public Map<String, String> grabar(
        @RequestBody EvaluacionRequestDTO request, 
        Model model
    ){
        try{
            Long id = evaluacionesService.save(request);
            if(id == null){
                throw new RuntimeException("No se pudo registrar la evaluación");
            }
            return Map.of("message","Evaluación registrada exitosamente");
        }catch(Exception e){
            return Map.of("error","Ocurrió un error al registrar la evaluación");
        }
    }

    @PostMapping("/eliminar/{id}")
    @ResponseBody
    public Map<String, String> eliminar(@PathVariable("id") Long id){
        try{
            evaluacionesService.deleteById(id);
            return Map.of("message","Evaluación eliminada exitosamente");
        }catch(Exception e){
            return Map.of("error","Ocurrió un error al eliminar la evaluación");
        }
    }
}
