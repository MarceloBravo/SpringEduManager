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
     * GET /cursos/list/{filtro}
     * @param filtro Filtro opcional para buscar por nombre (case insensitive)
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla Thymeleaf
     */
    @GetMapping("/list/{filtro}")
    public String getAll(@PathVariable(name = "filtro", required = false) String filtro, Model model){
        try{
            List<CursoDTO> cursos = null;
            if(filtro != null && !filtro.isEmpty()){
                cursos = cursoService.getAll(filtro);
            }else{
                cursos = cursoService.getAll();
            }
            model.addAttribute("cursos", cursos);
            model.addAttribute("filtro", filtro);
            return "cursos/list";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al buscar el listado de registros");
            return "redirect:/error";
        }

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
            model.addAttribute("curso", new CursoDTO());
            return "cursos/new";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al cargar el formulario");
            return "redirect:/error";
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
    public String saveNewCurso(@ModelAttribute CursoDTO curso, Model model){
        try{
            Long id = this.cursoService.save(curso);
            if(id != null){
                model.addAttribute("message", "Curso creado exitosamente");
                return "redirect: /cursos/list";
            }
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al crear el curso");
        }
        return "redirect:/error";
    }

    /**
     * Muestra el formulario para editar un curso existente.
     * GET /cursos/{id}
     * @param id ID del curso a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla de edición o redirect a error
     */
    @GetMapping("/{id}")
    public String goToEditCursoForm(@PathVariable Long id, Model model){
        try{
            CursoDTO curso = this.cursoService.findById(id);
            model.addAttribute("curso", curso);
            return "cursos/edit";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al buscar el curso a editar");
            return "redirect:/error";
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
    public String updateCurso(@PathVariable(name = "id", required = true) Long id, @ModelAttribute CursoDTO _curso, Model model){
        try{
            _curso.setId(id);
            Long result = this.cursoService.save(_curso);
            if(result == _curso.getId()){
                model.addAttribute("curso", _curso);
                model.addAttribute("message", "Curso actualizado exitosamente");
                return "redirect: /cursos/list";
            }
            model.addAttribute("error", "Ocurrió un error al actualizar el curso");
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al actualizar el curso");
        }
        return "redirect:/error";
    }

    /**
     * Elimina un curso por su ID.
     * POST /cursos/delete/{id}
     * @param id ID del curso a eliminar
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/delete/{id}")
    public String deleteCurso(@PathVariable(name = "id", required = true) Long id, Model model){
        try{
            this.cursoService.delete(id);
            model.addAttribute("message", "Curso eliminado exitosamente");
            return "redirect: /cursos/list";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al eliminar el curso");
        }
        return "redirect:/error";
    }
    
}
