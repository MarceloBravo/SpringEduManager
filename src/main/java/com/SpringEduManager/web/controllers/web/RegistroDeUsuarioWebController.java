package com.SpringEduManager.web.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.services.usuarios.UserService;

/**
 * Controlador web para el registro de nuevos usuarios en el sistema.
 * Proporciona endpoints para mostrar el formulario de registro y procesar el registro.
 * Este controlador está siendo reemplazado por AuthController para mejor consistencia.
 * 
 * @deprecated Usar {@link AuthController} para nuevas implementaciones
 */
@Controller
@RequestMapping("/register")
@Deprecated
public class RegistroDeUsuarioWebController {
    
    /**
     * Servicio para la gestión de usuarios.
     */
    @Autowired
    private UserService userService;

    /**
     * Muestra el formulario de registro de nuevos usuarios.
     * GET /register
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla del formulario de registro
     */
    @GetMapping
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    /**
     * Procesa el formulario de registro y crea un nuevo usuario.
     * POST /register
     * @param userDTO DTO con los datos del nuevo usuario
     * @return Redirección al login si es exitoso, o a error si falla
     */
    @PostMapping
    public String register(UserDTO userDTO) {
        try{
            userService.save(userDTO);
            return "redirect:/login";
        }catch(Exception e){
            return "redirect:/error";
        }
        
    }
}
