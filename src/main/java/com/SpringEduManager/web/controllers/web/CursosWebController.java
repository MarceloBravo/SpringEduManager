package com.SpringEduManager.web.controllers.web;

import java.util.List;

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

import com.SpringEduManager.web.dto.CursoDTO;
import com.SpringEduManager.web.services.cursos.CursoService;

/**
 * Controlador web para la gestión de cursos con vistas Thymeleaf.
 * Proporciona endpoints para operaciones CRUD con interfaz web.
 * Todas las vistas utilizan el motor de plantillas Thymeleaf.
 */
@Controller
@RequestMapping("/cursos")
public class CursosWebController {

    @Autowired
    private CursoService cursoService;
    
    /**
     * Muestra el listado de cursos con opcional filtro por nombre.
     * GET /cursos/list
     * @param filtro Filtro opcional para buscar por nombre (case insensitive)
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla Thymeleaf
     */
    @GetMapping("/list")
    public String getAll(@RequestParam(name = "filtro", required = false) String filtro, Model model, RedirectAttributes redirectAttributes){
        try{
            List<CursoDTO> cursos = null;
            if(filtro != null && !filtro.isEmpty()){
                cursos = cursoService.getAll(filtro);
            }else{
                cursos = cursoService.getAll();
            }
            model.addAttribute("cursos", cursos);
            model.addAttribute("filtro", filtro);
            if(redirectAttributes.getFlashAttributes().containsKey("message")) {
               model.addAttribute("message", redirectAttributes.getFlashAttributes().get("message"));
               model.addAttribute("code", redirectAttributes.getFlashAttributes().get("code"));
           }
        }catch(Exception e){
            model.addAttribute("message", "Ocurrió un error al buscar el listado de registros");
            model.addAttribute("code", 500);
        }
        setMenuAttribute(model);
        return "cursos/list";

    }

    /**
     * Muestra el formulario para crear un nuevo curso.
     * GET /cursos/new
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla del formulario
     */
    @GetMapping("/new")
    public String goToNewCursoForm(Model model){
        try{
            setMenuAttribute(model);
            model.addAttribute("curso", new CursoDTO());
            return "cursos/new";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al cargar el formulario");
            model.addAttribute("code", 500);
            return "redirect:/cursos/list";
        }
    }

    /**
     * Guarda un nuevo curso en el sistema.
     * POST /cursos/save
     * @param curso CursoDTO con los datos del nuevo curso
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/save")
    public String saveNewCurso(@ModelAttribute CursoDTO curso, RedirectAttributes redirectAttributes){
        try{
            setMenuAttribute(redirectAttributes);
            Long id = this.cursoService.save(curso);
            if(id != null){
                redirectAttributes.addFlashAttribute("message", "Curso creado exitosamente");
                redirectAttributes.addFlashAttribute("code", 200);
                return "redirect:/cursos/list";
            }
            throw new RuntimeException("Ocurrió un error al grabar el curso");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("code", 500);
            return "redirect:/cursos/form";
        }
    }

    /**
     * Muestra el formulario para editar un curso existente.
     * GET /cursos/{id}
     * @param id ID del curso a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla de edición o redirect a error
     */
    @GetMapping("/{id}")
    public String goToEditCursoForm(@PathVariable(required = true) Long id, Model model){
        try{
            setMenuAttribute(model);
            CursoDTO curso = this.cursoService.findById(id);
            model.addAttribute("curso", curso);
            model.addAttribute("code", 200);
            return "cursos/edit";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("code", 500);
            return "redirect:/cursos/list";
        }
    }
    

    /**
     * Actualiza un curso existente.
     * POST /cursos/update/{id}
     * @param id ID del curso a actualizar
     * @param _curso CursoDTO con los datos actualizados
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado o página de error
     */
    @PostMapping("/update/{id}")
    public String updateCurso(@PathVariable(name = "id", required = true) Long id, @ModelAttribute CursoDTO _curso, RedirectAttributes redirectAttributes){
        try{
            setMenuAttribute(redirectAttributes);
            _curso.setId(id);
            Long result = this.cursoService.save(_curso);
            if(result == _curso.getId()){
                redirectAttributes.addFlashAttribute("curso", _curso);
                redirectAttributes.addFlashAttribute("message", "Curso actualizado exitosamente");
                return "redirect:/cursos/list";
            }
            throw new RuntimeException("Ocurrió un error al actualizar el curso");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("code", 500);
            return "redirect:/cursos/form";
        }
    }

    /**
     * Elimina un curso por su ID.
     * POST /cursos/delete/{id}
     * @param id ID del curso a eliminar
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/delete")
    public String deleteCurso(@RequestParam(name = "id", required = true) Long id, RedirectAttributes redirectAttributes){
        try{
            setMenuAttribute(redirectAttributes);
            this.cursoService.delete(id);
            redirectAttributes.addAttribute("message", "Curso eliminado exitosamente");
            redirectAttributes.addFlashAttribute("code", 200);
        }catch(Exception e){
            // Verificar si es un error de constraint violation (clave externa)
            if(e.getCause() != null && e.getCause().getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                redirectAttributes.addFlashAttribute("message", "No se puede eliminar el estudiante porque tiene cursos asociados");
                redirectAttributes.addFlashAttribute("code", 400);
            } else {
                redirectAttributes.addAttribute("error", e.getMessage());
                redirectAttributes.addFlashAttribute("code", 500);
            }
        }
        return "redirect:/cursos/list";
    }

    private void setMenuAttribute(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("menu","estudiantes");
    }
    
    private void setMenuAttribute(Model model) {
        model.addAttribute("menu","estudiantes");
    }
    
}
