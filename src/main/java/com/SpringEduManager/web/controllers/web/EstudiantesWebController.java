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
    
    /**
     * Muestra el listado de estudiantes con opcional filtro por nombre.
     * GET /estudiantes/list/{filtro}
     * @param filtro Filtro opcional para buscar por nombre (case insensitive)
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla Thymeleaf
     */
    @GetMapping("/list/{filtro}")
    public String getAll(@PathVariable(name = "filtro", required = false) String filtro, Model model){
        try{
            List<EstudianteDTO> estudiantes = null;
            if(filtro != null && !filtro.isEmpty()){
                estudiantes = estudianteService.getAll(filtro);
            }else{
                estudiantes = estudianteService.getAll();
            }
            model.addAttribute("estudiantes", estudiantes);
            model.addAttribute("filtro", filtro);
            return "estudiantes/list";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al buscar el listado de registros");
            return "redirect:/error";
        }

    }

    /**
     * Muestra el formulario para crear un nuevo estudiante.
     * GET /estudiantes/new
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla del formulario
     */
    @GetMapping("/new")
    public String goToNewEstudianteForm(Model model){
        try{
            model.addAttribute("estudiante", new EstudianteDTO());
            return "estudiantes/new";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al cargar el formulario");
            return "redirect:/error";
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
    public String saveNewEstudiante(@ModelAttribute EstudianteDTO estudiante, Model model){
        try{
            Long id = this.estudianteService.save(estudiante);
            if(id != null){
                model.addAttribute("message", "Estudiante creado exitosamente");
                return "redirect: /estudiantes/list";
            }
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al crear el estudiante");
        }
        return "redirect:/error";
    }

    /**
     * Muestra el formulario para editar un estudiante existente.
     * GET /estudiantes/{id}
     * @param id ID del estudiante a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla de edición o redirect a error
     */
    @GetMapping("/{id}")
    public String goToEditEstudianteForm(@PathVariable(required = true) Long id, Model model){
        try{
            EstudianteDTO estudiante = this.estudianteService.findById(id);
            model.addAttribute("estudiante", estudiante);
            return "estudiantes/edit";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al buscar el estudiante a editar");
            return "redirect:/error";
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
    public String updateEstudiante(@PathVariable(name = "id", required = true) Long id, @ModelAttribute EstudianteDTO _estudiante, Model model){
        try{
            _estudiante.setId(id);
            Long result = this.estudianteService.save(_estudiante);
            if(Objects.equals(result, _estudiante.getId())){
                model.addAttribute("estudiante", _estudiante);
                model.addAttribute("message", "Estudiante actualizado exitosamente");
                return "redirect: /estudiantes/list";
            }
            model.addAttribute("error", "Ocurrió un error al actualizar el estudiante");
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al actualizar el estudiante");
        }
        return "redirect:/error";
    }

    /**
     * Elimina un estudiante por su ID.
     * POST /estudiantes/delete/{id}
     * @param id ID del estudiante a eliminar
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/delete/{id}")
    public String deleteEstudiante(@PathVariable(name = "id", required = true) Long id, Model model){
        try{
            this.estudianteService.delete(id);
            model.addAttribute("message", "Estudiante eliminado exitosamente");
            return "redirect: /estudiantes/list";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al eliminar el estudiante");
        }
        return "redirect:/error";
    }
    
}
