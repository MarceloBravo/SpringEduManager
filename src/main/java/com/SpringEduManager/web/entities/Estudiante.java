package com.SpringEduManager.web.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name="estudiantes")
public class Estudiante extends Persona {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "estudiante_cursos",
        joinColumns = {@JoinColumn(name = "estudiante_id")},
        inverseJoinColumns = {@JoinColumn(name = "curso_id")}
    )
    private Set<Curso> cursos = new HashSet<>();

    public Estudiante(){}
    
    // Getters and Setters
    public Set<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }
}
