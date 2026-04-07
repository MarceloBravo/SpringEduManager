package com.SpringEduManager.web.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SpringEduManager.web.dto.EstudianteCursoDTO;
import com.SpringEduManager.web.services.EstudiantesCursos.EstudianteCursoService;

import org.springframework.ui.Model;
import java.util.List;
import java.util.Map;

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
     * Muestra el listado de asignaciones con filtros opcionales.
     * GET /estudiantes-cursos/
     * @param cursoId ID del curso para filtrar (opcional)
     * @param estudianteId ID del estudiante para filtrar (opcional)
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista a renderizar
     */
    @GetMapping("/")
    public String getAll(
        @PathVariable(name = "curso", required = false) Long cursoId,
        @PathVariable(name = "estudiante", required = false) Long estudianteId,
        Model model
    ){
        try{
            if(cursoId != null && estudianteId != null){
                model.addAttribute("error", "No se puede especificar ambos parámetros");
                return "redirect:/error";
            }
            if(cursoId != null){
                List<EstudianteCursoDTO> estudianteCursos = estudianteCursoService.findByCursoId(cursoId);
                model.addAttribute("data", Map.of("estudianteCursos", estudianteCursos));
                return "estudiantes-cursos/index";
            }
            if(estudianteId != null){
                List<EstudianteCursoDTO> estudianteCursos = estudianteCursoService.findByEstudianteId(estudianteId);
                model.addAttribute("data", Map.of("estudianteCursos", estudianteCursos));
                return "estudiantes-cursos/index";
            }
            List<EstudianteCursoDTO> estudianteCursos = estudianteCursoService.findAll();
            model.addAttribute("data", Map.of("estudianteCursos", estudianteCursos));
            return "estudiantes-cursos/index";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al buscar el listado de registros");
            return "redirect:/error";
        }
    }

    /**
     * Muestra el detalle de una asignación específica.
     * GET /estudiantes-cursos/{id}
     * @param id ID de la asignación a mostrar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista de detalle
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable(name = "id", required = true) Long id, Model model){
        try{
            EstudianteCursoDTO estudianteCurso = estudianteCursoService.findById(id);
            model.addAttribute("data", estudianteCurso);
            return "estudiantes-cursos/detail";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al buscar el registro");
            return "redirect:/error";
        }
    }

    /**
     * Muestra el formulario para crear una nueva asignación.
     * GET /estudiantes-cursos/nuevo
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista del formulario
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("data", new EstudianteCursoDTO());
        return "estudiantes-cursos/nuevo";
    }

    /**
     * Procesa el formulario de creación de nueva asignación.
     * POST /estudiantes-cursos/grabar
     * @param estudianteCursoDTO DTO con datos de la asignación
     * @param model Modelo para pasar datos a la vista
     * @return Redirección al listado o página de error
     */
    @PostMapping("/grabar")
    public String grabar(@ModelAttribute EstudianteCursoDTO estudianteCursoDTO, Model model){
        try{
            estudianteCursoService.save(estudianteCursoDTO.getEstudiante().getId(), estudianteCursoDTO.getCurso().getId());
            return "redirect:/estudiantes-cursos";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al grabar el registro");
            return "redirect:/error";
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
            model.addAttribute("error", "Ocurrió un error al buscar el registro");
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
            model.addAttribute("error", "Ocurrió un error al actualizar el registro");
            return "redirect:/error";
        }
    }

    /**
     * Elimina una asignación existente.
     * POST /estudiantes-cursos/eliminar/{id}
     * @param id ID de la asignación a eliminar
     * @param model Modelo para pasar datos a la vista
     * @return Redirección al listado o página de error
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(name = "id", required = true) Long id, Model model){
        try{
            estudianteCursoService.delete(id);
            return "redirect:/estudiantes-cursos";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al eliminar el registro");
            return "redirect:/error";
        }
    }

}
