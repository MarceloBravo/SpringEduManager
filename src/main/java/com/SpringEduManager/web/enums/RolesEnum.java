package com.SpringEduManager.web.enums;

public enum RolesEnum {
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_ADMIN_STUDENT"),
    STUDENT(3, "ROLE_STUDENT");
    
    private final int role;
    private final String authority;
    
    RolesEnum(int role, String authority) {
        this.role = role;
        this.authority = authority;
    }
    
    public int getRole() {
        return role;
    }
    
    public String getAuthority() {
        return authority;
    }
    
    // Método para obtener el enum a partir del valor numérico
    public static RolesEnum fromRole(int role) {
        for (RolesEnum r : RolesEnum.values()) {
            if (r.role == role) {
                return r;
            }
        }
        throw new IllegalArgumentException("Rol no válido: " + role);
    }
    
    // Método para obtener el enum a partir del nombre (para compatibilidad)
    public static RolesEnum fromString(String roleName) {
        try {
            return RolesEnum.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Rol no válido: " + roleName);
        }
    }
    
    // Sobrescribir name() para que Spring Security funcione correctamente
    @Override
    public String toString() {
        return this.name();
    }

}
