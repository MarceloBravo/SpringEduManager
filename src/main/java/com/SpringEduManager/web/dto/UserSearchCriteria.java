package com.SpringEduManager.web.dto;

import com.SpringEduManager.web.enums.RolesEnum;

/**
 * DTO para encapsular criterios de búsqueda avanzada
 */
public class UserSearchCriteria {
    private String nombre;
    private String apellido;
    private String email;
    private RolesEnum role;
    private String searchTerm; // Para búsqueda global
    private boolean exactMatch = false; // true=exacto, false=like
    private String sortField = "nombre";
    
    // Constructores
    public UserSearchCriteria() {}
    
    public UserSearchCriteria(String nombre, String apellido, String email, RolesEnum role) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.role = role;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public RolesEnum getRole() {
        return role;
    }
    
    public void setRole(RolesEnum role) {
        this.role = role;
    }
    
    public String getSearchTerm() {
        return searchTerm;
    }
    
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    
    public boolean isExactMatch() {
        return exactMatch;
    }
    
    public void setExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }
    
    public String getSortField() {
        return sortField;
    }
    
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
    
    // Métodos utilitarios
    public boolean hasAnyFilter() {
        return (nombre != null && !nombre.trim().isEmpty()) ||
               (apellido != null && !apellido.trim().isEmpty()) ||
               (email != null && !email.trim().isEmpty()) ||
               (role != null) ||
               (searchTerm != null && !searchTerm.trim().isEmpty());
    }
    
    public void clearFilters() {
        this.nombre = null;
        this.apellido = null;
        this.email = null;
        this.role = null;
        this.searchTerm = null;
    }
}
