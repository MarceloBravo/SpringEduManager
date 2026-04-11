package com.SpringEduManager.web.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.dto.UserSearchCriteria;
import com.SpringEduManager.web.enums.RolesEnum;
import com.SpringEduManager.web.services.usuarios.UserServiceWithPagination;

/**
 * Controller para búsqueda avanzada con múltiples filtros
 */
@Controller
@RequestMapping("/users-search")
public class AdvancedSearchController {

    @Autowired
    private UserServiceWithPagination userServiceWithPagination;

    /**
     * Muestra el formulario de búsqueda avanzada
     */
    @GetMapping("/advanced")
    public String showAdvancedSearchForm(Model model) {
        model.addAttribute("criteria", new UserSearchCriteria());
        model.addAttribute("roles", RolesEnum.values());
        model.addAttribute("menu", "usuarios");
        return "usuarios/advanced-search";
    }

    /**
     * Procesa la búsqueda avanzada
     * URL: /users-search/results?nombre=juan&apellido=perez&email=@gmail.com&role=USER&exactMatch=true&page=0&size=10
     */
    @GetMapping("/results")
    public String advancedSearchResults(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) RolesEnum role,
            @RequestParam(defaultValue = "false") boolean exactMatch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sort,
            Model model) {
        
        // Crear criterios de búsqueda
        UserSearchCriteria criteria = new UserSearchCriteria(nombre, apellido, email, role);
        criteria.setExactMatch(exactMatch);
        criteria.setSortField(sort);
        
        // Ejecutar búsqueda
        Page<UserDTO> userPage = userServiceWithPagination.advancedSearch(criteria, page, size);
        
        // Agregar datos al modelo
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("criteria", criteria);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("roles", RolesEnum.values());
        model.addAttribute("menu", "usuarios");
        
        return "usuarios/search-results";
    }

    /**
     * Búsqueda simple en múltiples campos (búsqueda global)
     * URL: /users-search/global?q=juan&page=0&size=10
     */
    @GetMapping("/global")
    public String globalSearch(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<UserDTO> userPage = userServiceWithPagination.searchInAllFields(q, page, size);
        
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("searchTerm", q);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("menu", "usuarios");
        
        return "usuarios/global-search";
    }

    /**
     * Búsqueda con filtros combinados vía POST
     */
    @PostMapping("/filter")
    public String filterUsers(@ModelAttribute UserSearchCriteria criteria, Model model) {
        // Redirigir a GET para mantener URL limpia
        return "redirect:/users-search/results?" + buildQueryString(criteria, 0, 10);
    }

    /**
     * Construye query string a partir de criterios
     */
    private String buildQueryString(UserSearchCriteria criteria, int page, int size) {
        StringBuilder sb = new StringBuilder();
        
        if (criteria.getNombre() != null && !criteria.getNombre().isEmpty()) {
            sb.append("nombre=").append(criteria.getNombre()).append("&");
        }
        if (criteria.getApellido() != null && !criteria.getApellido().isEmpty()) {
            sb.append("apellido=").append(criteria.getApellido()).append("&");
        }
        if (criteria.getEmail() != null && !criteria.getEmail().isEmpty()) {
            sb.append("email=").append(criteria.getEmail()).append("&");
        }
        if (criteria.getRole() != null) {
            sb.append("role=").append(criteria.getRole()).append("&");
        }
        
        sb.append("exactMatch=").append(criteria.isExactMatch()).append("&");
        sb.append("page=").append(page).append("&");
        sb.append("size=").append(size);
        
        return sb.toString();
    }
}
