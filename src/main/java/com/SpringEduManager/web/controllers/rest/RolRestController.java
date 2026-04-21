package com.SpringEduManager.web.controllers.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.SpringEduManager.web.dto.RolDTO;
import com.SpringEduManager.web.services.roles.RolService;

@Deprecated
public class RolRestController {
    
    @Autowired
    private RolService rolService;
    
    @GetMapping("/api/roles")
    public Map<String, List<RolDTO>> getAll(){
        try{
            return Map.of("data", rolService.findAll());
        }catch(Exception e){
            return Map.of("error", List.of());
        }
    }
}
