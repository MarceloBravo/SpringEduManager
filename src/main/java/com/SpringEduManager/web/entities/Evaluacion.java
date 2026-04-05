package com.SpringEduManager.web.entities;

import java.sql.Date;
import java.text.DecimalFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="evaluaciones")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nota")
    private DecimalFormat nota;
    @Column(name="fecha")
    private Date fecha;
    @Column(name="estudiante_id")
    private Long estudianteId;
    @Column(name="curso_id")
    private Long cursoId;

    public Evaluacion(){}

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public DecimalFormat getNota() {
        return nota;
    }
    public void setNota(DecimalFormat nota) {
        this.nota = nota;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Long getEstudianteId() {
        return estudianteId;
    }
    public void setEstudianteId(Long estudianteId) {
        this.estudianteId = estudianteId;
    }
    public Long getCursoId() {
        return cursoId;
    }
    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

}
