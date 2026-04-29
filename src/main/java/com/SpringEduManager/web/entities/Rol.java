package com.SpringEduManager.web.entities;

import com.SpringEduManager.web.enums.RolesEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="authority", length=50, nullable=false)
    @Enumerated(EnumType.STRING)
    private RolesEnum authority;

    public Rol(){}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Rol(RolesEnum authority) {
        this.authority = authority;
    }
    
    public Rol(Long id, RolesEnum authority) {
        this.id = id;
        this.authority = authority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RolesEnum getAuthority() {
        return authority;
    }

    public void setAuthority(RolesEnum authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Rol rol = (Rol) o;
        return Objects.equals(id, rol.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Rol {id=" + id + ", authority=" + authority + "}";
    }
    
}
