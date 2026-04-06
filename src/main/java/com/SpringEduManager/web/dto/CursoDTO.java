package com.SpringEduManager.web.dto;

import com.SpringEduManager.common.validation.OnCreate;
import com.SpringEduManager.common.validation.OnUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public class CursoDTO {
    
    @Null(groups = OnCreate.class, message = "El ID no debe ser proporcionado al crear")
    @NotNull(groups = OnUpdate.class, message = "El ID es obligatorio para actualizar")
    private Long id;
    
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El nombre del curso es obligatorio")
    @Size(max = 255, groups = {OnCreate.class, OnUpdate.class}, message = "El nombre no puede exceder 255 caracteres")
    private String nombre;
    
    @Size(max = 255, groups = {OnCreate.class, OnUpdate.class}, message = "La descripción no puede exceder 255 caracteres")
    private String descripcion;
    
    // Constructor vacío
    public CursoDTO() {}
    
    // Constructor completo
    public CursoDTO(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
