package com.SpringEduManager.web.entities;

import com.SpringEduManager.web.enums.RolesEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario extends Persona {
    @Column(name="password", length = 100, nullable = false)
    private String password;
    @Column(name="role", nullable = false)
    private RolesEnum role;

    public Usuario(){        
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolesEnum getRole() {
        return role;
    }

    public void setRole(RolesEnum role) {
        this.role = role;
    }
    
}

