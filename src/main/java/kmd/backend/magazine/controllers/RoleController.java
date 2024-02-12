package kmd.backend.magazine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.models.Role;
import kmd.backend.magazine.services.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @GetMapping()
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/add")
    public Role addRole(@RequestBody Role Role){
        return roleService.saveRole(Role);
    } 

}
