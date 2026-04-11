package com.SpringEduManager.web.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.SpringEduManager.web.dto.UserDTO;
import com.SpringEduManager.web.enums.RolesEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Demostración de la estructura de un objeto Page<UserDTO>
 */
public class PageStructureDemo {

    public static void main(String[] args) {
        // Crear datos de ejemplo
        List<UserDTO> allUsers = createSampleUsers();
        
        // Crear Pageable: página 1 (0-based), tamaño 5, ordenado por nombre
        Pageable pageable = PageRequest.of(1, 5, Sort.by("nombre").ascending());
        
        // Simular una página de resultados
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allUsers.size());
        List<UserDTO> pageContent = allUsers.subList(start, end);
        
        // Crear objeto Page<UserDTO>
        Page<UserDTO> userPage = new PageImpl<>(pageContent, pageable, allUsers.size());
        
        // ===== MOSTRAR ESTRUCTURA COMPLETA =====
        System.out.println("📋 ESTRUCTURA DE Page<UserDTO>");
        System.out.println("=====================================");
        
        // === MÉTODOS DE Slice<T> ===
        System.out.println("\n📄 INFORMACIÓN DE PÁGINA ACTUAL (Slice):");
        System.out.println("getContent(): " + userPage.getContent());
        System.out.println("getNumber(): " + userPage.getNumber() + " (página actual, 0-based)");
        System.out.println("getSize(): " + userPage.getSize() + " (tamaño de página)");
        System.out.println("getNumberOfElements(): " + userPage.getNumberOfElements() + " (elementos en esta página)");
        System.out.println("hasContent(): " + userPage.hasContent());
        System.out.println("isFirst(): " + userPage.isFirst());
        System.out.println("isLast(): " + userPage.isLast());
        System.out.println("hasNext(): " + userPage.hasNext());
        System.out.println("hasPrevious(): " + userPage.hasPrevious());
        
        // === MÉTODOS DE Page<T> ===
        System.out.println("\n📊 INFORMACIÓN TOTAL (Page):");
        System.out.println("getTotalElements(): " + userPage.getTotalElements() + " (total de usuarios)");
        System.out.println("getTotalPages(): " + userPage.getTotalPages() + " (total de páginas)");
        System.out.println("getPageable(): " + userPage.getPageable());
        
        // === INFORMACIÓN DE PAGINACIÓN ===
        System.out.println("\n🔢 DETALLES DE PAGINACIÓN:");
        System.out.println("Página actual: " + (userPage.getNumber() + 1) + " de " + userPage.getTotalPages());
        System.out.println("Elementos mostrados: " + userPage.getNumberOfElements() + " de " + userPage.getTotalElements());
        System.out.println("Offset (inicio): " + pageable.getOffset());
        System.out.println("Sort: " + pageable.getSort());
        
        // === NAVEGACIÓN ===
        System.out.println("\n🧭 NAVEGACIÓN:");
        if (userPage.hasPrevious()) {
            System.out.println("Página anterior: " + userPage.previousPageable());
        }
        if (userPage.hasNext()) {
            System.out.println("Página siguiente: " + userPage.nextPageable());
        }
        
        // === CONVERSIÓN ===
        System.out.println("\n🔄 CONVERSIÓN CON .map():");
        Page<String> nombresPage = userPage.map(UserDTO::getNombre);
        System.out.println("Page<String> (solo nombres): " + nombresPage.getContent());
        System.out.println("Misma paginación: página " + (nombresPage.getNumber() + 1) + " de " + nombresPage.getTotalPages());
    }
    
    private static List<UserDTO> createSampleUsers() {
        List<UserDTO> users = new ArrayList<>();
        
        users.add(new UserDTO(1L, "Ana", "García", "ana@email.com", "123", RolesEnum.USER));
        users.add(new UserDTO(2L, "Beatriz", "López", "bea@email.com", "123", RolesEnum.USER));
        users.add(new UserDTO(3L, "Carlos", "Martínez", "carlos@email.com", "123", RolesEnum.ADMIN));
        users.add(new UserDTO(4L, "Diana", "Rodríguez", "diana@email.com", "123", RolesEnum.USER));
        users.add(new UserDTO(5L, "Eduardo", "Sánchez", "edu@email.com", "123", RolesEnum.TEACHER));
        users.add(new UserDTO(6L, "Fernanda", "Pérez", "fer@email.com", "123", RolesEnum.USER));
        users.add(new UserDTO(7L, "Gabriel", "Gómez", "gabriel@email.com", "123", RolesEnum.USER));
        users.add(new UserDTO(8L, "Hilda", "Díaz", "hilda@email.com", "123", RolesEnum.USER));
        users.add(new UserDTO(9L, "Ignacio", "Torres", "ignacio@email.com", "123", RolesEnum.ADMIN));
        users.add(new UserDTO(10L, "Juana", "Ruiz", "juana@email.com", "123", RolesEnum.USER));
        users.add(new UserDTO(11L, "Kevin", "Hernández", "kevin@email.com", "123", RolesEnum.USER));
        users.add(new UserDTO(12L, "Laura", "Jiménez", "laura@email.com", "123", RolesEnum.USER));
        
        return users;
    }
}
