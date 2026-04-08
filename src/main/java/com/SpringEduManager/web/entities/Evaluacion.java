package com.SpringEduManager.web.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="evaluaciones")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nota")
    private Double nota;

    @Column(name="fecha")
    private Date fecha;
    
    @ManyToOne
    @JoinColumn(name="estudiante_curso_id", nullable = false)
    private EstudianteCurso estudianteCurso;

    public Evaluacion(){}

    public Evaluacion(Long id, Double nota, Date fecha, EstudianteCurso estudianteCurso) {
        this.id = id;
        this.nota = nota;
        this.fecha = fecha;
        this.estudianteCurso = estudianteCurso;
    }

    // Getters and Setters
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
        return estudianteCurso;
    }

    public void setEstudianteCurso(EstudianteCurso estudianteCurso) {
        this.estudianteCurso = estudianteCurso;
    }

}
