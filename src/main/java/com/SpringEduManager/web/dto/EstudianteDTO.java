package com.SpringEduManager.web.dto;

import com.SpringEduManager.common.validation.OnCreate;
import com.SpringEduManager.common.validation.OnUpdate;
import com.SpringEduManager.common.validation.ValidaEmail;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public class EstudianteDTO {
    
    @Null(groups = OnCreate.class, message = "El ID no debe ser proporcionado al crear")
    @NotNull(groups = OnUpdate.class, message = "El ID es obligatorio para actualizar")
    private Long id;
    
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El nombre es obligatorio")
    @Size(max = 100, groups = {OnCreate.class, OnUpdate.class}, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El apellido es obligatorio")
    @Size(max = 100, groups = {OnCreate.class, OnUpdate.class}, message = "El apellido no puede exceder 100 caracteres")
    private String apellido;
    
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "El email es obligatorio")
    @Size(max = 150, groups = {OnCreate.class, OnUpdate.class}, message = "El email no puede exceder 150 caracteres")
    @ValidaEmail(groups = {OnCreate.class, OnUpdate.class})
    private String email;
    
    private List<CursoDTO> cursos;
    
    // Constructor vacío
    public EstudianteDTO() {}
    
    // Constructor completo
    public EstudianteDTO(Long id, String nombre, String apellido, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
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

    public List<CursoDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoDTO> cursos) {
        this.cursos = cursos;
    }
}
