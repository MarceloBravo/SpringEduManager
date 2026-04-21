package com.SpringEduManager.web.dto;

import com.SpringEduManager.common.validation.OnCreate;
import com.SpringEduManager.common.validation.OnUpdate;
import com.SpringEduManager.web.entities.Curso;
import com.SpringEduManager.web.entities.Usuario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public class EstudianteCursoDTO {
    
    @Null(groups = OnCreate.class, message = "El ID no debe ser proporcionado al crear")
    @NotNull(groups = OnUpdate.class, message = "El ID es obligatorio para actualizar")
    private Long id;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El estudiante es obligatorio")
    private Usuario estudiante;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El curso es obligatorio")
    private Curso curso;
    
    public EstudianteCursoDTO(){}

    public EstudianteCursoDTO(Long id, Usuario estudiante, Curso curso) {
        this.id = id;
        this.estudiante = estudiante;
        this.curso = curso;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }   

    public Usuario getEstudiante() {
        return estudiante;
    }
    
    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }
    
    public Curso getCurso() {
        return curso;
    }
    
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
