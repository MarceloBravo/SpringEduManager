package com.SpringEduManager.web.controllers.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.ui.Model;
import com.SpringEduManager.web.dto.RolDTO;
import com.SpringEduManager.web.services.roles.RolService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controlador web para la gestión de roles del sistema.
 * Proporciona endpoints para operaciones CRUD con interfaz web
 * utilizando el motor de plantillas Thymeleaf.
 * 
 * @author SpringEduManager
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/roles")
public class RolesWebController {

    @Autowired
    private RolService rolService;
    
    /**
     * Muestra el listado de roles con paginación y filtros.
     * GET /roles/list
     * @param filtro Filtro opcional para buscar roles
     * @param page Número de página (default: 0)
     * @param size Tamaño de página (default: 10)
     * @param sortBy Campo de ordenamiento (default: vacío)
     * @param model Modelo para pasar datos a la vista
     * @param redirectAttributes Atributos para redirección con mensajes flash
     * @return Nombre de la plantilla Thymeleaf
     */
    @GetMapping("/list")
    public String getAll(
        @RequestParam(name = "filtro", required = false) String filtro, 
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "sortBy", defaultValue = "") String sortBy,
        Model model, 
        RedirectAttributes redirectAttributes
    ){
        try{
            Page<RolDTO> roles;
            roles = this.rolService.searchInAllFields(filtro, page, size, sortBy);
            System.out.println("Roles: " + roles.getContent());
            List<String> availableRoles = rolService.getMissingRoles();
            System.out.println("Available roles: " + availableRoles);
            model.addAttribute("availableRoles", availableRoles);
            model.addAttribute("roles", roles.getContent());
            model.addAttribute("filtro", filtro);
            model.addAttribute("page", page);
            model.addAttribute("size", roles.getSize());
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("totalPages", roles.getTotalPages());
            model.addAttribute("totalElements", roles.getTotalElements());
            model.addAttribute("url", "roles");

            // Pasar mensajes flash al template si existen
            if(redirectAttributes.getFlashAttributes().containsKey("message")) {
                model.addAttribute("message", redirectAttributes.getFlashAttributes().get("message"));
                model.addAttribute("code", redirectAttributes.getFlashAttributes().get("code"));
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            model.addAttribute("message", "Ocurrió un error al buscar el registro");
            model.addAttribute("code", 500);
        }
        model.addAttribute("menu","roles");
        return "roles/list";
    }

    /**
     * Muestra el formulario para editar un rol existente.
     * GET /roles/{id}
     * @param id ID del rol a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla de edición o redirect a error
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable(name = "id", required = true) Long id, Model model){
        try{
            RolDTO rol = rolService.findById(id);
            List<String> availableRoles = rolService.getMissingRoles();
            availableRoles.add(rol.getNombre());
            model.addAttribute("availableRoles", availableRoles);
            model.addAttribute("rol", rol);
            model.addAttribute("code", 200);
            return "roles/form";
        }catch(Exception e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("code", 500);
            return "redirect:/roles/list";
        }
    }

    /**
     * Muestra el formulario para crear un nuevo rol.
     * GET /roles/new
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla del formulario
     */
    @GetMapping("/new")
    public String goToNewRolForm(Model model){
        try{
            List<String> availableRoles = rolService.getMissingRoles();
            model.addAttribute("availableRoles", availableRoles);
            model.addAttribute("menu","roles");
            model.addAttribute("rol", new RolDTO());
            model.addAttribute("code", 200);
            return "roles/form";
        }catch(Exception e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("code", 500);
            return "redirect:/roles/list";
        }
    }

    /**
     * Guarda un nuevo rol en el sistema.
     * POST /roles/save
     * @param rol RolDTO con los datos del nuevo rol
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado o página de error
     */
    @PostMapping("/save")
    public String saveRol(RolDTO rol, Model model){
        try{
            rolService.save(rol);
            model.addAttribute("message", "Rol guardado correctamente");
            model.addAttribute("code", 200);
            return "redirect:/roles/list";
        }catch(Exception e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("code", 500);
            return "redirect:/roles/list";
        }
    }

    /**
     * Actualiza un rol existente.
     * POST /roles/{id}
     * @param id ID del rol a actualizar
     * @param rol RolDTO con los datos actualizados
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado o página de error
     */
    @PostMapping("/{id}")
    public String updateRol(@PathVariable(name = "id", required = true) Long id, RolDTO rol, Model model){
        try{
            rol.setId(id);
            rolService.save(rol);
            model.addAttribute("message", "Rol actualizado correctamente");
            model.addAttribute("code", 200);
            return "redirect:/roles/list";
        }catch(Exception e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("code", 500);
            return "redirect:/roles/list";
        }
    }

    /**
     * Elimina un rol por su ID.
     * POST /roles/delete/{id}
     * @param id ID del rol a eliminar
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado o página de error
     */
    @PostMapping("/delete")
    public String deleteRol(@RequestParam(name = "id", required = true) Long id, Model model){
        try{
            rolService.deleteById(id);
            model.addAttribute("message", "Rol eliminado correctamente");
            model.addAttribute("code", 200);
            return "redirect:/roles/list";
        }catch(Exception e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("code", 500);
            return "redirect:/roles/list";
        }
    }
}
