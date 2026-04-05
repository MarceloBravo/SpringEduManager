package com.SpringEduManager.web.dto;

import com.SpringEduManager.web.enums.RolesEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    @JsonIgnore    
    private String password;
    private RolesEnum role;
    
    public UserDTO() {
    }

    public UserDTO(Long id, String nombre, String apellido, String email, String password, RolesEnum role) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public RolesEnum getRole() {
        return role;
    }
    
    public void setRole(RolesEnum role) {
        this.role = role;
    }
}
