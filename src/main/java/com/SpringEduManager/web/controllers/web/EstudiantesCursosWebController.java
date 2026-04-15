package com.SpringEduManager.web.controllers.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SpringEduManager.web.dto.EstudianteCursoDTO;
import com.SpringEduManager.web.dto.EstudianteCursoRequestDTO;
import com.SpringEduManager.web.services.EstudiantesCursos.EstudianteCursoService;

import org.springframework.ui.Model;

/**
 * Controlador web para la gestión de asignaciones de estudiantes a cursos.
 * Proporciona vistas y formularios para CRUD de relaciones estudiante-curso.
 * Maneja el filtrado por estudiante o curso específico.
 */
@Controller
@RequestMapping("/estudiantes-cursos")
public class EstudiantesCursosWebController {
    
    /**
     * Servicio para la gestión de asignaciones estudiante-curso.
     */
    @Autowired
    private EstudianteCursoService estudianteCursoService;


    /**
     * Procesa el formulario de creación de nueva asignación.
     * POST /estudiantes-cursos/grabar
     * @param estudianteCursoDTO DTO con datos de la asignación
     * @param model Modelo para pasar datos a la vista
     * @return Redirección al listado o página de error
     */
    @PostMapping("/grabar")
    @ResponseBody   
    public Map<String, String> grabar(
        @RequestBody EstudianteCursoRequestDTO request, 
        Model model
    ){
        try{
            Long id = estudianteCursoService.save(request.estudianteId(), request.cursoId());
            if(id == null){
                throw new RuntimeException("No se pudo asignar el estudiante al curso");
            }
            return Map.of("message","El estudiante ha sido asignado al curso exitosamente");
        }catch(Exception e){
            return Map.of("error","Ocurrió un error al grabar el registro");
        }
    }

    /**
     * Muestra el formulario para editar una asignación existente.
     * GET /estudiantes-cursos/editar/{id}
     * @param id ID de la asignación a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista del formulario de edición
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable(name = "id", required = true) Long id, Model model){
        try{
            EstudianteCursoDTO estudianteCurso = estudianteCursoService.findById(id);
            model.addAttribute("data", estudianteCurso);
            return "estudiantes-cursos/editar";
        }catch(Exception e){
            model.addAttribute("message", "Ocurrió un error al buscar el registro");
            return "redirect:/error";
        }
    }

    /**
     * Procesa el formulario de actualización de asignación.
     * POST /estudiantes-cursos/actualizar/{id}
     * @param id ID de la asignación a actualizar
     * @param estudianteCursoDTO DTO con nuevos datos
     * @param model Modelo para pasar datos a la vista
     * @return Redirección al listado o página de error
     */
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable(name = "id", required = true) Long id, @ModelAttribute EstudianteCursoDTO estudianteCursoDTO, Model model){
        try{
            estudianteCursoService.update(id, estudianteCursoDTO.getEstudiante().getId(), estudianteCursoDTO.getCurso().getId());
            return "redirect:/estudiantes-cursos";
        }catch(Exception e){
            model.addAttribute("message", "Ocurrió un error al actualizar el registro");
            return "redirect:/error";
        }
    }

    /**
     * Elimina una asignación existente con respuesta JSON.
     * POST /estudiantes-cursos/eliminar/{id}
     * @param id ID de la asignación a eliminar
     * @param model Modelo para pasar datos a la vista
     * @return Map con mensaje de éxito o error en formato JSON
     */
    @PostMapping("/eliminar/{id}")
    @ResponseBody
    public Map<String, String> eliminar(@PathVariable(name = "id", required = true) Long id, Model model){
        try{
            estudianteCursoService.delete(id);
            return Map.of("message", "Asignación eliminada exitosamente");
        }catch(Exception e){
            return Map.of("error", "Ocurrió un error al eliminar el registro");
        }
    }

}
