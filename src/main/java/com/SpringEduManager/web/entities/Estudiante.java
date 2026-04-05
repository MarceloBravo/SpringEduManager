package com.SpringEduManager.web.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="estudiantes")
public class Estudiante extends Persona {

    public Estudiante(){}
    
}
