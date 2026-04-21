package com.SpringEduManager.web.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name="cursos")  
public class Curso{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre", length=255)
    private String nombre;
    @Column(name="descripcion", length=255)
    private String descripcion;

    @ManyToMany(mappedBy = "cursos", fetch = FetchType.LAZY)
    private Set<Usuario> estudiantes = new HashSet<>();

    public Curso(){}
    
    // Getters and Setters
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

    public Set<Usuario> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Set<Usuario> estudiantes) {
        this.estudiantes = estudiantes;
    }
}