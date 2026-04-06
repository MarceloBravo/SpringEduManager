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

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.services.usuarios.UserService;

@Controller
@RequestMapping("/users")
public class UsuariosWebController {

    @Autowired
    private UserService userService;
    
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

    @GetMapping("/{id}")
    public String goToEditUserForm(@PathVariable Long id, Model model){
        try{
            UserDTO user = this.userService.findById(id);
            model.addAttribute("user", user);
            return "users/edit";
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al buscar el usuario a editar");
            return "redirect:/error";
        }
    }    
    

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable(name = "id", required = true) Long id, @ModelAttribute UserDTO _user, Model model){
        try{
            _user.setId(id);
            Long result = this.userService.save(_user);
            if(result == _user.getId()){
                model.addAttribute("user", _user);
                model.addAttribute("message", "Usuario actualizado exitosamente");
                return "redirect: /users/list";
            }
        }catch(Exception e){
            model.addAttribute("error", "Ocurrió un error al actualizar el usuario");
        }
        return "redirect: /error";
    }

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
