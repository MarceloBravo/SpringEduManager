package com.SpringEduManager.web.dto;

import java.sql.Date;

import com.SpringEduManager.common.validation.OnCreate;
import com.SpringEduManager.common.validation.OnUpdate;
import com.SpringEduManager.web.entities.EstudianteCurso;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public class EvaluacionRequestDTO {

    @Null(groups = OnCreate.class, message = "ID no válido para crear el registro")
    @NotNull(groups = OnUpdate.class, message = "El ID es obligatorio para actualizar")
    Long id;
    
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La nota es obligatoria")
    Double nota;
    
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha es obligatoria")
    Date fecha;
    
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El estudiante curso es obligatorio")
    EstudianteCurso estudianteCurso;

    public EvaluacionRequestDTO() {
    }

    public EvaluacionRequestDTO(Long id, Double nota, Date fecha, EstudianteCurso estudianteCurso) {
        this.id = id;
        this.nota = nota;
        this.fecha = fecha;
        this.estudianteCurso = estudianteCurso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstudianteCurso getEstudianteCurso() {
        return this.estudianteCurso;
    }

    public void setEstudianteCurso(EstudianteCurso estudianteCurso) {
        this.estudianteCurso = estudianteCurso;
    }
}
