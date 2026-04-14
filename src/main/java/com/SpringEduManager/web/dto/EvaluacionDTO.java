package com.SpringEduManager.web.dto;

import java.sql.Date;

import com.SpringEduManager.common.validation.OnCreate;
import com.SpringEduManager.common.validation.OnUpdate;
import com.SpringEduManager.web.entities.EstudianteCurso;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public class EvaluacionDTO {
    
    @Null(groups = OnCreate.class, message = "El ID no debe ser proporcionado al crear")
    @NotNull(groups = OnUpdate.class, message = "El ID es obligatorio para actualizar")
    private Long id;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La nota es obligatoria")
    @Min(value = 0, message = "La nota no puede ser negativa")
    @Max(value = 10, message = "La nota no puede ser mayor a 10")
    private Double nota;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "La fecha es obligatoria")
    private Date fecha;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "El estudiante es obligatorio")
    private EstudianteCurso estudianteCurso;

    public EvaluacionDTO() {
    }

    public EvaluacionDTO(Long id, Double nota, Date fecha) {
        this.id = id;
        this.nota = nota;
        this.fecha = fecha;
        this.estudianteCurso = null;
    }

    public EvaluacionDTO(Long id, Double nota, Date fecha, EstudianteCurso estudianteCurso) {
        this.id = id;
        this.nota = nota;
        this.fecha = fecha;
        this.estudianteCurso = estudianteCurso;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNota() {
        return this.nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Date getFecha() {
        return this.fecha;
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
