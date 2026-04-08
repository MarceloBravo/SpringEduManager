package com.SpringEduManager.web.controllers.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SpringEduManager.web.dto.EvaluacionDTO;
import com.SpringEduManager.web.services.evaluaciones.EvaluacionesService;

import org.springframework.ui.Model;

/**
 * Controlador web para la gestión de evaluaciones académicas.
 * Proporciona endpoints para la interfaz web del sistema con vistas HTML
 * para operaciones CRUD sobre evaluaciones.
 */
@Controller
@RequestMapping("/evaluaciones")
public class EvaluacionesWebController {

    /**
     * Servicio de negocio para la gestión de evaluaciones.
     */
    @Autowired
    private EvaluacionesService evaluacionesService;
    
    /**
     * Muestra la lista de todas las evaluaciones o las filtra por curso o estudiante.
     * 
     * @param cursoId ID del curso para filtrar (opcional)
     * @param estudianteId ID del estudiante para filtrar (opcional)
     * @param model Modelo Spring para pasar datos a la vista
     * @return Nombre de la vista a renderizar
     */
    @GetMapping("/")
    public String getAll(
        @PathVariable(name= "curso", required=false) Long cursoId,
        @PathVariable(name= "estudiante", required=false) Long estudianteId,
        Model model
    ) {
        try{
            if(estudianteId != null && cursoId != null){
                model.addAttribute("error", "No se puede especificar ambos parámetros curso y estudiante");
                return "redirect:/error";
            }
            if(estudianteId != null){
                List<EvaluacionDTO> evaluaciones = this.evaluacionesService.getByEstudianteId(estudianteId);
                model.addAttribute("data", Map.of("evaluaciones", evaluaciones));
                return "evaluaciones/list";
            }
            if(cursoId != null){
                List<EvaluacionDTO> evaluaciones = this.evaluacionesService.getByCursoId(cursoId);
                model.addAttribute("data", Map.of("cursos", evaluaciones));
                return "evaluaciones/list";
            }
            List<EvaluacionDTO> evaluaciones = this.evaluacionesService.getAll();
            model.addAttribute("data", Map.of("evaluaciones", evaluaciones));
            return "evaluaciones/list";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "redirect:/error";
        }
    }


    /**
     * Muestra los detalles de una evaluación específica.
     * 
     * @param id Identificador único de la evaluación
     * @param model Modelo Spring para pasar datos a la vista
     * @return Nombre de la vista de detalles
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable(required = true) Long id, Model model) {
        try{
            EvaluacionDTO evaluacion = this.evaluacionesService.getById(id);
            model.addAttribute("data", Map.of("evaluacion", evaluacion));
            return "evaluaciones/detail";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "redirect:/error";
        }
    }


    /**
     * Muestra el formulario para crear una nueva evaluación.
     * 
     * @param model Modelo Spring para pasar datos a la vista
     * @return Nombre de la vista del formulario de creación
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("data", new EvaluacionDTO());
        return "evaluaciones/nuevo";
    }


    /**
     * Procesa el formulario de creación de una nueva evaluación.
     * 
     * @param evaluacionDTO DTO con los datos de la evaluación a crear
     * @param model Modelo Spring para pasar datos a la vista
     * @return Redirección a la lista de evaluaciones
     */
    @PostMapping("/grabar")
    public String grabar(@ModelAttribute EvaluacionDTO evaluacionDTO, Model model) {
        try{
            evaluacionesService.save(evaluacionDTO.getId(), evaluacionDTO.getNota(), evaluacionDTO.getFecha(), evaluacionDTO.getEstudianteCurso());
            return "redirect:/evaluaciones";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al grabar el registro");
            return "redirect:/error";
        }
    }

    /**
     * Muestra el formulario para editar una evaluación existente.
     * 
     * @param id Identificador único de la evaluación a editar
     * @param model Modelo Spring para pasar datos a la vista
     * @return Nombre de la vista del formulario de edición
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable(required = true) Long id, Model model) {
        try{
            EvaluacionDTO evaluacion = this.evaluacionesService.getById(id);
            model.addAttribute("data", evaluacion);
            return "evaluaciones/editar";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "redirect:/error";
        }
    }

    /**
     * Procesa el formulario de actualización de una evaluación existente.
     * 
     * @param id Identificador único de la evaluación a actualizar
     * @param evaluacionDTO DTO con los datos actualizados de la evaluación
     * @param model Modelo Spring para pasar datos a la vista
     * @return Redirección a la lista de evaluaciones
     */
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable(required = true) Long id, @ModelAttribute EvaluacionDTO evaluacionDTO, Model model) {
        try{
            evaluacionesService.save(id, evaluacionDTO.getNota(), evaluacionDTO.getFecha(), evaluacionDTO.getEstudianteCurso());
            return "redirect:/evaluaciones";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al actualizar el registro");
            return "redirect:/error";
        }
    }

    /**
     * Elimina una evaluación del sistema por su ID.
     * 
     * @param id Identificador único de la evaluación a eliminar
     * @param model Modelo Spring para pasar datos a la vista
     * @return Redirección a la lista de evaluaciones
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(required = true) Long id, Model model) {
        try{
            evaluacionesService.delete(id);
            return "redirect:/evaluaciones";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al eliminar el registro");
            return "redirect:/error";
        }
    }
    

}
