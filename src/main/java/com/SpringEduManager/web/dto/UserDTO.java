package com.SpringEduManager.web.dto;

import com.SpringEduManager.web.dto.validation.OnCreate;
import com.SpringEduManager.web.dto.validation.OnUpdate;
import com.SpringEduManager.web.enums.RolesEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long id;

    private String nombre;

    private String apellido;

    private String email;

    @JsonProperty("password")
    @NotBlank(groups = OnCreate.class, message = "La contraseña es obligatoria")
    @Size(min = 8, groups = OnCreate.class)
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
    
    @JsonIgnore
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
