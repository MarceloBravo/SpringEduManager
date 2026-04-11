package com.SpringEduManager.web.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.services.usuarios.UserServiceWithPagination;

/**
 * Controller de ejemplo que muestra cómo usar paginación
 */
@Controller
@RequestMapping("/users-paged")
public class UsuariosWebControllerWithPagination {

    @Autowired
    private UserServiceWithPagination userServiceWithPagination;

    /**
     * Lista usuarios con paginación básica
     * URL: /users-paged/list?page=0&size=10&sort=nombre&dir=asc
     */
    @GetMapping("/list")
    public String listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model) {
        
        Page<UserDTO> userPage = userServiceWithPagination.getAllUsers(page, size, sort, dir);
        
        model.addAttribute("users", userPage.getContent()); // Lista de usuarios de la página actual
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sort);
        model.addAttribute("sortDir", dir);
        model.addAttribute("reverseSortDir", dir.equals("asc") ? "desc" : "asc");
        model.addAttribute("menu", "usuarios");
        
        return "usuarios/list-paged";
    }

    /**
     * Busca usuarios por nombre con paginación
     * URL: /users-paged/search?nombre=juan&page=0&size=5
     */
    @GetMapping("/search")
    public String searchUsers(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        
        Page<UserDTO> userPage = userServiceWithPagination.searchByName(nombre, page, size);
        
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("searchTerm", nombre);
        model.addAttribute("menu", "usuarios");
        
        return "usuarios/list-paged";
    }

    /**
     * Navegación a página específica
     * URL: /users-paged/page/2
     */
    @GetMapping("/page/{pageNumber}")
    public String showPage(@PathVariable int pageNumber, Model model) {
        return listUsers(pageNumber, 10, "nombre", "asc", model);
    }

    /**
     * Cambiar tamaño de página
     * URL: /users-paged/size/25
     */
    @GetMapping("/size/{pageSize}")
    public String changePageSize(@PathVariable int pageSize, Model model) {
        return listUsers(0, pageSize, "nombre", "asc", model);
    }
}
