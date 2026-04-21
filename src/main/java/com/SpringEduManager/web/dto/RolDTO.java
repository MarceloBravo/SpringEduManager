package com.SpringEduManager.web.dto;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.NotNull;

import com.SpringEduManager.common.validation.OnCreate;
import com.SpringEduManager.common.validation.OnUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RolDTO {
    
    @Null(groups = OnCreate.class, message = "El ID no debe ser proporcionado al crear")
    @NotNull(groups = OnUpdate.class, message = "El ID es obligatorio para actualizar")
    private Long id;
    
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El nombre es obligatorio")
    @Size(max = 50, groups = {OnCreate.class, OnUpdate.class}, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;
    
    public RolDTO() {
    }
    
    public RolDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
