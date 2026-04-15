package com.SpringEduManager.web.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.services.usuarios.UserService;

/**
 * Controlador para la gestión de autenticación y registro de usuarios.
 * Maneja el login (gestionado por Spring Security) y el registro de nuevos usuarios.
 * Utiliza UserService para la creación de usuarios con rol por defecto USER.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    /**
     * Servicio de usuarios para la gestión de registros.
     */
    @Autowired
    private UserService service;

    /**
     * Muestra el formulario de login.
     * GET /auth/login
     * Spring Security maneja la autenticación automáticamente a través de la configuración.
     * @return Nombre de la plantilla del formulario de login
     */
    @GetMapping("/login")
    public String returnFromLogin(
        @RequestParam(value = "logout", required = false) String logout, 
        @RequestParam(value = "error", required = false) String error,
        Model model
    ) {
        
        if (logout != null) {
            model.addAttribute("message", "Has cerrado sesión correctamente");
            model.addAttribute("code", 200);
        }
        
        if (error != null) {
            model.addAttribute("message", "Credenciales incorrectas");
            model.addAttribute("code", 400);
        }
        return "login";
    }

    /**
     * Muestra el formulario de registro de nuevos usuarios.
     * GET /auth/register
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla del formulario de registro
     */
    @GetMapping("/register")
    public String returnFromRegister(Model model){
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    /**
     * Procesa el formulario de registro y crea un nuevo usuario.
     * POST /auth/register
     * Asigna automáticamente el rol USER al nuevo usuario.
     * @param userDTO DTO con los datos del nuevo usuario
     * @param model Modelo para pasar mensajes a la vista
     * @return Redirect al login si es exitoso, o a register con error
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDTO userDTO, Model model){
        try{
            this.service.register(userDTO);
            model.addAttribute("success", "Usuario registrado correctamente");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "redirect:/register";
        }
        
    }

    /**
     * Maneja el cierre de sesión del usuario.
     * POST /auth/logout
     * Spring Security maneja el logout automáticamente a través de la configuración.
     * @return Redirect al formulario de login
     */
    @PostMapping("/logout")
    public String logout(){
        return "redirect:/login";
    }
    
}