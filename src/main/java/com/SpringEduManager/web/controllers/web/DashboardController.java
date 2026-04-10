package com.SpringEduManager.web.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("menu","home");
        return "dashboard";
    }
}
