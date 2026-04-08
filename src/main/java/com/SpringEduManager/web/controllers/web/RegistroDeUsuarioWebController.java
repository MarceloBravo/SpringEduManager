package com.SpringEduManager.web.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.services.usuarios.UserService;

@Controller
@RequestMapping("/register")
public class RegistroDeUsuarioWebController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

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
