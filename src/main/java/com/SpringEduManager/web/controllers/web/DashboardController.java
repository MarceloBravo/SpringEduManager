package com.SpringEduManager.web.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador web para la página principal del dashboard.
 * Gestiona la vista de inicio del sistema después de la autenticación.
 * Proporciona acceso a la página principal con información contextual del usuario.
 */
@Controller
public class DashboardController {

    /**
     * Muestra la página principal del dashboard.
     * GET /home
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla del dashboard
     */
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("menu","home");
        return "dashboard";
    }
}
