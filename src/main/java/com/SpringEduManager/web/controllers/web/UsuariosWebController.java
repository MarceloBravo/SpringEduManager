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

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.services.usuarios.UserService;

/**
 * Controlador web para la gestión de usuarios con vistas Thymeleaf.
 * Proporciona endpoints para operaciones CRUD con interfaz web.
 * Todas las vistas utilizan el motor de plantillas Thymeleaf.
 */
@Controller
@RequestMapping("/users")
public class UsuariosWebController {

    @Autowired
    private UserService userService;
    
    /**
     * Muestra el listado de usuarios con opcional filtro por nombre.
     * GET /users/list
     * @param filtro Filtro opcional para buscar por nombre (case insensitive)
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla Thymeleaf
     */
    @GetMapping("/list")
    public String getAll(@RequestParam(name = "filtro", required = false) String filtro, Model model, RedirectAttributes redirectAttributes){
        try{
            setMenuAttribute(model);
            List<UserDTO> users = null;
            if(filtro != null && !filtro.isEmpty()){
                users = userService.getAll(filtro);
            }else{
                users = userService.getAll();
            }
            model.addAttribute("users", users);
            model.addAttribute("filtro", filtro);
            
            // Pasar mensajes flash al template si existen
            if(redirectAttributes.getFlashAttributes().containsKey("message")) {
                model.addAttribute("message", redirectAttributes.getFlashAttributes().get("message"));
                model.addAttribute("code", redirectAttributes.getFlashAttributes().get("code"));
            }
        }catch(Exception e){
            model.addAttribute("message", "Ocurrió un error al buscar el listado de registros");
            model.addAttribute("code", 500);
        }
        return "usuarios/list";
    }

    /**
     * Muestra el formulario para crear un nuevo usuario.
     * GET /users/new
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla del formulario
     */
    @GetMapping("/new")
    public String goToNewUserForm(Model model){
        try{
            setMenuAttribute(model);
            model.addAttribute("user", new UserDTO());
            return "usuarios/form";
        }catch(Exception e){
            model.addAttribute("message", "Ocurrió un error al cargar el formulario");
            model.addAttribute("code", 500);
            return "redirect:/users/list";
        }
    }

    /**
     * Guarda un nuevo usuario en el sistema.
     * POST /users/save
     * @param user UserDTO con los datos del nuevo usuario
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/save")
    public String saveNewUser(@ModelAttribute UserDTO user, RedirectAttributes redirectAttributes){
        try{
            setMenuAttribute(redirectAttributes);
            Long id = this.userService.save(user);
            if(id != null){
                redirectAttributes.addFlashAttribute("message", "Usuario " + (user.getId() != null ? "actualizado" : "creado") + " exitosamente");
                redirectAttributes.addFlashAttribute("code", 200);
                return "redirect:/users/list";
            }
            throw new RuntimeException("Ocurrió un error al grabar el usuario");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("code", 500);
            return "redirect:/users/form";
        }
    }

    /**
     * Muestra el formulario para editar un usuario existente.
     * GET /users/{id}
     * @param id ID del usuario a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla de edición o redirect a error
     */
    @GetMapping("/{id}")
    public String goToEditUserForm(@PathVariable(name = "id", required = true) Long id, Model model){
        try{
            setMenuAttribute(model);
            UserDTO user = this.userService.findById(id);
            model.addAttribute("user", user);
            return "usuarios/form";
        }catch(Exception e){
            model.addAttribute("message", "Ocurrió un error al buscar el usuario a editar");
            model.addAttribute("code", 500);
            return "redirect:/users/list";
        }
    }
    

    /**
     * Actualiza un usuario existente.
     * POST /users/update/{id}
     * @param id ID del usuario a actualizar
     * @param _user UserDTO con los datos actualizados
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado o página de error
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable(name = "id", required = true) Long id, @ModelAttribute UserDTO _user, RedirectAttributes redirectAttributes){
        try{
            setMenuAttribute(redirectAttributes);
            _user.setId(id);
            Long result = this.userService.save(_user);
            if(Objects.equals(result, _user.getId())){
                redirectAttributes.addFlashAttribute("message", "Usuario actualizado exitosamente");
                redirectAttributes.addFlashAttribute("code", 200);
                return "redirect:/users/list";
            }
            throw new RuntimeException("Ocurrió un error al actualizar el usuario");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("code", 500);
        }
        return "redirect:/users/form";
    }

    /**
     * Elimina un usuario por su ID.
     * POST /users/delete
     * @param id ID del usuario a eliminar
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam(name = "id", required = true) Long id, RedirectAttributes redirectAttributes){
        try{
            setMenuAttribute(redirectAttributes);
            this.userService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Usuario eliminado exitosamente");
            redirectAttributes.addFlashAttribute("code", 200);
        }catch(Exception e){
            // Verificar si es un error de constraint violation (clave externa)
            if(e.getCause() != null && e.getCause().getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                redirectAttributes.addFlashAttribute("message", "No se puede eliminar el usuario porque tiene registros asociados");
                redirectAttributes.addFlashAttribute("code", 400);
            } else {
                redirectAttributes.addFlashAttribute("message", e.getMessage());
                redirectAttributes.addFlashAttribute("code", 500);
            }
        }
        return "redirect:/users/list";
    }

    private void setMenuAttribute(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("menu","usuarios");
    }
    
    private void setMenuAttribute(Model model) {
        model.addAttribute("menu","usuarios");
    }
    
}
