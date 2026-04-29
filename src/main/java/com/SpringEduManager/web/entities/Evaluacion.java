package com.SpringEduManager.web.entities;

import java.sql.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    public Evaluacion(Double nota, Date fecha, EstudianteCurso estudianteCurso) {
        this.id = null;
        this.nota = nota;
        this.fecha = fecha;
        this.estudianteCurso = estudianteCurso;
    }
    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Evaluacion that = (Evaluacion) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Evaluacion {id=" + id + 
                ", nota=" + nota + 
                ", fecha=" + fecha + 
                ", estudianteCurso_id=" + (estudianteCurso != null ? estudianteCurso.getId() : "null") + 
                "}";
    }

}
