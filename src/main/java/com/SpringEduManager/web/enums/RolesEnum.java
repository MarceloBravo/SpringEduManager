package com.SpringEduManager.web.enums;

public enum RolesEnum {
    ADMIN(  "ADMIN"),
    TEACHER("TEACHER"),
    STUDENT("STUDENT");
    
    private final String role;
    
    RolesEnum(String role) {
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }
}
