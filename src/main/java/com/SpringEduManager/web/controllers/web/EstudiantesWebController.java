package com.SpringEduManager.web.controllers.web;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SpringEduManager.web.dto.EstudianteDTO;
import com.SpringEduManager.web.services.estudiantes.EstudianteService;

/**
 * Controlador web para la gestión de estudiantes con vistas Thymeleaf.
 * Proporciona endpoints para operaciones CRUD con interfaz web.
 * Todas las vistas utilizan el motor de plantillas Thymeleaf.
 */
@Controller
@RequestMapping("/estudiantes")
public class EstudiantesWebController {

    @Autowired
    private EstudianteService estudianteService;
    

    @GetMapping("/list")
    public String getAll(@RequestParam(name = "filtro", required = false) String filtro, Model model, RedirectAttributes redirectAttributes){
        List<EstudianteDTO> estudiantes = null;
        
        if(filtro != null && !filtro.isEmpty()){
            estudiantes = estudianteService.getAll(filtro);
        }else{
            estudiantes = estudianteService.getAll();
        }
        
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("filtro", filtro);
        
        // Pasar mensajes flash al template si existen
        if(redirectAttributes.getFlashAttributes().containsKey("message")) {
            model.addAttribute("message", redirectAttributes.getFlashAttributes().get("message"));
            model.addAttribute("code", redirectAttributes.getFlashAttributes().get("code"));
        }
        setMenuAttribute(model);
        return "estudiantes/list";
    }

    /**
     * Muestra el formulario para crear un nuevo estudiante.
     * GET /estudiantes/new
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla del formulario
     */
    @GetMapping("/new")
    public String goToNewEstudianteForm(Model model){
        setMenuAttribute(model);
        try{
            model.addAttribute("estudiante", new EstudianteDTO());
            model.addAttribute("code", 200);
            return "estudiantes/form";
        }catch(Exception e){
            model.addAttribute("message", "Ocurrió un error al cargar el formulario");
            model.addAttribute("code", 500);
            return "redirect:/estudiantes/list";
        }
    }

    /**
     * Guarda un nuevo estudiante en el sistema.
     * POST /estudiantes/save
     * @param estudiante EstudianteDTO con los datos del nuevo estudiante
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/save")
    public String saveNewEstudiante(@ModelAttribute EstudianteDTO estudiante, RedirectAttributes redirectAttributes){
        setMenuAttribute(redirectAttributes);
        try{
            Long id = this.estudianteService.save(estudiante);
            if(id != null){
                redirectAttributes.addFlashAttribute("message", "Estudiante " + (estudiante.getId() != null ? "actualizado" : "creado") + " exitosamente");
                redirectAttributes.addFlashAttribute("code", 200);
                return "redirect:/estudiantes/list";
            }
            throw new RuntimeException("Ocurrió un error al grabar el estudiante");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message", "Ocurrió un error al grabar el estudiante");
            redirectAttributes.addFlashAttribute("code", 500);
            return "redirect:/estudiantes/form";
        }
    }

    /**
     * Muestra el formulario para editar un estudiante existente.
     * GET /estudiantes/{id}
     * @param id ID del estudiante a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla de edición o redirect a error
     */
    @GetMapping("/{id}")
    public String goToEditEstudianteForm(@PathVariable(name = "id", required = true) Long id, Model model){
        try{
            setMenuAttribute(model);
            EstudianteDTO estudiante = this.estudianteService.findById(id);
            model.addAttribute("estudiante", estudiante);
            model.addAttribute("code", 200);
            return "estudiantes/form";
        }catch(Exception e){
            model.addAttribute("message", "Ocurrió un error al buscar el estudiante a editar");
            model.addAttribute("code", 500);
            return "redirect:/estudiantes/list";
        }
    }
    

    /**
     * Actualiza un estudiante existente.
     * POST /estudiantes/update/{id}
     * @param id ID del estudiante a actualizar
     * @param _estudiante EstudianteDTO con los datos actualizados
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/update/{id}")
    public String updateEstudiante(@PathVariable(name = "id", required = true) Long id, @ModelAttribute EstudianteDTO _estudiante, RedirectAttributes redirectAttributes){
        try{
            setMenuAttribute(redirectAttributes);
            _estudiante.setId(id);
            Long result = this.estudianteService.save(_estudiante);
            if(Objects.equals(result, _estudiante.getId())){
                redirectAttributes.addFlashAttribute("message", "Estudiante actualizado exitosamente");
                redirectAttributes.addFlashAttribute("code", 200);
                return "redirect:/estudiantes/list";
            }
            throw new RuntimeException("Ocurrió un error al actualizar el estudiante");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message", "Ocurrió un error al actualizar el estudiante");
            redirectAttributes.addFlashAttribute("code", 500);
            return "redirect:/estudiantes/form";
        }
    }

    /**
     * Elimina un estudiante por su ID.
     * POST /estudiantes/delete
     * @param id ID del estudiante a eliminar
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/delete")
    public String deleteEstudiante(@RequestParam(name = "id", required = true) Long id, RedirectAttributes redirectAttributes){
        try{
            setMenuAttribute(redirectAttributes);
            this.estudianteService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Estudiante eliminado exitosamente");
            redirectAttributes.addFlashAttribute("code", 200);
        }catch(Exception e){
            // Verificar si es un error de constraint violation (clave externa)
            if(e.getCause() != null && e.getCause().getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                redirectAttributes.addFlashAttribute("message", "No se puede eliminar el estudiante porque tiene cursos asociados");
                redirectAttributes.addFlashAttribute("code", 400);
            } else {
                redirectAttributes.addFlashAttribute("message", "Ocurrió un error al eliminar el estudiante");
                redirectAttributes.addFlashAttribute("code", 500);
            }
        }
        return "redirect:/estudiantes/list";
    }

    private void setMenuAttribute(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("menu","estudiantes");
    }
    
    private void setMenuAttribute(Model model) {
        model.addAttribute("menu","estudiantes");
    }
    
}
