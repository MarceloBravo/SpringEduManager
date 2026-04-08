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
     * GET /users/list/{filtro}
     * @param filtro Filtro opcional para buscar por nombre (case insensitive)
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla Thymeleaf
     */
    @GetMapping("/list/{filtro}")
    public String getAll(@PathVariable(name = "filtro", required = false) String filtro, Model model){
        try{
            List<UserDTO> users = null;
            if(filtro != null && !filtro.isEmpty()){
                users = userService.getAll(filtro);
            }else{
                users = userService.getAll();
            }
            model.addAttribute("users", users);
            model.addAttribute("filtro", filtro);
            return "users/list";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al buscar el listado de registros");
            return "redirect:/error";
        }

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
            model.addAttribute("user", new UserDTO());
            return "users/new";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al cargar el formulario");
            return "redirect:/error";
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
    public String saveNewUser(@ModelAttribute UserDTO user, Model model){
        try{
            Long id = this.userService.save(user);
            if(id != null){
                model.addAttribute("message", "Usuario creado exitosamente");
                return "redirect: /users/list";
            }
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al crear el usuario");
        }
        return "redirect:/error";
    }

    /**
     * Muestra el formulario para editar un usuario existente.
     * GET /users/{id}
     * @param id ID del usuario a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla de edición o redirect a error
     */
    @GetMapping("/{id}")
    public String goToEditUserForm(@PathVariable(required = true) Long id, Model model){
        try{
            UserDTO user = this.userService.findById(id);
            model.addAttribute("user", user);
            return "users/edit";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al buscar el usuario a editar");
            return "redirect:/error";
        }
    }    
    

    /**
     * Actualiza un usuario existente.
     * POST /users/update/{id}
     * @param id ID del usuario a actualizar
     * @param _user UserDTO con los datos actualizados
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable(name = "id", required = true) Long id, @ModelAttribute UserDTO _user, Model model){
        try{
            _user.setId(id);
            Long result = this.userService.save(_user);
            if(Objects.equals(result, _user.getId())){
                model.addAttribute("user", _user);
                model.addAttribute("message", "Usuario actualizado exitosamente");
                return "redirect: /users/list";
            }
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al actualizar el usuario");
        }
        return "redirect: /error";
    }

    /**
     * Elimina un usuario por su ID.
     * POST /users/delete/{id}
     * @param id ID del usuario a eliminar
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al listado u página de error
     */
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id", required = true) Long id, Model model){
        try{
            this.userService.delete(id);
            model.addAttribute("message", "Usuario eliminado exitosamente");
            return "redirect: /users/list";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al eliminar el usuario");
        }
        return "redirect: /error";
    }


}
