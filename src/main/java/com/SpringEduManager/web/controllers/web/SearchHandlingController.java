package com.SpringEduManager.web.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.services.usuarios.UserService;

/**
 * Controller que demuestra diferentes formas de manejar búsquedas vacías
 */
@Controller
@RequestMapping("/search-demo")
public class SearchHandlingController {

    @Autowired
    private UserService userService;

    /**
     * Ejemplo 1: Búsqueda con manejo de término vacío
     * Si searchTerm está vacío, muestra todos los registros
     */
    @GetMapping("/example1")
    public String searchExample1(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<UserDTO> userPage = userService.searchInAllFields(searchTerm, page, size, "nombre");
        
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("searchType", "Con manejo de vacío");
        
        return "demo/search-results";
    }

    /**
     * Ejemplo 2: Búsqueda que retorna página vacía si no hay término
     */
    @GetMapping("/example2")
    public String searchExample2(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        // Validar que haya término de búsqueda
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            // Retornar página vacía con mensaje
            model.addAttribute("message", "Por favor ingrese un término de búsqueda");
            model.addAttribute("searchType", "Requiere término obligatorio");
            return "demo/empty-search";
        }
        
        Page<UserDTO> userPage = userService.searchInAllFields(searchTerm, page, size, "nombre");
        
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("searchType", "Búsqueda obligatoria");
        
        return "demo/search-results";
    }

    /**
     * Ejemplo 3: Búsqueda con valor por defecto
     * Si searchTerm está vacío, usa un valor por defecto
     */
    @GetMapping("/example3")
    public String searchExample3(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        // Si searchTerm está vacío, buscar usuarios con rol USER por defecto
        String effectiveSearchTerm = (searchTerm == null || searchTerm.trim().isEmpty()) ? 
            "default-search-term" : searchTerm.trim();
        
        Page<UserDTO> userPage = userService.searchInAllFields(effectiveSearchTerm, page, size, "nombre");
        
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("searchTerm", effectiveSearchTerm);
        model.addAttribute("searchType", "Con valor por defecto");
        model.addAttribute("usedDefault", searchTerm == null || searchTerm.trim().isEmpty());
        
        return "demo/search-results";
    }

    /**
     * Ejemplo 4: Búsqueda con múltiples fallbacks
     * Intenta diferentes estrategias según el contenido
     */
    /*
    @GetMapping("/example4")
    public String searchExample4(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<UserDTO> userPage;
        String searchType;
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            // Opción A: Retornar todos
            userPage = userService.getAllPaginated(page, size, "nombre");
            searchType = "Todos los registros (sin filtro)";
        } else if (searchTerm.length() < 3) {
            // Opción B: Búsqueda muy corta, retornar todos
            userPage = userService.getAllPaginated(page, size, "nombre");
            searchType = "Término muy corto (< 3 chars) - Mostrando todos";
        } else {
            // Opción C: Búsqueda normal
            userPage = userService.searchInAllFields(searchTerm, page, size, "nombre");
            searchType = "Búsqueda normal con: " + searchTerm;
        }
        
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("searchType", searchType);
        
        return "demo/search-results";
    }
}*/
}
